package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.entity.TRANSACTION_STATUS;
import com.eventflowerexchange.entity.USER_ROLE;
import com.eventflowerexchange.repository.PostRepository;
import com.eventflowerexchange.repository.TransactionRepository;
import com.eventflowerexchange.repository.UserRepository;
import com.eventflowerexchange.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Map<String, Object> getDashBoard() {
        Map<String, Object>  status = new HashMap<>();
        long totalPosts = postRepository.count();
        status.put("totalPosts", totalPosts);
        long totalUsers = userRepository.countByRole(USER_ROLE.ROLE_CUSTOMER);
        status.put("totalCustomers", totalUsers);
        long totalOwners = userRepository.countByRole(USER_ROLE.ROLE_SELLER);
        status.put("totalSeller", totalOwners);
        return status;
    }

    public Map <String,Object> getMonthlyRevenue(String userID) {
        Map<String,Object> revenueDate = new HashMap<>();

        List<Object[]> monthlyRevenue = transactionRepository.calculateMonthlyRevenue(TRANSACTION_STATUS.SUCCESS, userID);
        List<Map<String,Object>> monthlyRevenueList = new ArrayList<>();

        float totalBalance = 0.0f;
        for (Object[] result : monthlyRevenue) {
            Map<String,Object> monthData = new HashMap<>();
            monthData.put("year", result[0]);
            monthData.put("month", result[1]);
            monthData.put("revenue", result[2]);
            totalBalance += Float.parseFloat(result[2].toString());
            monthlyRevenueList.add(monthData);
        }
        revenueDate.put("monthlyRevenue", monthlyRevenueList);
        revenueDate.put("totalBalance", totalBalance);
        return revenueDate;
    }
}
