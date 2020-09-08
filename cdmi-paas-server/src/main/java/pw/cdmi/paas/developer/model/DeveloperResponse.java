package pw.cdmi.paas.developer.model;

import lombok.Data;
import pw.cdmi.paas.developer.model.NewDeveloper.Manager;

@Data
public class DeveloperResponse {
	private String id;
	
	private String name;
	
	private String type;
	
	private Manager manager;
	
	private String createtime;
}
