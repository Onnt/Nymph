package cn.virde.nymph.net.tool;

import java.io.IOException;

import cn.virde.nymph.util.IPUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.virde.nymph.Nym;
import cn.virde.nymph.config.Config;
import cn.virde.nymph.entity.base.LocationEntity;
import cn.virde.nymph.exception.LocationException;

public class Position {
	
	private JSONObject position ;

	public Position() throws LocationException, IOException{
		setPosition(null);
	}
	
	public Position(String ip) throws LocationException, IOException{
		if(isValiIp(ip)){
			setPosition(ip);
		}else{
			setPosition(null);
		}
		
	}

	public String getAddress() {
		if(position == null) return null ;
		return position.getJSONObject("content").getString("address");
	}

	public LocationEntity getLocation() {
		if(position == null) return null ;

		LocationEntity location = new LocationEntity();
		location.setLat(position.getJSONObject("content").getJSONObject("point").getDoubleValue("y")/100000);
		location.setLng(position.getJSONObject("content").getJSONObject("point").getDoubleValue("x")/100000);

		return location;
	}

	public void setPosition(String ip) throws LocationException, IOException {
		String reqUrl = Config.position.getReqUrl(ip) ;
		String resp = Nym.http.get(reqUrl);
		
		this.position = JSON.parseObject(resp);
		
		if(position.getIntValue("status") != 0){
			throw new LocationException("普通IP定位返回值有误, 请求值：" + reqUrl +",返回值：" + resp);
		}
		
	}
	
	private boolean isValiIp(String ip){
		boolean isVali = true ;
		if(!IPUtils.isValidIP(ip))
			isVali = false ;
		if(ip.startsWith("192.168"))
			isVali = false ;
		if(ip.startsWith("127.0"))
			isVali = false ;
		return isVali ;
	}
}
