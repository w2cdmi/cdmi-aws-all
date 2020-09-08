package pw.cdmi.open.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;
import pw.cdmi.collection.PageView;
import pw.cdmi.core.http.exception.AWSClientException;
import pw.cdmi.msm.geo.ClientReason;
import pw.cdmi.open.ClientError;
import pw.cdmi.open.model.entity.DataAccess;
import pw.cdmi.open.model.entity.Employee;
import pw.cdmi.open.model.entity.SiteMenu;
import pw.cdmi.open.model.entity.SiteMenuAcl;
import pw.cdmi.open.model.entity.SiteRole;
import pw.cdmi.open.model.entity.SiteUser;
import pw.cdmi.open.model.entity.SiteUserRole;
import pw.cdmi.open.service.EmployeeService;
import pw.cdmi.open.service.PermissionService;
import pw.cdmi.open.service.SingleSiteApplicationService;
import pw.cdmi.open.service.UserService;

/************************************************************
 * 控制类，提供处理权限管理模块的请求操作
 * 
 * @author 佘朝军
 * @version iSoc Service Platform, 2015-5-13
 ************************************************************/
@Controller
@RequestMapping(value = "/permission")
public class PermissionController {

    private static final Logger log = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SingleSiteApplicationService appService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/role/create", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject createRole(SiteRole siteRole) {
        JSONObject json = new JSONObject();
        siteRole.setCustomRole(true);
        String appId = appService.getCurrentAppId();
        try {
            permissionService.createSiteRole(appId, null, siteRole);
            json.put("msg", "success");
        } catch (Exception e) {
            json.put("msg", e.getMessage());
        }
        return json;
    }

    @RequestMapping(value = "/role/updateRole", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateRole(SiteRole siteRole) {
        JSONObject json = new JSONObject();
        siteRole.setCustomRole(true);
        String appId = appService.getCurrentAppId();
        try {
            permissionService.updateSiteRole(appId, siteRole);
            json.put("msg", "success");
        } catch (Exception e) {
            json.put("msg", e.getMessage());
        }
        return json;
    }

    @RequestMapping(value = "/role/deleteSiteRoleById/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteAsset(@PathVariable String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new AWSClientException(ClientError.InvalidRequest, ClientReason.InvalidRequest);
        }
        String[] idStrings = ids.split(",");
        boolean b = true;
        for (String id : idStrings) {
            List<SiteUserRole> siteUserRoles = findSiteUserPermissionByRoleId(Integer.parseInt(id));
            b = permissionService.deleteSiteRoleById((Integer.parseInt(id))) && b;
            if (null != siteUserRoles) {
                for (SiteUserRole siteUserRole : siteUserRoles) {
                    siteUserRole.setRoleId(0);
                    permissionService.updateSiteUserRole(siteUserRole);
                }
            }
        }
        return b;
    }

    @RequestMapping(value = "/role/findListByPage", method = RequestMethod.GET)
    @ResponseBody
    public PageView findSiteRoleList(Integer page, Integer pageSize) {
        PageView pageView = new PageView();
        try {
            String appId = appService.getCurrentAppId();
            pageView = permissionService.findSiteRoleByPage(appId, null, page, pageSize);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return pageView;
    }

    @RequestMapping(value = "/role/list", method = RequestMethod.GET)
    @ResponseBody
    public List<SiteRole> findSiteRoleList() {
        String appId = appService.getCurrentAppId();
        return permissionService.findSiteRole(appId, null);

    }

    @RequestMapping(value = "/role/userId/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public List<SiteRole> findRoleListByUser(Long userId) {
        String appId = appService.getCurrentAppId();
        return permissionService.findSiteRolesByUserId(appId, null, userId);
    }

    @RequestMapping(value = "/userPermission/roleId/{roleId}/userIds/{userIds}", method = RequestMethod.POST)
    @ResponseBody
    public void createUserPermissionByRole(@PathVariable(value = "userIds") List<String> userIds,
        @PathVariable(value = "roleId") String roleEnum) {
        String appId = appService.getCurrentAppId();
        permissionService.createSiteUserPermissionByRole(appId, null, roleEnum, userIds);

    }

    @RequestMapping(value = "/userPermission/userId/{userId}/roleIds/{roleIds}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> createUserPermissionByUser(@PathVariable(value = "userId") String userId,
        @PathVariable(value = "roleIds") List<Integer> roleIds) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            String appId = appService.getCurrentAppId();
            permissionService.createSiteUserPermissionByUser(appId, null, userId, roleIds);
            map.put("message", "success");
        } catch (Exception e) {
            map.put("message", e.getMessage());
        }
        return map;

    }

    @RequestMapping(value = "/role/deleteRoleByUser", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, String> deleteRoleByUser(String userId) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            permissionService.deleteSiteUserPermissionByUser(userId);
            map.put("message", "success");
        } catch (Exception e) {
            map.put("message", "failure");
        }
        return map;
    }

    @RequestMapping(value = "/menu/create", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> createMenu(SiteMenu siteMenu) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            siteMenu.setSiteId("1");
            siteMenu.setModelId(1);
            permissionService.createSiteMenu(siteMenu);
            map.put("message", "success");
        } catch (Exception e) {
            map.put("message", "failure");
        }
        return map;
    }

    @RequestMapping(value = "/menu/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> updateMenu(SiteMenu siteMenu) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            if (null != siteMenu.getId()) {
                SiteMenu menu = permissionService.getSiteMenuById(siteMenu.getId());
                if (null != menu) {
                    if (!siteMenu.getBeClass()) {
                        List<SiteMenu> subMenus = permissionService.findAllSiteMenuByParentId(menu.getId());
                        if (subMenus.size() > 0) {
                            map.put("message", "haveSub");
                            return map;
                        }
                    }
                    menu.setName(siteMenu.getName());
                    menu.setOrderNumber(siteMenu.getOrderNumber());
                    menu.setParentId(siteMenu.getParentId());
                    menu.setBeClass(siteMenu.getBeClass());
                    menu.setResourceURL(siteMenu.getResourceURL());
                    permissionService.updateSiteMenu(menu);
                }
                map.put("message", "success");
            }
        } catch (Exception e) {
            map.put("message", "error");
        }
        return map;
    }

    @RequestMapping(value = "/menu/findAll", method = RequestMethod.GET)
    @ResponseBody
    public List<SiteMenu> findAllMenu() {
        return permissionService.findAllSiteMenu();
    }

    @RequestMapping(value = "/menu/findAllWithSub", method = RequestMethod.GET)
    @ResponseBody
    public List<SiteMenu> findAllMenuWithSub() {
        return permissionService.findAllSiteMenuWithSub();
    }

    @RequestMapping(value = "/menu/findAllBeClass", method = RequestMethod.GET)
    @ResponseBody
    public List<SiteMenu> findAllSiteMenuBeClass() {
        return permissionService.findAllSiteMenuBeClass();
    }

    @RequestMapping(value = "/menu/findListByRole", method = RequestMethod.GET)
    @ResponseBody
    public List<SiteMenu> findMenuByRoleId(Integer roleId) {
        // permissionService.findSiteMenuListByRoleId(roleId);
        return null;
    }

    @RequestMapping(value = "/menu/addMenuToRole", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> createMenuAcl(@RequestParam(value = "roleIds[]", required = false) List<Integer> roleIds,
        @RequestParam(value = "menuIds[]", required = false) List<Long> menuIds) {
        Map<String, String> map = new HashMap<String, String>();
        if (menuIds == null) {
            menuIds = new ArrayList<Long>();
        }
        if (roleIds == null) {
            roleIds = new ArrayList<Integer>();
        }
        try {
            permissionService.createSiteMenuAcl(roleIds, menuIds);
            map.put("message", "success");
        } catch (Exception e) {
            map.put("message", "failure");
        }
        return map;
    }

    @RequestMapping(value = "/menu/deleteMenuByRole", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, String> deleteMenuByRoleId(Integer roleId) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            permissionService.deleteSiteMenuAclByRoleId(roleId);
            map.put("message", "success");
        } catch (Exception e) {
            map.put("message", "failure");
        }
        return map;
    }

    @RequestMapping(value = "/menu/deleteMenuByMenuId/{menuId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, String> deleteMenuByMenuId(@PathVariable Long menuId) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            permissionService.deleteSiteMenuByMenuId(menuId);
            map.put("message", "success");
        } catch (Exception e) {
            map.put("message", "failure");
        }
        return map;
    }

    @RequestMapping(value = "/dataAccess/findAll", method = RequestMethod.GET)
    @ResponseBody
    public List<DataAccess> findAllDataAccess() {
        return permissionService.findAllDataAccess();
    }

    @RequestMapping(value = "/dataAccess/findListByRole", method = RequestMethod.GET)
    @ResponseBody
    public List<DataAccess> findDataAccessByRoleId(Integer roleId) {
        return permissionService.findDataAccessListByRoleId(roleId);
    }

    @RequestMapping(value = "/dataAccess/addDataAccessToRole", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> createRoleAndDataAccess(@RequestParam(value = "roleIds[]") List<Integer> roleIds,
        @RequestParam(value = "dataAccessIds[]") List<Integer> dataAccessIds) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            permissionService.createRoleAndDataAccess(roleIds, dataAccessIds);
            map.put("message", "success");
        } catch (Exception e) {
            map.put("message", "failure");
        }
        return map;
    }

    @RequestMapping(value = "/dataAccess/deleteDataAccessByRole", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, String> deleteDataAccessByRoleId(Integer roleId) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            permissionService.deleteRoleAndDataAccessByRoleId(roleId);
            map.put("message", "success");
        } catch (Exception e) {
            map.put("message", "failure");
        }
        return map;
    }

    @RequestMapping(value = "/menuAcl/roleId/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public List<SiteMenuAcl> findSiteMenuAclByRoleId(@PathVariable("roleId") Integer roleId) {
        return permissionService.findSiteMenuAclListByRoleId(roleId);
    }

    @RequestMapping(value = "/userPermission/roleId/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public List<SiteUserRole> findSiteUserPermissionByRoleId(@PathVariable("roleId") Integer roleId) {
        return permissionService.findSiteUserRoleByRoleId(roleId);
    }

    @RequestMapping(value = "/userPermission/employeeId/{employeeId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findSiteUserPermissionByUserId(@PathVariable("employeeId") String employeeId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Employee employee = employeeService.getSingleEmployeeById(employeeId);
        if (employee.getAccountId() == null) {
            map.put("message", "该员工未创建登录账号！");
        } else {
            SiteUser user = userService.getSiteUserByAccountAndUserDomain(appService.getCurrentAppId(), null,
                employee.getAccountId());
            if (user != null) {
                List<SiteUserRole> list = (List<SiteUserRole>) permissionService.findSiteUserRoleByUserId(user.getId());
                map.put("message", "success");
                map.put("datas", list);
                map.put("userId", user.getId());
            } else {
                map.put("message", "该员工尚未拥有此应用的用户信息！");
            }
        }
        return map;
    }

}
