package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.ORDER_STATUS;
import com.eventflowerexchange.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrdersByUserIdAndStatusIsNotOrderByOrderDate(String userID, ORDER_STATUS status);
    Order findOrderById(Long id);
    @Query("SELECT o " +
            "FROM Order o " +
            "JOIN OrderDetail od ON o.id = od.order.id " +
            "JOIN Post p ON od.post.id = p.id " +
            "WHERE p.user.id = ?1 " +
            "AND o.status != ?2")
    List<Order> findOrdersBySeller(String sellerID, ORDER_STATUS status);
    List<Order> findOrdersByStatusIsNotAndStatusIsNot(ORDER_STATUS status1, ORDER_STATUS status2);
}
