package com.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	@Query("SELECT p FROM Product p WHERE p.category.category_id = :category_id")
	
    List<Product> findProductsByCategoryId(@Param("category_id") Long categoryId);	
	
	@Query(value = "Select p from Product p where p.p_name like %:keyword% order by id desc")
	List<Product> searchProduct(String keyword);

	@Query(value = "Select * from Product where product_id = :id and p_price between :min and :max", nativeQuery = true)
	List<Product> getListProductByPriceRange(long id, int min, int max);

	
	@Query("SELECT p FROM Product p ORDER BY p.p_name ASC")
	List<Product> findAllSortedByNameAsc();

	@Query("SELECT p FROM Product p ORDER BY p.p_name DESC")
	List<Product> findAllSortedByNameDesc();

	@Query("SELECT p FROM Product p ORDER BY p.p_price ASC")
	List<Product> findAllSortedByPriceAsc();

	@Query("SELECT p FROM Product p ORDER BY p.p_price DESC")
	List<Product> findAllSortedByPriceDesc();

	@Query("SELECT p FROM Product p")
	List<Product> findAllWithAllPrice();

	@Query("SELECT p FROM Product p WHERE p_price >= 0 and p_price <= 100000")
	List<Product> findAllWithRange0To100();

	@Query("SELECT p FROM Product p WHERE p_price > 100000 and p_price <= 200000")
	List<Product> findAllWithRange100To200();

	@Query("SELECT p FROM Product p WHERE p_price > 200000 and p_price <= 500000")
	List<Product> findAllWithRange200To500();

	@Query("SELECT p FROM Product p WHERE p_price > 500000 and p_price <= 1000000")
	List<Product> findAllWithRange500To1000();

	@Query("SELECT p FROM Product p WHERE p_price > 1000000")
	List<Product> findAllWithRangeGreaterThan1000();

//	@Query("SELECT p FROM Product p WHERE p.createdDate = (SELECT MAX(p2.createdDate) FROM Product p2)")
//    @Query(nativeQuery = true, value = "SELECT * FROM pharmacy_online_final.dbo.product WHERE created_date = (SELECT MAX(created_date) FROM pharmacy_online_final.dbo.product)")
	@Query("SELECT p FROM Product p ORDER BY p.createdDate DESC")
	List<Product> findProductsLatest();

//	@Query(nativeQuery = true, value = "SELECT COUNT(*) FROM pharmacy_online_final.dbo.product WHERE p_price > 0 and p_price <=100")
//	List<Product> countForEachPrice();
	
	@Query("SELECT COUNT(p) FROM Product p WHERE p.p_price >= :minPrice AND p.p_price <= :maxPrice")
    Long countProductsInPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
	
	@Query("SELECT COUNT(p) FROM Product p WHERE p.p_name LIKE %:keyword% AND p.p_price >= :minPrice AND p.p_price <= :maxPrice")
	Long countProductsByKeywordAndPriceRange(
	    @Param("keyword") String keyword,
	    @Param("minPrice") BigDecimal minPrice,
	    @Param("maxPrice") BigDecimal maxPrice
	);
	
	@Modifying
	@Query("UPDATE Product p SET p.p_quantity = p.p_quantity - :quantity WHERE p.product_id IN (SELECT pi.product_id FROM ProductInfo pi WHERE pi.orderInfo.id = :orderId)")
	void updateProductQuantity(@Param("orderId") Long orderId, @Param("quantity") int quantity);
}
