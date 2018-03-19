package com.thesys.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.thesys.base.core.util.ValidateUtil;
import com.thesys.base.core.util.WebPath;

public class Configuration {
	
	/**
	 * 鍙傛暟鏂囦欢鍚?
	 */
	private final static String PARAMETERFILENAME="parameter.properties";

	
	/**
	 * 
	 */
	private Properties propertie;
	/**
	 * 
	 */
	private FileInputStream inputFile;
	/**
	 * 
	 */
	private FileOutputStream outputFile;
	
	/**
	 * 鍒濆鍖朇onfiguration绫?
	 */
	public Configuration() {
		propertie = new Properties();
	}	
	/**
	 * 鍒濆鍖朇onfiguration绫?
	 * 
	 * @param filePath
	 *            瑕佽鍙栫殑閰嶇疆鏂囦欢鐨勮矾寰?鍚嶇О
	 */
	public Configuration(String filePath) {
		propertie = new Properties();
		try {
			inputFile = new FileInputStream(filePath);
			propertie.load(inputFile);
			inputFile.close();
		} catch (FileNotFoundException ex) {
			System.out.println("璇诲彇灞炴?鏂囦欢--->澶辫触锛? 鍘熷洜锛氭枃浠惰矾寰勯敊璇垨鑰呮枃浠朵笉瀛樺湪");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("瑁呰浇鏂囦欢--->澶辫触!");
			ex.printStackTrace();
		}
	}// end ReadConfigInfo(...)

	/** */
	/**
	 * 閲嶈浇鍑芥暟锛屽緱鍒発ey鐨勫?
	 * 
	 * @param key
	 *            鍙栧緱鍏跺?鐨勯敭
	 * @return key鐨勫?
	 */
	public String getValue(String key) {
		if (propertie.containsKey(key)) {
			String value = propertie.getProperty(key);// 寰楀埌鏌愪竴灞炴?鐨勫?
			return value;
		} else
			return "";
	}// end getValue(...)

	/** */
	/**
	 * 閲嶈浇鍑芥暟锛屽緱鍒発ey鐨勫?
	 * 
	 * @param fileName
	 *            properties鏂囦欢鐨勮矾寰?鏂囦欢鍚?
	 * @param key
	 *            鍙栧緱鍏跺?鐨勯敭
	 * @return key鐨勫?
	 */
	public String getValue(String fileName, String key) {
		try {
			String value = "";
			inputFile = new FileInputStream(fileName);
			propertie.load(inputFile);
			inputFile.close();
			if (propertie.containsKey(key)) {
				value = propertie.getProperty(key);
				return value;
			} else
				return value;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}// end getValue(...)

	/** */
	/**
	 * 娓呴櫎properties鏂囦欢涓墍鏈夌殑key鍜屽叾鍊?
	 */
	public void clear() {
		propertie.clear();
	}// end clear();

	/** */
	/**
	 * 鏀瑰彉鎴栨坊鍔犱竴涓猭ey鐨勫?锛屽綋key瀛樺湪浜巔roperties鏂囦欢涓椂璇ey鐨勫?琚玽alue鎵?唬鏇匡紝 褰搆ey涓嶅瓨鍦ㄦ椂锛岃key鐨勫?鏄痸alue
	 * 
	 * @param key
	 *            瑕佸瓨鍏ョ殑閿?
	 * @param value
	 *            瑕佸瓨鍏ョ殑鍊?
	 */
	public void setValue(String key, String value) {
		propertie.setProperty(key, value);
	}// end setValue(...)

	/** */
	/**
	 * 灏嗘洿鏀瑰悗鐨勬枃浠舵暟鎹瓨鍏ユ寚瀹氱殑鏂囦欢涓紝璇ユ枃浠跺彲浠ヤ簨鍏堜笉瀛樺湪銆?
	 * 
	 * @param fileName
	 *            鏂囦欢璺緞+鏂囦欢鍚嶇О
	 * @param description
	 *            瀵硅鏂囦欢鐨勬弿杩?
	 */
	public void saveFile(String fileName, String description){
        try {
            outputFile = new FileOutputStream(fileName);
            propertie.store(outputFile, description);
            outputFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }	
	
	

	
	
	public static String getParameterValue(String key){
		Configuration rc = new Configuration(getAbsolutePathWithClass()+PARAMETERFILENAME);//鐩稿璺緞        
		String str = rc.getValue(key);//浠ヤ笅璇诲彇properties鏂囦欢鐨勫?
		return str;
	}
	
	public static Integer getParameterValueInt(String key){
		Configuration rc = new Configuration(getAbsolutePathWithClass()+PARAMETERFILENAME);//鐩稿璺緞        
		String str = rc.getValue(key);//浠ヤ笅璇诲彇properties鏂囦欢鐨勫?
		if (ValidateUtil.isEmpty(str)) {
			return null;
		}
		return Integer.parseInt(str);
	}
	
	public static String getAbsolutePathWithClass() {
		String path = WebPath.getAbsolutePathWithClass(Configuration.class).getPath();
		String classPath = Configuration.class.getName().replace(".", "/").replace(Configuration.class.getSimpleName(), "");;
		path = path.replace(classPath, "");
		return path;
	}
	
	
	
	/**
	 * 鑾峰彇WEB-INF璺緞
	 * @return
	 */
	public static String getWebRootPath() {
		String path = Configuration.getAbsolutePathWithClass();
		path = path.substring(0, path.indexOf("WEB-INF"));
		return path;
	}
}
