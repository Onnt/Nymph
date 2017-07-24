package cn.virde.nymph.net.tool;

import cn.virde.nymph.entity.base.LocationEntity;

public class PositionTool {
	
	/**
	 * 获取指定IP的经纬度
	 * @param ip
	 * @return
	 */
	public LocationEntity getLocationByIp(String ip){
		return new Position(ip).getLocation() ;
	}
	/**
	 * 获取程序IP的所在地址的经纬度
	 * @return
	 */
	public LocationEntity getLocation(){
		return new Position().getLocation() ;
	}
	/**
	 * 获取指定IP的地址
	 * @param ip
	 * @return
	 */
	public String getAddressByIp(String ip){
		return new Position(ip).getAddress() ;
	}
	/**
	 * 获取程序IP所在的地址
	 * @return
	 */
	public String getAddress(){
		return new Position().getAddress() ;
	}
	
	/**
	 * 经纬度转地址
	 * @param location
	 * @return
	 */
	public String locationToAddress(LocationEntity location){
		return new GeocodingTool().locationToAddress(location);
	}
	/**
	 * 地址转经纬度
	 * @param address
	 * @return
	 */
	public LocationEntity addressToLocation(String address){
		return new GeocodingTool().addressToLocation(address);
	}
}
