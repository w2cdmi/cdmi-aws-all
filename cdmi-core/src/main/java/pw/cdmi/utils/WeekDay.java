package pw.cdmi.utils;


/****************************************************
 * 育美互动基础数据，星期枚举信息。
 * 
 * @author 伍伟
 * @version e2h Service Platform, Aug 13, 2014
 ***************************************************/
public enum WeekDay {
	Monday("Mon.",1),
	Tuesday("Tue.", 2),
	Wednesday("Wed.",3),
	Thursday("Thu.", 4),
	Friday("Fri.", 5),
	Saturday("Sat.", 6),
	Sunday("Sun.", 0);
	
	private int value;
	private String shortname; //缩写
	
	private WeekDay(String shortname, int value){
		this.shortname = shortname;
		this.value = value;
	}
	
	public static WeekDay fromValue(int value) {
		for (WeekDay day : WeekDay.values()) {
			if (day.value == value) {
				return day;
			}
		}
		return null;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public String getShortName(){
		return this.shortname;
	}
}
