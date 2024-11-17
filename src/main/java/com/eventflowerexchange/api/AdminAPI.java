package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.FeeRequestDTO;
import com.eventflowerexchange.dto.response.OrderResponseDTO;
import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminAPI {
    private final PostService postService;
    private final OrderService orderService;
    private final FeeService feeService;
    private final TransactionService transactionService;
    private final ReportService reportService;

    @PutMapping("/posts/{id}/{status}")
    public ResponseEntity<String> updatePostStatus(
            @PathVariable("id") Long id,
            @PathVariable("status") Boolean status
    ) throws Exception {
        postService.updatePostStatus(id, status);
        return new ResponseEntity<>("Successfully Update Status", HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<Object> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderResponseDTO> ordersResponse = new ArrayList<>();
        for (Order order : orders) {
            OrderResponseDTO orderResponseDTO = OrderResponseDTO.builder()
                    .order(order)
                    .shop(order.getOrderDetails().get(0).getPost().getUser().getShop())
                    .transaction(order.getTransactions().get(order.getTransactions().size() - 1))
                    .totalFee(feeService.getFeeAmountById(order.getFeeId()) * order.getTotalMoney())
                    .build();
            ordersResponse.add(orderResponseDTO);
        }
        return new ResponseEntity<>(ordersResponse, HttpStatus.OK);
    }

    @PutMapping("/fee")
    public ResponseEntity<String> updateFee(
            @RequestBody FeeRequestDTO feeRequestDTO
    ) {
        if (feeRequestDTO.getAmount() < 0f || feeRequestDTO.getAmount() > 0.25f) {
            return new ResponseEntity<>("Invalid Amount", HttpStatus.BAD_REQUEST);
        }
        feeService.updateFeeAmount(1, feeRequestDTO.getAmount());
        return new ResponseEntity<>("Successfully Update Fee", HttpStatus.OK);
    }

    @PutMapping("/transaction/{id}")
    public ResponseEntity<String> updateTransactionToSeller(
            @PathVariable Long id
    ) {
        transactionService.updateStatusFromAdminToSeller(id);
        return new ResponseEntity<>("Successfully Update Transaction Status", HttpStatus.OK);
    }

    @PutMapping("/report/{id}")
    public ResponseEntity<String> updateReportIsSolved(
            @PathVariable int id
    ) {
        reportService.solveReport(id);
        return new ResponseEntity<>("Successfully Update Transaction Status", HttpStatus.OK);
    }
}
