package pw.cdmi.paas;

public class ApplicationClient {

	public static void main(String[] args) {
		System.setProperty("local.registry.file", "/registry.yaml");
		// TODO your code
		System.clearProperty("local.registry.file");
	}
}
