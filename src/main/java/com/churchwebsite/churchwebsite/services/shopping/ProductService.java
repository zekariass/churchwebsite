package com.churchwebsite.churchwebsite.services.shopping;

import com.churchwebsite.churchwebsite.entities.shopping.Product;
import com.churchwebsite.churchwebsite.entities.shopping.ProductCategory;
import com.churchwebsite.churchwebsite.enums.ProductDeliveryType;
import com.churchwebsite.churchwebsite.enums.ProductListingStatus;
import com.churchwebsite.churchwebsite.repositories.Shopping.ProductCategoryRepository;
import com.churchwebsite.churchwebsite.repositories.Shopping.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public Page<Product> getAllProducts(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "name";
        }

        Pageable pageable;
        if(sortBy == "name"){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return productRepository.findAll(pageable);
    }

    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        product.setListingStatus(ProductListingStatus.LISTED);
        return productRepository.save(product);
    }

    public Product updateProduct(Integer id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(updatedProduct.getName());
                    existingProduct.setDescription(updatedProduct.getDescription());
                    existingProduct.setPrice(updatedProduct.getPrice());
                    existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
                    existingProduct.setDeliveryType(updatedProduct.getDeliveryType());
                    existingProduct.setCategory(updatedProduct.getCategory());
                    existingProduct.setListingStatus(updatedProduct.getListingStatus());
                    return productRepository.save(existingProduct);
                }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }


    public Page<Product> searchByKeywordDeliveryType(String keyword,
                                                     ProductDeliveryType deliveryType,
                                                     Integer page,
                                                     Integer pageSize,
                                                     String sortBy) {
        // If sortBy is empty set 'name' as default sorting column
        if(sortBy.isEmpty()){
            sortBy = "name";
        }

        Pageable pageable;
        if(sortBy.equals("name")){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        if(deliveryType.equals(ProductDeliveryType.DELIVERY)){
            return productRepository.findAllListedProductsBySearchParams(keyword, ProductListingStatus.LISTED, ProductDeliveryType.DELIVERY, pageable);
        }else if (deliveryType.equals(ProductDeliveryType.COLLECT)){
            return productRepository.findAllListedProductsBySearchParams(keyword, ProductListingStatus.LISTED, ProductDeliveryType.COLLECT, pageable);
        }else if(deliveryType.equals(ProductDeliveryType.DELIVERY_OR_COLLECT)) {
            return productRepository.findByListingStatusAndStockQuantityGreaterThan(ProductListingStatus.LISTED, 0, pageable);
        }else{
            return Page.empty();
        }

    }

    public Page<Product> getAllListedProducts(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "name";
        }

        Pageable pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));

        return productRepository.findByListingStatusAndStockQuantityGreaterThan(ProductListingStatus.LISTED, 0, pageable);
    }

    public Page<Product> findByCategory(Integer categoryId, int page, Integer pageSize, String sortBy) {

        if(sortBy.isEmpty()){
            sortBy = "name";
        }

        Pageable pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));

        Optional<ProductCategory> productCategory = productCategoryRepository.findById(categoryId);

        if(productCategory.isPresent()){
            return productRepository.findByCategoryAndListingStatusAndStockQuantityGreaterThan(productCategory.get(), ProductListingStatus.LISTED, 0, pageable);
        }


        return Page.empty();
    }
}
