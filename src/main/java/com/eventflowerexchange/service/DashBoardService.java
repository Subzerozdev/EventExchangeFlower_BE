package com.eventflowerexchange.service;

import java.util.Map;

public interface DashBoardService {
    Map<String, Object> getDashBoard();
    Map <String,Object> getMonthlyRevenue(String shopId);
}
