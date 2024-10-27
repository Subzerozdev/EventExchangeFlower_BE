package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.entity.USER_ROLE;
import com.eventflowerexchange.repository.CategoryRepository;
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
        // đếm số lượng sản phẩm trong hệ thống
        long totalPosts = postRepository.count();
        status.put("totalPosts", totalPosts);

        // số lượng customer
        long totalUsers = userRepository.countByRole(USER_ROLE.ROLE_CUSTOMER);
        status.put("totalUsers", totalUsers);
        // số lượng owner
        long totalOwners = userRepository.countByRole(USER_ROLE.ROLE_SELLER);

        return status;
    }

    public Map <String,Object> getMonthlyRevenue(String shopId) {
        Map<String,Object> revenueDate = new HashMap<>();

        List<Object[]> monthlyRevenue = transactionRepository.calculateMonthlyRevenue(shopId);
        List<Map<String,Object>> monthlyRevenueList = new ArrayList<>();

        for (Object[] result : monthlyRevenue) {
            Map<String,Object> monthData = new HashMap<>();
            monthData.put("year", result[0]);
            monthData.put("month", result[1]);
            monthData.put("revenue", result[2]);
            monthlyRevenueList.add(monthData);
        }
        revenueDate.put("monthlyRevenue", monthlyRevenueList);
        return revenueDate;


    }
}
