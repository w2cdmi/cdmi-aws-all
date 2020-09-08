package pw.cdmi.error;

public interface ErrorReason {
	public int getHttpStatus();
	public int getCode();
	public String getReason();
}
