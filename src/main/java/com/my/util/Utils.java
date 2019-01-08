package com.my.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
public class Utils {
	 /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }
    
    public static <T> Set<T> jsonToSet(String json,Class<T> clazz) throws InstantiationException, IllegalAccessException{
		 JSONArray data=JSONArray.fromObject(json);
			Set<T> sets=new HashSet<T>();
			for (int i = 0; i < data.size(); i++) {
				JSONObject jsons = (JSONObject) data.get(i);
				Object role=clazz.newInstance();
				role=JSONObject.toBean(jsons, clazz);
				sets.add((T)role);
			}
			return sets;
	 }
	 
	public static <T> List<T> jsonToList(String json, Class<T> clazz) throws InstantiationException, IllegalAccessException {
		List<T> lists = new ArrayList<T>();
		if(json==null||json.equals("null")){
			return null;
		}else if(json.trim().equals("")){
			return lists;
		}
		Gson gson = new Gson();
		// lists = gson.fromJson(json, new TypeToken<List<T>>(){}.getType());
		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
		for (final JsonElement elem : array) {
			lists.add(gson.fromJson(elem, clazz));
		}
		return lists;
	}
	
	public static <T> List<T> jsonToList(String json, Class<T> clazz,String... par) throws InstantiationException, IllegalAccessException, Exception {
		List<T> lists = new ArrayList<T>();
		if(json==null||json.equals("null")){
			return null;
		}else if(json.trim().equals("")){
			return lists;
		}
		Gson gson = new Gson();
		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
		for (final JsonElement elem : array) {
			Object obj =gson.fromJson(elem, clazz);
			for (int j = 0; j < par.length; j++) {
				BeanUtils.setProperty(obj, par[j].toLowerCase(), format(BeanUtils.getProperty(obj, par[j].toLowerCase())));
			}
			lists.add((T) obj);
		}
		return lists;
	}
	
	public static String format(String s){
		DecimalFormat df  = new DecimalFormat("###,###,###,###,###,###,###,##0.00");
		return df.format(Double.parseDouble(s==null?"0":s.contains(",")?s.replaceAll(",", ""):s));
	}
	
	public static String format(double s){
		DecimalFormat df  = new DecimalFormat("###,###,###,###,###,###,###,##0.00");
		return df.format(s);
	}
}
