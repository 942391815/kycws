package com.wxy.dg.modules.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sun.misc.BASE64Decoder;

import com.wxy.dg.common.base.BaseAction;
import com.wxy.dg.common.config.Global;
import com.wxy.dg.common.constant.Constant;
import com.wxy.dg.common.util.DateUtils;
import com.wxy.dg.common.util.Page;
import com.wxy.dg.modules.model.GroupContiner;
import com.wxy.dg.modules.model.Photo;
import com.wxy.dg.modules.model.User;
import com.wxy.dg.modules.service.PhotoService;

/**
 * 照片管理
 */
@Controller
public class PhotoAction extends BaseAction {

	@Autowired
	private PhotoService photoService;

	@ModelAttribute
	public Photo get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id) && !"0".equals(id)) {
			return photoService.getPhoto(Integer.parseInt(id));
		} else {
			return new Photo();
		}
	}

	@RequestMapping("/photo")
	public String list(@RequestParam Map<String, Object> paramMap, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String destpage = ObjectUtils.toString(paramMap.get("destpage"));
		if(StringUtils.isNotBlank(destpage)) {
			request.setAttribute("pageNo", destpage);
		}
		Page<Photo> page = photoService.getPhotos(new Page<Photo>(request, response, 12), paramMap);
		model.addAttribute("page", page);
		model.addAttribute("paramMap", paramMap);
		return "modules/photoList";
	}

	@RequestMapping("/photo/manage")
	public String form() {
		return "modules/photoManage";
	}

	@RequestMapping("/photo/save")
	public String save(Photo photo, RedirectAttributes redirectAttributes) {
		addMessage(redirectAttributes, "保存照片成功");
		photoService.save(photo);
		return "redirect:/photo";
	}

	@RequestMapping("/photo/delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		photoService.deleteById(Integer.valueOf(id));
		addMessage(redirectAttributes, "删除照片成功");
		return "redirect:/photo";
	}
	
	@RequestMapping("/mobile/uploadPic")
	public void uploadPic(Integer userId, String category, String imgStr,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String shotplace,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		BASE64Decoder decoder = new BASE64Decoder();
		String[] imgStrArray = imgStr.split(",");
		for(String str : imgStrArray) {
			byte[] bytes = decoder.decodeBuffer(str);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
			String uploadPath = request.getSession().getServletContext().getRealPath("../") + "\\photo\\";
			FileOutputStream out = new FileOutputStream(uploadPath + File.separator + fileName);
			out.write(bytes);
			out.flush();
			out.close();
			
			Photo photo = new Photo();
			photo.setCategory(category);
			photo.setDescription(description);
			photo.setName(fileName);
			photo.setShotplace(shotplace);
			// 存储缩略图
			Thumbnails.of(uploadPath + fileName).size(200, 200).outputQuality(1.0f).
				toFile(uploadPath + Global.getLitImgFolder() + File.separator + Constant.PREFIX_LIT + fileName);

			User uploader = new User();
			uploader.setId(userId);
			photo.setUploader(uploader);
			photo.setUploadtime(new Date());
			photo.setDel_flag(Constant.NotDeleteFlg);
			photoService.save(photo);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("response", "uploadPic");
		map.put("state", "0");
		toJsonString(response, map);
	}

	@RequestMapping("/mobile/searchPhoto")
	public void searchPhoto(@RequestParam(value = "user_id") String userId,
			HttpServletResponse response) {
		List<Photo> photos = photoService.getPhotos(Integer.valueOf(userId));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("response", "searchPhoto");
		map.put("photos", getPhotos(photos));
		toJsonString(response, map);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List getPhotos(List<Photo> list) {
		List datalist = new ArrayList();
		List<GroupContiner> grouplist = group(list);
		String imgpath = Global.getPhotoUrl();
		String litpath = imgpath + Global.getLitImgFolder() + "/";
		for (GroupContiner gc : grouplist) {
			Map map = new HashMap();
			map.put("date", gc.getGroupID());
			List gList = new ArrayList();
			for (Photo photo : gc.getPhotoList()) {
				Map photoMap = new HashMap();
				photoMap.put("name", photo.getName());
				photoMap.put("description", photo.getDescription());
				photoMap.put("category", photo.getCategory());
				photoMap.put("url", imgpath + photo.getName());
				photoMap.put("liturl", litpath + Constant.PREFIX_LIT + photo.getName());
				photoMap.put("uploadtime", DateUtils.formatDateTime(photo.getUploadtime()));
				gList.add(photoMap);
			}
			map.put("list", gList);
			datalist.add(map);
		}
		return datalist;
	}
	
	/**
	 * 按照日期对照片分组
	 */
	private List<GroupContiner> group(List<Photo> target) {
		List<GroupContiner> result = new ArrayList<GroupContiner>();
		for (int i = 0; i < target.size();) {
			Photo photo = target.get(i);
			target.remove(photo);
			GroupContiner gc = new GroupContiner();
			gc.setGroupID(DateUtils.formatDate(photo.getUploadtime(), "yyyy-MM"));
			gc.getPhotoList().add(photo);
			for (int j = 0; j < target.size();) {
				Photo _photo = target.get(j);
				// 相同，分组，并加入到组容器集合
				if (DateUtils.formatDateTime(_photo.getUploadtime()).startsWith(gc.getGroupID())) {
					gc.getPhotoList().add(_photo);
					target.remove(_photo);
				} else {
					j++;
				}
			}
			result.add(gc);
		}
		return result;
	}
}