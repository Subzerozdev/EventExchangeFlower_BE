package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.Transactions;
import com.eventflowerexchange.entity.TransactionsEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {
    @Query("SELECT YEAR(t.createAt) as year, MONTH(t.createAt) as month, SUM(t.amount) " +
            "FROM Transactions t  WHERE t.status=:status " +
            "AND t.to.id=:shop_id " +
            "GROUP BY YEAR(t.createAt), MONTH (t.createAt)" +
            "ORDER BY YEAR(t.createAt), MONTH (t.createAt)")
    List<Object[]> calculateMonthlyRevenue(
            @Param("status") TransactionsEnum status,
            @Param("shop_id") String shop_id);
}
