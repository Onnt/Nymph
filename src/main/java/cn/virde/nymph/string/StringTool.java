package cn.virde.nymph.string;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.virde.nymph.Nym;

/**
 * 字符串处理工具
 * @author Blacard
 * 
 * 2016年7月11日 下午10:51:59
 */
public class StringTool {
	/**
	 * 在给定的字符串中匹配对应正则表达式，
	 * 所有符合表达式的字符打包至集合中返回
	 * @param str 给定的字符串
	 * @param regEx  正则表达式
	 * @return 返回 
	 */
	public List<String> getStringsByReg(String str,String regEx){
		List<String> list = new ArrayList<String>();
		Matcher m = Pattern.compile(regEx).matcher(str);
		while(m.find()){
			list.add(m.group());
		}
		return list;
	}

	/**
	 * 在给定的字符串中匹配对应正则表达式，
	 * 只返回第一个匹配的结果
	 * @author Blacard
	 * 2017年2月7日 下午5:49:30
	 * @param str 给定的字符串
	 * @param regEx 正则表达式
	 * @return 返回
	 */
	public String getStringByReg(String str,String regEx){
		List<String> list = getStringsByReg(str,regEx);
		if(list==null||list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	/**
	 * 去除字符中的所有空白符
	 * 
	 * @author Blacard
	 * 2017年1月24日 下午2:49:23
	 * @param str 字符串
	 * @return 返回
	 */
    public String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
	
	/**
	 * 获取后缀名
	 * @author Blacard
	 * 2016年9月1日 下午2:38:51
	 * @param str 字符串
	 * @return 返回 后缀名
	 */
	public String getSuffix(String str){
		if(str.contains(".") && str.lastIndexOf(".") < str.length())
			return str.substring(str.lastIndexOf(".")+1, str.length());
		else
			return null;
	}
	/**
	 * 获取文件名
	 * @author Blacard
	 * 2016年9月1日 下午3:05:21
	 * @param str 字符串
	 * @return 返回
	 */
	public String getFileName(String str){
		return str.substring(str.lastIndexOf("/")+1,str.length());
	}
	

	/**
	 * 判断这个字符串是否是IP地址
	 * @author Virde
	 * 2018年1月26日 下午2:03:54
	 * @param str 字符串
	 * @return 返回
	 */
    public boolean isIP(String str){  
        if(str == null || str.length() < 7 || str.length() > 15 || "".equals(str)){  
            return false;  
        }  
        
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";  
        Pattern pat = Pattern.compile(rexp);    
        Matcher mat = pat.matcher(str);    
        boolean ipAddress = mat.find();  
        return ipAddress;  
    }
    
	/**
	 * 返回url中名字为name的参数值。
	 * 如果没有这个参数值或者发生异常，则返回NULL
	 * 
	 * @param url url地址
	 * @param name 名字
	 * @return 返回
	 */
	public String getParam(String url,String name) {
		Map<String,String> map = getParamMap(url);
		Set<String> nameSet = map.keySet();
		for(String n : nameSet) {
			if(n.equals(name)) {
				return map.get(n);
			}
		}
		return null;
	}
	

	/**
	 * 返回pageUrl中携带的参数，存储到map中返回
	 * @param pageUrl 带有参数的url地址
	 * @return 返回 
	 *  如果不包含参数，或者遇到异常则返回空的Map对象
	 * 
	 */
	public Map<String,String> getParamMap(String pageUrl){
		Map<String,String> map = new HashMap<String,String>();
		if(pageUrl.contains("?")&&!pageUrl.endsWith("?")) {
			pageUrl = pageUrl.substring(pageUrl.indexOf("?")+1, pageUrl.length());
		}
		
		if(pageUrl.contains("#")) {
			pageUrl = pageUrl.substring(0,pageUrl.indexOf("#"));
		}
		String[] paramKeyV = pageUrl.split("&");
		for(String keyV : paramKeyV) {
			if(keyV.contains("=")) {
				String[] param = keyV.split("=");
				map.put(param[0], param[1]);
			}else {
				map.put(keyV, "");
			}
		}
		return map ;
	}
	
	/**
	 * 传入链接和参数，拼接返回。
	 * 缺陷：没有验证url的既有参数
	 * @author Blacard
	 * 2017年12月20日 14:25:21
	 * @param url url地址
	 * @param params 参数
	 * @return 返回
	 */
	public String makeUrlWithParams(String url,Map<String,String> params) {
		if(params==null||params.size()==0) return url ;
		
		StringBuffer sb = new StringBuffer();
		for(Map.Entry<String, String> m : params.entrySet()) {
			sb.append(m.getKey()+"="+m.getValue()+"&");
		}
		return url +"?"+ sb.toString().substring(0, sb.length()-1);
	}
	public String makeUrlWithParams(String...args) {
		if(args.length == 0 )
			return null ;
		if(args.length == 1) 
			return args[0] ;
		
		Map<String,String> map = new HashMap<String,String>();
		String key = "" ;
		for(int i = 1 ; i < args.length ; i++) {
			if(i % 2 == 1) {
				key = args[i] ;
			}else {
				map.put(key, args[i]) ;
			}
		}
		return makeUrlWithParams(args[0], map);
	}
	
	/**
	 * 
	 * @author Virde
	 * 2018年6月7日 下午3:04:30
	 * @param url url地址
	 * @param params 参数
	 * @return 返回
	 * @throws InvocationTargetException  异常
	 * @throws IllegalArgumentException  异常
	 * @throws IllegalAccessException  异常
	 */
	public String makeUrlWithParams(String url,Object params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return makeUrlWithParams(url, Nym.clazz.getField(params)) ;
	}
	
	public String getHost(String url) {
		return getStringByReg(url, "(http|ftp|https):\\/\\/([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}");
	}
	
}