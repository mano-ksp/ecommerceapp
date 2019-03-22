package com.ecommerceapp.api;

import com.ecommerceapp.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "InitData", urlPatterns = "api/v1/db/init")
public class InitData extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String auth = request.getHeader("auth-token");
        if (Objects.nonNull(auth) && auth.equals("superadminmano")) {
            insertAccountData();
            insertInventoryData();
            insertUserSecrets();
            response.getWriter().println("{\"status\":\"success\"}");
            response.setContentType("application/json");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private void insertUserSecrets() {
        Session session = HibernateSessionFactoryUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.createSQLQuery("INSERT INTO UserSecrets (ID, AccountID, Created, CreditCard, DebitCard, Modified, Password, UserName) VALUES (1, 1, '2019-03-22 07:21:42.720000', 'xxxx-7238-2192', 'xxxx-7238-2192', '2019-03-22 07:22:03.796000', 0x617364617364, 'ABC');")
                .executeUpdate();
        session.createSQLQuery("INSERT INTO UserSecrets (ID, AccountID, Created, CreditCard, DebitCard, Modified, Password, UserName) VALUES (2, 2, '2019-03-22 07:21:42.720000', 'xxxx-7238-2192', 'xxxx-7238-2192', '2019-03-22 07:22:03.796000', 0x617364617364, 'PDG');")
                .executeUpdate();
        session.createSQLQuery("INSERT INTO UserSecrets (ID, AccountID, Created, CreditCard, DebitCard, Modified, Password, UserName) VALUES (3, 3, '2019-03-22 07:21:42.720000', 'xxxx-7238-2192', 'xxxx-7238-2192', '2019-03-22 07:22:03.796000', 0x617364617364, 'WAE');")
                .executeUpdate();

        transaction.commit();
        session.close();
    }

    private void insertInventoryData() {
        Session session = HibernateSessionFactoryUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.createSQLQuery("INSERT INTO Inventory (ID, Available, AvailableUnits, Discount, Name, PerUnitPrice, QuantityMetric, QuantityPerUnit, Version) VALUES (1, true, 100, 2, 'Milk', 26.00, 'milli litres', 500, 1);")
                .executeUpdate();
        session.createSQLQuery("INSERT INTO Inventory (ID, Available, AvailableUnits, Discount, Name, PerUnitPrice, QuantityMetric, QuantityPerUnit, Version) VALUES (2, true, 150, 1, 'Burger', 100.00, 'unit', 1, 1);")
                .executeUpdate();
        session.createSQLQuery("INSERT INTO Inventory (ID, Available, AvailableUnits, Discount, Name, PerUnitPrice, QuantityMetric, QuantityPerUnit, Version) VALUES (3, true, 90, 3, 'Cake', 10.00, 'unit', 1, 1);")
                .executeUpdate();
        session.createSQLQuery("INSERT INTO Inventory (ID, Available, AvailableUnits, Discount, Name, PerUnitPrice, QuantityMetric, QuantityPerUnit, Version) VALUES (4, true, 120, 0, 'Gasoline', 86.78, 'litres', 1, 1);")
                .executeUpdate();
        session.createSQLQuery("INSERT INTO Inventory (ID, Available, AvailableUnits, Discount, Name, PerUnitPrice, QuantityMetric, QuantityPerUnit, Version) VALUES (5, true, 200, 5, 'Drinks', 20.00, 'litres', 1, 1);")
                .executeUpdate();

        transaction.commit();
        session.close();
    }

    private void insertAccountData() {

        Session session = HibernateSessionFactoryUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("INSERT INTO Account (ID, Address, City, Country, Created, Email, FirstName, LastName, Mobile, Modified, Phone, State, Discount, UserType) VALUES (1, 'abc', 'a', 'india', '2019-03-22 05:10:56.786000', 'abc@test.com', 'ABC', 'TEST', 2131231, '2019-03-22 05:11:22.958000', 12312123, 'B', 10, 'SENIOR')")
                .executeUpdate();
        session.createSQLQuery("INSERT INTO Account (ID, Address, City, Country, Created, Email, FirstName, LastName, Mobile, Modified, Phone, State, Discount, UserType) VALUES (3, 'abc', 'a', 'india', '2019-03-22 05:10:56.786000', 'pfg@test.com', 'PFG', 'TEST', 2131231, '2019-03-22 05:11:22.958000', 12312123, 'B', 5, 'EMPLOYEE')")
                .executeUpdate();
        session.createSQLQuery("INSERT INTO Account (ID, Address, City, Country, Created, Email, FirstName, LastName, Mobile, Modified, Phone, State, Discount, UserType) VALUES (2, 'abc', 'a', 'india', '2019-03-22 05:10:56.786000', 'cda@test.com', 'CDA', 'TEST', 2131231, '2019-03-22 05:11:22.958000', 12312123, 'B', 0, 'GENERAL')")
                .executeUpdate();
        transaction.commit();
        session.close();
    }
}
