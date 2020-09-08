package pw.cdmi.open.service;

import java.util.List;

import pw.cdmi.open.model.entity.SiteMenuAcl;

/************************************************
 * @funciontDescrible 操作权限的接口
 * @author chenchao
 * @date 2015年4月30日
 * @version 
 ************************************************/
public interface SiteMenuAclService {
     /**
      * 获取所有权限
      * @return List<Privilege>
      */
	public abstract List<SiteMenuAcl> findPrivilege();
    /**
     * 保存权限
     * @param privilege
     */
	public abstract boolean savePrivilege(SiteMenuAcl privilege);
    /**
     * 修改权限
     * @param privilege
     * @return boolean
     */
	public abstract boolean updatePrivilege(SiteMenuAcl privilege);
	 /**
     * 删除权限
     * @param id 
     * @return boolean
     */
	public abstract boolean deletePrivilege(int id);
	/**
	 * 获得指定 Privilege
	 * @param id
	 * @return Privilege
	 */
	public abstract SiteMenuAcl getPrivilege(int id);

}