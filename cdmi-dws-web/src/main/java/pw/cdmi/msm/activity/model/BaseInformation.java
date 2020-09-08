package pw.cdmi.msm.activity.model;

import java.util.Date;

import lombok.Data;

@Data
public class BaseInformation {
	
	private String name;
	
	private String Describe;
	
	private ActivityTypeSupper type; 
	
	private Date startTime;
	
	private Date endTime;
}
