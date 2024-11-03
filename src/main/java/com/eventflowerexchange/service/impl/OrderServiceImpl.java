package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.OrderRequestDTO;
import com.eventflowerexchange.entity.*;
import com.eventflowerexchange.mapper.OrderMapper;
import com.eventflowerexchange.repository.OrderRepository;
import com.eventflowerexchange.service.OrderService;
import com.eventflowerexchange.service.PaymentService;
import com.eventflowerexchange.util.FieldValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final PaymentService paymentService;

    @Override
    public Order createOrder(OrderRequestDTO orderRequestDTO, User user) {
        // Map request to order entity
        Order order = orderMapper.toOrder(orderRequestDTO);
        // Set other fields
        order.setOrderDate(LocalDateTime.now());
        order.setFeeId(1);
        order.setStatus(ORDER_STATUS.AWAITING_PAYMENT);
        order.setUser(user);
        orderRepository.save(order);
        // Create and save payment
        Payment payment = paymentService.createPayment(order, orderRequestDTO.getPaymentMethod());
        // Set to order and return
        order.setPayment(payment);
        return order;
    }

    @Override
    public Order getOrderById(Long orderID) {
        return orderRepository.findOrderById(orderID);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findOrdersByStatusIsNot(ORDER_STATUS.CANCELLED);
    }

    @Override
    public List<Order> getCustomerOrders(String userID) {
        return orderRepository.findOrdersByUserId(userID);
    }

    @Override
    public List<Order> getSellerOrders(String sellerID) {
        return orderRepository.findOrdersBySeller(sellerID);
    }

    @Override
    public void updateOrderStatus(Long orderID, Boolean status) {
        Order order = orderRepository.findOrderById(orderID);
        FieldValidation.checkObjectExist(order, "Order");
        if (status && order.getStatus().equals(ORDER_STATUS.AWAITING_PICKUP)) {
            order.setStatus(ORDER_STATUS.COMPLETED);
        }
        orderRepository.save(order);
    }

    private String generateHMAC(String secretKey, String signData) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmacSha512.init(keySpec);
        byte[] hmacBytes = hmacSha512.doFinal(signData.getBytes(StandardCharsets.UTF_8));

        StringBuilder result = new StringBuilder();
        for (byte b : hmacBytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    // cái này là code demo của VN Pay
    @Override
    public String createUrl(Order order, User user) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime createDate = LocalDateTime.now();
        String formattedCreateDate = createDate.format(formatter);

        //Chuyển dổi tiền tệ về String cho nó đúng định dạng
        float money = order.getTotalMoney()*100;

        String amount = String.valueOf( (int) money);

        String tmnCode = "BWGP25D7";
        String secretKey = "N4UOZEEJMHX04953JVHQ3Z0SIU5ESVE0";
        String vnpUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
        String returnUrl = "http://localhost:5173/loadingPage?orderID=" + order.getId();
       // String returnUrl = "http://flowershop-ten.vercel.app/loadingPage?orderID=" + order.getId();
        String currCode = "VND";

        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", tmnCode);
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_CurrCode", currCode);
        vnpParams.put("vnp_TxnRef", order.getId().toString());
        vnpParams.put("vnp_OrderInfo", "Thanh toan cho ma GD: " + order.getId());
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Amount",amount);

        vnpParams.put("vnp_ReturnUrl", returnUrl);
        vnpParams.put("vnp_CreateDate", formattedCreateDate);
        vnpParams.put("vnp_IpAddr", "128.199.178.23");

        StringBuilder signDataBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            signDataBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("=");
            signDataBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("&");
        }
        signDataBuilder.deleteCharAt(signDataBuilder.length() - 1); // Remove last '&'

        String signData = signDataBuilder.toString();
        String signed = generateHMAC(secretKey, signData);

        vnpParams.put("vnp_SecureHash", signed);

        StringBuilder urlBuilder = new StringBuilder(vnpUrl);
        urlBuilder.append("?");
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            urlBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("=");
            urlBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("&");
        }
        urlBuilder.deleteCharAt(urlBuilder.length() - 1); // Remove last '&'

        return urlBuilder.toString();
    }

}
