package com.hwy.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.hwy.ui.Face;

public class JsonPost {

	public static String post(String urlAddr, String value) {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		//String str = null;
		StringBuffer response = new StringBuffer();
		try {
			url = new URL(urlAddr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			//conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Type","application/json");
			conn.getOutputStream().write(value.getBytes());
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			int code = conn.getResponseCode();
			if (code == 200) {
				// DataInputStream in = new
				// DataInputStream(conn.getInputStream());
				// int len = in.available();
				// byte[] by = new byte[len];
				// in.readFully(by);
				// str = new String(by);
				// in.close();
				
				is = conn.getInputStream();
				BufferedReader rd = new BufferedReader(new InputStreamReader(is, "utf-8"));
				/*int i;
				while((i=rd.read())>0){
					response.append((char)i);
				}*/
				String line;
				while ((line = rd.readLine()) != null) {
					response.append(line);
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			Face.showMessageDialog("错误",e.getMessage(),0);
		} finally {
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					//e.printStackTrace();
					Face.showMessageDialog("错误",e.getMessage(),0);
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		//return str;
		return response.toString();
	}
	public static void postNotReturn(String urlAddr, String value) {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		try {
			url = new URL(urlAddr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			//conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Type","application/json");
			conn.getOutputStream().write(value.getBytes());
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			int code = conn.getResponseCode();
			if (code == 200) {
				is = conn.getInputStream();
				BufferedReader rd = new BufferedReader(new InputStreamReader(is, "utf-8"));
				String line;
				StringBuffer sb = new StringBuffer();
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}
				System.out.println(sb.toString());
				//Face.result_area.append(sb.toString());
			}
		} catch (Exception e) {
			//e.printStackTrace();
			Face.showMessageDialog("错误",e.getStackTrace().toString(),0);
		} finally {
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					//e.printStackTrace();
					Face.showMessageDialog("错误",e.getStackTrace().toString(),0);
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	public static void main(String[] args) {
		//String val = "{\"ada\":\"9300\",\"language\":\"en\"}";
		//System.out.println(post("http://localhost:8080/ahk_mshop/m_dist!login.action",val));
		String val = "{\"updateTime\":\"2014-06-15 00:00:00\",\"ada\":9300}";
		//{"updateTime":"2014-06-15 00:00:00","ada":9300}
		//System.out.println(post("http://119.145.89.144:9080/ahk_mshop_macau/m_order!syncProducts.action",val));
		postNotReturn("http://119.145.89.144:9080/ahk_mshop_macau/m_order!syncProducts.action",val);
	}
}
