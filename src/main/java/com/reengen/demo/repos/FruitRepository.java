package com.reengen.demo.repos;

import com.reengen.demo.domain.Fruit;
import com.reengen.demo.model.FruitDTO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface FruitRepository extends JpaRepository<Fruit, Long> {

	List<Fruit> findByNameOrQuantity(String name, Integer quantity); 
} 
