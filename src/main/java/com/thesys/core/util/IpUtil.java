package com.thesys.core.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class IpUtil {

	/**
	 * 获取访问者IP 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)， 如果还不存在则调用Request .getRemoteAddr()。
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}


	
	/**
	 * 参考：http://ip.taobao.com/instructions.php
	 */
	class TaobaoIpDto {
		
		private Integer code;
		
		private TaobaoIpDataDto data;

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public TaobaoIpDataDto getData() {
			return data;
		}

		public void setData(TaobaoIpDataDto data) {
			this.data = data;
		}
	}
	
	class TaobaoIpDataDto {
		private String ip;
		private String country;
		private String area;
		private String region;
		private String city;
		private String isp;
		private String country_id;
		private String area_id;
		private String region_id;
		private String city_id;
		private String county_id;
		private String isp_id;
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getIsp() {
			return isp;
		}
		public void setIsp(String isp) {
			this.isp = isp;
		}
		public String getCountry_id() {
			return country_id;
		}
		public void setCountry_id(String countryId) {
			country_id = countryId;
		}
		public String getArea_id() {
			return area_id;
		}
		public void setArea_id(String areaId) {
			area_id = areaId;
		}
		public String getRegion_id() {
			return region_id;
		}
		public void setRegion_id(String regionId) {
			region_id = regionId;
		}
		public String getCity_id() {
			return city_id;
		}
		public void setCity_id(String cityId) {
			city_id = cityId;
		}
		public String getCounty_id() {
			return county_id;
		}
		public void setCounty_id(String countyId) {
			county_id = countyId;
		}
		public String getIsp_id() {
			return isp_id;
		}
		public void setIsp_id(String ispId) {
			isp_id = ispId;
		}
	}
}
