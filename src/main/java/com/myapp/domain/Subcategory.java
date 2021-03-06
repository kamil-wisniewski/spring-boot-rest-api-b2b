/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Kamil
 */
@Entity
@Table(name = "subcategory")
@NoArgsConstructor
@Data
public class Subcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    private String name;

    @OneToMany(mappedBy = "subcategory", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Product> product;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

}
