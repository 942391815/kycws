package com.wxy.dg.modules.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wxy.dg.common.util.UserUtils;
import com.wxy.dg.modules.security.SystemAuthorizingRealm;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wxy.dg.common.base.BaseAction;
import com.wxy.dg.common.constant.Constant;
import com.wxy.dg.common.util.CommonUtil;
import com.wxy.dg.common.util.Page;
import com.wxy.dg.common.util.excel.ReadExcelUtil;
import com.wxy.dg.modules.model.Organization;
import com.wxy.dg.modules.model.User;
import com.wxy.dg.modules.service.OrgService;
import com.wxy.dg.modules.service.UserService;

/**
 * 人员管理
 */
@Controller
@RequestMapping(value = "/user")
public class UserAction extends BaseAction {

	@Autowired
	private UserService userService;
	@Autowired
	private OrgService orgService;

	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id) && !"0".equals(id)){
			return userService.getUser(Integer.parseInt(id));
		}else{
			return new User();
		}
	}
	
	@RequestMapping("index")
	public String index() {
		return "modules/userIndex";
	}

	@RequestMapping("")
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<User> page = userService.findUser(new Page<User>(request, response), user);
		model.addAttribute("page", page);
		if(user.getOrganization() != null) {
			model.addAttribute("orgName", user.getOrganization().getName());
		} else {
			model.addAttribute("orgName", orgService.getById(1).getName());
		}
		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
		User loginUser = UserUtils.get(principal.getId());
		model.addAttribute("userType",loginUser.getUser_type());

		return "modules/userList";
	}
	
	@RequestMapping("manage")
	public String form(User user, Model model) {
		if(user.getOrganization() == null || user.getOrganization().getId() == 0) {
			Organization org = orgService.getById(1);
			user.setOrganization(org);
		}
		model.addAttribute("user", user);
		model.addAttribute("orgId", user.getOrganization().getId());
		model.addAttribute("orgName", user.getOrganization().getName());

		User currentUser = UserUtils.get(UserUtils.getPrincipal().getId());
		model.addAttribute("userType",currentUser.getUser_type());
		return "modules/userManage";
	}
	
	@RequestMapping("save")
	public String save(User user, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, user)) {
			return form(user, model);
		}
		user.setDel_flag(Constant.NotDeleteFlg);
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(CommonUtil.entryptPassword(user.getNewPassword()));
		}
		userService.save(user);
		addMessage(redirectAttributes, "保存用户'" + user.getName() + "'成功");
		return "redirect:/user";
	}
	
	@RequestMapping("delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		if (User.isAdmin(id)) {
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		} else {
			userService.deleteById(Integer.valueOf(id));
			addMessage(redirectAttributes, "删除用户成功");
		}
		return "redirect:/user";
	}
	
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
		String fileName = file.getOriginalFilename();
		if(StringUtils.isBlank(fileName)) {
			addMessage(redirectAttributes, "导入用户失败！失败信息：请选择文件!");
		} else if(!fileName.toLowerCase().endsWith("xls")) {
			addMessage(redirectAttributes, "导入用户失败！失败信息：文件格式不对,请导入xls格式的文件!");
		} else {
			String colName = "";
			String errmsg = "";
			int row = 0;
			List<List<String>> contents = ReadExcelUtil.readExcel(file.getInputStream());
			List<User> list = new ArrayList<User>();
			if (contents.size() > 0) {
				try {
					// 判断导入文件中的登录名是否重复
					for (int i = 0; i < contents.size(); i++) {
						List<String> rowContent = contents.get(i);
						String name = rowContent.get(0).trim();
						if (StringUtils.isBlank(name)) {
							row = i + 2;
							colName = "登录名";
							errmsg = "导入文件中的登录名不能为空!";
							throw new Exception();
						}
						// 定位频率
						String frequency = rowContent.get(4).trim();
						if (StringUtils.isBlank(frequency) || "0".equals(frequency)) {
							row = i + 2;
							colName = "定位频率";
							errmsg = "导入文件中的定位频率不能为空且必须大于0!";
							throw new Exception();
						}
						for (int j = i + 1; j < contents.size(); j++) {
							List<String> rowContent2 = contents.get(j);
							String name2 = rowContent2.get(0).trim();
							if (name2.equals(name)) {
								row = j + 2;
								colName = "登录名";
								errmsg = "导入文件中的登录名有重复!";
								throw new Exception();
							}
						}
						row = i + 2;
						User ci = new User();
						for (int col = 0; col < rowContent.size(); col++) {
							switch (col) {
							case 0: // 登录名
								String userName = rowContent.get(col).trim();
								if (userService.getUserByLoginName(userName) != null) {
									colName = "登录名";
									errmsg = "登录名在系统中已存在!";
									throw new Exception();
								}
								ci.setLogin_name(userName);
								break;
							case 1:// 姓名
								ci.setName(rowContent.get(col).trim());
								break;
							case 2:// 手机号码
								String mobile = rowContent.get(col).trim();
								if (!CommonUtil.isMobile(mobile)) {
									colName = "手机号码";
									errmsg = "手机号码格式不对!";
									throw new Exception();
								}
								ci.setPhone(mobile);
								break;
							case 3:// 归属组织
								String orgName = rowContent.get(col).trim();
								Organization org = orgService.getByName(orgName);
								if (org == null) {
									colName = "归属组织";
									errmsg = "归属组织不存在,请在系统里事先录入!";
									throw new Exception();
								}
								ci.setOrganization(org);
								break;
							case 4:// 定位频率(分钟)
								ci.setFrequency(Integer.parseInt(rowContent.get(col).trim()));
								break;
							case 5:// 备注
								ci.setRemark(rowContent.get(col).trim());
								break;
							default:
								break;
							}
						}
						//默认密码123456
						ci.setPassword(CommonUtil.entryptPassword("123456"));
						//默认用户类型(102:普通巡视员)
						ci.setUser_type("102");
						ci.setDel_flag(Constant.NotDeleteFlg);
						list.add(ci);
						userService.save(ci);
					}
					addMessage(redirectAttributes, "已成功导入 " + list.size()+" 条用户信息");
				} catch (Exception e) {
					if (CommonUtil.isNotNull(colName)) {
						addMessage(redirectAttributes, "导入用户失败！文件的第" + row + "行[" + colName
								+ "]列有错误,错误信息:" + errmsg);
					} else {
						e.printStackTrace();
					}
				}
			}
		}
		return "redirect:/user";
	}

	@RequestMapping("checkUser")
	public void checkLoginName(HttpServletResponse response,
			@RequestParam("param") String loginName, @RequestParam("id") String id) {
		if (StringUtils.isNotBlank(loginName)) {
			// 取得当前编辑的登录名
			String currentLoginName = "";
			if (!"0".equals(id)) {
				User userInfo = userService.getUser(Integer.parseInt(id));
				currentLoginName = userInfo.getLogin_name();
			}
			Map<String, String> dataMap = new HashMap<String, String>();
			// 名称改变时才进行校验
			if (loginName.equals(currentLoginName)) {
				dataMap.put("info", "");
				dataMap.put("status", "y");
			} else {
				User user = userService.getUserByLoginName(loginName);
				if (user != null) {
					dataMap.put("info", "登录名已存在");
					dataMap.put("status", "n");
				} else {
					dataMap.put("info", "");
					dataMap.put("status", "y");
				}
			}
			toJsonString(response, dataMap);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("list")
	public void getUsersByOrg(HttpServletResponse response, @RequestParam String orgId) {
		List<User> userList = userService.findUsersByOrg(Integer.valueOf(orgId));
		List list = new ArrayList();
		for(User user:userList) {
			Map map = new HashMap();
			map.put("id", user.getId());
			map.put("name", user.getName());
			list.add(map);
		}
		toJsonString(response, list, "text/plain; charset=utf-8");
	}
	
	@RequiresUser
	@RequestMapping(value = "treeData")
	public void treeData(@RequestParam Integer orgId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Organization org = orgService.getById(orgId);
		List<User> list = org.getUserList();
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", "u_"+e.getId());
			map.put("pId", orgId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		toJsonString(response, mapList);
	}

}