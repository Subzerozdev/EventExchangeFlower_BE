package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrdersByUserId(String userID);
    Order findOrderById(Long id);
    @Query("SELECT o " +
            "FROM Order o " +
            "JOIN OrderDetail od ON o.id = od.order.id " +
            "JOIN Post p ON od.post.id = p.id " +
            "WHERE p.user.id = ?1 ")
    public List<Order> findOrdersBySeller(String sellerID);
}
