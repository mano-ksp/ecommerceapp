package com.ecommerceapp.api;

import com.ecommerceapp.service.OrderProcessService;
import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(name = "OrderProcessServlet", urlPatterns = {"/api/v1/order"})
public class OrderProcessServlet extends HttpServlet {

    // TODO Add filter to any request from client and authenticate and authorize client.
    // TODO Map Many To One And One To One in hibernate entities itself;
    // TODO Order Cancellation

    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        CartRequest cartRequest = gson.fromJson(request.getReader(), CartRequest.class);
        Customer customer = cartRequest.customer;
        String paymentType = cartRequest.paymentType;

        TransactionStatus transactionStatus = OrderProcessService.processOrder(cartRequest.orders, customer, paymentType);

        if (transactionStatus == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else if (transactionStatus.isOneOf(TransactionStatus.FAILED_COMMIT, TransactionStatus.ROLLED_BACK,
                TransactionStatus.ROLLING_BACK)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order Not Placed");
        } else if (transactionStatus.isOneOf(TransactionStatus.FAILED_ROLLBACK)) {
            // TODO Cleanup writes
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order Not Placed");
        }

        PrintWriter writer = response.getWriter();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("OrderStatus", "Order Successfully Placed");
        writer.write(gson.toJson(responseMap));
        writer.close();
    }

    @Getter
    private class CartRequest {
        private List<Order> orders;
        private Customer customer;
        private String paymentType;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Customer {
        private String customerId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class Order {
        private String productName;
        private int orderQuantity;
        private long productId;
    }
}
