package pw.cdmi.open.service;

import java.util.List;
import java.util.Map;

import pw.cdmi.collection.PageView;
import pw.cdmi.open.model.entity.People;
import pw.cdmi.open.model.entity.ProtectAQ;
import pw.cdmi.open.model.entity.SiteUser;
import pw.cdmi.open.model.entity.Tenant;
import pw.cdmi.open.model.entity.UserAccount;
import pw.cdmi.open.model.queryObject.UserAccountQuery;

/************************************************************
 * 接口类，提供4A系统用户管理模块的操作方法
 * 
 * @author 佘朝军
 * @version cas Service Platform, 2015-5-4
 ************************************************************/
public interface UserService {

    /**
     * 新增一条公民信息
     * 
     * @param people 公民信息对象实体
     * @return 返回新增公民信息的编号（id）
     */
    public String createPeople(People people);

    /**
     * 判断公民信息是否存在
     * 
     * @param people 公民信息对象实体
     * @return true表示公民信息已存在
     */
    public boolean existPeople(People people);

    /**
     * 修改公民信息
     * 
     * @param people 公民信息对象实体
     */
    public void updatePeople(People people);

    /**
     * 根据id删除公民信息
     * 
     * @param id 公民信息编号
     */
    public void deletePeopleById(String id);

    /**
     * 根据公民信息编号获取公民信息对象实体
     * 
     * @param id 公民信息编号
     * @return 返回公民对象实体
     */
    public People getPeopleById(String id);

    /**
     * 根据公民的身份证信息获得公民信息
     * 
     * @param idCard 公民的身份证
     * @return 返回公民对象实体
     */
    public People getPeopleByIdCode(String idCard);

    /**
     * 根据公民的社保号信息获得公民信息
     * 
     * @param idCard 公民的社保号
     * @return 返回公民对象实体
     */
    public People getPeopleBySocialCode(String socialCode);

    /**
     * 根据公民的驾驶证信息获得公民信息
     * 
     * @param driverNumber 公民的驾驶证号
     * @return 返回公民对象实体
     */
    public People getPeopleByDriverLicenseNumber(String driverNumber);

    /**
     * 根据公民的护照信息获得公民信息
     * 
     * @param passportNumber 公民的护照号
     * @return 返回公民对象实体
     */
    public People getPeopleByPassportNumber(String passportNumber);

    /**
     * 根据公民的几个关键信息获得公民信息,必须有一个参数不为空
     * 
     * @param idCard 公民的身份证
     * @param passportNumber 公民的护照号
     * @param socialSecurityCode 公民的社保号
     * @param driverLicenseNumber 公民的驾驶证
     * 
     * @return 返回公民对象实体
     */
    public People getPeopleByKeyFields(String idCard, String passportNumber, String socialSecurityCode,
        String driverLicenseNumber);

    /**
     * 根据外键SiteUser编号获取公民信息对象实体
     * 
     * @param userId 用户在SiteUser中的编号
     * @return 返回公民对象实体
     */
    public People getPeopleByUserId(long userId);

    /**
     * 根据外键UserAccount编号获取公民信息对象实体
     * 
     * @param accountId 用户在UserAccount中的编号
     * @return 返回公民对象实体
     */
    public People getPeopleByAccountId(long accountId);

    /**
     * 查找所有公民信息列表
     * 
     * @return 返回公民信息对象列表
     */
    public Iterable<People> findAllPeople();

    /**
     * 根据条件查询公民信息列表并进行分页展示
     * 
     * @param pageNo 页码
     * @param pageSize 每页最大显示数
     * @param queryObject 条件参数封装的对象
     * @return 返回PageView对象，封装了当前查询页的记录列表和待查询数据的总记录数
     */
    public PageView findPeopleByConditionAndPage(int pageNo, int pageSize, People queryObject);

    /**
     * 创建账号
     * 
     * @param userName 登录名
     * @param password 密码
     * @param employeeId 员工信息编号
     */
    public void createUserAccount(UserAccount userAccount);

    /**
     * 判断用户帐号是否已存在
     * 
     * @param userAccount 用户帐号信息
     * @return true为帐号信息已经存在
     */
    public boolean existUserAccount(UserAccount userAccount);

    /**
     * 为员工创建账号
     * 
     * @param userName 登录名
     * @param password 密码
     * @param employeeId 员工信息编号
     */
    public void createUserAccountForEmployee(String userName, String password, String employeeId);

    /**
     * 修改账号信息
     * 
     * @param userAccount 账号实体对象
     */
    public void updateUserAccount(UserAccount userAccount);

    /**
     * 
     * 修改指定用户的密码.
     * 
     * @param newpassword
     * @param username
     */
    public void updatePassword(String newpassword, String username);

    /**
     * 
     * 设置指定用户的密码.
     * 
     * @param newpassword
     * @param accountId
     */
    public void setPassword(String newpassword, String accountId);

    /**
     * 根据公民信息编号删除账号
     * 
     * @param peopleId 公民信息的编号
     */
    public void deleteUserAccountByPeopleId(String peopleId);

    /**
     * 根据id查找账号实体
     * 
     * @param id 账号信息的编号
     * @return 返回账号实体对象
     */
    public UserAccount getUserAccountById(String id);

    /**
     * 根据账号和密码查找账号实体
     * 
     * @param userName
     * @param hash_password
     * @return
     */
    public UserAccount findUserAccountByUserNameAndPassword(String userName,String hash_password);
    
    /**
     * 根据角色名称获取应用的初始化帐号，初始化账户与角色具有唯一性
     * 
     * @param roleName 角色的名称
     * @return
     */
    public UserAccount getInitAccount(String roleName);

    /**
     * 根据账号名查找账号实体
     * 
     * @param username 账号名
     * @return 返回账号实体对象
     */
    public UserAccount getUserAccountByUsername(String username);

    /**
     * 根据手机号码查找账号实体
     * 
     * @param mobile 手机号码
     * @return 返回账号实体对象
     */
    public UserAccount getUserAccountByMobile(String mobile);

    /**
     * 根据邮件地址查找账号实体
     * 
     * @param email 邮件地址
     * @return 返回账号实体对象
     */
    public UserAccount getUserAccountByEmail(String email);

    /**
     * 根据账号的几个关键不重复的信息查找账号实体
     * 
     * @param username 账号名
     * @param email 邮件地址
     * @param mobile 手机号码
     * @return 返回账号实体对象
     */
    public UserAccount getUserAccountByKeyFields(String username, String email, String mobile);

    /**
     * 根据peopleId查找账号实体
     * 
     * @param peopleId 公民信息的编号
     * @return 返回账号实体对象
     */
    public UserAccount getUserAccountByPeopleId(String peopleId);

    /**
     * 根据账号和密码查找账号实体
     * 
     * @param userName 用户的账号
     * @param password 用户的密码
     * @return 返回账号实体对象
     */
    public UserAccount getUserAccountByUserNameAndPassword(String userName, String password);

    /**
     * 获取所有账号
     * 
     * @return 返回账号信息实体对象列表
     */
    public Iterable<UserAccount> findAllUserAccount();

    /**
     * 获得指定站点下的所有账号
     * 
     * @return 返回账号信息实体对象列表
     */
    public Iterable<UserAccount> findAllUserAccountBySite(String siteId);

    /**
     *  根据条件查询账号信息列表并进行分页展示
     * 
     * @param pageNo 当前页码
     * @param pageSize 每页显示最大记录数
     * @param queeryObject 条件参数封装的对象
     * @return 返回PageView对象，封装了当前查询页的记录列表和待查询数据的总记录数
     */
    public PageView findUserAccountByConditionAndPage(int pageNo, int pageSize, UserAccount queeryObject);

    public void createProtectAQ(ProtectAQ protectAQ);

    public void createProtectAQs(long userAccountId, List<Map<String, String>> protectAQList);

    public void updateProtectAQ(ProtectAQ protectAQ);

    public void deleteProtectAQById(long id);

    public List<ProtectAQ> findProtectAQListByUserAccountId(long userAccountId);

    public ProtectAQ findSingleProtectAQByUserAccountIdAndRandom(long userAccountId);

    public ProtectAQ findProtectAQById(long id);

    public void createSiteUser(String appId, SiteUser siteUser);

    public void updateSiteUser(String appId, SiteUser siteUser);

    public void updateSiteUser(SiteUser siteUser);

    public void deleteSiteUserById(String id);

    public void deleteSiteUserByAccountId(String appId, long accountId);

    public void deleteSiteUserByEmployeeId(String employeeId);

    public SiteUser getSiteUserById(String id);

    public SiteUser getSiteUserByAccountAndUserDomain(String appId, String userDomain, String accountId);

    public List<SiteUser> findSiteUserBySiteId(String appId);

    public PageView findSiteUserByConditionAndPage(int pageNo, int pageSize, SiteUser queryObject);

    public SiteUser getSiteUserByAccountId(String accountId);

    /**
     * 
     * 为指定应用创建一个租户信息.
     * 
     * @param appId
     * @param tenant
     */
    public void createTenant(String appId, Tenant tenant);

    /**
     * 
     * 修改指定应用下的指定租户信息.
     * 
     * @param appId
     * @param tenant
     */
    public void updateTenant(String appId, Tenant tenant);

    /**
     * 
     * 删除指定应用下的指定租户.
     * 
     * @param appId
     * @param tenantId
     */
    public void deleteTenant(String appId, long tenantId);

    /**
     * 
     * 根据指定的租户编号获得租户的信息.
     * 
     * @param tenantId
     * @return 租户信息
     */
    public Tenant getTenantById(long tenantId);

    /**
     * 
     * 根据指定的账号编号获得租户的信息.
     * 
     * @param tenantId
     * @return 租户信息
     */
    public Tenant getTenantByAccountId(long accountId);

    /**
     * 账号分页展示信息
     * 
     * @param userAccountQuery
     * @param page
     * @param pageSize
     * @return
     */
    public PageView findUserAccountList(UserAccountQuery userAccountQuery, Integer page, Integer pageSize);

    /**
     * 查找有IT权限的指定姓名的用户是否存在
     * @param username
     * @return  存在的用户信息
     */
    public UserAccount getUserAccountByIdAndStatus(String id);

}
