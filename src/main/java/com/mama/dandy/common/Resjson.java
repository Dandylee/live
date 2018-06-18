package com.mama.dandy.common;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Resjson {
	
	private String code="0"; //0默认为成功 
	
	private Object data;
	
	private String msg="success";

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String toJsonString(){
		return JSON.toJSONString(this);
	}

	public <T> T getData(Class<T> c) {
		return JSON.toJavaObject((JSONObject)data, c);
	}
	
	public <T> List<T> getDataList(Class<T> c){
		List<T> result=new ArrayList<T>();
		JSONArray jsonArray=(JSONArray)data;
		for(int i=0;i<jsonArray.size();i++){
			T t=JSON.toJavaObject((JSONObject)jsonArray.get(i),c);
			result.add(t);
		}
		return result;
	}
	
	public static <T> List<T> getDataList(JSONArray jsonArray,Class<T> c){
		List<T> result=new ArrayList<T>();
		for(int i=0;i<jsonArray.size();i++){
			T t=JSON.toJavaObject((JSONObject)jsonArray.get(i),c);
			result.add(t);
		}
		return result;
	}
}
