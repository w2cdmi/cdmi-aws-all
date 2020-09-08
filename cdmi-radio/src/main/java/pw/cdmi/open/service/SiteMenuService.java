package pw.cdmi.open.service;

import java.util.List;

import pw.cdmi.collection.PageView;
import pw.cdmi.open.model.entity.DataAccess;
import pw.cdmi.open.model.entity.RoleAndDataAccess;
import pw.cdmi.open.model.entity.SiteMenu;
import pw.cdmi.open.model.entity.SiteMenuAcl;
import pw.cdmi.open.model.entity.SiteRole;
import pw.cdmi.open.model.entity.SiteUserRole;

/************************************************************
 * 接口类，提供权限管理的相关操作方法
 * 
 * @author 佘朝军
 * @version iSoc Service Platform, 2015-5-7
 ************************************************************/
public interface SiteMenuService {

	public void createSiteRole(SiteRole siteRole);

	public void updateSiteRole(SiteRole siteRole);

	public void deleteSiteRoleById(int id);

	public SiteRole getSiteRoleById(int id);

	public List<SiteRole> findAllSiteRole();

	public PageView findSiteRoleByPage(int pageNo, int pageSize);

	public void createSiteMenu(SiteMenu siteMenu);

	public void updateSiteMenu(SiteMenu siteMenu);

	public void deleteSiteMenuById(int id);

	public SiteMenu getSiteMenuById(long id);

	public List<SiteMenu> findAllSiteMenu();

	public List<SiteMenu> findAllSiteMenuWithSub();

//	public List<SiteMenu> findSiteMenuListByRoleId(int roleId);

	public void createDataAccess(DataAccess dataAccess);

	public void updateDataAccess(DataAccess dataAccess);

	public void deleteDataAccessById(int id);

	public DataAccess getDataAccessById(int id);

	public List<DataAccess> findAllDataAccess();

	public List<DataAccess> findDataAccessListByRoleId(int roleId);

	public List<SiteUserRole> findAllSiteUserPermission();

	public List<SiteUserRole> findSiteUserPermissionListByUserId(long employeeId);

	public List<SiteUserRole> findSiteUserPermissionListByRoleId(int roleId);

	public void createSiteMenuAcl(List<Integer> roleIds, List<Long> menuIds);

	public void deleteSiteMenuAclById(int id);

	public void deleteSiteMenuAclByMenuId(int menuId);

	public void deleteSiteMenuAclByRoleId(int roleId);

	public SiteMenuAcl getSiteMenuAclById(int id);

	public List<SiteMenuAcl> findAllSiteMenuAcl();

	public List<SiteMenuAcl> findSiteMenuAclListByRoleId(int roleId);

	public void createRoleAndDataAccess(List<Integer> roleIds, List<Integer> dataAccessIds);

	public void deleteRoleAndDataAccessById(int id);

	public void deleteRoleAndDataAccessByDataAccessId(int dataAccessId);

	public void deleteRoleAndDataAccessByRoleId(int roleId);

	public RoleAndDataAccess getRoleAndDataAccessById(int id);

	public List<RoleAndDataAccess> findAllRoleAndDataAccess();

	public List<RoleAndDataAccess> findRoleAndDataAccessListByRoleId(int roleId);

}
