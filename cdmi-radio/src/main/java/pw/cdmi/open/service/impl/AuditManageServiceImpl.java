package pw.cdmi.open.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import pw.cdmi.core.db.GenericDao;
import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.open.model.entity.AuditPrivilege;
import pw.cdmi.open.service.AuditManageService;
/**
 * **********************************************************
 * 审计管理的实现类
 * 
 * @author liujh
 * @version iSoc Service Platform, 2015-5-5
 ***********************************************************
 */
@Service
public class AuditManageServiceImpl implements AuditManageService {
    
    
    @Resource
    private GenericDao daoImpl;

    @Override
    @Transactional
    public void createAuditPrivailege(AuditPrivilege auditPrivilege) {
        try {
            daoImpl.save(auditPrivilege);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }

    }

    @Override
    @Transactional
    public void deleteAuditPrivilegeById(int id) {
        try {
            AuditPrivilege ap = getAuditPrivilegeById(id);
            if(ap != null){
                daoImpl.delete(ap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public AuditPrivilege getAuditPrivilegeById(int id) {
        AuditPrivilege auditPrivilege = null;
        try {
            auditPrivilege = daoImpl.get(id, AuditPrivilege.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return auditPrivilege;
    }

    @Override
    public Iterable<AuditPrivilege> findAllAuditPrivilege() {
        Iterable<AuditPrivilege> list = new ArrayList<AuditPrivilege>();
        try {
            list = daoImpl.findAll(AuditPrivilege.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public List<AuditPrivilege> findAuditPrivilegeByPage(int pageNo, int pageSize) {
        try {
            return daoImpl.findAll(AuditPrivilege.class, pageNo, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public List<AuditPrivilege> searchAuditPrivilegeList(AuditPrivilege auditPrivilege) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<AuditPrivilege> searchAuditPrivilegeListPage(AuditPrivilege auditPrivilege, int pageNo, int pageSize) {
        // TODO Auto-generated method stub
        return null;
    }

}
