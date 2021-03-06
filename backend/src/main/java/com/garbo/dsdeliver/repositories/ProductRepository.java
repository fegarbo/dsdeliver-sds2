package com.garbo.dsdeliver.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garbo.dsdeliver.entities.Product;

//Classe para retornar entidade de produto
public interface  ProductRepository extends JpaRepository<Product, Long>{
	
	List<Product> findAllByOrderByNameAsc(); //findAllBy é padrão do spring
}
