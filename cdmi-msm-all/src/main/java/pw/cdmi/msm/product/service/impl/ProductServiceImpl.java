package pw.cdmi.msm.product.service.impl;


import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.msm.product.model.ProductSaleStatus;
import pw.cdmi.msm.product.model.entities.Product;
import pw.cdmi.msm.product.rs.repositories.ProductRepository;
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
    private ProductRepository repository;

    @Override
    @Transactional
    public boolean createProduct(Product product) {
        boolean flag = false;
        try {
        	repository.save(product);
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
            Product product2 = repository.findOne(product.getId());
            if (product2 != null) {
                product.setPublishDate(product2.getPublishDate());
                repository.save(product);
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
            Product product = repository.findOne(id);
            if (product != null) {
            	repository.delete(product);
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
            product = repository.findOne(id);
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return product;
    }

    @Override
    public Iterable<Product> findAllProductList() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    @Transactional
    public boolean setProductStatus(String id, ProductSaleStatus status) {
        boolean flag = false;
        try {
            Product product = repository.findOne(id);
            product.setSaleStatus(status);
            repository.save(product);
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

        	Iterable<Product> list = repository.findProductByName(name);
            if (list.iterator().hasNext()) {
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
            Iterable<Product> list = repository.findProductByNameOrId(name, id);
            if (list.iterator().hasNext()) {
                flag = true;
            }
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return flag;
    }

}
