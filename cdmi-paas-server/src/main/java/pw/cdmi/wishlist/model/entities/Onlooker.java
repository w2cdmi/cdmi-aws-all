package pw.cdmi.wishlist.model.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

/**
 * 愿望商品围观人员列表
 * @author No.11
 *
 */
@Data
@Entity
@Table(name = "wishlist_onlookwer")
public class Onlooker {
	private String id;
	private String productId;
	private String wxuserId;
	private String inviterId;					//邀请人的Id，如果没有邀请人，该字段为空。
	private Date creatime;
}
