package com.educandoweb.course.repositories;

import com.educandoweb.course.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

//@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {


}
