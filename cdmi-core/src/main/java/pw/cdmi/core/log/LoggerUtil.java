package pw.cdmi.core.log;

import org.apache.commons.lang3.StringUtils;

import pw.cdmi.utils.EnvironmentUtils;


public final class LoggerUtil implements LogConstants
{
    private static final ThreadLocal<String> CURRENT_LOGID = new ThreadLocal<String>();
    
    private LoggerUtil()
    {
    }
    
    public static void regiestThreadLocalLog()
    {
        regiestThreadLocalLog(generateNewLogId());
    }
    
    public static void regiestThreadLocalLogWithPrefix(String prefix)
    {
        regiestThreadLocalLog(prefix + generateNewLogId());
    }
    
    public static void regiestThreadLocalLog(String logID)
    {
        CURRENT_LOGID.set(logID);
        
        org.slf4j.MDC.put("LogID", logID);
    }
    
    public static String getCurrentLogID()
    {
        String logID = CURRENT_LOGID.get();
        
        if (StringUtils.isBlank(logID))
        {
            regiestThreadLocalLog();
            logID = CURRENT_LOGID.get();
        }
        
        return logID;
    }
    
    private static String generateNewLogId()
    {
        return EnvironmentUtils.getServerTag() + '-' + Thread.currentThread().getName() + '-'
            + RandNumGenerator.nextNumber();
    }
    
    private static final class RandNumGenerator
    {
        
        private static long count = 0L;
        
        private static long lastTime = 0L;
        
        private RandNumGenerator()
        {
        }
        
        public static synchronized long nextNumber()
        {
            long curTime = System.currentTimeMillis() * 1000000L;
            if (lastTime != curTime)
            {
                count = 0;
                lastTime = curTime;
            }
            return curTime + nextCountValue();
        }
        
        private static long nextCountValue()
        {
            if (count >= 999999L)
            {
                count = 0;
            }
            else
            {
                count = count + 1L;
            }
            return Long.parseLong(StringUtils.leftPad(String.valueOf(count), 6, '0'));
        }
    }
}
