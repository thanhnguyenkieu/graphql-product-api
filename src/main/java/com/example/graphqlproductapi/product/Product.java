package com.example.graphqlproductapi.product;

import com.example.graphqlproductapi.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String title;
    @NotNull @Min(0) private Integer quantity;
    private String desc;
    @NotNull @DecimalMin("0.0") private BigDecimal price;
    private Long userId;
    @ManyToMany
    @JoinTable(name = "product_categories",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    protected Product() {}
    public Product(String title, Integer quantity, String desc, BigDecimal price, Long userId) {
        this.title = title; this.quantity = quantity; this.desc = desc; this.price = price; this.userId = userId;
    }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public List<Category> getCategories() { return categories; }
}
