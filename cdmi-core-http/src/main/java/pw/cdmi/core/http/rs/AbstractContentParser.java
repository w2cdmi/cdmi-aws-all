package pw.cdmi.core.http.rs;

import pw.cdmi.core.http.MediaFormat;
import pw.cdmi.core.http.exception.AWSClientException;
import pw.cdmi.core.http.exception.AWSServiceException;

public abstract class AbstractContentParser<T> {
	
	
	public T parseRequest(byte[] in, MediaFormat format) throws AWSClientException{
		if (MediaFormat.XML.equals(format)) {
			return this.parseXML(in);
		} else {
			return this.parseJSON(in);
		}
	}
	
	public byte[] toBytes4Response(T t, MediaFormat format) throws AWSServiceException{
		if (MediaFormat.XML.equals(format)) {
			return this.toXML(t);
		} else {
			return this.toJSON(t);
		}
	}
	
	protected abstract T parseXML(byte[] in) throws AWSClientException;
	
	protected abstract T parseJSON(byte[] in) throws AWSClientException;
	
	protected abstract byte[] toXML(T t) throws AWSServiceException;
	
	protected abstract byte[] toJSON(T t) throws AWSServiceException;
	
}
