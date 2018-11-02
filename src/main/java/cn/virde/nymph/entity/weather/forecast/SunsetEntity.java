package cn.virde.nymph.entity.weather.forecast;

import cn.virde.nymph.entity.base.BaseEntity;;

public class SunsetEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7936243692802769829L;
	
	private String time;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public SunsetEntity(String time) {
		super();
		this.time = time;
	}

	public SunsetEntity() {
		super();
	}
	
	

}
