package com.mama.dandy.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class MD5Util {

	public static String getMd5(String input){
	byte[] out = null;
	try {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(input.getBytes("UTF-8"));
		out = md.digest();
	} catch (NoSuchAlgorithmException e) {
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}
	return toHexString(out);
	}
	
	private static String toHexString(byte[] out) {
		StringBuffer buf = new StringBuffer();
		for(byte b:out){
			buf.append(String.format("%02X", b));
		}
		return buf.toString();
	}
	
	public static void main(String args[]){
		String data="1";
		String timeStamp=new Date().getTime()/1000+"";
		String key="1849CB30471FFE4F668250BC78ED69B6";
		System.out.println(key+data+timeStamp);
		String token =getMd5(key+data+timeStamp).toLowerCase();
		System.out.println(timeStamp);
		System.out.println(token);
	}
}
