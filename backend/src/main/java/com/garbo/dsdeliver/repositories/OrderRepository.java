package com.garbo.dsdeliver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garbo.dsdeliver.entities.Order;

public interface  OrderRepository extends JpaRepository<Order, Long>{

}