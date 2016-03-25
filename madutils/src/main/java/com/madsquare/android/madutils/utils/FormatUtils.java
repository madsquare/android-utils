package com.madsquare.android.madutils.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.madsquare.android.madutils.MadLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Form check related utility class
 * Created by Ted
 */
public class FormatUtils {

    private static final String TAG = FormatUtils.class.getSimpleName();

    /**
     * 이메일 형식인지 boolean 값 반환
     */
    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /**
     * 유효한 채널 주소인지 정규식 판별
     */
    public static boolean isChannelUrlValid(String url) {
        // String patt = "/([a-z0-9-_.]+)/gi";//수석님이 주신 것
        String patt = "([a-zA-Z0-9-_.]+)";//적용
        Pattern pattern = Pattern.compile(patt);
        Matcher matcher = pattern.matcher(url);

        if (matcher.matches()) {
            MadLog.d(TAG, "isChannelUrlValid : TRUE");
            return true;
        } else {
            MadLog.d(TAG, "isChannelUrlValid : FALSE");
            return false;
        }
    }

    /**
     * Json을 Hashmap으로 변경
     */
    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    /**
     * Hashmap을 Json으로 변경
     */
    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    /**
     * Json을 List로 변경
     */
    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    /**
     * Make to json
     *
     * @param obj vo object
     * @return Json String
     */
    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }

    /**
     * Json Object 를 vo 형태로 만들기
     *
     * @param object json object
     * @param clazz  convert class
     * @return vo object
     */
    public static Object toJsonObject(Object object, Class clazz) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        return gson.fromJson(jsonObject, clazz);
    }

    /**
     * Json String 을 vo 형태로 만들기
     *
     * @param json  json string
     * @param clazz convert class
     * @return vo object
     */
    public static Object toJsonString(String json, Class clazz) {
        Gson gson = new Gson();
        TypeAdapter adapter = gson.getAdapter(clazz);
        try {
            return adapter.fromJson(json);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * UTF-8 로 인코딩된 String 문자열 반환
     */
    public static String getEncodingTerm(String term) {
        String encodingTerm = "";

        if (term != null) {
            try {
                encodingTerm = URLEncoder.encode(term, "UTF-8").replace("+", "%20");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        MadLog.d(TAG, "getEncodingTerm : encodingTerm = " + encodingTerm);
        return encodingTerm;
    }

    /**
     * UTF-8 로 디코딩된 String 문자열 반환
     */
    public static String getDecodingTerm(String term) {
        String decodingTerm = "";

        if (term != null) {
            try {
                decodingTerm = URLDecoder.decode(term, "UTF-8".replace("+", "%20"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        MadLog.d(TAG, "getDecodingTerm : decodingTerm = " + decodingTerm);
        return decodingTerm;
    }

    /**
     * GET 방식으로 서버에 요청을 할 Url을 만들어주는 메소드
     *
     * @param list Map List
     * @return Url address
     */
    public static String getDirectUrlSortParams(Map<String, String> list) {
        StringBuilder sb = new StringBuilder();

        Iterator<String> paramsIterator = list.keySet().iterator();

        for (int i = 0; i < list.size(); i++) {
            String key = paramsIterator.next();
            String value = list.get(key);

            sb.append("&");
            sb.append(key);
            sb.append("=");
            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * GET 방식으로 서버에 요청을 할 Url을 만들어주는 메소드
     *
     * @param list Map List
     * @return Url address
     */
    public static String getDirectUrlParams(Map<String, String> list) {
        StringBuilder sb = new StringBuilder();

        Iterator<String> paramsIterator = list.keySet().iterator();

        for (int i = 0; i < list.size(); i++) {
            String key = paramsIterator.next();
            String value = list.get(key);

            if (i == 0) {
                sb.append("?");
            } else {
                sb.append("&");
            }
            sb.append(key);
            sb.append("=");
            sb.append(value);
        }
        return sb.toString();
    }

}
