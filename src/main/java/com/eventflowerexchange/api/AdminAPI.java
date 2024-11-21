package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.MailBody;
import com.eventflowerexchange.dto.request.FeeRequestDTO;
import com.eventflowerexchange.dto.response.OrderResponseDTO;
import com.eventflowerexchange.dto.response.ApplicationResponseDTO;
import com.eventflowerexchange.entity.*;
import com.eventflowerexchange.mapper.ReportMapper;
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
    private final ApplicationService applicationService;
    private final JwtService jwtService;
    private final NotificationService notificationService;
    private final ReportMapper reportMapper;
    private final EmailService emailService;

    @PutMapping("/posts/{id}/{status}")
    public ResponseEntity<String> updatePostStatus(
            @PathVariable("id") Long id,
            @PathVariable("status") Boolean status
    ) throws Exception {
        Post post = postService.updatePostStatus(id, status);
        String message;
        if (status) {
            message = "Bài đăng số " + id + " của bạn đã được duyệt và đang được đăng bán";
        } else {
            message = "Bài đăng số " + id + " của bạn không được duyệt do vi phạm chính sách của trang web";
        }
        notificationService.createNotification(post.getUser(), "System", NOTIFICATION_TYPE.INFORMATION, message);
        return new ResponseEntity<>("Successfully Update Status", HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<Object> getAllOrders(
            @RequestHeader("Authorization") String jwt
    ) {
        User admin = jwtService.getUserFromJwtToken(jwt);
        List<Order> orders = orderService.getAllOrders();
        List<OrderResponseDTO> ordersResponse = new ArrayList<>();
        for (Order order : orders) {
            OrderResponseDTO orderResponseDTO = OrderResponseDTO.builder()
                    .order(order)
                    .shop(order.getOrderDetails().get(0).getPost().getUser().getShop())
                    .transaction(transactionService.getTransactionsFromAdmin(order, admin))
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
        boolean result = transactionService.updateStatusFromAdminToSeller(id);
        if (result) {
            return new ResponseEntity<>("Successfully Update Transaction Status", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unsuccessfully Update Transaction Status", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/report/{id}/{status}")
    public ResponseEntity<String> updateReportIsSolved(
            @PathVariable int id,
            @PathVariable boolean status
    ) {
        User user = applicationService.solveReport(id, status);
        Application application = applicationService.getReport(id);
        String responseMessage;
        String refundMessage = "";
        if (status) {
            responseMessage = "Đơn: " + application.getProblem() + " của bạn đã được xử lí.";
            Order order = orderService.getOrderById(application.getOrderID());
            if (application.getType().equals(APPLICATION_TYPE.REFUND)) {
                refundMessage = " Bạn nhận được " + order.getTotalMoney() + " từ đơn hàng số " + order.getId() + "  bị hủy";
            } else if (application.getType().equals(APPLICATION_TYPE.REPORT)){
                MailBody mailBody = emailService.createEmail(user.getEmail(), "Hoàn tiền đơn hàng", "Chúng tôi xin chân thành xin lỗi khi đơn hàng số " + order.getId() + " của quý khách đã bị hủy. " +
                        "\nĐể hỗ trợ quý khách hoàn tiền, vui lòng truy cập đường dẫn sau: \nhttp://localhost:5173/backMoney.");
                emailService.sendEmail(mailBody);
                refundMessage = "\nQuý khách vui lòng truy cập đường dẫn sau để được hỗ trợ thực hiện thủ tục hoàn tiền cho đơn hàng "+ order.getId() +": \nhttp://localhost:5173/backMoney.";
            }
        } else {
            responseMessage = "Đơn: " + application.getProblem() + " của bạn đã bị từ chối xử lí.";
        }
        notificationService.createNotification(user, "System", NOTIFICATION_TYPE.INFORMATION, responseMessage + refundMessage);
        return new ResponseEntity<>("Successfully Update Report Status", HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<Object> getUserApplication() {
        List<Application> applications = applicationService.getUserReport();
        List<ApplicationResponseDTO> reportListResponseDTO = new ArrayList<>();
        applications.forEach(report -> {
            ApplicationResponseDTO applicationResponseDTO = reportMapper.toReportResponseDTO(report);
            applicationResponseDTO.setUserEmail(report.getUser().getEmail());
            applicationResponseDTO.setOrder(orderService.getOrderById(report.getOrderID()));
            reportListResponseDTO.add(applicationResponseDTO);
        });
        return new ResponseEntity<>(reportListResponseDTO, HttpStatus.OK);
    }
}
