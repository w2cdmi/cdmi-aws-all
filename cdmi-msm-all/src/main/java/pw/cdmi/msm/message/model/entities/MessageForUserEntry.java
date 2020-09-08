package pw.cdmi.msm.message.model.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import lombok.Data;

@Data
public class MessageForUserEntry {
    
    private String id;
    
    private String messageTitle;
    
    private String messageContent;
    
    private String messageType;
    
    private String messageStatus;
    
    private String notifyTimeString;
    
    /**
     * 
     * notifyUserMessage 实体 与 message实体 融合为页面呈现实体.<br/>
     * 
     * @param notifyUserMessage
     * @param message
     */
    public void putInfor(NotifyUserMessage notifyUserMessage,Message message){
        this.id = notifyUserMessage.getId();
        this.messageTitle = message.getTitle();
        this.messageContent = message.getContent();
        this.messageType = message.getType().getText();
        this.messageStatus = notifyUserMessage.getStatus().getText();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.notifyTimeString = dateFormat.format(notifyUserMessage.getNotifyDate()); 
   }

}
