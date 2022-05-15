package com.customer.cookingrecipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
public class Expression {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // todo Long before DB insert
    private String citation;
    private String author;
}
