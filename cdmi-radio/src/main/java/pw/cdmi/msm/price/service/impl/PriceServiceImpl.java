package pw.cdmi.msm.price.service.impl;

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
import pw.cdmi.msm.price.model.entity.PriceItemInfo;
import pw.cdmi.msm.price.model.entity.ProductPriceItem;
import pw.cdmi.msm.price.service.PriceService;

/**
 * **********************************************************
 * <br/>
 * 实现类，提供系统产品定价接口的具体实现方法
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年6月16日
 ***********************************************************
 */
@Service
public class PriceServiceImpl implements PriceService {

    private static Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);

    @Autowired
    private GenericJPAHibernateImpl jpaImpl;

    @Override
    @Transactional
    public boolean createProductPriceItem(ProductPriceItem productPriceItem) {
        boolean flag = false;
        try {
            jpaImpl.save(productPriceItem);
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
    public boolean updateProductPriceItem(ProductPriceItem productPriceItem) {
        boolean flag = false;
        try {
            ProductPriceItem productPriceItem2 = jpaImpl.get(productPriceItem.getId(), ProductPriceItem.class);
            if (productPriceItem2 != null) {
                jpaImpl.update(productPriceItem);
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
    public boolean deleteProductPriceItem(String id) {
        boolean flag = false;
        try {
            ProductPriceItem productPriceItem = jpaImpl.get(id, ProductPriceItem.class);
            if (productPriceItem != null) {
                jpaImpl.delete(productPriceItem);
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
    public List<ProductPriceItem> findProductPriceItemListByProductId(String productId) {
        List<ProductPriceItem> list = new ArrayList<ProductPriceItem>();
        try {
            String jpql = "from ProductPriceItem where product_id= :product_id";
            JPQuery query = JPQuery.createQuery(jpql);
            query.setParamater("product_id", productId);
            list = jpaImpl.find(query);
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public ProductPriceItem getProductPriceItem(String id) {
        ProductPriceItem productPriceItem = null;
        try {
            productPriceItem = jpaImpl.get(id, ProductPriceItem.class);
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return productPriceItem;
    }

    @Override
    @Transactional
    public boolean createPriceItemInfo(PriceItemInfo priceItemInfo) {
        boolean flag = false;
        try {
            jpaImpl.save(priceItemInfo);
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
    public boolean updatePriceItemInfo(PriceItemInfo priceItemInfo) {
        boolean flag = false;
        try {
            PriceItemInfo priceItemInfo2 = jpaImpl.get(priceItemInfo.getId(), PriceItemInfo.class);
            if (priceItemInfo2 != null) {
                jpaImpl.update(priceItemInfo);
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
    public boolean deletePriceItemInfo(String id) {
        boolean flag = false;
        try {
            PriceItemInfo priceItemInfo = jpaImpl.get(id, PriceItemInfo.class);
            if (priceItemInfo != null) {
                jpaImpl.delete(priceItemInfo);
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
    public List<PriceItemInfo> findPriceItemInfoListByPriceItemId(String priceItemId) {
        List<PriceItemInfo> list = new ArrayList<PriceItemInfo>();
        try {
            String jpql = "from PriceItemInfo where priceItem_Id= :priceItem_Id";
            JPQuery query = JPQuery.createQuery(jpql);
            query.setParamater("priceItem_Id", priceItemId);
            list = jpaImpl.find(query);
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public PriceItemInfo getPriceItemInfo(String id) {
        PriceItemInfo priceItemInfo = null;
        try {
            priceItemInfo = jpaImpl.get(id, PriceItemInfo.class);
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return priceItemInfo;
    }

}
