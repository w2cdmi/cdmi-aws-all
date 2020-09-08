package pw.cdmi.msm.question.model;
/**
 * **********************************************************
 * 枚举对象 代表对问题的类型
 * @author wsl
 * @version cdm Service Platform, 2016年6月21日
 ***********************************************************
 */
public enum QuestionStatus {
    New(1,"新问题"),
    NewReplied(2,"有新回复"),
    Replied(3,"回复已被提问人查看"),
    Closed(4,"已关闭"),       
    Resolved(5,"已解决");
    private int value;
    private String text;
    
    QuestionStatus(int value ,String text){
        this.value = value;
        this.text = text;
    }
    
    public static QuestionStatus fromValue(int value) {
        for (QuestionStatus item : QuestionStatus.values()) {
            if (item.value == value) {
                return item;
            }
        }
        throw new IllegalArgumentException("0-5 is a range of parameter('value')");
    }

    public static QuestionStatus fromText(String text) {
        for (QuestionStatus item : QuestionStatus.values()) {
            if (item.text.equals(text)) {
                return item;
            }
        }
        throw new IllegalArgumentException(".. is a range of parameter('value')");
    }

    public static QuestionStatus fromEnumName(String name) {
        for (QuestionStatus item : QuestionStatus.values()) {
            if (item.toString().equals(name)) {
                return item;
            }
        }
        throw new IllegalArgumentException(".. is a range of parameter('name')");
    }

    public int getValue() {
        return this.value;
    }
    public String getText() {
        return this.text;
    }
}
