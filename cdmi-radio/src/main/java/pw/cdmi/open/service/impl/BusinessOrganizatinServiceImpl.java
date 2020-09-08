package pw.cdmi.open.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import pw.cdmi.collection.PageView;
import pw.cdmi.core.db.GenericDao;
import pw.cdmi.core.db.JPQuery;
import pw.cdmi.core.http.exception.AWSClientException;
import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.msm.geo.ClientReason;
import pw.cdmi.open.ClientError;
import pw.cdmi.open.model.entity.Area;
import pw.cdmi.open.model.entity.Commissioner;
import pw.cdmi.open.model.entity.Company;
import pw.cdmi.open.model.entity.Department;
import pw.cdmi.open.model.entity.DepartmentGroup;
import pw.cdmi.open.model.entity.Employee;
import pw.cdmi.open.model.entity.EmployeeAndCommissioner;
import pw.cdmi.open.model.entity.EmployeeAndDeptGroup;
import pw.cdmi.open.model.entity.EmployeeAndOffice;
import pw.cdmi.open.model.entity.Office;
import pw.cdmi.open.model.entity.People;
import pw.cdmi.open.model.entity.Position;
import pw.cdmi.open.model.entity.PositionalTitle;
import pw.cdmi.open.model.entity.UserAccount;
import pw.cdmi.open.model.queryObject.EmployeeQuery;
import pw.cdmi.open.service.BusinessOrganizationService;
import pw.cdmi.open.service.EmployeeService;
import pw.cdmi.open.service.SingleSiteApplicationService;
import pw.cdmi.open.service.UserService;
import pw.cdmi.utils.UUIDUtils;

/**
 * ********************************************************** 企业组织的实现类
 * 
 * @author liujh
 * @version cas Service Platform, 2015-5-7
 ***********************************************************
 */
@Service("BusinessOrganizationService")
public class BusinessOrganizatinServiceImpl implements BusinessOrganizationService {

    private static final Logger log = LoggerFactory.getLogger(BusinessOrganizatinServiceImpl.class);

    @Autowired
    private GenericDao daoImpl;

    @Autowired
    private UserService userService;

    @Autowired
    private SingleSiteApplicationService appService;

    @Autowired
    private EmployeeService employeeService;

    @Transactional
    @Override
    public void createCompany(Company company) {
        // 判断企业的几个关键唯一的信息是否在系统中存在，存在，则不会被保存
        Company comp = this.getCompanyByKeyFields(company.getName(), company.getCodeCertificate(),
            company.getLicenseNumber(), company.getTaxCode());
        if (comp != null) {
            throw new AWSClientException(ClientError.DataAlreadyExists, ClientReason.DataAlreadyExists);
        }

        if (StringUtils.isEmpty(company.getName())) {
            throw new AWSClientException(ClientError.IncompleteBody, ClientReason.IncompleteBody);
        }
        company.setOpenId(UUIDUtils.getUUIDTo64());
        company.setAppId(appService.getCurrentAppId());
        company.setCreateTime(new Date());
        company.setId(null);
        daoImpl.save(company);
    }

    @Transactional
    @Override
    public void updateCompany(Company company) {
        Company comp = daoImpl.get(company.getId(), Company.class);
        if (comp == null) {
            throw new AWSClientException(ClientError.NoFoundData, ClientReason.NoFoundData);
        }
        // TODO 企业的几个关键唯一信息是不允许修改的。
        daoImpl.update(company);
    }

    @Transactional
    @Override
    public boolean deleteCompany(String id) {
        boolean b = false;
        try {
            Company comp = getCompany(id);
            if (comp != null) {
                /*Department queryObject = new Department();
                queryObject.setCompanyId(id);
                List<Department> deptList = findDepartmentList(queryObject);
                for (Department dept : deptList) {
                    jpaImpl.delete(dept);
                }*/
                daoImpl.delete(comp);
                b = true;
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return b;
    }

    @Override
    public Company getCompany(String id) {
        Company company = null;
        try {
            company = daoImpl.get(id, Company.class);
        } catch (NoResultException e) {
            log.info("没有找到相关的公司信息！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return company;
    }

    @Override
    public Company getCompanyByName(String companyName) {
        String hql = "from Company c where c.name='" + companyName + "'";
        return daoImpl.findOne(hql);
    }

    @Override
    public Company getCompanyByOpenId(String openId) {
        String hql = "from Company c where c.openId='" + openId + "'";
        return daoImpl.findOne(hql);
    }

    @Override
    public Company getCompanyByRegistrationName(String registrationName) {
        String hql = "from Company c where c.businessRegistrationName='" + registrationName + "'";
        return daoImpl.findOne(hql);
    }

    @Override
    public Company getCompanyByTaxCode(String taxCode) {
        String hql = "from Company c where c.taxCode='" + taxCode + "'";
        return daoImpl.findOne(hql);
    }

    @Override
    public Company getCompanyByKeyFields(String name, String licenseNumber, String codeCertificate, String taxCode) {
        StringBuilder hql = new StringBuilder("from Company where ");
        if (!StringUtils.isEmpty(name)) {
            hql.append("name = '" + name + "'");
        }
        if (!StringUtils.isEmpty(licenseNumber)) {
            if ((!StringUtils.isEmpty(name))) {
                hql.append(" or licenseNumber ='" + licenseNumber + "'");
            } else {
                hql.append(" licenseNumber ='" + licenseNumber + "'");
            }
        }
        if (!StringUtils.isEmpty(codeCertificate)) {
            if (!StringUtils.isEmpty(name) || !StringUtils.isEmpty(licenseNumber)) {
                hql.append(" or codeCertificate ='" + codeCertificate + "'");
            } else {
                hql.append(" codeCertificate ='" + codeCertificate + "'");
            }
        }
        if (!StringUtils.isEmpty(taxCode)) {
            if (!StringUtils.isEmpty(name) || !StringUtils.isEmpty(licenseNumber)
                    || !StringUtils.isEmpty(codeCertificate)) {
                hql.append(" or taxCode ='" + taxCode + "'");
            } else {
                hql.append(" taxCode ='" + taxCode + "'");
            }
        }
        return daoImpl.findOne(hql.toString());
    }

    @Override
    public String getCurrentCompanyId() {
        String hql = "from Company where appId='" + appService.getCurrentAppId() + "' and parentId = null";
        Company company = daoImpl.findOne(hql);
        if (company == null) {
            throw new AWSClientException(ClientError.NoFoundData, ClientReason.NoFoundData);
        }
        return company.getId();
    }

    @Override
    public Company getCurrentCompany() {
        String hql = "from Company where appId='" + appService.getCurrentAppId() + "' and parentId = null";
        Company company = daoImpl.findOne(hql);
        if (company == null) {
            throw new AWSClientException(ClientError.NoFoundData, ClientReason.NoFoundData);
        }
        return company;
    }

    @Override
    public List<String> getCurrentAndSubCompanyIds() {

        List<String> ids = new ArrayList<String>();
        String hql = "from Company where appId='" + appService.getCurrentAppId() + "' and parentId = null";
        Company company = daoImpl.findOne(hql);

        if (company == null) {
            throw new AWSClientException(ClientError.NoFoundData, ClientReason.NoFoundData);
        }

        ids.add(company.getId());

        hql = "select c.id from Company c where 1=1 and c.parentId = " + company.getId();

        List<String> companyIds = daoImpl.find(hql);

        if (null != companyIds) {
            ids.addAll(companyIds);
        }

        return ids;
    }

    @Override
    public Company getSuperCompany() {
        Company company = null;
        try {
            JPQuery query = JPQuery.createQuery("Company.getSuper");
            company = daoImpl.findOneByNamedQuery(query);
        } catch (NoResultException e) {
            log.info("没有找到相关的公司信息！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return company;
    }

    @Override
    public Iterable<Company> findAllCompany() {
        Iterable<Company> list = new ArrayList<Company>();
        try {
            list = daoImpl.findAll(Company.class);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public List<Company> findParentCompany() {
        List<Company> list = null;
        try {
            JPQuery query = JPQuery.createQuery("Company.findByParentId");
            query.setParamater("parentId", 0L);
            list = daoImpl.findByNamedQuery(query);
        } catch (NoResultException e) {
            log.info("没有找到相关的公司列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public PageView findCompanyList(Company company, int pageNo, int pageSize) {
        PageView pageView = new PageView();
        List<Company> list = null;
        try {
            JPQuery query = JPQuery.createQuery(company.createSearchQuery());
            list = daoImpl.find(query, pageNo, pageSize);
            long total = daoImpl.getCount(query);

            pageView.setList(list);
            pageView.setTotalRecord(total);
        } catch (NoResultException e) {
            log.info("没有找到相关的公司列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);

        }
        return pageView;
    }

    @Override
    public PageView findAllCompany(int pageNo, int pageSize) {
        PageView pageView = new PageView();
        try {
            pageView.setList(daoImpl.findAll(Company.class, pageNo, pageSize));
            pageView.setTotalRecord(daoImpl.getCount(Company.class));
        } catch (NoResultException e) {
            log.info("没有找到相关的公司列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return pageView;
    }

    @Override
    public List<Company> findSubCompanyList(Company company) {
        company.setParentId(this.getCurrentCompanyId());
        List<Company> list = null;
        try {
            list = daoImpl.find(company.createSearchQuery());
        } catch (NoResultException e) {
            log.info("没有找到相关的公司列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public List<Company> findSubCompanyListByEmployeeId(String employeeId) {
        List<Company> list = null;
        try {
            String hql = "from Company where supervisorId='" + employeeId + "'";
            list = daoImpl.find(hql);
        } catch (NoResultException e) {
            log.info("没有找到相关的公司列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public PageView findSubCompanyListPage(Company company, int pageNo, int pageSize) {
        company.setParentId(this.getCurrentCompanyId());
        PageView pageView = new PageView();
        String parentId = company.getParentId();
        String name = company.getName();
        String supervisorId = company.getSupervisorId();
        String queryString = company.createSearchQuery();
        try {
            JPQuery query = JPQuery.createQuery(queryString);
            daoImpl.find(query, pageNo, pageSize);
            pageView.setTotalRecord(daoImpl.getCount(query));
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return pageView;
    }

    @Transactional
    @Override
    public void createOffice(Office office) {
        // 办事处的名称不能重复
        String companyId = office.getCompanyId();
        if (companyId == null) {
            companyId = this.getCurrentCompanyId();
            office.setCompanyId(companyId);
        }
        Office o = this.getOfficeByName(companyId, office.getName());
        if (o != null) {
            throw new AWSClientException(ClientError.DataAlreadyExists, ClientReason.DataAlreadyExists);
        }
        office.setCompanyId(companyId);
        office.setId(null);
        try {
            daoImpl.save(office);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void updateOffice(Office office) {
        // 办事处的名称不能重复
        String companyId = office.getCompanyId();
        if (companyId == null) {
            companyId = this.getCurrentCompanyId();
        }
        Office o = this.getOfficeByName(companyId, office.getName());
        if (o != null && o.getId() != office.getId() && o.getId().equals(office.getId())) {
            throw new AWSClientException(ClientError.DataAlreadyExists, ClientReason.DataAlreadyExists);
        }
        try {
            daoImpl.update(office);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public boolean deleteOffice(String id) {
        boolean b = false;
        try {
            Office office = getOffice(id);
            if (office != null) {
                deleteEmployeeAndOffice(id);
                daoImpl.delete(office);
                b = true;
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return b;
    }

    @Override
    public Office getOffice(String id) {
        Office office = null;
        try {
            office = daoImpl.get(id, Office.class);
        } catch (NoResultException e) {
            log.info("没有找到相关的公司信息！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return office;
    }

    @Override
    public Office getOfficeByName(String companyId, String officeName) {
        String hql = "from Office where companyId=" + companyId + " and name='" + officeName + "'";
        return daoImpl.findOne(hql);
    }

    @Override
    public List<Office> findAllOffice() {
        List<Office> list = new ArrayList<Office>();
        try {
            JPQuery query = JPQuery.createQuery("Office.findAllByCompanyId");
            query.setParamater("companyId", this.getCurrentCompanyId());
            list = daoImpl.findByNamedQuery(query);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Deprecated
    @Override
    public PageView findAllOffice(int pageNo, int pageSize) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Office> findOfficeByKeyword(String keyword) {
        List<Office> list = null;
        try {
            if (keyword == null || keyword == "") {
                return findAllOffice();
            }
            JPQuery query = JPQuery.createQuery("Office.findByKeyword");
            query.setParamater("keyword", keyword);
            query.setParamater("companyId", this.getCurrentCompanyId());
            list = daoImpl.findByNamedQuery(query);
        } catch (NoResultException e) {
            log.info("没有找到相关的公司列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public List<Office> findOfficeByEmployeeId(String employeeId) {
        List<Office> list = null;
        try {
            String hql = "from Office where supervisorId='" + employeeId + "'";
            list = daoImpl.find(hql);
        } catch (NoResultException e) {
            log.info("没有找到相关的办事处列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Transactional
    @Override
    public void createDepartment(Department department) {
        if (department.getCompanyId() == null) {
            department.setCompanyId(this.getCurrentCompanyId());
        }
        department.setId(null);
        if (department.getParentId() != null) {
            department.setParentId(null);
        }
        // 需判断同一个ParentId下，部门的名称是否存在重复
        Department d = this.getDepartmentByName(this.getCurrentCompanyId(), department.getParentId(),
            department.getName());
        if (d != null) {
            throw new AWSClientException(ClientError.DataAlreadyExists, ClientReason.DataAlreadyExists);
        }
        try {
            daoImpl.save(department);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void updateDepartment(Department department) {
        department.setCompanyId(this.getCurrentCompanyId());
        if (department.getParentId() != null) {
            department.setParentId(null);
        }
        // 需判断同一个ParentId下，部门的名称是否存在重复
        Department d = this.getDepartmentByName(this.getCurrentCompanyId(), department.getParentId(),
            department.getName());
        if (d != null && !d.getId().equals(department.getId())) {
            throw new AWSClientException(ClientError.DataAlreadyExists, ClientReason.DataAlreadyExists);
        }
        try {
            daoImpl.update(department);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public boolean deleteDepartment(String id) {
        boolean b = false;
        Department queryObject = new Department();
        try {
            Department dept = getDepartment(id);
            if (dept != null) {
                queryObject.setParentId(id);
                List<Department> deptList = findDepartmentList(queryObject);
                List<DepartmentGroup> deptGroupList = findDepartmentGroupByDeptId(id);
                for (Department department : deptList) {
                    // jpaImpl.delete(department);
                    deleteDepartment(department.getId());
                }
                for (DepartmentGroup deptGroup : deptGroupList) {
                    daoImpl.delete(deptGroup);
                }
                daoImpl.delete(dept);
                b = true;
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return b;
    }

    @Override
    public Department getDepartment(String id) {
        Department department = null;
        try {
            department = daoImpl.get(id, Department.class);
        } catch (NoResultException e) {
            log.info("没有找到相关的部门信息！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return department;
    }

    @Override
    public Department getDepartmentByName(String companyId, String parentId, String deptName) {
        String hql = "from Department where companyId=" + companyId + " and name='" + deptName + "' and parentId ="
                + parentId;
        return daoImpl.findOne(hql);
    }

    @Override
    @ResponseBody
    public List<Department> findFirstDepartment() {
        List<Department> list = null;
        try {
            JPQuery query = JPQuery.createQuery("Department.findFirstDepartment");
            query.setParamater("companyId", this.getCurrentCompanyId());
            list = daoImpl.findByNamedQuery(query);
        } catch (NoResultException e) {
            log.info("没有找到相关的部门列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public List<Department> findAllDepartment() {
        List<Department> list = new ArrayList<Department>();
        try {
            JPQuery query = JPQuery.createQuery("Department.findAllByCompanyId");
            query.setParamater("companyId", this.getCurrentCompanyId());
            list = daoImpl.findByNamedQuery(query);
        } catch (NoResultException e) {
            log.info("没有找到相关的部门列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public List<Department> findDepartmentList(Department queryObject) {

        List<Department> list = null;
        try {
            if (queryObject == null) {
                list = findAllDepartment();
            } else {
                queryObject.setCompanyId(this.getCurrentCompanyId());
                list = daoImpl.find(queryObject.createSearchQuery());
            }
        } catch (NoResultException e) {
            log.info("没有找到相关的部门列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public PageView findDepartmentListPage(Department queryObject, int pageNo, int pageSize) {
        PageView pageView = new PageView();
        queryObject.setCompanyId(this.getCurrentCompanyId());
        String queryString = queryObject.createSearchQuery();
        try {
            daoImpl.find(queryString, pageNo, pageSize);
            pageView.setTotalRecord(daoImpl.getCount(queryString));
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return pageView;
    }

    @Transactional
    @Override
    public void createDepartmentGroup(DepartmentGroup departmentGroup) {
        // 部门群组的名称在全公司范围内不能重复
        String companyId = departmentGroup.getCompanyId();
        if (companyId == null) {
            companyId = this.getCurrentCompanyId();
            departmentGroup.setCompanyId(companyId);
        }
        DepartmentGroup o = this.getDepartmentGroupByName(companyId, departmentGroup.getName());
        if (o != null) {
            throw new AWSClientException(ClientError.DataAlreadyExists, ClientReason.DataAlreadyExists);
        }
        try {
            departmentGroup.setId(null);
            daoImpl.save(departmentGroup);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void updateDepartmentGroup(DepartmentGroup departmentGroup) {
        try {
            daoImpl.update(departmentGroup);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public boolean deleteDepartmentGroup(String id) {
        boolean b = false;
        DepartmentGroup depg = getDepartmentGroup(id);
        try {
            if (depg != null) {
                deleteEmployeeAndDeptGroup(id);
                daoImpl.delete(depg);
                b = true;
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return b;
    }

    @Override
    public DepartmentGroup getDepartmentGroup(String id) {
        DepartmentGroup deptGroup = null;
        try {
            deptGroup = daoImpl.get(id, DepartmentGroup.class);
        } catch (NoResultException e) {
            log.info("没有找到相关的群组信息！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return deptGroup;
    }

    @Override
    public DepartmentGroup getDepartmentGroupByName(String deptId, String deptGroupName) {
        String hql = "from DepartmentGroup where deptId='" + deptId + "' and name='" + deptGroupName + "'";
        return daoImpl.findOne(hql);
    }

    @Override
    public List<DepartmentGroup> findDeptGroupByEmployeeId(String employeeId) {
        String queryString = "select ed.deptGroupId from EmployeeAndDeptGroup ed where ed.employeeId =" + employeeId;
        try {
            if (employeeId == null) {
                return findAllDepartmentGroup();
            }
            List<DepartmentGroup> deptGroupList = new ArrayList<DepartmentGroup>();
            List<String> list = daoImpl.find(queryString);
            for (String id : list) {
                DepartmentGroup deptGroup = getDepartmentGroup(id);
                if (null != deptGroup) {
                    deptGroupList.add(deptGroup);
                }
            }
            return deptGroupList;
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public List<DepartmentGroup> findDepartmentGroupByDeptId(String departmentId) {
        List<DepartmentGroup> list = null;
        try {
            JPQuery query = JPQuery.createQuery("DepartmentGroup.findByDeptId");
            query.setParamater("deptId", departmentId);
            list = daoImpl.findByNamedQuery(query);
        } catch (NoResultException e) {
            log.info("没有找到相关的群组列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public List<DepartmentGroup> findAllDepartmentGroup() {
        List<DepartmentGroup> list = new ArrayList<DepartmentGroup>();
        try {
            list = daoImpl.findAll(DepartmentGroup.class);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public List<DepartmentGroup> findDepartmentGroupByKeyword(String keyword) {
        List<DepartmentGroup> list = null;
        try {
            if (keyword == null) {
                list = findAllDepartmentGroup();
            } else {
                JPQuery query = JPQuery.createQuery("DepartmentGroup.findByKeyword");
                query.setParamater("name", keyword);

                list = daoImpl.findByNamedQuery(query);
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Transactional
    @Override
    public void createCommissioner(Commissioner commissioner) {
        commissioner.setCompanyId(this.getCurrentCompanyId());
        commissioner.setId(null);
        try {
            daoImpl.save(commissioner);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void updateCommissioner(Commissioner commissioner) {
        commissioner.setCompanyId(this.getCurrentCompanyId());
        try {
            daoImpl.update(commissioner);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public boolean deleteCommissioner(String id) {
        boolean b = false;
        try {
            Commissioner comm = getCommissioner(id);
            if (comm != null) {
                deleteEmployeeAndCommissionerByCommissioner(id);
                daoImpl.delete(comm);
                b = true;
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return b;
    }

    @Override
    public Commissioner getCommissioner(String id) {
        Commissioner commissioner = null;
        try {
            commissioner = daoImpl.get(id, Commissioner.class);
        } catch (NoResultException e) {
            log.info("没有找到相关的专员信息！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return commissioner;
    }

    @Override
    public Commissioner getCommissionerByName(String companyId, String commissionerName) {
        String hql = "from Commissioner where companyId=" + companyId + " and name='" + commissionerName + "'";
        return daoImpl.findOne(hql);
    }

    @Override
    public List<Commissioner> findCommissionerByName(String commissionerName) {
        List<Commissioner> list = null;
        try {
            JPQuery query = JPQuery.createQuery("Commissioner.findByName");
            query.setParamater("name", commissionerName);
            query.setParamater("companyId", this.getCurrentCompanyId());
            list = daoImpl.findByNamedQuery(query);
        } catch (NoResultException e) {
            log.info("没有找到相关的专员列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public List<Commissioner> findAllCommissioner() {
        List<Commissioner> list = new ArrayList<Commissioner>();
        try {
            JPQuery query = JPQuery.createQuery("Commissioner.findAllByCompanyId");
            query.setParamater("companyId", this.getCurrentCompanyId());
            list = daoImpl.findByNamedQuery(query);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Transactional
    @Override
    public void createArea(Area area) {
        area.setId(null);
        area.setCompanyId(this.getCurrentCompanyId());
        try {
            daoImpl.save(area);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void updateArea(Area area) {
        Area newArea = null;
        try {
            newArea = getArea(area.getId());
            newArea.setName(area.getName());
            newArea.setSupervisorId(area.getSupervisorId());
            newArea.setDescription(area.getDescription());
            daoImpl.update(newArea);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public boolean deleteArea(String id) {
        Company company = new Company();
        boolean b = false;
        try {
            Area area = getArea(id);
            company.setAreaId(id);
            List<Company> companyList = findSubCompanyList(company);
            String hql = "from Office where areaId='" + id + "'";
            List<Office> officeList = daoImpl.find(hql);
            for (Office office : officeList) {
                if (null != office.getId()) {
                    try {
                        List<Employee> emps = new ArrayList<Employee>();
                        String officeId = office.getId();
                        emps = employeeService.findEmployeeByOfficeId(officeId);
                        if (emps.size() > 0) {
                            for (Employee emp : emps) {
                                emp.setCompanyId(null);
                                emp.setOfficeId(null);
                                daoImpl.update(emp);
                            }
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        e.printStackTrace();
                    }
                }
                daoImpl.delete(office);
            }
            for (Company comp : companyList) {
                if (null != comp.getId()) {
                    try {
                        List<Employee> emps = new ArrayList<Employee>();
                        EmployeeQuery employeeQuery = new EmployeeQuery();
                        employeeQuery.setCompanyId(comp.getId());
                        emps = employeeService.findEmployeeByQuery(employeeQuery);
                        if (emps.size() > 0) {
                            for (Employee emp : emps) {
                                emp.setCompanyId(null);
                                daoImpl.update(emp);
                            }
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        e.printStackTrace();
                    }
                }
                daoImpl.delete(comp);
            }
            daoImpl.delete(area);
            b = true;
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return b;
    }

    @Override
    public Area getArea(String id) {
        Area area = null;
        try {
            area = daoImpl.get(id, Area.class);
        } catch (NoResultException e) {
            log.info("没有找到相关的片区信息！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return area;
    }

    @Override
    public List<Area> findAreaList(Area area) {
        area.setCompanyId(this.getCurrentCompanyId());
        List<Area> list = null;
        try {
            list = daoImpl.find(area.createSearchQuery());
        } catch (NoResultException e) {
            log.info("没有找到相关的片区列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public List<Area> findAllArea() {
        try {
            JPQuery query = JPQuery.createQuery("Area.findAllByCompany");
            query.setParamater("companyId", this.getCurrentCompanyId());
            return daoImpl.findByNamedQuery(query);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void createPosition(Position position) {
        position.setCompanyId(this.getCurrentCompanyId());
        position.setId(null);
        try {
            daoImpl.save(position);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void updatePosition(Position position) {
        position.setCompanyId(this.getCurrentCompanyId());
        try {
            daoImpl.update(position);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public boolean deletePosition(String id) {
        boolean b = false;
        try {
            Position pos = getPosition(id);
            if (pos != null) {
                PositionalTitle posTitle = new PositionalTitle();
                posTitle.setPositionId(id);
                List<PositionalTitle> posTitleList = findPositionalTitle(posTitle);
                for (PositionalTitle postitle : posTitleList) {
                    daoImpl.delete(postitle);
                }
                daoImpl.delete(pos);
                b = true;
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return b;
    }

    @Override
    public Position getPosition(String id) {
        Position position = null;
        try {
            position = daoImpl.get(id, Position.class);
        } catch (NoResultException e) {
            log.info("没有找到相关的岗位信息！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return position;
    }

    @Override
    public Position getPositionByName(String companyId, String positionName) {
        String hql = "from Position where companyId=" + companyId + " and name='" + positionName + "'";
        return daoImpl.findOne(hql);
    }

    @Override
    public List<Position> findAllPosition() {
        List<Position> list = new ArrayList<Position>();
        try {
            JPQuery query = JPQuery.createQuery("Position.findAllByCompany");
            query.setParamater("companyId", this.getCurrentCompanyId());
            list = daoImpl.findByNamedQuery(query);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public List<Position> findPositionByName(String positionName) {
        List<Position> list = null;
        try {
            JPQuery query = JPQuery.createQuery("Position.findByName");
            query.setParamater("companyId", this.getCurrentCompanyId());
            list = daoImpl.findByNamedQuery(query);
        } catch (NoResultException e) {
            log.info("没有找到相关的岗位列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Transactional
    @Override
    public void createPositionalTitle(PositionalTitle positionalTitle) {
        try {
            positionalTitle.setId(null);
            daoImpl.save(positionalTitle);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void updatePositionTitle(PositionalTitle positionalTitle) {
        PositionalTitle title = null;
        try {
            title = getPositionalTitle(positionalTitle.getId());
        } catch (Exception e) {
            log.error("执行updatePositionalTitle方法出错!");
            throw new AWSServiceException(SystemReason.SQLError);
        }
        title.setName(positionalTitle.getName());
        title.setPositionId(positionalTitle.getPositionId());
        title.setDescription(positionalTitle.getDescription());
        try {
            daoImpl.update(title);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public boolean deletePositionalTitle(String id) {
        boolean b = false;
        try {
            PositionalTitle pot = getPositionalTitle(id);
            if (pot != null) {
                daoImpl.delete(pot);
                b = true;
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return b;
    }

    @Override
    public PositionalTitle getPositionalTitle(String id) {
        PositionalTitle positionalTitle = null;
        try {
            positionalTitle = daoImpl.get(id, PositionalTitle.class);
        } catch (NoResultException e) {
            log.info("没有找到相关的职称信息！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return positionalTitle;
    }

    @Override
    public PositionalTitle getPositionalTitleByName(String companyId, String positionalTitleName) {
        String hql = "from PositionalTitle where companyId = " + companyId + " and name='" + positionalTitleName + "'";
        return daoImpl.findOne(hql);
    }

    @Override
    public List<PositionalTitle> findPositionalTitle(PositionalTitle positionalTitle) {
        List<PositionalTitle> list = null;
        try {
            if (positionalTitle == null) {
                list = findAllPositionalTitle();
            } else {
                list = daoImpl.find(positionalTitle.createSearchQuery());
            }
        } catch (NoResultException e) {
            log.info("没有找到相关的职称列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public List<PositionalTitle> findPositionalTitle(String name, String positionName) {

        List<PositionalTitle> list = null;

        try {

            if (StringUtils.isBlank(name) && StringUtils.isBlank(positionName)) {
                list = findAllPositionalTitle();
            } else {

                StringBuffer hql = new StringBuffer("select pt from PositionalTitle pt");

                String positionIds = getPositionIds(positionName);
                if (StringUtils.isNotBlank(name) && StringUtils.isBlank(positionIds)) {
                    hql.append(" where pt.name like '%" + name.trim() + "%' ");
                } else if (StringUtils.isBlank(name) && StringUtils.isNotBlank(positionIds)) {
                    hql.append(" where pt.positionId in " + positionIds + "");
                } else {
                    hql.append(" where pt.name like '%" + name.trim() + "%' and pt.positionId in " + positionIds + "");
                }

                list = daoImpl.find(hql.toString());
            }

        } catch (NoResultException e) {
            log.info("没有找到相关的职称列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }

        return list;
    }

    private String getPositionIds(String positionName) {
        if (StringUtils.isBlank(positionName)) {
            return null;
        }
        List<Position> positions = findPositionByName(positionName);
        if (null != positions && positions.size() > 0) {
            StringBuilder ids = new StringBuilder("(");
            for (Position p : positions) {
                ids.append(p.getId());
                ids.append(",");
            }
            ids.delete(ids.lastIndexOf(","), ids.length());
            ids.append(")");
            return ids.toString();
        }
        return null;
    }

    @Override
    public List<PositionalTitle> findAllPositionalTitle() {
        List<PositionalTitle> list = new ArrayList<PositionalTitle>();
        try {
            JPQuery query = JPQuery.createQuery("PositionalTitle.findAllByCompany");
            query.setParamater("companyId", this.getCurrentCompanyId());
            list = daoImpl.findByNamedQuery(query);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    @Transactional
    public void createEmployee(String companyId, Employee employee) {
        Employee empl = this.getEmployeeByCompanyIdAndCode(companyId, employee.getCode());
        if (empl != null) {
            throw new AWSClientException(ClientError.DataAlreadyExists, ClientReason.DataAlreadyExists);
        }
        employee.setCompanyId(companyId);
        daoImpl.save(employee);
    }

    @Override
    @Transactional
    public void createEmployee(String companyId, Employee employee, People people) {
        try {
            people.setId(null);
            userService.createPeople(people);
            employee.setPeopleId(people.getId());
            employee.setId(null);
            daoImpl.save(employee);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    @Transactional
    public void updateEmployee(Employee employee) {
        employee.setCompanyId(this.getCurrentCompanyId());
        try {
            daoImpl.update(employee);
            if (employee.getDeptManagerId() != null) {
                Department dept = this.getDepartment(employee.getDeptManagerId());
                if (dept != null) {
                    dept.setSupervisorId(employee.getId());
                    this.updateDepartment(dept);
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
    public void updateEmployee(Employee employee, People people) {
        employee.setCompanyId(this.getCurrentCompanyId());
        try {
            daoImpl.update(employee);
            userService.updatePeople(people);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    @Transactional
    public void deleteEmployeeById(String id) {
        try {
            Employee employee = daoImpl.get(id, Employee.class);
            if (employee != null) {
                daoImpl.delete(employee);
                if (employee.getId() != null) {
                    this.deleteEmployeeAndDeptGroup(id);
                    this.deleteEmployeeAndOffice(id);
                    this.deleteEmployeeAndCommissionerByCommissioner(id);
                    userService.deletePeopleById(employee.getPeopleId());
                    userService.deleteUserAccountByPeopleId(employee.getPeopleId());
                    userService.deleteSiteUserByEmployeeId(employee.getId());
                    // FIXME 删除企业雇员时候，需要检查该雇员是否具有IT权限，有则删除IT权限
                    // permissionService.deleteSiteUserPermissionByUserId(employee
                    // .getId());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }

    }

    @Override
    public Map<String, Object> getEmployeeById(String id) {
        Map<String, Object> map = null;
        try {
            Employee employee = daoImpl.get(id, Employee.class);
            People people = userService.getPeopleById(employee.getPeopleId());
            UserAccount userAccount = userService.getUserAccountByPeopleId(employee.getPeopleId());
            Department dept = this.getDepartment(employee.getDeptId());
            Company company = this.getCompany(employee.getCompanyId());
            Position position = this.getPosition(employee.getPositionId());
            PositionalTitle positionalTitle = this.getPositionalTitle(employee.getPositionalTitleId());
            map = new HashMap<String, Object>();
            map.put("employee", employee);
            map.put("people", people);
            map.put("userAccount", userAccount);
            map.put("department", dept);
            map.put("company", company);
            map.put("position", position);
            map.put("positionalTitle", positionalTitle);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return map;
    }

    @Override
    public Employee getSingleEmployeeById(String id) {
        Employee employee = null;
        try {
            if (id != null) {
                employee = daoImpl.get(id, Employee.class);
            }
        } catch (NoResultException e) {
            log.info("没有找到相关的员工信息！");
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return employee;
    }

    @Override
    public Employee getEmployeeByCompanyIdAndCode(String companyId, String code) {
        String hql = "from Employee where companyId= " + companyId + " and code='" + code + "'";
        return daoImpl.findOne(hql);
    }

    @Override
    public Employee getSingleEmployeeByPeopleId(String peopleId) {
        Employee employee = null;
        try {
            String jpql = "SELECT t FROM Province t WHERE t.countryId = :countryId";
            JPQuery query = JPQuery.createQuery(jpql);
            query.setParamater("peopleId", peopleId);
            query.setParamater("companyId", 0);

            return daoImpl.findOne(query);
        } catch (NoResultException e) {
            log.info("没有找到相关的员工信息！");
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return employee;
    }

    @Override
    public Iterable<Employee> getAllEmployee() {
        Iterable<Employee> list = new ArrayList<Employee>();
        try {
            list = daoImpl.findAll(Employee.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> findEmployeeByCondition(String code, String name, String telephone,
        String deptId) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {

            String searchHql = "SELECT e from Employee e WHERE e.status ='OK'";
            String conditionHql = getEmployeeConditionHql(code, name, telephone, deptId);

            list = daoImpl.find(searchHql + conditionHql);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public PageView findEmployeeByConditionAndPage(int pageNo, int pageSize, String code, String name, String telephone,
        String deptId) {
        PageView pageView = null;
        try {
            String searchHql = "SELECT e from Employee e WHERE e.status ='OK'";
            String countHql = "SELECT count(*) from Employee e WHERE e.status ='OK'";

            String conditionHql = getEmployeeConditionHql(code, name, telephone, deptId);
            pageView = new PageView();
            pageView.setList(daoImpl.find(searchHql + conditionHql, pageNo, pageSize));
            pageView.setTotalRecord(daoImpl.getCount(countHql + conditionHql));
        } catch (NoResultException e) {
            log.info("没有找到相关的员工信息！");
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return pageView;
    }

    @Override
    public List<Employee> findEmployeeByDeptGroupId(String deptGroupId) {
        List<Employee> employeeList = new ArrayList<Employee>();
        String queryString = "select ed.employeeId from EmployeeAndDeptGroup ed where ed.deptGroupId =" + deptGroupId;
        try {
            List<String> list = daoImpl.find(queryString);
            for (String id : list) {
                Employee employee = getSingleEmployeeById(id);
                employeeList.add(employee);
            }
        } catch (NoResultException e) {
            log.info("没有员工列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return employeeList;
    }

    @Override
    public int getCountEmployeeByDeptGroupId(String deptGroupId) {
        long num = 0;
        String queryString = "select count(ed.employeeId) from EmployeeAndDeptGroup ed where ed.deptGroupId ="
                + deptGroupId;
        try {
            num = daoImpl.getCount(queryString);
        } catch (NoResultException e) {
            log.info("没有员工！");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return (int) num;
    }

    @Override
    public List<Employee> findEmployeeByOfficeId(String officeId) {
        List<Employee> employeeList = new ArrayList<Employee>();
        String queryString = "select ed.employeeId from EmployeeAndOffice ed where ed.officeId =" + officeId;
        try {
            List<String> list = daoImpl.find(queryString);
            for (String id : list) {
                Employee employee = getSingleEmployeeById(id);
                employeeList.add(employee);
            }
        } catch (NoResultException e) {
            log.info("没有员工列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return employeeList;
    }

    @Override
    public int getCountEmployeeByOfficeId(String officeId) {
        long num = 0;
        String queryString = "select count(ed.employeeId) from EmployeeAndOffice ed where ed.officeId =" + officeId;
        try {
            num = daoImpl.getCount(queryString);
        } catch (NoResultException e) {
            log.info("没有员工！");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return (int) num;
    }

    @Override
    public List<Employee> findEmployeeNotOnOffice(String officeId, String code, String name) {
        List<Employee> list = null;
        String queryString = "from Employee e where e.id not in (select ed.employeeId from EmployeeAndOffice ed where ed.officeId ="
                + officeId + ")";
        String conditionHql = getEmployeeConditionHql(code, name, null, null);
        try {
            list = daoImpl.find((queryString + conditionHql));
        } catch (NoResultException e) {
            log.info("没有找到相关的员工列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public List<Employee> findEmployeeByCommissionerId(String commissionerId) {
        List<Employee> employeeList = new ArrayList<Employee>();
        String queryString = "select ed.employeeId from EmployeeAndCommissioner ed where ed.commissionerId ="
                + commissionerId;
        try {
            List<String> list = daoImpl.find(queryString);
            for (String id : list) {
                Employee employee = getSingleEmployeeById(id);
                employeeList.add(employee);
            }
        } catch (NoResultException e) {
            log.info("没有员工列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return employeeList;
    }

    @Override
    public int getCountEmployeeByCommissionerId(String commissionerId) {
        long num = 0;
        String queryString = "select count(ed.employeeId) from EmployeeAndCommissioner ed where ed.commissionerId ="
                + commissionerId;
        try {
            num = daoImpl.getCount(queryString);
        } catch (NoResultException e) {
            log.info("没有员工！");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return (int) num;
    }

    @Override
    public List<Employee> findEmployeeNotIsCommissioner(String commissionerId, String code, String name) {
        List<Employee> list = null;
        String queryString = "from Employee e where e.id not in (select ed.employeeId from EmployeeAndCommissioner ed where ed.commissionerId ="
                + commissionerId + ")";
        String conditionHql = getEmployeeConditionHql(code, name, null, null);
        try {
            list = daoImpl.find((queryString + conditionHql));
        } catch (NoResultException e) {
            log.info("没有找到相关的员工列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public int getCountEmployeeByQuery(EmployeeQuery employeeQuery) {
        long num = 0;
        try {
            num = daoImpl.getCount(employeeQuery.createQuery());
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return (int) num;
    }

    @Override
    public List<Employee> findEmployeeByQuery(EmployeeQuery employeeQuery) {
        List<Employee> list = null;
        try {
            list = daoImpl.find(employeeQuery.createSearchQuery());
        } catch (NoResultException e) {
            log.info("没有找到相关的公司列表！");
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    /**
     * 封装Employee的查询条件语句
     * 
     * @param code
     *            员工工号
     * @param name
     *            员工的姓名
     * @param telephone
     *            员工联系电话
     * @param deptId
     *            员工所在部门id
     * @return 返回封装好的条件（HQL）语句
     */
    private String getEmployeeConditionHql(String code, String name, String telephone, String deptId) {
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isBlank(code)) {
            sb.append(" and e.code like '%" + code + "%' ");
        }
        if (!StringUtils.isBlank(name)) {
            sb.append(" and e.name like '%" + name + "%' ");
        }
        if (!StringUtils.isBlank(telephone)) {
            sb.append(" and e.telephone like '%" + telephone + "%' ");
        }
        if (deptId != null) {
            sb.append(" and e.deptId = '" + deptId + "'");
        }

        return sb.toString();
    }

    @Override
    @Transactional
    public void createEmployeeAndDeptGroup(EmployeeAndDeptGroup employeeAndDeptGroup) {
        try {
            daoImpl.save(employeeAndDeptGroup);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public EmployeeAndDeptGroup getEmployeeAndDeptGroup(String employeeId, String deptGroupId) {
        EmployeeAndDeptGroup empAndGroup = null;
        String queryString = "select ed from EmployeeAndDeptGroup ed where ed.deptGroupId ='" + deptGroupId
                + "' and ed.employeeId = '" + employeeId + "'";
        try {
            empAndGroup = daoImpl.findOne(queryString);
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return empAndGroup;
    }

    @Override
    @Transactional
    public int deleteEmployeeAndDeptGroup(String employeeId, String deptGroupId) {
        int num = 0;
        try {
            EmployeeAndDeptGroup employeeAndDeptGroup = getEmployeeAndDeptGroup(employeeId, deptGroupId);
            DepartmentGroup group = getDepartmentGroup(deptGroupId);
            if (employeeAndDeptGroup != null) {
                if (employeeId != group.getSupervisorId()) {
                    daoImpl.delete(employeeAndDeptGroup);
                    num = 1;
                } else {

                    deleteEmployeeAndDeptGroup(deptGroupId);

                    deleteDepartmentGroup(deptGroupId);

                    num = 2;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return num;
    }

    @Override
    @Transactional
    public void deleteEmployeeAndDeptGroup(long employeeId) {
        String queryString = "select eg from EmployeeAndDeptGroup eg where eg.employeeId =" + employeeId;
        List<EmployeeAndDeptGroup> groupList = null;
        try {
            groupList = daoImpl.find(queryString);
            if (groupList.size() != 0) {
                daoImpl.delete(groupList);
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    @Transactional
    public void deleteEmployeeAndDeptGroup(String deptGroupId) {
        String queryString = "select eg from EmployeeAndDeptGroup eg where eg.deptGroupId =" + deptGroupId;
        List<EmployeeAndDeptGroup> groupList = null;
        try {
            groupList = daoImpl.find(queryString);
            if (groupList.size() > 0) {
                for (EmployeeAndDeptGroup empAndGroup : groupList) {
                    daoImpl.delete(empAndGroup);
                }
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    @Transactional
    public void createEmployeeAndOffice(EmployeeAndOffice employeeAndOffice) {
        try {
            daoImpl.save(employeeAndOffice);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public EmployeeAndOffice getEmployeeAndOffice(String employeeId, String officeId) {
        EmployeeAndOffice empAndGroup = null;
        String queryString = "select ed from EmployeeAndOffice ed where ed.officeId =" + officeId
                + " and ed.employeeId = " + employeeId;
        try {
            empAndGroup = daoImpl.findOne(queryString);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            return null;
        }
        return empAndGroup;
    }

    @Override
    public EmployeeAndOffice getEmployeeAndOffice(String employeeId) {
        EmployeeAndOffice empAndOffice = null;
        String queryString = "select eo from EmployeeAndOffice eo where eo.employeeId = " + employeeId;
        try {
            empAndOffice = daoImpl.findOne(queryString);
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return empAndOffice;
    }

    @Override
    @Transactional
    public boolean deleteEmployeeAndOffice(String employeeId, String officeId) {
        boolean b = false;
        try {
            EmployeeAndOffice employeeAndOffice = getEmployeeAndOffice(employeeId, officeId);
            if (employeeAndOffice != null) {
                daoImpl.delete(employeeAndOffice);
                b = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return b;
    }

    @Transactional
    @Override
    public void deleteEmployeeAndOffice(String officeId) {
        String queryString = "select eo from EmployeeAndOffice eo where eo.officeId =" + officeId;
        List<EmployeeAndOffice> list = null;
        try {
            list = daoImpl.find(queryString);
            if (list.size() != 0) {
                daoImpl.delete(list);
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void deleteEmployeeAndOffice(long employeeId) {
        String queryString = "select eo from EmployeeAndOffice eo where eo.employeeId =" + employeeId;
        List<EmployeeAndOffice> list = null;
        try {
            list = daoImpl.find(queryString);
            if (list.size() != 0) {
                for (EmployeeAndOffice e : list)
                    daoImpl.delete(e);
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    @Transactional
    public void updateEmployeeAndOffice(EmployeeAndOffice employeeAndOffice) {
        try {
            daoImpl.update(employeeAndOffice);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    @Transactional
    public void createEmployeeAndCommissioner(EmployeeAndCommissioner employeeAndCommissioner) {
        try {
            daoImpl.save(employeeAndCommissioner);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public EmployeeAndCommissioner getEmployeeAndCommissioner(String employeeId, String commissionerId) {
        EmployeeAndCommissioner empAndGroup = null;
        String queryString = "select ec from EmployeeAndCommissioner ec where ec.commissionerId =" + commissionerId
                + " and ec.employeeId = " + employeeId;
        try {
            empAndGroup = daoImpl.findOne(queryString);
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return empAndGroup;
    }

    @Override
    @Transactional
    public boolean deleteEmployeeAndCommissioner(String employeeId, String commissionerId) {
        boolean b = false;
        try {
            EmployeeAndCommissioner employeeAndCommissioner = getEmployeeAndCommissioner(employeeId, commissionerId);
            if (employeeAndCommissioner != null) {
                daoImpl.delete(employeeAndCommissioner);
                b = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return b;
    }

    @Transactional
    @Override
    public void deleteEmployeeAndCommissionerByCommissioner(String commissionerId) {
        String queryString = "select ec from EmployeeAndCommissioner ec where ec.commissionerId =" + commissionerId;
        List<EmployeeAndCommissioner> list = null;
        try {
            list = daoImpl.find(queryString);
            if (list.size() != 0) {
                daoImpl.delete(list);
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void deleteEmployeeAndCommissioner(String employeeId) {
        String queryString = "select ec from EmployeeAndCommissioner ec where ec.employeeId =" + employeeId;
        List<EmployeeAndCommissioner> list = null;
        try {
            list = daoImpl.find(queryString);
            if (list.size() != 0) {
                daoImpl.delete(list);
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public void fillBankInfo(String openId, String bankid, String bankname, String accountName, String accountNumber) {
        String hql = "update  Company set bankId='" + bankid + "', bankName='" + bankname + "', accountName='"
                + accountName + "', accountNumber='" + "' where openId='" + openId + "'";
        try {
            daoImpl.update(hql);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Employee getEmployeeByAccountId(String accountId) {
        String hql = "from Employee where accountId= '" + accountId + "'";
        return daoImpl.findOne(hql);
    }

}
