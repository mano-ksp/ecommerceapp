package com.ecommerceapp.api;

import com.ecommerceapp.service.DashboardService;
import com.ecommerceapp.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/api/v1/totalsales", "/api/v1/inventory"})
public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getRequestURI().substring(request.getContextPath().length());
        if (path.endsWith("totalsales")) {
            String fromDateParam = request.getParameter("fromDate");
            String toDateParam = request.getParameter("toDate");
            String classifier = request.getParameter("classifier");
            LocalDateTime fromDate = LocalDateTime.of(LocalDate.parse(fromDateParam), LocalTime.MIDNIGHT);
            LocalDateTime toDate = LocalDateTime.of(LocalDate.parse(toDateParam), LocalTime.MIDNIGHT);

            String totalSales = DashboardService.getTotalSales(fromDate, toDate, classifier);

            PrintWriter writer = response.getWriter();
            writer.println(totalSales);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.setContentType("application/json");

        } else if (path.endsWith("inventory")) {
            String type = request.getParameter("type");
            String inventoryData = DashboardService.getInventoryData(type);
            PrintWriter writer = response.getWriter();
            writer.println(inventoryData);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.setContentType("application/json");
        }

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
