package com.wxy.dg.common.util.wx;

import com.wxy.dg.common.model.Consumer;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * 网页授权获取微信用户信息
 * Created by wxy on 2017/1/2.
 */
@Component
public class GetWxUserInfo {

    public Consumer getUserInfo(String accessToken, String openId) {
        String user_url = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
        Consumer consumer = null;
        // 通过网页授权获取用户信息
        JSONObject jsonObject = HttpClientUtil.httpsRequest(user_url, "GET", null);

        if (null != jsonObject) {
            try {
                consumer = new Consumer();
                consumer.setOpenId(openId);
                consumer.setName(jsonObject.getString("nickname"));
                consumer.setImg(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                consumer = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                e.printStackTrace();
            }
        }
        return consumer;
    }
}
