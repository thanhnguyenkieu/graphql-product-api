package com.example.graphqlproductapi.user;

import com.example.graphqlproductapi.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String fullname;
    @Email @NotBlank private String email;
    @NotBlank private String password;
    private String phone;
    @ManyToMany
    @JoinTable(name = "user_categories",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    protected User() {}
    public User(String fullname, String email, String password, String phone) {
        this.fullname = fullname; this.email = email; this.password = password; this.phone = phone;
    }
    public Long getId() { return id; }
    public String getFullname() { return fullname; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public List<Category> getCategories() { return categories; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPhone(String phone) { this.phone = phone; }
}
