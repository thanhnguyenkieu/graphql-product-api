package com.example.graphqlproductapi.category;

import com.example.graphqlproductapi.product.Product;
import com.example.graphqlproductapi.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String name;
    private String images;
    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();
    @ManyToMany(mappedBy = "categories")
    private List<User> users = new ArrayList<>();

    protected Category() {}
    public Category(String name, String images) { this.name = name; this.images = images; }
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }
    public List<Product> getProducts() { return products; }
    public List<User> getUsers() { return users; }
}
