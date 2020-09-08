package pw.cdmi.open.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import pw.cdmi.collection.PageView;
import pw.cdmi.core.db.GenericDao;
import pw.cdmi.core.db.JPQuery;
import pw.cdmi.core.http.exception.AWSClientException;
import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.msm.geo.ClientReason;
import pw.cdmi.open.ClientError;
import pw.cdmi.open.model.entity.DataAccess;
import pw.cdmi.open.model.entity.RoleAndDataAccess;
import pw.cdmi.open.model.entity.SiteMenu;
import pw.cdmi.open.model.entity.SiteMenuAcl;
import pw.cdmi.open.model.entity.SiteRole;
import pw.cdmi.open.model.entity.SiteUser;
import pw.cdmi.open.model.entity.SiteUserRole;
import pw.cdmi.open.service.PermissionService;
import pw.cdmi.open.service.UserService;

/************************************************************
 * 实现类，提供权限管理的操作方法
 * 
 * @author 佘朝军
 * @version iSoc Service Platform, 2015-5-7
 ************************************************************/
@Service
public class PermissionServiceImpl implements PermissionService {

    private static final Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Autowired
    private GenericDao daoImpl;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void createSiteRole(String appId, String loginDomain, SiteRole siteRole) {
        if (!StringUtils.isEmpty(siteRole.getSiteId()) && !siteRole.getSiteId().equals(appId)) {
            throw new AWSClientException(ClientError.ErrorPermissionType, ClientReason.ErrorPermissionType);
        }
        if (StringUtils.isEmpty(siteRole.getName()) || StringUtils.isEmpty(siteRole.getRoleEnum())) {
            throw new AWSClientException(ClientError.IncompleteBody, ClientReason.IncompleteBody);
        }
        String hql2 = "from SiteRole where name = '" + siteRole.getName() + "'and siteId ='" + appId + "'";
        SiteRole role2 = daoImpl.findOne(hql2);
        if (role2 != null) {
            throw new AWSClientException(ClientError.DataAlreadyExists, ClientReason.DataAlreadyExists);
        }
        String hql = null;
        if (StringUtils.isEmpty(loginDomain)) {
            hql = "from SiteRole where roleEnum = '" + siteRole.getRoleEnum() + "' and siteId ='" + appId + "'";
        } else {
            hql = "from SiteRole where roleEnum = '" + siteRole.getRoleEnum() + "' and siteId ='" + appId
                    + "' and loginDomain＝'" + loginDomain + "'";
        }
        SiteRole role = daoImpl.findOne(hql);
        if (role == null) {
            siteRole.setSiteId(appId);
            daoImpl.save(siteRole);
        } else {
            throw new AWSClientException(ClientError.DataAlreadyExists, ClientReason.DataAlreadyExists);
        }
    }

    @Override
    @Transactional
    public void updateSiteRole(String appId, SiteRole siteRole) {
        if (!StringUtils.isEmpty(siteRole.getSiteId()) && !siteRole.getSiteId().equals(appId)) {
            throw new AWSClientException(ClientError.ErrorPermissionType, ClientReason.ErrorPermissionType);
        }
        if (StringUtils.isEmpty(siteRole.getName())) {
            throw new AWSClientException(ClientError.IncompleteBody, ClientReason.IncompleteBody);
        }
        String hql = "from SiteRole where name = '" + siteRole.getName() + "'and siteId ='" + appId + "'and id !='"
                + siteRole.getId() + "'";
        SiteRole role2 = daoImpl.findOne(hql);
        if (role2 != null) {
            throw new AWSClientException(ClientError.DataAlreadyExists, ClientReason.DataAlreadyExists);
        }
        // 只有自定义的角色才能进行修改，但也不允许修改RoleEnmu值
        SiteRole role = daoImpl.get(siteRole.getId(), SiteRole.class);
        if (role.getCustomRole() != null && !role.getCustomRole().booleanValue()) {
            throw new AWSClientException(ClientError.ErrorPermissionType, ClientReason.ErrorPermissionType);
        }
        if (StringUtils.isEmpty(siteRole.getRoleEnum()) || !siteRole.getRoleEnum().equals(role.getRoleEnum())) {
            throw new AWSClientException(ClientError.ErrorPermissionType, ClientReason.ErrorPermissionType);
        }
        siteRole.setSiteId(appId);
        daoImpl.update(siteRole);

    }

    @Override
    @Transactional
    public boolean deleteSiteRoleById(int id) {
        try {
            SiteRole siteRole = getSiteRoleById(id);
            if (siteRole != null && siteRole.getCustomRole() != null && siteRole.getCustomRole()) {
                daoImpl.delete(siteRole);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }

    }

    @Override
    public SiteRole getSiteRoleById(int id) {
        return daoImpl.get(id, SiteRole.class);
    }

    @Override
    public SiteRole getSiteRoleByRoleEnum(String appId, String roleEnum) {
        String hql = "from SiteRole where siteId='" + appId + "' and roleEnum = '" + roleEnum + "'";
        return daoImpl.findOne(hql);
    }

    @Override
    public List<SiteRole> findSiteRole(String appId, String loginDomain) {
        String hql = null;
        if (StringUtils.isEmpty(loginDomain)) {
            hql = "from SiteRole where siteId = '" + appId + "'";
        } else {
            hql = "from SiteRole where siteId ='" + appId + "' and loginDomain='" + loginDomain + "'";
        }
        return daoImpl.find(hql);
    }

    @Override
    public List<SiteRole> findSiteRolesByUserId(String appId, String loginDomain, long userId) {
        // String hql =
        // "select r from SiteRole r, SiteUserRole u where r.appId = '"
        // + appId
        // + "' and r.loginDomain='"
        // + loginDomain
        // + "' and u.accountId = "
        // + accountId
        // + " and r.roleEnum = u.roleEnum";
        // return jpaImpl.find(hql);
        String hql = "from SiteUserRole where siteId = '" + appId + "' and userId=" + userId;

        List<SiteUserRole> userroles = daoImpl.find(hql);
        List<SiteRole> list = new ArrayList<SiteRole>();
        for (SiteUserRole userrole : userroles) {
            if (StringUtils.isEmpty(loginDomain)) {
                hql = "from SiteRole where siteId = '" + appId + "' and roleEnum='" + userrole.getRoleEnum() + "'";
            } else {
                hql = "from SiteRole where siteId = '" + appId + "' and roleEnum='" + userrole.getRoleEnum()
                        + "' and loginDomain='" + loginDomain + "'";
            }
            list.add((SiteRole) daoImpl.findOne(hql));
        }
        return list;
    }

    @Override
    public PageView findSiteRoleByPage(String appId, String loginDomain, int pageNo, int pageSize) {

        PageView pageView = new PageView();
        String hql = null;
        if (StringUtils.isEmpty(loginDomain)) {
            hql = "from SiteRole where siteId = '" + appId + "'";
        } else {
            hql = "from SiteRole where siteId = '" + appId + "' and loginDomain='" + loginDomain + "'";
        }

        long total = daoImpl.getCount("select count(*) " + hql);
        List<SiteRole> list = daoImpl.find(hql, pageNo, pageSize);
        pageView.setList(list);
        pageView.setTotalRecord(total);
        return pageView;
    }

    @Override
    @Transactional
    public void createSiteMenu(SiteMenu siteMenu) {
        daoImpl.save(siteMenu);
    }

    @Override
    @Transactional
    public void updateSiteMenu(SiteMenu siteMenu) {
        try {
            daoImpl.update(siteMenu);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    @Transactional
    public void deleteSiteMenuById(Long id) {
        try {
            SiteMenu siteMenu = getSiteMenuById(id);
            if (siteMenu != null) {
                daoImpl.delete(siteMenu);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }

    }

    @Override
    public SiteMenu getSiteMenuById(Long id) {
        SiteMenu siteMenu = null;
        try {
            siteMenu = daoImpl.get(id, SiteMenu.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return siteMenu;
    }

    @Override
    public List<SiteMenu> findAllSiteMenu() {
        List<SiteMenu> list = new ArrayList<SiteMenu>();
        try {
            list = daoImpl.findByNamedQuery("SiteMenu.findAll");
        } catch (NoResultException e) {
            log.warn("没有找到相应的结果！");
            return null;
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public List<SiteMenu> findAllSiteMenuWithSub() {
        List<SiteMenu> list = new ArrayList<SiteMenu>();
        try {
            String hql = "from SiteMenu where parentId is null ";
            list = daoImpl.find(hql);
            list = getAllSubMenu(list);
        } catch (NoResultException e) {
            log.warn("没有找到相应的结果！");
            return null;
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    /**
     * 获取所有菜单的所有下级子菜单列表列表
     * 
     * @param list
     *            上级菜单列表
     * @return 返回所有菜单列表
     */
    private List<SiteMenu> getAllSubMenu(List<SiteMenu> list) {
        String jpql = "select m from SiteMenu m where m.parentId = :parentId ";
        JPQuery query = JPQuery.createQuery(jpql);
        for (SiteMenu siteMenu : list) {
            query.setParamater("parentId", siteMenu.getId());
            List<SiteMenu> subList = daoImpl.find(query);
            if (!subList.isEmpty()) {
                subList = getAllSubMenu(subList);
                siteMenu.setSubMenus(subList);
            }
        }
        return list;
    }

    // @Override
    // public List<SiteMenu> findSiteMenuListByRoleId(int roleId) {
    // List<SiteMenu> list = new ArrayList<SiteMenu>();
    // try {
    // list = jpaImpl.findByNamedQuery("SiteMenu.findListByRoleId", new Object[]
    // { roleId, null });
    // list = getSubMenus(list, roleId);
    // } catch (NoResultException e) {
    // log.warn("没有找到相应的结果！");
    // return null;
    // } catch (Exception e) {
    // log.error(e.getMessage());
    // e.printStackTrace();
    // throw new AWSServiceException(SystemError.SQLError);
    // }
    // return list;
    // }

    // /**
    // * 根据角色编号获取菜单下级子菜单的方法
    // *
    // * @param list 上级菜单列表
    // * @param roleId 角色的编号
    // * @return 返回菜单列表
    // */
    // private List<SiteMenu> getSubMenus(List<SiteMenu> list, int roleId) {
    // for (SiteMenu siteMenu : list) {
    // List<SiteMenu> subList =
    // jpaImpl.findByNamedQuery("SiteMenu.findListByRoleId", new Object[] {
    // roleId,
    // siteMenu.getId() });
    // if (!subList.isEmpty()) {
    // subList = getSubMenus(subList, roleId);
    // siteMenu.setSubMenus(subList);
    // }
    // }
    // return list;
    // }

    @Override
    @Transactional
    public void createDataAccess(DataAccess dataAccess) {
        try {
            daoImpl.save(dataAccess);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }

    }

    @Override
    @Transactional
    public void updateDataAccess(DataAccess dataAccess) {
        try {
            daoImpl.update(dataAccess);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }

    }

    @Override
    @Transactional
    public void deleteDataAccessById(int id) {
        try {
            DataAccess dataAccess = getDataAccessById(id);
            if (dataAccess != null) {
                daoImpl.delete(dataAccess);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }

    }

    @Override
    public DataAccess getDataAccessById(int id) {
        DataAccess dataAccess = null;
        try {
            dataAccess = daoImpl.get(id, DataAccess.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return dataAccess;
    }

    @Override
    public List<DataAccess> findAllDataAccess() {
        List<DataAccess> list = new ArrayList<DataAccess>();
        String jpql = "select d from DataAccess d where parentId = :parentId";
        JPQuery query = JPQuery.createQuery(jpql);
        query.setParamater("parentId", null);
        try {
            list = daoImpl.find(query);
            list = getAllSubDataAccess(list);
        } catch (NoResultException e) {
            log.warn("没有找到相应的结果！");
            return null;
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    /**
     * 查找所有下级数据权限列表
     * 
     * @param list
     *            上级数据权限列表
     * @return 返回所有数据权限列表
     */
    private List<DataAccess> getAllSubDataAccess(List<DataAccess> list) {
        String jpql = "select d from DataAccess d where parentId = :parentId";
        JPQuery query = JPQuery.createQuery(jpql);
        for (DataAccess da : list) {
            query.setParamater("parentId", da.getId());
            List<DataAccess> subList = daoImpl.find(query);
            if (!subList.isEmpty()) {
                subList = getAllSubDataAccess(subList);
                da.setSubDataAccess(subList);
            }
        }
        return list;
    }

    @Override
    public List<DataAccess> findDataAccessListByRoleId(int roleId) {
        List<DataAccess> list = new ArrayList<DataAccess>();
        String jpql = "select d from DataAccess d where id in "
                + "(select dataAccessId from RoleAndDataAccess where roleId = :roleId) and parentId = :parentId ";
        JPQuery query = JPQuery.createQuery(jpql);
        try {
            query.setParamater("roleId", roleId);
            query.setParamater("parentId", null);
            list = daoImpl.find(query);
            list = getSubDataAccesses(list, roleId);
        } catch (NoResultException e) {
            log.warn("没有找到相应的结果！");
            return null;
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    /**
     * 根据角色编号获取菜单下级数据权限的方法
     * 
     * @param list
     *            上级数据权限列表
     * @param roleId
     *            角色的编号
     * @return 返回数据权限列表
     */
    private List<DataAccess> getSubDataAccesses(List<DataAccess> list, int roleId) {
        for (DataAccess da : list) {
            String jpql = "select d from DataAccess d where id in "
                    + "(select dataAccessId from RoleAndDataAccess where roleId = :roleId) and parentId = :parentId ";
            JPQuery query = JPQuery.createQuery(jpql);
            query.setParamater("roleId", roleId);
            query.setParamater("parentId", da.getId());
            List<DataAccess> subList = daoImpl.find(query);
            if (!subList.isEmpty()) {
                subList = getSubDataAccesses(subList, roleId);
                da.setSubDataAccess(subList);
            }
        }
        return list;
    }

    @Override
    @Transactional
    public void createSiteUserPermissionByUser(String appId, String userDomain, String userId, List<Integer> roleIds) {
        // 检查用户是否存在
        SiteUser user = userService.getSiteUserById(userId);
        if (user == null) {
            throw new AWSClientException(ClientError.NoFoundData, ClientReason.NoFoundData);
        }
        if (!appId.equals(user.getAppId())
                || (!StringUtils.isEmpty(userDomain)) && userDomain.equals(user.getUserDomain())) {
            throw new AWSClientException(ClientError.DataConsistent, ClientReason.DataConsistent);
        }
        deleteSiteUserPermissionByUser(userId);
        if (roleIds.size() > 0) {
            for (Integer roleId : roleIds) {
                SiteRole siteRole = getSiteRoleById(roleId);
                if (siteRole != null) {
                    SiteUserRole userrole = new SiteUserRole();
                    userrole.setRoleEnum(siteRole.getRoleEnum());
                    userrole.setSiteId(appId);
                    userrole.setUserId(userId);
                    userrole.setRoleId(roleId);
                    daoImpl.save(userrole);
                }
            }
        } else {
            throw new AWSClientException(ClientError.NoFoundData, ClientReason.NoFoundData);
        }
    }

    @Override
    public void createSiteUserPermissionByRole(String appId, String userDomain, String roleEnum, List<String> users) {
        // 检查应用的角色是否存在
        SiteRole role = this.getSiteRoleByRoleEnum(appId, roleEnum);
        if (role == null) {
            throw new AWSClientException(ClientError.NoFoundData, ClientReason.NoFoundData);
        }

        for (String userId : users) {
            SiteUser user = userService.getSiteUserById(userId);
            if (user != null) {
                if ((!StringUtils.isEmpty(userDomain) && !userDomain.equals(user.getUserDomain()))
                        || (!user.getAppId().equals(appId))) {// 传入信息与系统保存信息不一致
                    throw new AWSClientException(ClientError.DataConsistent, ClientReason.DataConsistent);
                }
                SiteUserRole userrole = new SiteUserRole();
                userrole.setRoleEnum(roleEnum);
                userrole.setSiteId(appId);
                userrole.setUserId(user.getId());
                daoImpl.save(userrole);
            }

        }

    }

    @Override
    @Transactional
    public void deleteSiteUserPermissionById(int id) {
        try {
            SiteUserRole sup = getSiteUserPermissionById(id);
            if (sup != null) {
                daoImpl.delete(sup);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }

    }

    @Override
    @Transactional
    public void deleteSiteUserPermissionByUser(String userId) {
        String jpql = "delete from SiteUserRole where userId = :userId";
        JPQuery query = JPQuery.createQuery(jpql);
        query.setParamater("userId", userId);
        try {
            daoImpl.delete(query);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    @Transactional
    public void deleteSiteUserPermissionByRoleId(int roleId) {
        String jpql = "delete from SiteUserRole where roleId = :roleId";
        JPQuery jPQ = JPQuery.createQuery(jpql);
        jPQ.setParamater("roleId", roleId);
        try {
            daoImpl.delete(jPQ);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }

    }

    @Override
    public SiteUserRole getSiteUserPermissionById(int id) {
        SiteUserRole sup = null;
        try {
            sup = daoImpl.get(id, SiteUserRole.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return sup;
    }

    @Override
    public List<SiteUserRole> findAllSiteUserPermission() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SiteUserRole> findSiteUserRoleByUserId(String userId) {
        List<SiteUserRole> list = new ArrayList<SiteUserRole>();
        String jpql = "select sup from SiteUserRole sup where userId = :userId";
        JPQuery jPQ = JPQuery.createQuery(jpql);
        jPQ.setParamater("userId", userId);
        try {
            list = daoImpl.find(jPQ);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public List<SiteUserRole> findSiteUserRoleByRoleId(int roleId) {
        List<SiteUserRole> list = new ArrayList<SiteUserRole>();
        String jpql = "select sup from SiteUserRole sup where roleId = :roleId ";
        JPQuery jPQ = JPQuery.createQuery(jpql);
        jPQ.setParamater("roleId", roleId);
        try {
            list = daoImpl.find(jPQ);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    @Transactional
    public void createSiteMenuAcl(List<Integer> roleIds, List<Long> menuIds) {
        try {
            for (Integer roleId : roleIds) {
                SiteRole siteRole = getSiteRoleById(roleId);
                deleteSiteMenuAclByRoleId(roleId);
                for (Long menuId : menuIds) {
                    SiteMenuAcl sma = new SiteMenuAcl();
                    sma.setMenuId(menuId);
                    sma.setRoleId(roleId);
                    sma.setSiteId(siteRole.getSiteId());
                    daoImpl.save(sma);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    @Transactional
    public void deleteSiteMenuAclById(int id) {
        try {
            SiteMenuAcl siteMenuAcl = getSiteMenuAclById(id);
            if (siteMenuAcl != null) {
                daoImpl.delete(siteMenuAcl);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }

    }

    @Override
    public void deleteSiteMenuAclByMenuId(int menuId) {
        // TODO Auto-generated method stub

    }

    @Override
    @Transactional
    public void deleteSiteMenuAclByRoleId(int roleId) {
        String jpql = "delete from SiteMenuAcl where roleId = :roleId";
        JPQuery jPQ = JPQuery.createQuery(jpql);
        jPQ.setParamater("roleId", roleId);
        try {
            daoImpl.delete(jPQ);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    @Transactional
    public void deleteSiteMenuByMenuId(Long menuId) {
        String jpql = "select m from SiteMenu m where m.id = :menuId ";
        JPQuery jPQ = JPQuery.createQuery(jpql);
        jPQ.setParamater("menuId", menuId);
        try {
            SiteMenu siteMenu = daoImpl.findOne(jPQ);
            if (null != siteMenu) {
                if (siteMenu.getBeClass()) {
                    jpql = "select m from SiteMenu m where m.parentId = :parentId";
                    jPQ = JPQuery.createQuery(jpql);
                    jPQ.setParamater("parentId", menuId);
                    List<SiteMenu> menus = daoImpl.find(jPQ);
                    if (null != menus) {
                        jpql = "delete from SiteMenu where id = :id ";
                        jPQ = JPQuery.createQuery(jpql);
                        for (SiteMenu menu : menus) {
                            Long id = menu.getId();
                            deleteSiteMenuByMenuId(id);
                            jPQ.setParamater("id", menuId);
                            daoImpl.delete(jPQ);
                        }
                    }
                }
                jpql = "delete from SiteMenu where id = :id ";
                jPQ = JPQuery.createQuery(jpql);
                jPQ.setParamater("id", menuId);
                daoImpl.delete(jPQ);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public SiteMenuAcl getSiteMenuAclById(int id) {
        SiteMenuAcl siteMenuAcl = null;
        try {
            siteMenuAcl = daoImpl.get(id, SiteMenuAcl.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return siteMenuAcl;
    }

    @Override
    public List<SiteMenuAcl> findAllSiteMenuAcl() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SiteMenuAcl> findSiteMenuAclListByRoleId(int roleId) {
        List<SiteMenuAcl> list = new ArrayList<SiteMenuAcl>();
        String jpql = "select sma from SiteMenuAcl sma where roleId = :roleId";
        JPQuery jPQ = JPQuery.createQuery(jpql);
        jPQ.setParamater("roleId", roleId);
        try {
            list = daoImpl.find(jPQ);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    @Transactional
    public void createRoleAndDataAccess(List<Integer> roleIds, List<Integer> dataAccessIds) {
        try {
            for (Integer roleId : roleIds) {
                SiteRole siteRole = getSiteRoleById(roleId);
                deleteRoleAndDataAccessByRoleId(roleId);
                for (Integer dataAccessId : dataAccessIds) {
                    RoleAndDataAccess rada = new RoleAndDataAccess();
                    rada.setDataAccessId(dataAccessId);
                    rada.setRoleId(roleId);
                    rada.setSiteId(siteRole.getSiteId());
                    daoImpl.save(rada);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    @Transactional
    public void deleteRoleAndDataAccessById(int id) {
        try {
            RoleAndDataAccess rada = getRoleAndDataAccessById(id);
            if (rada != null) {
                daoImpl.delete(rada);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }

    }

    @Override
    public void deleteRoleAndDataAccessByDataAccessId(int menuId) {
        // TODO Auto-generated method stub

    }

    @Override
    @Transactional
    public void deleteRoleAndDataAccessByRoleId(int roleId) {
        String jpql = "delete from RoleAndDataAccess where roleId = :roleId ";
        JPQuery jPQ = JPQuery.createQuery(jpql);
        jPQ.setParamater("roleId", roleId);
        try {
            daoImpl.delete(jPQ);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public RoleAndDataAccess getRoleAndDataAccessById(int id) {
        RoleAndDataAccess rada = null;
        try {
            rada = daoImpl.get(id, RoleAndDataAccess.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return rada;
    }

    @Override
    public List<RoleAndDataAccess> findAllRoleAndDataAccess() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RoleAndDataAccess> findRoleAndDataAccessListByRoleId(int roleId) {
        List<RoleAndDataAccess> list = new ArrayList<RoleAndDataAccess>();
        String jpql = "select rada from RoleAndDataAccess rada where roleId = :roleId ";
        JPQuery jPQ = JPQuery.createQuery(jpql);
        jPQ.setParamater("roleId", roleId);
        try {
            list = daoImpl.find(jPQ);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    @Transactional
    public void createSiteUserRole(String appId, String userId, String RoleEnum, int roleId) {
        SiteUserRole user_role = new SiteUserRole();
        user_role.setRoleEnum(RoleEnum);
        user_role.setUserId(userId);
        user_role.setSiteId(appId);
        user_role.setRoleId(roleId);
        daoImpl.save(user_role);
    }

    @Override
    @Transactional
    public void updateSiteUserRole(SiteUserRole siteUserRole) {
        try {
            daoImpl.update(siteUserRole);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<SiteMenu> findAllSiteMenuBeClass() {
        List<SiteMenu> list = new ArrayList<SiteMenu>();
        try {
            String hql = "from SiteMenu where beClass = 1 ";
            list = daoImpl.find(hql);
            list = getAllSubMenu(list);
        } catch (NoResultException e) {
            log.warn("没有找到相应的结果！");
            return null;
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public List<SiteMenu> findAllSiteMenuByParentId(Long parentId) {
        List<SiteMenu> list = new ArrayList<SiteMenu>();
        try {
            String hql = "from SiteMenu where parentId = " + parentId;
            list = daoImpl.find(hql);
            list = getAllSubMenu(list);
        } catch (NoResultException e) {
            log.warn("没有找到相应的结果！");
            return null;
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

}
