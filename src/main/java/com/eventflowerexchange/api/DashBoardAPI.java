package com.eventflowerexchange.api;

import com.eventflowerexchange.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class DashBoardAPI {
    private final DashBoardService dashBoardService;

    @GetMapping("/status")
    public ResponseEntity getStatus() {
        Map<String,Object> status = dashBoardService.getDashBoard();
        return ResponseEntity.ok(status);
    }

    @GetMapping("/status")
    public ResponseEntity getMonthlyRevenue(@PathVariable String shopid) {
        Map<String,Object> revenueMonthly = dashBoardService.getMonthlyRevenue(shopid);
        return ResponseEntity.ok(revenueMonthly);
    }
}
