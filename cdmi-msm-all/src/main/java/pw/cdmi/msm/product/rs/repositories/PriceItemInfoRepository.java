package pw.cdmi.msm.product.rs.repositories;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.price.model.entities.PriceItemInfo;

@NoRepositoryBean
public interface PriceItemInfoRepository extends PagingAndSortingRepository<PriceItemInfo, String>,QueryByExampleExecutor<PriceItemInfo>{

	/**
	 * 根据产品价格项获取产品价格信息
	 * @param productId
	 * @return
	 */
	public List<PriceItemInfo> findPriceItemInfoByItemId(String productItemId);
}
