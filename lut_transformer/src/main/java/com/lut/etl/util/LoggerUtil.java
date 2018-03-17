package com.lut.etl.util;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.lut.common.EventLogConstants;
import com.lut.etl.util.IPSeekerExt.RegionInfo;
import com.lut.etl.util.UserAgentUtil.UserAgentInfo;
import com.lut.util.TimeUtil;


/**
 * 处理日志数据的具体工作类
 * 
 * @author gg
 *
 */
public class LoggerUtil {
	//日志对象
	private static final Logger logger = Logger.getLogger(LoggerUtil.class);
	//解析ip地址的对象
	private static IPSeekerExt ipSeekerExt = new IPSeekerExt();
	 /**
     * 处理日志数据logText，返回处理结果map集合<br/>
     * 如果logText没有指定数据格式，那么直接返回empty的集合
     * 
     * @param logText
     * @return
     */
	public static Map<String,String> handleLog(String logText){
		Map<String,String> clientInfo = new HashMap<>();
		if(StringUtils.isNotBlank(logText)){
			String[] splits = logText.split(EventLogConstants.LOG_SEPARTIOR);
			if(splits.length == 4){
				//日志格式为: ip^A服务器时间^Ahost^A请求参数
				clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_IP, splits[0].trim());
				//设置服务器时间
				clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_SERVER_TIME, String.valueOf
						(TimeUtil.parseNginxServerTime2Long(splits[1].trim())));
				int index = splits[3].indexOf("?");
				if(index > -1){
					String requestBody = splits[3].substring(index + 1);//获取请求参数，也就是我们的收集数据
					//处理请求参数
					handleRequestBody(requestBody,clientInfo);
					//处理useAgent
					handleUserAgent(clientInfo);
					//处理ip地址
					handleIp(clientInfo);
				}else{
					//数据格式异常
					clientInfo.clear();
				}
			}
		}
		return clientInfo;
	}
	 /**
     * 处理请求参数
     * 
     * @param requestBody
     * @param clientInfo
     */
	private static void handleRequestBody(String requestBody, Map<String, String> clientInfo) {
		if(StringUtils.isNotBlank(requestBody)){
			String[] requestParams = requestBody.split("&");
			for(String param : requestParams){
				int index = param.indexOf("=");
				if(index < 0){
					logger.warn("无法解析的参数：" + param + "，请求参数为：" + requestBody);
					continue;
				}
				String key = null,value = null;
				try {
					key = param.substring(0,index);
					value = URLDecoder.decode(param.substring(index + 1),"utf-8");
				} catch (Exception e) {
					logger.warn("解码操作出现异常",e);
					continue;
				}
				if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)){
					clientInfo.put(key, value);
				}
			}
			
		}
		
	}
	/**
     * 处理浏览器的userAgent信息
     * 
     * @param clientInfo
     */
	private static void handleUserAgent(Map<String, String> clientInfo) {
		if(clientInfo.containsKey(EventLogConstants.LOG_COLUMN_NAME_USER_AGENT)){
			UserAgentInfo userAgentInfo = UserAgentUtil.analyticUserAgent(clientInfo.get
					(EventLogConstants.LOG_COLUMN_NAME_USER_AGENT));
			if(userAgentInfo != null){
				clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_OS_NAME, userAgentInfo.getOsName());
				clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_OS_VERSION, userAgentInfo.getOsVersion());
				clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_BROWSER_NAME, userAgentInfo.getBrowserName());
				clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_BROWSER_VERSION, userAgentInfo.getBrowserVersion());
			}
		}
		
	}
	/**
	 * 处理ip地址
	 * 
	 * @param clientInfo
	 */
	private static void handleIp(Map<String, String> clientInfo) {
		if(clientInfo.containsKey(EventLogConstants.LOG_COLUMN_NAME_IP)){
			String ip = clientInfo.get(EventLogConstants.LOG_COLUMN_NAME_IP);
			RegionInfo regionInfo = ipSeekerExt.analyticIp(ip);
			if(regionInfo != null){
				clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_COUNTRY, regionInfo.getCountry());
				clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_PROVINCE, regionInfo.getProvince());
				clientInfo.put(EventLogConstants.LOG_COLUMN_NAME_CITY, regionInfo.getCity());
			}
		}
		
	}
	
	
}
