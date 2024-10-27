package com.eventflowerexchange.service;

import java.util.Map;
import java.util.Objects;

public interface DashBoardService {

    Map<String, Object> getDashBoard();
    Map <String,Object> getMonthlyRevenue(String shopId);
}
