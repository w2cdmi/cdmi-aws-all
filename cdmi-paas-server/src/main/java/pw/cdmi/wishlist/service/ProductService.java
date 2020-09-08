package pw.cdmi.wishlist.service;

import java.util.List;

import pw.cdmi.wishlist.model.entities.Product;

public interface ProductService {

    public Product createProduct(Product product);

    public List<Product> getProductList();

    public Product getProductById(String id);
}
