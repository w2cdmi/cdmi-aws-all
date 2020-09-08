package pw.cdmi.core.http.auth;

public class SecurityToken {
	private String token;
	private long createtime;	//生效時間
	private long expiretime;	//失效時間
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}
	public long getExpiretime() {
		return expiretime;
	}
	public void setExpiretime(long expiretime) {
		this.expiretime = expiretime;
	}
}
