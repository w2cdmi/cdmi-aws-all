package pw.cdmi.msm.comment.model;

/**
 * 支持的評論对象类型，当前支持租户用户账号，租户文件
 * @author No.11
 *
 */
public enum SupportTargetType {
	Tenancy_File,		//租户文件宝文件  
	Tenancy_Comment,	//评论类型
    Tenancy_User;		//租户用户
	
    public static SupportTargetType fromName(String name) {
        for (SupportTargetType item : SupportTargetType.values()) {
            if (item.toString().equals(name)) {
                return item;
            }
        }
        return null;
    }
}
