package pw.cdmi.wishlist.model.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

/**
 * 微信用户地址表
 * @author No.11
 *
 */
@Data
@Entity
@Table(name = "p_person_address")
public class PeopleAddress {
	private String id;
	private String accountId;
	private String accountType;				//对应的WXUser表还是UserAccount表
	private String name;
	private String label;					//对应的收获人地址标签，家，公司等
	private String phoneNumber;
    private Integer districtId;				// 对应的区县行政单位ID
    private Integer cityId;					// 对应的城市ID
    private Integer provinceId;				// 对应的省份ID
    private Integer countryId;				// 对应的国家ID
    private String address;					// 收获人的详细地址
    private String postcode;
}
