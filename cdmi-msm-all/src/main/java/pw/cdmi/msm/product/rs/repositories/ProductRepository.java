package pw.cdmi.msm.product.rs.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.product.model.entities.Product;

@NoRepositoryBean
public interface ProductRepository extends PagingAndSortingRepository<Product, String>,QueryByExampleExecutor<Product>{

	public Iterable<Product> findProductByName(String name);
	
	
	public Iterable<Product> findProductByNameOrId(String name, String id);
}
