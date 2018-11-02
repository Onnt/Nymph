package cn.virde.nymph.net.weather;

import cn.virde.nymph.Nym;
import cn.virde.nymph.enums.common.SkyconsEnum;

/**
 * 
 * @author Virde
 * 2018年1月25日 上午11:02:26
 */
public class Skycon {
	private SkyconsEnum skycon ;
	
	private String descript ;
	
	public Skycon(SkyconsEnum skycon) {
		this.skycon = skycon ;
		calculation();
	}
	public void calculation() {
		switch(skycon) {
		case CLEAR_DAY:
			descript = (String) Nym.random.getOne("晴天","晴朗天气") ;
			break;
		case CLEAR_NIGHT:
			descript = (String) Nym.random.getOne("晴天","晴朗夜空","晴朗天气");
			break;
		case CLOUDY:
			descript = (String) Nym.random.getOne("阴天");
			break;
		case FOG:
			descript = (String) Nym.random.getOne("大雾天气");
			break;
		case HAZE:
			descript = (String) Nym.random.getOne("雾霾天气");
			break;
		case PARTLY_CLOUDY_DAY:
			descript = (String) Nym.random.getOne("多云天气");
			break;
		case PARTLY_CLOUDY_NIGHT:
			descript = (String) Nym.random.getOne("多云天气");
			break;
		case RAIN:
			descript = (String) Nym.random.getOne("雨水天气");
			break;
		case SLEET:
			descript = (String) Nym.random.getOne("冻雨天气");
			break;
		case SNOW:
			descript = (String) Nym.random.getOne("雪，这到底咋写？");
			break;
		case WIND:
			descript = (String) Nym.random.getOne("风");
			break;
		default:
			break;
		}
	}
	
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
}
