package com.ecommerceapp;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            initializeData(client);

            checkInventory(client, "inStock");
            checkInventory(client, "outOfStock");
            checkInventory(client, "allStock");

            orderProducts(client,"{\"orders\":[{\"productName\":\"Milk\",\"orderQuantity\":2,\"productId\":1},{\"productName\":\"Burger\",\"orderQuantity\":2,\"productId\":2},{\"productName\":\"Cake\",\"orderQuantity\":3,\"productId\":3}],\"customer\":{\"customerId\":\"1\"},\"paymentType\":\"VISA\"}");
            orderProducts(client,"{\"orders\":[{\"productName\":\"Milk\",\"orderQuantity\":5,\"productId\":1},{\"productName\":\"Burger\",\"orderQuantity\":1,\"productId\":2},{\"productName\":\"Cake\",\"orderQuantity\":3,\"productId\":3},{\"productName\":\"Drinks\",\"orderQuantity\":6,\"productId\":5}],\"customer\":{\"customerId\":\"2\"},\"paymentType\":\"MASTERCARD\"}");
            orderProducts(client,"{\"orders\":[{\"productName\":\"Milk\",\"orderQuantity\":3,\"productId\":1},{\"productName\":\"Drinks\",\"orderQuantity\":4,\"productId\":5},{\"productName\":\"Gasoline\",\"orderQuantity\":5,\"productId\":4}],\"customer\":{\"customerId\":\"3\"},\"paymentType\":\"OFFLINE\"}");

            checkSalesData(client, "2019-03-22", "2019-03-24", "byDate");
            checkSalesData(client, "2019-03-22", "2019-03-24", "byCustomer");
            checkSalesData(client, "2019-03-22", "2019-03-24", "byProduct");

            checkInventory(client, "inStock");
            checkInventory(client, "outOfStock");
            checkInventory(client, "allStock");
        }
    }

    private static void checkSalesData(CloseableHttpClient client, String fromDate, String toDate, String classifier) throws IOException {
        HttpGet request = new HttpGet("http://localhost:9999/api/v1/totalsales?fromDate=" + fromDate +
                "&toDate=" + toDate + "&classifier=" + classifier);
        CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String data = EntityUtils.toString(entity);
        System.out.println("Sales: " + classifier + " ==>  " + data);
        System.out.println();
        response.close();
    }

    private static void orderProducts(CloseableHttpClient client, String data) throws IOException {
        HttpPost request = new HttpPost("http://localhost:9999/api/v1/order");
        HttpEntity stringEntity = new StringEntity(data);
        request.setEntity(stringEntity);
        CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String out = EntityUtils.toString(entity);
        System.out.println("Order Status ==>  " + out);
        System.out.println();
        response.close();
    }

    private static void checkInventory(CloseableHttpClient client, String type) throws IOException {
        HttpGet request = new HttpGet("http://localhost:9999/api/v1/inventory?type=" + type);
        CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String data = EntityUtils.toString(entity);
        System.out.println("Inventory: " + type + " ==>  " + data);
        System.out.println();
        response.close();
    }

    private static void initializeData(CloseableHttpClient client) throws IOException {
        HttpPost request = new HttpPost("http://localhost:9999/api/v1/db/init");
        request.setHeader("auth-token", "superadminmano");
        CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String data = EntityUtils.toString(entity);
        System.out.println("Initialization ==>  " + data);
        System.out.println();
        response.close();
    }
}
