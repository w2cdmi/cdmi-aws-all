package pw.cdmi.paas.developer.model;

import lombok.Data;
@Data
public class NewDeveloper {
	private String name;
	
	private String type;
	
	private Manager manager = new Manager();
	@Data
	public class Manager {
		private String name;
		
		private String Email;
		
		private String Mobile;
	}
}
