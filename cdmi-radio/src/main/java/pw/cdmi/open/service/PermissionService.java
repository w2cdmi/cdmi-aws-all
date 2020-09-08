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
public interface PermissionService {

    /**
     * 为应用创建一个新的用户角色
     * @param appId 应用的编号
     * @param loginDomain 登录域的编号，可以为空
     * @param siteRole 新的用户角色
     */
    public void createSiteRole(String appId, String loginDomain, SiteRole siteRole);

    /**
     * 为应用更新一个用户角色描述信息，更新不能变更角色美剧值
     * @param appId 应用的编号
     * @param siteRole 待编辑的用户角色
     */
    public void updateSiteRole(String appId, SiteRole siteRole);

    /**
     * 为应用删除一个用户自定义的角色，系统角色不允许被删除
     * @param appId 应用的编号
     */
    public boolean deleteSiteRoleById(int id);

    /**
     * 通过角色的Id，获得角色的详情
     * @param id 角色的Id
     * @return 一个用户角色
     */
    public SiteRole getSiteRoleById(int id);

    /**
     * 通过角色的枚举值，获得角色的详情
     * @param appId 应用的编号
     * @param roleEnum 角色的枚举值，在同一个站点中，枚举值唯一
     * @return 一个用户角色
     */
    public SiteRole getSiteRoleByRoleEnum(String appId, String roleEnum);

    /**
     * 获得一个应用登录域下的所有的用户角色，
     * @param appId 应用的编号
     * @param loginDomain 登录域的编号，可以为空
     * @return 符合条件的用户角色列表
     */
    public List<SiteRole> findSiteRole(String appId, String loginDomain);

    /**
     * 获得指定用户在指定应用的登录域下的所有的角色
     * @param appId 应用的编号
     * @param loginDomain 登录域的编号，可以为空
     * @param userId
     * @return
     */
    public List<SiteRole> findSiteRolesByUserId(String appId, String loginDomain, long userId);

    public PageView findSiteRoleByPage(String appId, String loginDomain, int pageNo, int pageSize);

    public void createSiteMenu(SiteMenu siteMenu);

    public void updateSiteMenu(SiteMenu siteMenu);

    public void deleteSiteMenuById(Long id);

    public SiteMenu getSiteMenuById(Long id);

    public List<SiteMenu> findAllSiteMenu();

    public List<SiteMenu> findAllSiteMenuWithSub();

    public List<SiteMenu> findAllSiteMenuBeClass();

    public List<SiteMenu> findAllSiteMenuByParentId(Long parentId);

    // public List<SiteMenu> findSiteMenuListByRoleId(int roleId);

    public void createDataAccess(DataAccess dataAccess);

    public void updateDataAccess(DataAccess dataAccess);

    public void deleteDataAccessById(int id);

    public DataAccess getDataAccessById(int id);

    public List<DataAccess> findAllDataAccess();

    public List<DataAccess> findDataAccessListByRoleId(int roleId);

    public void createSiteUserPermissionByUser(String appId, String userDomain, String userId, List<Integer> roleIds);

    public void createSiteUserPermissionByRole(String appId, String userDomain, String roleEnum, List<String> users);

    public void deleteSiteUserPermissionById(int id);

    public void deleteSiteUserPermissionByUser(String userId);

    public void deleteSiteUserPermissionByRoleId(int roleId);

    public SiteUserRole getSiteUserPermissionById(int id);

    public List<SiteUserRole> findAllSiteUserPermission();

    public List<SiteUserRole> findSiteUserRoleByUserId(String userId);

    public List<SiteUserRole> findSiteUserRoleByRoleId(int roleId);

    public void createSiteMenuAcl(List<Integer> roleIds, List<Long> menuIds);

    public void deleteSiteMenuAclById(int id);

    public void deleteSiteMenuAclByMenuId(int menuId);

    public void deleteSiteMenuAclByRoleId(int roleId);

    public void deleteSiteMenuByMenuId(Long menuId);

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

    public void createSiteUserRole(String appId, String userId, String RoleEnum, int roleId);

    public void updateSiteUserRole(SiteUserRole siteUserRole);

}
