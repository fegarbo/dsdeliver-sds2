package com.garbo.dsdeliver.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.garbo.dsdeliver.dto.OrderDTO;
import com.garbo.dsdeliver.dto.ProductDTO;
import com.garbo.dsdeliver.entities.Order;
import com.garbo.dsdeliver.entities.OrderStatus;
import com.garbo.dsdeliver.entities.Product;
import com.garbo.dsdeliver.repositories.OrderRepository;
import com.garbo.dsdeliver.repositories.ProductRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly = true) //Para evitar lock no banco já que é só leitura
	public List<OrderDTO> findAll(){
		List<Order> list = repository.findOrdersWithProducts();
		return list.stream().map(x -> new OrderDTO(x)).collect(Collectors.toList()); //Convertendo a lista de produtos para DTO. Primeiro para stream e depois volta para lista
		
	}
	
	@Transactional
	public OrderDTO insert(OrderDTO dto){
		Order order = new Order(null, dto.getAddress(), dto.getLatitude(), dto.getLongitude(), 
				Instant.now(), OrderStatus.PENDING);
		for (ProductDTO p : dto.getProducts()) {
			Product product = productRepository.getOne(p.getId()); //Cria uma entidade gerenciada pelo JPA para ser utilizada na criação das associações order/product
			order.getProducts().add(product);
		}
		order = repository.save(order);
		return new OrderDTO(order);		
	}
	
	@Transactional
	public OrderDTO setDelivered(Long id){
		Order order = repository.getOne(id);
		order.setStatus(OrderStatus.DELIVERED);
		order = repository.save(order);
		return new OrderDTO(order);
	}
}
