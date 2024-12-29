package com.churchwebsite.churchwebsite.services.shopping;

import com.churchwebsite.churchwebsite.entities.shopping.ProductCategory;
import com.churchwebsite.churchwebsite.repositories.Shopping.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public Page<ProductCategory> getAllCategories(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "name";
        }

        Pageable pageable;
        if(sortBy == "name"){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return productCategoryRepository.findAll(pageable);
    }

    public Optional<ProductCategory> getCategoryById(Integer id) {
        return productCategoryRepository.findById(id);
    }

    public ProductCategory saveCategory(ProductCategory category) {
        return productCategoryRepository.save(category);
    }

    public ProductCategory updateCategory(Integer id, ProductCategory updatedCategory) {
        return productCategoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(updatedCategory.getName());
                    existingCategory.setParentCategory(updatedCategory.getParentCategory());
                    return productCategoryRepository.save(existingCategory);
                }).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public void deleteCategory(Integer id) {
        productCategoryRepository.deleteById(id);
    }

    public List<ProductCategory> getAllCategories() {
        return productCategoryRepository.findAll();
    }
}

