package pw.cdmi.paas.developer.model;

public enum DeveloperType {
	Persion, Enterprise;
	public static DeveloperType fromName(String name) {
		for (DeveloperType item : DeveloperType.values()) {
			if (item.toString().equals(name)) {
				return item;
			}
		}
		return null;
	}
}
