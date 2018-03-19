package com.thesys.core.sphinx.crawler;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.google.gson.Gson;
import com.thesys.core.dto.ResultDataDto;

public class HttpClientUtil {

	/**
	 * 超时设置
	 */
	private final static int TIMEOUT = 30 * 1000;
	
	/**
	 * Get请求返回html字符串
	 */
	public static String getResponseBodyAsString(String url) {
		return getResponseBodyAsString(url, null);
	}
	
	public static String getResponseBodyAsString(String url, String charset) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout( TIMEOUT );
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, TIMEOUT);
		try {
			int stateCode = httpClient.executeMethod(getMethod);
			if (200 == stateCode) {
				if (null == charset) {
					return getMethod.getResponseBodyAsString();
				}
				if (null == charset || "ISO-8859-1".equalsIgnoreCase(charset)) {
					charset = "GB2312";
				}
				return new String(getMethod.getResponseBody(), charset);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}
	
	public static ResultDataDto postJsonAsResultDataDto(String url, String json) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout( TIMEOUT );
		PostMethod postMethod = new PostMethod(url);
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, TIMEOUT);
		try {
			RequestEntity requestEntity = new StringRequestEntity(json, "application/json","UTF-8");
			postMethod.setRequestEntity(requestEntity);
			@SuppressWarnings("unused")
			int stateCode = httpClient.executeMethod(postMethod);
			String rejson = new String(postMethod.getResponseBody(), "UTF-8");
			ResultDataDto resultDataDto = new Gson().fromJson(rejson, ResultDataDto.class);
			return resultDataDto;
		} catch (Exception e) {
			return new ResultDataDto(ResultDataDto.CODE_ERROR_OTHER, e.getMessage());
		}		
	}
	/**
	 * Get请求返回 ResultDataDto
	 */
	public static ResultDataDto getAsResultDataDto(String url) {
		try {
			String jsonstr = getResponseBodyAsString(url, null);
			if (null == jsonstr) {
				return new ResultDataDto(ResultDataDto.CODE_ERROR_WEB, "网络错误");
			}
			ResultDataDto resultDataDto = new Gson().fromJson(jsonstr, ResultDataDto.class);
			return resultDataDto;
		} catch (Exception e) {
			return new ResultDataDto(ResultDataDto.CODE_ERROR_OTHER, e.getMessage());
		}
		
	}
	
}
