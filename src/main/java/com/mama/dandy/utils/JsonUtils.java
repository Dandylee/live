package com.mama.dandy.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;

public class JsonUtils {
	
	// 自动将驼峰转下划线 orderId -> order_id
	private static SerializeFilter f = new MySerializeFilter();
	/**
	 * 自定义序列化-下划线转驼峰
	 * 
	 */
	static class MySerializeFilter implements NameFilter {
		@Override
		public String process(Object object, String name, Object value) {
			return StringHelper.underlineToCamel(name);
		}
	}
	/**
	 * 驼峰转下划线
	 * @param javaObject
	 * @return
	 */
	public static String toJSONString(Object javaObject) {
		return JSON.toJSONString(javaObject, f);
	}
	
	// to json
	public static JSONObject toJSON(String text) {
		return JSON.parseObject(text);
	}

	public static JSONObject toJSON(Object javaObject) {
		return (JSONObject) JSON.toJSON(javaObject);
	}

	public static JSONArray toJsonArray(List<?> objs) {
		return (JSONArray) JSON.toJSON(objs);
	}

	public static <T> T toBean(String json, TypeReference<T> reference) {
		return JSON.parseObject(json, reference);
	}

	// to bean
	public static <T> T toBean(JSONObject json, Class<T> bean) {
		return JSON.toJavaObject(json, bean);
	}

	public static <T> T toBean(String json, Class<T> bean) {
		return JSON.parseObject(json, bean);
	}

	public static <T> T date2Bean(String data, Class<T> bean) {
		return JSON.parseObject(data, bean);
	}

	public static <T> List<T> parseArray(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz);
	}

	// to string
	public static String toString(Object javaObject) {
		return JSON.toJSONString(javaObject);
	}

	public static <T> List<T> toBeanList(String data, Class<T> bean) {
		return JSON.parseArray(data, bean);
	}
	
}
