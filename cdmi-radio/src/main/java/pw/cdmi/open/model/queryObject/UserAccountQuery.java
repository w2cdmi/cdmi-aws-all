package pw.cdmi.open.model.queryObject;

import lombok.Data;

import org.apache.commons.lang.StringUtils;

import pw.cdmi.open.model.UserStatus;

@Data
public class UserAccountQuery {
	
	private String userName;
	
	private UserStatus status;

	public String createQuery() {
		StringBuilder hql = new StringBuilder("from UserAccount where 1=1");
		return addWhere(hql);
	}

	public String createPageCountQuery() {
		StringBuilder hql = new StringBuilder("select count(*) from UserAccount where 1=1");
		return addWhere(hql);
	}

	public String addWhere(StringBuilder hql) {
		if (StringUtils.isNotBlank(userName)) {
			hql.append(" and userName like '%" + userName + "%'");
		}
		if (status!=null) {
			hql.append(" and status = '" + status.toString() + "'");
		}
		return hql.toString();
	}
}
