package com.backend.handicrafts.repository;

import com.backend.handicrafts.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByCategoryIgnoreCase(String category);

	long countByCategoryIgnoreCase(String category);

	Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

	@Query("select avg(p.price) from Product p where lower(p.category) = lower(:category)")
	BigDecimal averagePriceByCategory(String category);

	@Query("select p from Product p where lower(p.name) like lower(concat('%', ?1, '%')) or lower(p.description) like lower(concat('%', ?1, '%'))")
	List<Product> searchByKeyword(String keyword);
}
