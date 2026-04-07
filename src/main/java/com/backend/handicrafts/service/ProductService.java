package com.backend.handicrafts.service;

import com.backend.handicrafts.dto.ProductRequest;
import com.backend.handicrafts.dto.ProductResponse;
import com.backend.handicrafts.entity.Product;
import com.backend.handicrafts.entity.User;
import com.backend.handicrafts.exception.ResourceNotFoundException;
import com.backend.handicrafts.repository.ProductRepository;
import com.backend.handicrafts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ModelMapper modelMapper;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchByKeyword(keyword)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public long countByCategory(String category) {
        return productRepository.countByCategoryIgnoreCase(category);
    }

    public BigDecimal averagePriceByCategory(String category) {
        BigDecimal value = productRepository.averagePriceByCategory(category);
        return value == null ? BigDecimal.ZERO : value;
    }

    public Page<ProductResponse> getProductsPaged(int page, int size, String sortBy, String direction) {
        Sort sort = "desc".equalsIgnoreCase(direction)
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable).map(this::toResponse);
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product product = modelMapper.map(request, Product.class);
        product.setId(null);
        Product savedProduct = productRepository.save(product);
        log.info("DATA INSERTED SUCCESSFULLY: Product{{id={}, name={}}}",
            savedProduct.getId(), savedProduct.getName());

        List<User> users = userRepository.findAll();
        for (User user : users) {
            try {
                emailService.sendNewProductEmail(user.getEmail(), savedProduct.getName());
            } catch (Exception ex) {
                log.error("Email failed for: {}", user.getEmail(), ex);
            }
        }

        return toResponse(savedProduct);
    }

    public ProductResponse updateProduct(Long id, ProductRequest payload) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        existing.setName(payload.getName());
        existing.setDescription(payload.getDescription());
        existing.setPrice(payload.getPrice());
        existing.setImageUrl(payload.getImageUrl());
        existing.setCategory(payload.getCategory());

        return toResponse(productRepository.save(existing));
    }

    public void deleteProduct(Long id) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(existing);
    }

    private ProductResponse toResponse(Product product) {
        return modelMapper.map(product, ProductResponse.class);
    }
}
