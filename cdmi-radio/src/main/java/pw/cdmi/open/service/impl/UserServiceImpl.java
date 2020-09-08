package pw.cdmi.open.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pw.cdmi.collection.PageView;
import pw.cdmi.core.db.GenericDao;
import pw.cdmi.core.db.JPQuery;
import pw.cdmi.core.http.exception.AWSClientException;
import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.msm.geo.ClientReason;
import pw.cdmi.open.ClientError;
import pw.cdmi.open.model.UserStatus;
import pw.cdmi.open.model.entity.Employee;
import pw.cdmi.open.model.entity.People;
import pw.cdmi.open.model.entity.ProtectAQ;
import pw.cdmi.open.model.entity.SiteApplication;
import pw.cdmi.open.model.entity.SiteUser;
import pw.cdmi.open.model.entity.SiteUserRole;
import pw.cdmi.open.model.entity.Tenant;
import pw.cdmi.open.model.entity.UserAccount;
import pw.cdmi.open.model.queryObject.UserAccountQuery;
import pw.cdmi.open.service.BusinessOrganizationService;
import pw.cdmi.open.service.EmployeeService;
import pw.cdmi.open.service.PermissionService;
import pw.cdmi.open.service.SingleSiteApplicationService;
import pw.cdmi.open.service.UserService;
import pw.cdmi.utils.UUIDUtils;

/************************************************************
 * 实现类，提供4A系统中用户管理模块的操作方法
 * 
 * @author 佘朝军
 * @version cas Service Platform, 2015-5-4
 ************************************************************/
@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private GenericDao daoImpl;

    @Autowired
    private SingleSiteApplicationService appService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private BusinessOrganizationService organizationService;

    @Override
    @Transactional
    public String createPeople(People people) {

        if (!existPeople(people)) {
            try {
                people.setOpenId(UUIDUtils.getUUIDTo64());
                people.setId(null);
                daoImpl.save(people);
            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
                throw new AWSServiceException(SystemReason.SQLError);
            }
        } else {
            throw new AWSClientException(ClientError.DataAlreadyExists, ClientReason.DataAlreadyExists);
        }

        return people.getId();

    }

    @Override
    public boolean existPeople(People people) {
        // 检查身份信息是否已经存在于系统中了，需要比较足以表明身份信息的关键信息
        People p = this.getPeopleByKeyFields(people.getIdCard(), people.getPassportNumber(),
            people.getSocialSecurityCode(), people.getDriverLicenseNumber());
        if (p != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public void updatePeople(People people) {
        // FIXME 不应该将用户的关键信息进行修改，关键系统的修改需要独立的方法。
        try {
            daoImpl.update(people);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }

    }

    @Override
    @Transactional
    public void deletePeopleById(String id) {
        try {
            People people = getPeopleById(id);
            if (people != null) {
                daoImpl.delete(people);
                // TODO 删除级联到此people的其它信息数据
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }

    }

    @Override
    public People getPeopleById(String id) {
        People people = null;
        try {
            people = daoImpl.get(id, People.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return people;
    }

    @Override
    public People getPeopleByIdCode(String idCard) {
        String sql = "from People where idCard = '" + idCard + "'";
        return daoImpl.findOne(sql);
    }

    @Override
    public People getPeopleBySocialCode(String socialCode) {
        String sql = "from People where socialSecurityCode = '" + socialCode + "'";
        return daoImpl.findOne(sql);
    }

    @Override
    public People getPeopleByDriverLicenseNumber(String driverNumber) {
        String sql = "from People where driverLicenseNumber = '" + driverNumber + "'";
        return daoImpl.findOne(sql);
    }

    @Override
    public People getPeopleByPassportNumber(String passportNumber) {
        String sql = "from People where passportNumber = '" + passportNumber + "'";
        return daoImpl.findOne(sql);
    }

    @Override
    public People getPeopleByKeyFields(String idCard, String passportNumber, String socialSecurityCode,
        String driverLicenseNumber) {

        StringBuilder hql = new StringBuilder("from People where ");
        if (!StringUtils.isEmpty(idCard)) {
            hql.append("idCard = '" + idCard + "'");
        }
        if (!StringUtils.isEmpty(passportNumber)) {
            if (!StringUtils.isEmpty(idCard)) {
                hql.append(" or passportNumber ='" + passportNumber + "'");
            } else {
                hql.append(" passportNumber ='" + passportNumber + "'");
            }
        }
        if (!StringUtils.isEmpty(socialSecurityCode)) {
            if ((!StringUtils.isEmpty(idCard) || !StringUtils.isEmpty(passportNumber))) {
                hql.append(" or socialSecurityCode ='" + socialSecurityCode + "'");
            } else {
                hql.append(" socialSecurityCode ='" + socialSecurityCode + "'");
            }
        }
        if (!StringUtils.isEmpty(driverLicenseNumber)) {
            if ((!StringUtils.isEmpty(idCard) || !StringUtils.isEmpty(passportNumber))
                    || !StringUtils.isEmpty(socialSecurityCode)) {
                hql.append(" or driverLicenseNumber ='" + driverLicenseNumber + "'");
            } else {
                hql.append(" driverLicenseNumber ='" + driverLicenseNumber + "'");
            }
        }
        return daoImpl.findOne(hql.toString());
    }

    @Override
    public People getPeopleByUserId(long userId) {
        String hql = "from People p, SiteUser s, UserAccount a where p.id = a.peopleId and a.id= s.accountId and s.id="
                + userId;
        return daoImpl.findOne(hql);
    }

    @Override
    public People getPeopleByAccountId(long accountId) {
        String hql = "from People p, UserAccount a where a.accountId=" + accountId + " and a.peopleId=p.id";
        return daoImpl.findOne(hql);
    }

    @Deprecated
    @Override
    public Iterable<People> findAllPeople() {
        Iterable<People> list = new ArrayList<People>();
        try {
            list = daoImpl.findAll(People.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public PageView findPeopleByConditionAndPage(int pageNo, int pageSize, People queryObject) {
        // TODO Auto-generated method stub
        return null;
    }

    @Transactional
    public void createUserAccount(UserAccount account) {
        if (existUserAccount(account)) {
            throw new AWSClientException(ClientError.DataAlreadyExists, ClientReason.DataAlreadyExists);
        }

        if (StringUtils.isEmpty(account.getEmail())) {
            throw new AWSClientException(ClientError.IncompleteBody, ClientReason.IncompleteBody);
        }
        account.setRegisterTime(new Date());
        account.setOpenId(UUIDUtils.getUUIDTo64());
        if (UserStatus.NotActive == account.getStatus()) {
            // TODO 向用户的邮件地址中发送一封注册邮件，用户点击后，修改初始密码后，账号状态才能变为正常，用户才能进行登录。
        }
        if (UserStatus.NeedChangePassword == account.getStatus()) {
            // TODO 向用户的邮件地址中发送一封注册邮件确认后，用户的账号进入生效。
        }
        // 对用户的密码进行加密处理
        BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
        String hash_password = bcryptEncoder.encode(account.getPassword());
        account.setPassword(hash_password);
        daoImpl.save(account);

    }

    @Override
    public boolean existUserAccount(UserAccount account) {
        UserAccount userAccount = this.getUserAccountByKeyFields(account.getUserName(), account.getEmail(),
            account.getMobile());
        if (userAccount != null) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void createUserAccountForEmployee(String userName, String password, String employeeId) {
        if (userName == null) {
            throw new AWSServiceException(SystemReason.InvalidRequest);
        }
        if (password == null) {
            throw new AWSServiceException(SystemReason.InvalidRequest);
        }
        Employee employee = employeeService.getSingleEmployeeById(employeeId);

        UserAccount userAccount = new UserAccount();
        userAccount.setPassword(password);
        userAccount.setUserName(userName);
        userAccount.setNickName(employee.getName());
        userAccount.setEmail(employee.getEmail());
        userAccount.setStatus(UserStatus.OK);
        if (StringUtils.isNotBlank(employee.getTelephone())) {
            userAccount.setMobile(employee.getTelephone());
        }
        createUserAccount(userAccount);

        /*
         * employee.setAccountId(userAccount.getId());
         * employeeService.updateEmployee(employee);
         */

        SiteUser user = new SiteUser();
        user.setAccountId(userAccount.getId());
        user.setNickName(employee.getName());
        user.setStatus(UserStatus.OK);
        String appId = appService.getCurrentAppId();
        createSiteUser(appId, user);

        // 更新雇员表，给雇员发放IT权限
        employee.setAccountId(userAccount.getId());
        organizationService.updateEmployee(employee);

        permissionService.createSiteUserRole(appId, user.getId(), "Operator", 0);

    }

    @Override
    @Transactional
    public void updateUserAccount(UserAccount userAccount) {
        daoImpl.update(userAccount);
    }

    @Override
    public void updatePassword(String newpassword, String username) {
        UserAccount account = daoImpl.findOne("from UserAccount ua where ua.userName='" + username + "'");
        // 对用户的密码进行加密处理
        BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
        String hash_password = bcryptEncoder.encode(account.getPassword());
        account.setPassword(hash_password);
        daoImpl.update(account);
    }

    @Override
    public void setPassword(String newpassword, String accountId) {
        UserAccount account = daoImpl.findOne("from UserAccount ua where ua.id = " + accountId);
        // 对用户的密码进行加密处理
        BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
        String hash_password = bcryptEncoder.encode(account.getPassword());
        account.setPassword(hash_password);
        daoImpl.update(account);
    }

    @Override
    @Transactional
    public void deleteUserAccountByPeopleId(String peopleId) {
        String jpql = "delete from UserAccount where peopleId = : peopleId";
        JPQuery jPQ = JPQuery.createQuery(jpql);
        jPQ.setParamater("peopleId", peopleId);
        try {
            daoImpl.delete(jPQ);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }

    }

    @Override
    public UserAccount getUserAccountById(String id) {
        UserAccount userAccount = null;
        try {
            userAccount = daoImpl.get(id, UserAccount.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return userAccount;
    }

    @Override
    public UserAccount getInitAccount(String roleName) {
        SiteApplication app = appService.getAccessAplication();
        if (app == null) {
            return null;
        }
        String hql = "select a from UserAccount a,SiteRole r,SiteUserRole ar,SiteUser u where ar.siteId ='"
                + app.getAppId() + "' and r.name='" + roleName
                + "' and ar.userId=u.id and u.accountId=a.id and ar.roleEnum=r.roleEnum";
        return daoImpl.findOne(hql);
    }
    
    @Override
    public UserAccount findUserAccountByUserNameAndPassword(String userName, String hash_password) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserAccount getUserAccountByPeopleId(String peopleId) {
        String jpql = "select ua from UserAccount ua where peopleId = :peopleId";
        JPQuery jPQ = JPQuery.createQuery(jpql);
        jPQ.setParamater("peopleId", peopleId);

        UserAccount userAccount = daoImpl.findOne(jPQ);
        return userAccount;
    }

    @Override
    public UserAccount getUserAccountByUserNameAndPassword(String userName, String password) {
        String jpql = "select ua from UserAccount ua where userName = :userName and password = :password ";
        JPQuery jPQ = JPQuery.createQuery(jpql);
        jPQ.setParamater("userName", userName);
        jPQ.setParamater("password", password);
        return daoImpl.findOne(jPQ);
    }

    @Override
    public UserAccount getUserAccountByUsername(String username) {
        try {
            UserAccount userAccount = daoImpl.findOne("from UserAccount where userName='" + username + "'");
            return userAccount;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public UserAccount getUserAccountByIdAndStatus(String id) {
        try {
            UserAccount userAccount = daoImpl
                .findOne("from UserAccount where id='" + id + "' and status <> 'ToDeleted'");
            return userAccount;
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public UserAccount getUserAccountByMobile(String mobile) {
        return daoImpl.findOne("from UserAccount ua where ua.mobile='" + mobile + "'");
    }

    @Override
    public UserAccount getUserAccountByEmail(String email) {
        return daoImpl.findOne("from UserAccount ua where ua.email='" + email + "'");
    }

    @Override

    public UserAccount getUserAccountByKeyFields(String username, String email, String mobile) {
        StringBuilder hql = new StringBuilder("from UserAccount where ");
        if (!StringUtils.isEmpty(username)) {
            hql.append("userName = '" + username + "'");
        }
        if (!StringUtils.isEmpty(email)) {
            if (!StringUtils.isEmpty(username)) {
                hql.append(" or email ='" + email + "'");
            } else {
                hql.append(" email ='" + email + "'");
            }
        }
        if (!StringUtils.isEmpty(mobile)) {
            if ((!StringUtils.isEmpty(username) || !StringUtils.isEmpty(email))) {
                hql.append(" or mobile ='" + mobile + "'");
            } else {
                hql.append(" mobile ='" + mobile + "'");
            }
        }
        return daoImpl.findOne(hql.toString());
    }

    @Override
    public Iterable<UserAccount> findAllUserAccount() {
        Iterable<UserAccount> list = new ArrayList<UserAccount>();
        list = daoImpl.findAll(UserAccount.class);
        return list;
    }

    @Override
    public List<UserAccount> findAllUserAccountBySite(String siteId) {
        List<UserAccount> list = new ArrayList<UserAccount>();
        String hql = "select a from UserAccount a, SiteUser s where a.id = s.accountId and s.appId='" + siteId + "'";
        list = daoImpl.find(hql);
        return list;
    }

    @Override
    public PageView findUserAccountByConditionAndPage(int pageNo, int pageSize, UserAccount queeryObject) {
        // TODO Auto-generated method stub
        return null;
    }

    @Transactional
    public void createProtectAQ(ProtectAQ protectAQ) {
        try {
            daoImpl.save(protectAQ);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    public void createProtectAQs(long userAccountId, List<Map<String, String>> protectAQList) {
        try {
            for (Map<String, String> map : protectAQList) {
                ProtectAQ protectAQ = new ProtectAQ();
                protectAQ.setQuestion(map.get("question"));
                protectAQ.setAnswer(map.get("answer"));
                protectAQ.setUserAccountId(userAccountId);
                createProtectAQ(protectAQ);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    public void updateProtectAQ(ProtectAQ protectAQ) {
        // TODO
    }

    public void deleteProtectAQById(long id) {
        // TODO
    }

    public ProtectAQ findProtectAQById(long id) {
        ProtectAQ protectAQ = null;
        try {
            protectAQ = daoImpl.get(id, ProtectAQ.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return protectAQ;
    }

    public List<ProtectAQ> findProtectAQListByUserAccountId(long userAccountId) {
        List<ProtectAQ> list = new ArrayList<ProtectAQ>();
        String jpql = "from ProtectAQ where userAccountId = :accountId";
        JPQuery jPQ = JPQuery.createQuery(jpql);
        jPQ.setParamater("accountId", userAccountId);
        try {
            list = daoImpl.find(jPQ);
        } catch (NoResultException e) {
            log.warn("未找到密保信息！");
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    public ProtectAQ findSingleProtectAQByUserAccountIdAndRandom(long userAccountId) {
        ProtectAQ protectAQ = null;
        try {
            List<ProtectAQ> list = findProtectAQListByUserAccountId(userAccountId);
            if (!list.isEmpty()) {
                Random random = new Random();
                int index = random.nextInt(list.size());
                protectAQ = list.get(index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return protectAQ;
    }

    @Override
    @Transactional
    public void createSiteUser(String appId, SiteUser siteUser) {
        siteUser.setAppId(appId);
        siteUser.setRegisterTime(new Date());
        try {
            daoImpl.save(siteUser);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AWSServiceException(SystemReason.SQLError);
        }

    }

    @Override
    @Transactional
    public void updateSiteUser(String appId, SiteUser siteUser) {
        // TODO Auto-generated method stub

    }

    @Override
    @Transactional
    public void updateSiteUser(SiteUser siteUser) {
        daoImpl.update(siteUser);

    }

    @Override
    @Transactional
    public void deleteSiteUserById(String id) {
        SiteUser user = this.getSiteUserById(id);
        if (user == null) {
            throw new AWSClientException(ClientError.NoFoundData, ClientReason.NoFoundData);
        }
        // 删除应用用户对应的角色的关系
        String queryString = "from SiteUserRole up where userId=" + id;
        List<SiteUserRole> userRoles = daoImpl.find(queryString);
        for (SiteUserRole userRole : userRoles) {
            daoImpl.delete(userRole);
        }
        // TODO 继续删除该用户的其他信息
        // 删除应用用户
        daoImpl.delete(user);

    }

    @Override
    @Transactional
    public void deleteSiteUserByAccountId(String appId, long accountId) {
        String jpql = "delete from SiteUser where accountId = :accountId and  appId = :appId";
        JPQuery jPQ = JPQuery.createQuery(jpql);
        jPQ.setParamater("appId", appId);
        jPQ.setParamater("accountId", accountId);
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
    public void deleteSiteUserByEmployeeId(String employeeId) {
        String jpql = "delete from SiteUser where accountId = :employeeId ";
        JPQuery jPQ = JPQuery.createQuery(jpql);
        jPQ.setParamater("employeeId", employeeId);
        try {
            daoImpl.delete(jPQ);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public SiteUser getSiteUserById(String id) {
        SiteUser user = null;
        try {
            user = daoImpl.get(id, SiteUser.class);
        } catch (Exception e) {
            log.error(e.toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return user;
    }

    @Override
    public SiteUser getSiteUserByAccountId(String accountId) {
        String hql = null;
        hql = "from SiteUser where accountId='" + accountId + "'";
        return daoImpl.findOne(hql);
    }

    @Override
    public SiteUser getSiteUserByAccountAndUserDomain(String appId, String userDomain, String accountId) {
        String hql = null;
        if (StringUtils.isEmpty(userDomain)) {
            hql = "from SiteUser where appId='" + appId + "' and accountId=" + accountId;
        } else {
            hql = "from SiteUser where appId='" + appId + "' and userDomain='" + userDomain + "' and accountId="
                    + accountId;
        }
        return daoImpl.findOne(hql);
    }

    @Override
    public PageView findSiteUserByConditionAndPage(int pageNo, int pageSize, SiteUser queryObject) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SiteUser> findSiteUserBySiteId(String appId) {
        String hql = "from SiteUser where appId='" + appId + "'";
        return daoImpl.find(hql);
    }

    @Override
    @Transactional
    public void createTenant(String appId, Tenant tenant) {
        tenant.setAppId(appId);
        try {
            daoImpl.save(tenant);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void updateTenant(String appId, Tenant tenant) {
        if (appId == null || (tenant.getAppId() != null && !appId.equalsIgnoreCase(tenant.getAppId()))) {
            throw new AWSClientException(ClientError.InvalidRequest, ClientReason.InvalidParameter);
        }
        tenant.setAppId(appId);
        daoImpl.update(tenant);
    }

    @Override
    @Transactional
    public void deleteTenant(String appId, long tenantId) {
        Tenant tenant = this.getTenantById(tenantId);
        if (tenant == null || !appId.equalsIgnoreCase(tenant.getAppId())) {
            throw new AWSClientException(ClientError.InvalidRequest, ClientReason.InvalidParameter);
        }
        daoImpl.delete(tenant);
    }

    @Override
    public Tenant getTenantById(long tenantId) {
        String hql = "from Tenant where id= " + tenantId;
        return daoImpl.findOne(hql);
    }

    @Override
    public Tenant getTenantByAccountId(long accountId) {
        String hql = "from Tenant where mangerId= " + accountId;
        return daoImpl.findOne(hql);
    }

    @Override
    public PageView findUserAccountList(UserAccountQuery userAccountQuery, Integer page, Integer pageSize) {
        PageView pageView = new PageView();

        try {
            List<UserAccount> list = daoImpl.find(userAccountQuery.createQuery(), page, pageSize);
            long total = daoImpl.getCount(userAccountQuery.createPageCountQuery());
            pageView.setList(list);
            pageView.setTotalRecord(total);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return pageView;
    }

}
