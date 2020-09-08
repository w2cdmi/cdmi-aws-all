package pw.cdmi.open.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pw.cdmi.collection.PageView;
import pw.cdmi.core.db.GenericDao;
import pw.cdmi.core.db.JPQuery;
import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.open.model.entity.DataAccess;
import pw.cdmi.open.model.entity.RoleAndDataAccess;
import pw.cdmi.open.model.entity.SiteMenu;
import pw.cdmi.open.model.entity.SiteMenuAcl;
import pw.cdmi.open.model.entity.SiteRole;
import pw.cdmi.open.model.entity.SiteUserRole;
import pw.cdmi.open.service.SiteMenuService;

/************************************************************
 * 实现类，提供权限管理的操作方法
 * 
 * @author 佘朝军
 * @version iSoc Service Platform, 2015-5-7
 ************************************************************/
@Service
public class SiteMenuServiceImpl implements SiteMenuService {

	private static final Logger log = LoggerFactory.getLogger(SiteMenuServiceImpl.class);

	@Autowired
	private GenericDao daoImpl;

	@Override
	@Transactional
	public void createSiteRole(SiteRole siteRole) {
		try {
			daoImpl.save(siteRole);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new AWSServiceException(SystemReason.SQLError);
		}
	}

	@Override
	@Transactional
	public void updateSiteRole(SiteRole siteRole) {
		try {
			daoImpl.update(siteRole);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new AWSServiceException(SystemReason.SQLError);
		}

	}

	@Override
	@Transactional
	public void deleteSiteRoleById(int id) {
		try {
			SiteRole siteRole = getSiteRoleById(id);
			if (siteRole != null) {
				daoImpl.delete(siteRole);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new AWSServiceException(SystemReason.SQLError);
		}

	}

	@Override
	public SiteRole getSiteRoleById(int id) {
		SiteRole siteRole = null;
		try {
			siteRole = daoImpl.get(id, SiteRole.class);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new AWSServiceException(SystemReason.SQLError);
		}
		return siteRole;
	}

	@Override
	public List<SiteRole> findAllSiteRole() {
		List<SiteRole> list = new ArrayList<SiteRole>();
		try {
			list = daoImpl.findAll(SiteRole.class);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new AWSServiceException(SystemReason.SQLError);
		}
		return list;
	}


	@Override
	public PageView findSiteRoleByPage(int pageNo, int pageSize) {
		PageView pageView = null;
		try {
			String searchHql = "SELECT sr from SiteRole sr WHERE 1 = 1";
			String countHql = "SELECT count(*) from SiteRole WHERE 1 = 1";
			pageView = new PageView();
			pageView.setList(daoImpl.find(searchHql, pageNo, pageSize));
			pageView.setTotalRecord(daoImpl.getCount(countHql));
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new AWSServiceException(SystemReason.SQLError);
		}
		return pageView;
	}

	@Override
	@Transactional
	public void createSiteMenu(SiteMenu siteMenu) {
		try {
			daoImpl.save(siteMenu);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new AWSServiceException(SystemReason.SQLError);
		}

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
	public void deleteSiteMenuById(int id) {
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
	public SiteMenu getSiteMenuById(long id) {
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
	 * @param list 上级菜单列表
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

//	@Override
//	public List<SiteMenu> findSiteMenuListByRoleId(int roleId) {
//		List<SiteMenu> list = new ArrayList<SiteMenu>();
//		try {
//			list = jpaImpl.findByNamedQuery("SiteMenu.findListByRoleId", new Object[] { roleId, null });
//			list = getSubMenus(list, roleId);
//		} catch (NoResultException e) {
//			log.warn("没有找到相应的结果！");
//			return null;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			e.printStackTrace();
//			throw new AWSServiceException(SystemError.SQLError);
//		}
//		return list;
//	}

//	/**
//	 * 根据角色编号获取菜单下级子菜单的方法
//	 * 
//	 * @param list 上级菜单列表
//	 * @param roleId 角色的编号
//	 * @return 返回菜单列表
//	 */
//	private List<SiteMenu> getSubMenus(List<SiteMenu> list, int roleId) {
//		for (SiteMenu siteMenu : list) {
//			List<SiteMenu> subList = jpaImpl.findByNamedQuery("SiteMenu.findListByRoleId", new Object[] { roleId,
//					siteMenu.getId() });
//			if (!subList.isEmpty()) {
//				subList = getSubMenus(subList, roleId);
//				siteMenu.setSubMenus(subList);
//			}
//		}
//		return list;
//	}

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
		String jpql = "select d from DataAccess d where d.parentId = :parentId ";
		try {
			JPQuery query = JPQuery.createQuery(jpql);
			query.setParamater("parentId", null);
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
	 * @param list 上级数据权限列表
	 * @return 返回所有数据权限列表
	 */
	private List<DataAccess> getAllSubDataAccess(List<DataAccess> list) {
		String jpql = "SELECT d FROM DataAccess d WHERE parentId = :parentId ";
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
				+ "(select dataAccessId from RoleAndDataAccess where roleId = :roleId) and parentId = : parentId";
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
	 * @param list 上级数据权限列表
	 * @param roleId 角色的编号
	 * @return 返回数据权限列表
	 */
	private List<DataAccess> getSubDataAccesses(List<DataAccess> list, int roleId) {
		String jpql = "select d from DataAccess d where id in "
				+ "(select dataAccessId from RoleAndDataAccess where roleId = :roleId) and parentId = : parentId";
		JPQuery query = JPQuery.createQuery(jpql);
		
		for (DataAccess da : list) {
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
	public List<SiteUserRole> findAllSiteUserPermission() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SiteUserRole> findSiteUserPermissionListByUserId(long employeeId) {
		List<SiteUserRole> list = new ArrayList<SiteUserRole>();
//		try {
//			list = daoImpl.findByNamedQuery("SiteUserPermission.findListByUser", new Object[] { employeeId });
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			e.printStackTrace();
//			throw new AWSServiceException(SystemError.SQLError);
//		}
		return list;
	}

	@Override
	public List<SiteUserRole> findSiteUserPermissionListByRoleId(int roleId) {
		List<SiteUserRole> list = new ArrayList<SiteUserRole>();
		
//		try {
//			list = daoImpl.findByNamedQuery("SiteUserPermission.findListByRole", new Object[] { roleId });
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			e.printStackTrace();
//			throw new AWSServiceException(SystemError.SQLError);
//		}
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
		JPQuery query = JPQuery.createQuery(jpql);
		query.setParamater("roleId", roleId);
		try {
			daoImpl.delete(query);
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
		JPQuery query = JPQuery.createQuery(jpql);
		query.setParamater("roleId", roleId);
		try {
			list = daoImpl.find(query);
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
        JPQuery query = JPQuery.createQuery(jpql);
        query.setParamater("roleId", roleId);
		try {
			daoImpl.delete(query);
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
		String jpql = "select rada from RoleAndDataAccess rada where roleId = :roleId";
		JPQuery query = JPQuery.createQuery(jpql);
		query.setParamater("roleId", roleId);
		try {
			list = daoImpl.find(query);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new AWSServiceException(SystemReason.SQLError);
		}
		return list;
	}

}
