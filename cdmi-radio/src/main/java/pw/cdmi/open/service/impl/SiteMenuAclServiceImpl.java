package pw.cdmi.open.service.impl;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pw.cdmi.core.db.GenericDao;
import pw.cdmi.open.model.entity.SiteMenuAcl;
import pw.cdmi.open.service.SiteMenuAclService;

/************************************************
 * 对基于URL的权限Menu的访问控制列表类的处理
 * @author WUWEI
 * @date 2015年4月30日
 * @version
 ************************************************/
@Service
public class SiteMenuAclServiceImpl implements SiteMenuAclService {
    @Autowired
    private GenericDao daoImpl;

    @Override
    public List<SiteMenuAcl> findPrivilege() {
        List<SiteMenuAcl> prilist = daoImpl.findAll(SiteMenuAcl.class);
        return prilist;
    }

    @Override
    public SiteMenuAcl getPrivilege(int id) {
        return daoImpl.get(id, SiteMenuAcl.class);
    }

    @Override
    @Transactional
    public boolean savePrivilege(SiteMenuAcl privilege) {
        try {
            daoImpl.save(privilege);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updatePrivilege(SiteMenuAcl privilege) {
        try {
            daoImpl.update(privilege);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean deletePrivilege(int id) {
        try {
            String ql = "from SiteMenuAcl rp where rp.privilege_Id=" + id;
        //  List<Object> list = rolePrivilegeDao.find(ql);
            long count = daoImpl.getCount(ql);
            //判断权限是否被使用
            if (count > 0) {

            } else {
                String ql2="from SiteMenu p where p.parentId="+id +" or p.id="+id;
                List<SiteMenuAcl> plist = daoImpl.find(ql2);
                for (SiteMenuAcl privilege : plist) {
                    daoImpl.delete(privilege);
                }
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
