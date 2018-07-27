package cn.virde.nymph.util;

import java.util.Date;


import cn.virde.nymph.Nym;
import cn.virde.nymph.text.TextOut;
/**
 * 升级功能
 * 需要增加日志的输出等级控制 
 * @author Virde
 * @date 2018年6月4日 下午6:21:02
 */
public class Log {
	
	// 模式切换，日志文件输出模式 和 控制台输出模式 以及 双输出模式
	public static final String MODE_FILEOUT = "fileOut" ;
	public static final String MODE_CONSOLE = "console" ;
	public static final String MODE_CONSOLE_FILE = "console_and_file" ;
	private static volatile String mode = MODE_CONSOLE ;
	// 日志文件输出地址
	private static volatile String logFile ;
	
	// 利用接口支持用户自定义的日志输出方式
	private static volatile LogPrint logPrint ;
	
	// 日志输出级别
	public static final int LEVEL_INFO = 1;
	public static final int LEVEL_ALERT = 2 ;
	private static volatile int level = LEVEL_INFO;
	
	// 设置是否为开发者模式，开发者模式下debug日志会输出
	private static boolean isDev = true ;
	
	public synchronized static void info(String msg){
		if(level < LEVEL_ALERT ) return ;
        String traceInfo = getTraceInfo(new Throwable().getStackTrace());
		String time = Nym.time.toString(new Date(), "hh:mm:ss");
		
		syso(time + " " + msg );
		syso(traceInfo);
		
	}
	
	public synchronized static void info(String msg,Exception e){
		if(level < LEVEL_ALERT ) return ;
        String traceInfo = getTraceInfo(new Throwable().getStackTrace());
		String time = Nym.time.toString(new Date(), "hh:mm:ss");
		
		syso(time + " " + msg + "。 异常信息："+e.getMessage());
		syso(traceInfo);
		
	}

	public synchronized static void info(String msg,String alert){
		if(level < LEVEL_ALERT ) return ;
        String traceInfo = getTraceInfo(new Throwable().getStackTrace());
		String time = Nym.time.toString(new Date(), "hh:mm:ss");
		
		syso(time + " " + msg);
		syso("异常提示信息："+alert);
		syso(traceInfo);
		
	}
	
	public synchronized static void alert(String msg) {
		String time = Nym.time.toString(new Date(), "hh:mm:ss");
		syso(time + " " + msg );
	}
	public synchronized static void debug(String msg) {
		if(isDev) 
			alert(msg) ;
	}

	public synchronized static void error(String msg,Exception e){
		
        String traceInfo = getTraceInfo(new Throwable().getStackTrace());
		String time = Nym.time.toString(new Date(), "hh:mm:ss");
		syso("==================错误提示=====================");
		syso(time + " " + msg + "。 异常信息："+e.getMessage());
		syso(traceInfo);
		syso("============================================");
		
	}

	public synchronized static void error(String msg,String alert){
		
        String traceInfo = getTraceInfo(new Throwable().getStackTrace());
		String time = Nym.time.toString(new Date(), "hh:mm:ss");

		syso("========错误提示======错误提示=======错误提示=======");
		syso(time + " " + msg);
		syso("异常提示信息："+alert);
		syso(traceInfo);
		syso("============================================");
	}

	public synchronized static void setFileOutMode(String file){
		Log.logFile = file ;
		Log.mode = MODE_FILEOUT ;
	}
	public synchronized static void setDoubleMode(String file){
		Log.logFile = file ;
		Log.mode = MODE_CONSOLE_FILE ;
	}
	
	private synchronized static String getTraceInfo(StackTraceElement[] stacks){
        StringBuffer sb = new StringBuffer();     
        sb.append("class: " ).append(stacks[1].getClassName()).append("; method: ").append(stacks[1].getMethodName()).append("; number: ").append(stacks[1].getLineNumber());
        return sb.toString();
	}	
	private synchronized static void syso(String text){
		switch(mode){
		case MODE_CONSOLE :
			println(text);
			break;
		case MODE_FILEOUT :
			new TextOut(logFile).putln(text);
			break;
		case MODE_CONSOLE_FILE :
			println(text);
			new TextOut(logFile).putln(text);
			break;
		default : ;
		}
	}
	
	// 定制日志的打印方式
	public static void setLogPrint(LogPrint print) {
		logPrint = print ;
	}
	public static void println(String text) {
		if(logPrint == null)
			System.out.println(text);
		else 
			logPrint.println(text);
	}

	public static int getLevel() {
		return level;
	}

	public static void setLevel(int level) {
		Log.level = level;
	}
	
	public static void isDev(boolean isDev) {
		Log.isDev = isDev ;
	}

}