package pw.cdmi.core.log;

public interface LogFormat
{
    String START = "[";
    
    String END = "]";
    
    String SPLIT = ", ";
    
    String NULL = "null";
    
    String EMPTY = "";
    
    String logFormat();
}
