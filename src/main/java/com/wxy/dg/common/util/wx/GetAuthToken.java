package com.wxy.dg.common.util.wx;

import com.wxy.dg.common.model.AuthToken;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;


/**
 * Created by wxy on 2017/1/2.
 */
@Component
public class GetAuthToken {

    /**
     * 获取网页授权凭证
     */
    public AuthToken getAuthToken(String code){

        AuthToken vo = null;
        String auth_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+WeixinConstants.APPID+"&secret="+WeixinConstants.APP_SECRECT+"&code="+code+"&grant_type=authorization_code";

            // 获取网页授权凭证
            JSONObject jsonObject = HttpClientUtil.httpsRequest(auth_url, "GET", null);

            if (null != jsonObject) {
                try {
                    vo = new AuthToken();
                    vo.setAccess_token(jsonObject.getString("access_token"));
                    vo.setOpenid(jsonObject.getString("openid"));
                    vo.setScope(jsonObject.getString("scope"));
                } catch (Exception e) {
                    vo = null;
                    int errorCode = jsonObject.getInt("errcode");
                    String errorMsg = jsonObject.getString("errmsg");
                    e.printStackTrace();
                }
            }
        return vo;
    }
}
