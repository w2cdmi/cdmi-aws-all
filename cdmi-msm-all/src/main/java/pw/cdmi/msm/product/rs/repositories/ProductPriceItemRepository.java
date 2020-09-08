package pw.cdmi.msm.product.rs.repositories;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.price.model.entities.ProductPriceItem;

@NoRepositoryBean
public interface ProductPriceItemRepository extends PagingAndSortingRepository<ProductPriceItem, String>,QueryByExampleExecutor<ProductPriceItem>{

	/**
	 * 根据产品的ID获取该产品所拥有的价格项
	 * @param productId
	 * @return
	 */
	public List<ProductPriceItem> findByProductId(String productId);
	
}
