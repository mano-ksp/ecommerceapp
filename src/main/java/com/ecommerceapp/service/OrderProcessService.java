package com.ecommerceapp.service;

import com.ecommerceapp.api.OrderProcessServlet;
import com.ecommerceapp.entity.*;
import com.ecommerceapp.exceptions.InsufficientQuantityException;
import com.ecommerceapp.util.HibernateSessionFactoryUtil;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderProcessService {

    private static Logger logger = Logger.getLogger(OrderProcessService.class.getName());

    public static TransactionStatus processOrder(List<OrderProcessServlet.Order> orders, OrderProcessServlet.Customer customer,
                                                 String paymentType) {
        Timestamp currentTime = Timestamp.from(Instant.now());
        TransactionStatus status;
        Session session = HibernateSessionFactoryUtil.getSession();
        Transaction tx = session.beginTransaction(); // TODO Transaction Level Set To Serializable to prevent race conditions. Although Repeatable_Read is sufficient for this app cause we only update the row not inserting any row in inventory
        try {
            session.setHibernateFlushMode(FlushMode.COMMIT);

            AccountEntity accountEntity = getCustomerId(customer, session);

            OrdersEntity ordersEntity = new OrdersEntity();
            UUID uuid = UUID.randomUUID(); // TODO Change to time based UUID
            ordersEntity.setId(uuid);
            ordersEntity.setCreated(currentTime);
            ordersEntity.setModified(currentTime);
            ordersEntity.setOrderDate(currentTime);
            ordersEntity.setAccountId(accountEntity.getId());
            ordersEntity.setTxStatus("Online");
            session.save(ordersEntity);

            PaymentEntity paymentEntity = new PaymentEntity();
            paymentEntity.setType(paymentType); // TODO Add card details table and reference it
            paymentEntity.setOrderId(uuid);
            session.save(paymentEntity);

            for (OrderProcessServlet.Order order : orders) {
                processIndividualProductOrders(accountEntity, order, session, currentTime, uuid);
            }

            session.flush();
            session.clear();
            tx.commit();
            status = tx.getStatus();
            session.close();
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            tx.rollback();
            status = tx.getStatus();
        } catch (InsufficientQuantityException e) {
            tx.rollback();
            status = tx.getStatus();
        }
        return status;
    }

    private static AccountEntity getCustomerId(OrderProcessServlet.Customer customer, Session session) {
        Query<AccountEntity> query = session.createQuery("from AccountEntity WHERE id=:id", AccountEntity.class);
        return query.setParameter("id", Long.parseLong(customer.getCustomerId())).getSingleResult();
    }

    private static void processIndividualProductOrders(AccountEntity accountEntity, OrderProcessServlet.Order order, Session session,
                                                       Timestamp currentTime, UUID orderId) throws InsufficientQuantityException {
        long prductId = order.getProductId();
        int quantity = order.getOrderQuantity();

        Query<InventoryEntity> query = session.createQuery("from InventoryEntity where id=:id", InventoryEntity.class);
        query.setParameter("id", prductId);
        InventoryEntity inventoryEntity = query.getSingleResult();
        Integer availableUnits = inventoryEntity.getAvailableUnits();

        // Update available units
        int updateUnits = availableUnits - quantity;
        if (updateUnits < 0) throw new InsufficientQuantityException(inventoryEntity); // Throw error when request quantity is less than available quantity
        inventoryEntity.setAvailableUnits(updateUnits);
        session.update(inventoryEntity);

        // update order for customer
        OrderDetailsEntity orderDetailsEntity = new OrderDetailsEntity();
        orderDetailsEntity.setOrderId(orderId);
        orderDetailsEntity.setCreated(currentTime);
        orderDetailsEntity.setModified(currentTime);
        orderDetailsEntity.setQuantity(quantity);
        orderDetailsEntity.setProductId(prductId);
        // Discount is processed here for customers and products

        int discount = inventoryEntity.getDiscount() + accountEntity.getDiscount();
        BigDecimal price = inventoryEntity.getPerUnitPrice()
                .subtract(inventoryEntity.getPerUnitPrice().multiply(new BigDecimal(discount)).divide(
                        new BigDecimal(100)
                )).multiply(new BigDecimal(quantity));
        orderDetailsEntity.setPrice(price);
        orderDetailsEntity.setDiscount(discount);
        session.save(orderDetailsEntity);
    }
}
