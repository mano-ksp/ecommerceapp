package com.ecommerceapp.service;

import com.ecommerceapp.entity.InventoryEntity;
import com.ecommerceapp.entity.OrderDetailsEntity;
import com.ecommerceapp.entity.OrdersEntity;
import com.ecommerceapp.util.HibernateSessionFactoryUtil;
import com.google.gson.Gson;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class DashboardService {

    private static Gson gson = new Gson();

    public static String getTotalSales(LocalDateTime fromDate, LocalDateTime toDate, String classifier) {
        Session session = HibernateSessionFactoryUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Query<OrderDetailsEntity> query = session.createQuery("from OrderDetailsEntity where created >= :fromDate and created < :toDate", OrderDetailsEntity.class);
        query.setParameter("fromDate", Timestamp.valueOf(fromDate));
        query.setParameter("toDate", Timestamp.valueOf(toDate));

        List<OrderDetailsEntity> list = query.list();

        transaction.commit();
        session.close();

        return getClassifiedData(classifier, list);
    }

    private static String getClassifiedData(String classifier, List<OrderDetailsEntity> list) {

        switch (classifier) {
            case "byCustomer": {
                // TODO reference customer id to customer name
                Map<UUID, Long> idMap = getCustomerIdsFromOrderId();
                Map<Long, Double> data = list.stream().collect(Collectors.groupingBy(e -> idMap.get(e.getOrderId()),
                        Collectors.summingDouble(e -> e.getPrice().doubleValue())));
                return gson.toJson(data);
            }
            case "byProduct": {
                // TODO reference product id to product name
                Map<Long, Double> data = list.stream().collect(Collectors.groupingBy(OrderDetailsEntity::getProductId,
                        Collectors.summingDouble(e -> e.getPrice().doubleValue())));
                return gson.toJson(data);
            }
            case "byDate": {
                Map<LocalDate, Double> data = list.stream().collect(Collectors.groupingBy(e -> e.getCreated().toLocalDateTime().toLocalDate(),
                        Collectors.summingDouble(e -> e.getPrice().doubleValue())));
                return gson.toJson(data);
            }
        }

        throw new IllegalArgumentException("Unknown Classifier type. Please check");
    }

    private static Map<UUID, Long> getCustomerIdsFromOrderId() {
        Session session = HibernateSessionFactoryUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Query<OrdersEntity> query = session.createQuery("from OrdersEntity", OrdersEntity.class);
        List<OrdersEntity> list = query.list();

        transaction.commit();
        session.close();

        return list.stream().collect(Collectors.toMap(OrdersEntity::getId, OrdersEntity::getAccountId));

    }

    public static String getInventoryData(String type) {
        Session session = HibernateSessionFactoryUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query<InventoryEntity> query;
        if (Objects.nonNull(type) && type.equals("inStock")) {
            query = session.createQuery("from InventoryEntity where availableUnits > 0", InventoryEntity.class);
        } else if (Objects.nonNull(type) && type.equals("outOfStock")) {
            query = session.createQuery("from InventoryEntity where availableUnits <= 0", InventoryEntity.class);
        } else {
            query = session.createQuery("from InventoryEntity", InventoryEntity.class);
        }

        List<InventoryEntity> list = query.list();


        transaction.commit();
        session.close();

        return gson.toJson(list);
    }
}
