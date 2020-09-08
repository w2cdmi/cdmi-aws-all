package pw.cdmi.core.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import pw.cdmi.core.log.Level;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodLogAble
{
    /**
     * 日志级别
     * @return
     */
    Level value() default Level.INFO;
    
    /**
     * 是否新生成日志ID，默认为false，不生成
     * @return
     */
    boolean newLogId() default false;
    
    /**
     * 是否打印参数
     * @return
     */
    boolean isPrintArgs() default true;
    
    /**
     * 是否打印返回值
     * @return
     */
    boolean isPrintResult() default true;
}
