package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.entity.Fee;
import com.eventflowerexchange.repository.FeeRepository;
import com.eventflowerexchange.service.FeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeeServiceImpl implements FeeService {
    private final FeeRepository feeRepository;

    @Override
    public float getFeeAmountById(int id) {
        Fee fee = feeRepository.getFeeById(id);
        return fee.getAmount();
    }

    @Override
    public Fee getFeeById(int id) {
        return feeRepository.getFeeById(id);
    }

    @Override
    public void updateFeeAmount(int id, float amount) {
        Fee fee = feeRepository.getFeeById(id);
        fee.setAmount(amount);
        feeRepository.saveAndFlush(fee);
    }
}
