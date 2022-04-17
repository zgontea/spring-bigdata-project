package com.shop.demo.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "categories")
@Data
@SuperBuilder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "category_name", unique = true, nullable = false)
    private String categoryName;

    @Column(name = "description", nullable = true)
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Product> products;

}