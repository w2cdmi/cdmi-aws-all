package pw.cdmi.wishlist.model.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

/**
 * 愿望商品新愿单人员列表
 * @author No.11
 *
 */
@Data
@Entity
@Table(name = "wishlist_participant")
public class Participant {
	private String id;
	private String productId;
	private String wxuserId;
	private String inviterNumber;					//邀请人员数量
	private Date creatime;
}
