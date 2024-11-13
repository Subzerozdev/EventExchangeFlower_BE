package com.eventflowerexchange.service;

import com.eventflowerexchange.entity.Fee;

public interface FeeService {
    float getFeeAmountById(int id);
    Fee getFeeById(int id);
    void updateFeeAmount(int id, float amount);
}
