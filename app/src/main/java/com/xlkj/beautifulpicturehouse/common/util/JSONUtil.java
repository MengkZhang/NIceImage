package com.xlkj.beautifulpicturehouse.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Administrator on 2017/9/26 0026.
 * json直接转换成javabean
 * WXUserInfo data = (WXUserInfo) JSONUtil.toObject(response, WXUserInfo.class);
 */

public class JSONUtil {

    public static String getJsonString(Object object) throws Exception {
        return JacksonMapper.getInstance().writeValueAsString(object);
    }

    public static Object toObject(String jsonString, Class cls) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, cls);
    }
}
