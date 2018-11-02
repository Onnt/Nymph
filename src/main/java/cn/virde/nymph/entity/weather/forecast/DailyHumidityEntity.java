package cn.virde.nymph.entity.weather.forecast;


import cn.virde.nymph.entity.base.BaseEntity;;

public class DailyHumidityEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -928341390287994075L;
	
	private String date;
	
	private double max;
	private double avg;
	private double min;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	
	
	

}
