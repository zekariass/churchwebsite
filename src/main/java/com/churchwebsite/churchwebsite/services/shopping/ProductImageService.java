package com.churchwebsite.churchwebsite.services.shopping;

import com.churchwebsite.churchwebsite.entities.shopping.Product;
import com.churchwebsite.churchwebsite.entities.shopping.ProductImage;
import com.churchwebsite.churchwebsite.repositories.Shopping.ProductImageRepository;
import com.churchwebsite.churchwebsite.utils.LocalFileStorageManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImageService {

    @Value("${local.file.product-images}")
    private String productImageDir;

    private final ProductImageRepository productImageRepository;
    private final ProductService productService;

    public ProductImageService(ProductImageRepository productImageRepository, ProductService productService) {
        this.productImageRepository = productImageRepository;
        this.productService = productService;
    }

    public List<ProductImage> getAllImages() {
        return productImageRepository.findAll();
    }

    public Optional<ProductImage> getImageById(Integer id) {
        return productImageRepository.findById(id);
    }

    public ProductImage saveImage(ProductImage image) {
        return productImageRepository.save(image);
    }

    public ProductImage saveImage(ProductImage image, Integer productId, MultipartFile multipartFile) {
        LocalFileStorageManager fileStorageManager = new LocalFileStorageManager(productImageDir);
        String productImagePath = fileStorageManager.storeFile(multipartFile);

        Product product = productService.getProductById(productId).orElseThrow(()-> new RuntimeException("No Product Found!"));

        image.setProduct(product);
        image.setImageUrl(productImagePath);

        return productImageRepository.save(image);
    }

    public ProductImage updateImage(Integer id, ProductImage updatedImage) {
        return productImageRepository.findById(id)
                .map(existingImage -> {
                    existingImage.setImageUrl(updatedImage.getImageUrl());
                    existingImage.setImageType(updatedImage.getImageType());
                    existingImage.setProduct(updatedImage.getProduct());
                    return productImageRepository.save(existingImage);
                }).orElseThrow(() -> new RuntimeException("Image not found"));
    }

    public void deleteImage(Integer id) {

        ProductImage productImage = productImageRepository.findById(id).orElseThrow(()->new RuntimeException("ProductImage not found!"));

        LocalFileStorageManager storageManager = new LocalFileStorageManager(productImageDir);

        //Split by path separator and get file name to delete
        String[] splittedPath = productImage.getImageUrl().split("/");
        String fileName = splittedPath[splittedPath.length-1];
        storageManager.deleteFile(fileName);

        productImageRepository.deleteById(id);
    }

    public Page<ProductImage> getAllImages(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "createdAt";
        }

        Pageable pageable;
        if(sortBy == "createdAt"){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return productImageRepository.findAll(pageable);
    }

    public Page<ProductImage> getImagesByProductId(Integer productId, int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "createdAt";
        }

        Pageable pageable;
        if(sortBy == "createdAt"){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return productImageRepository.findByProduct(productId, pageable);
    }
}
