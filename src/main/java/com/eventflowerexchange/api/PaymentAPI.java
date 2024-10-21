package com.eventflowerexchange.api;

import com.eventflowerexchange.config.BankingConfig;
import com.eventflowerexchange.dto.response.PaymentResponseDTO;
import com.eventflowerexchange.entity.*;
import com.eventflowerexchange.repository.OrderRepository;
import com.eventflowerexchange.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/payment")
public class PaymentAPI {


    private final OrderRepository orderRepository;
    private final PostRepository postRepository;

    public PaymentAPI(OrderRepository orderRepository, PostRepository postRepository) {
        this.orderRepository = orderRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/createPayment")
    public ResponseEntity<?> createPayment(
            HttpServletRequest req
    ) throws UnsupportedEncodingException {
        String orderType = "other";
        long amount = Integer.parseInt(req.getParameter("amount")) * 100;
        String bankCode = req.getParameter("bankCode");
        String vnp_TxnRef = BankingConfig.getRandomNumber(8);
        String vnp_IpAddr = BankingConfig.getIpAddress(req);
        String vnp_TmnCode = BankingConfig.vnp_TmnCode;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", BankingConfig.vnp_Version);
        vnp_Params.put("vnp_Command", BankingConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = BankingConfig.hmacSHA512(BankingConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = BankingConfig.vnp_PayUrl + "?" + queryUrl;

        PaymentResponseDTO paymentRequestDTO = new PaymentResponseDTO();
        paymentRequestDTO.setStatus("OK");
        paymentRequestDTO.setMessage("Successfully");
        paymentRequestDTO.setURL(paymentUrl);
        return ResponseEntity.status(HttpStatus.OK).body(paymentRequestDTO);
    }

    @GetMapping("/vnpay/callback")
    public ResponseEntity<Object> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        String orderID = request.getParameter("orderID");
        if (status.equals("00")) {
            Order order = orderRepository.findOrderById(Long.parseLong(orderID));
            order.setStatus(ORDER_STATUS.AWAITING_PICKUP);
            orderRepository.save(order);
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                Post post = orderDetail.getPost();
                post.setStatus(POST_STATUS.SOLD_OUT);
                postRepository.save(post);
            }

            return new ResponseEntity<>("Order Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Order failed", HttpStatus.BAD_REQUEST);
        }
    }
}
