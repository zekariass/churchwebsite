package com.churchwebsite.churchwebsite.services.shopping;

import com.churchwebsite.churchwebsite.entities.shopping.Product;
import com.churchwebsite.churchwebsite.entities.shopping.ProductImage;
import com.churchwebsite.churchwebsite.repositories.Shopping.ProductImageRepository;
import com.churchwebsite.churchwebsite.services.storage.CloudinaryFileStorageManager;
import com.churchwebsite.churchwebsite.services.storage.LocalFileStorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductImageService {

    private final Logger logger = LoggerFactory.getLogger(ProductImageService.class);

    @Value("${local.file.product-images}")
    private String localProductImageDir;

    @Value("${cloudinary.file.product-images}")
    private String cloudinaryProductImageDir;

    @Value("${file.storage.type}")
    private String fileStorageType;

    private final ProductImageRepository productImageRepository;
    private final ProductService productService;
    private final CloudinaryFileStorageManager cloudinaryFileStorageManager;

    public ProductImageService(ProductImageRepository productImageRepository, ProductService productService, CloudinaryFileStorageManager cloudinaryFileStorageManager) {
        this.productImageRepository = productImageRepository;
        this.productService = productService;
        this.cloudinaryFileStorageManager = cloudinaryFileStorageManager;
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
        Product product = productService.getProductById(productId).orElseThrow(()-> new RuntimeException("No Product Found!"));
        image.setProduct(product);

        if(fileStorageType.equalsIgnoreCase("cloudinary")){
            Map result = cloudinaryFileStorageManager.storeFile(multipartFile, cloudinaryProductImageDir);
            image.setImageUrl((String) result.get("secure_url"));
            image.setPublicId((String) result.get("public_id"));
        }else{
            LocalFileStorageManager fileStorageManager = new LocalFileStorageManager(localProductImageDir);
            String productImagePath = fileStorageManager.storeFile(multipartFile);
            image.setImageUrl(productImagePath);
            image.setPublicId(null);
        }

        return productImageRepository.save(image);
    }


    public void deleteImage(Integer id) {

        ProductImage productImage = productImageRepository.findById(id).orElseThrow(()->new RuntimeException("ProductImage not found!"));

        if(fileStorageType.equalsIgnoreCase("cloudinary")){
            try {
                cloudinaryFileStorageManager.deleteFile(productImage.getPublicId());
            } catch (IOException e) {
                logger.error("ERROR: ===========================> {}", e.getMessage());
            }
        }else{
            LocalFileStorageManager storageManager = new LocalFileStorageManager(localProductImageDir);

            //Split by path separator and get file name to delete
            String[] splittedPath = productImage.getImageUrl().split("/");
            String fileName = splittedPath[splittedPath.length-1];
            storageManager.deleteFile(fileName);
        }

        productImageRepository.deleteById(id);
    }

    public Page<ProductImage> getAllImages(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "createdAt";
        }

        Pageable pageable;
        if(sortBy.equals("createdAt")){
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
        if(sortBy.equals("createdAt")){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return productImageRepository.findByProduct(productId, pageable);
    }
}
