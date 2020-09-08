package pw.cdmi.msm.geo;

import pw.cdmi.exception.ErrorMessage;

/************************************************************
 * TODO(对类的简要描述说明 – 必须).
 * TODO(对类的作用含义说明 – 可选).
 * TODO(对类的使用方法说明 – 可选).
 * 
 * @author WUWEI
 * @version iSoc Service Platform, 2015年3月1日
 ************************************************************/
public enum ClientError implements ErrorMessage {

    NOT_GET_DATA("无法获取数据");
    
    private final String message;
    
    private ClientError(String message){
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return this.message;
    }
}

