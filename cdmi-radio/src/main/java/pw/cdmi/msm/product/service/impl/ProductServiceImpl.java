package pw.cdmi.msm.product.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pw.cdmi.core.db.GenericJPAHibernateImpl;
import pw.cdmi.core.db.JPQuery;
import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.msm.product.model.ProductSaleStatus;
import pw.cdmi.msm.product.model.entity.Product;
import pw.cdmi.msm.product.service.ProductService;

/**
 * **********************************************************
 * <br/>
 * 实现类，提供系统产品的具体实现方法
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年5月26日
 ***********************************************************
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private GenericJPAHibernateImpl jpaImpl;

    @Override
    @Transactional
    public boolean createProduct(Product product) {
        boolean flag = false;
        try {
            jpaImpl.save(product);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return flag;
    }

    @Override
    @Transactional
    public boolean updateProduct(Product product) {
        boolean flag = false;
        try {
            Product product2 = jpaImpl.get(product.getId(), Product.class);
            if (product2 != null) {
                product.setPublishDate(product2.getPublishDate());
                jpaImpl.update(product);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return flag;
    }

    @Override
    @Transactional
    public boolean deleteProduct(String id) {
        boolean flag = false;
        try {
            Product product = jpaImpl.get(id, Product.class);
            if (product != null) {
                jpaImpl.delete(product);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return flag;
    }

    @Override
    public Product getProduct(String id) {
        Product product = null;
        try {
            product = jpaImpl.get(id, Product.class);
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return product;
    }

    @Override
    public List<Product> findAllProductList() {
        List<Product> list = new ArrayList<Product>();
        try {
            list = jpaImpl.findAll(Product.class);
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    @Transactional
    public boolean setProductStatus(String id, ProductSaleStatus status) {
        boolean flag = false;
        try {
            Product product = jpaImpl.get(id, Product.class);
            product.setSaleStatus(status);
            jpaImpl.update(product);
            flag = true;
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return flag;
    }

    @Override
    public boolean addProductNameIsRepeat(String name) {
        boolean flag = false;
        try {
            String jpql = "select id from Product where name=:name";

            JPQuery query = JPQuery.createQuery(jpql);
            query.setParamater("name", name);

            List<String> list = jpaImpl.find(query);
            if (list.size() > 0) {
                flag = true;
            }
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return flag;
    }

    @Override
    public boolean updateProductNameIsRepeat(String id, String name) {
        boolean flag = false;
        try {
            String jpql = "select id from Product where id<>:id and name=:name";
            JPQuery query = JPQuery.createQuery(jpql);
            query.setParamater("id", id);
            query.setParamater("name", name);

            List<String> list = jpaImpl.find(query);
            if (list.size() > 0) {
                flag = true;
            }
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return flag;
    }

}
