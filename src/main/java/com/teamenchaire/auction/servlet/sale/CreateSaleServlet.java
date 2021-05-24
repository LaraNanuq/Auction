package com.teamenchaire.auction.servlet.sale;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bll.CategoryManager;
import com.teamenchaire.auction.bll.ItemManager;
import com.teamenchaire.auction.bll.UserManager;
import com.teamenchaire.auction.bo.Item;
import com.teamenchaire.auction.bo.User;
import com.teamenchaire.auction.servlet.ParameterParser;

/**
 * A {@code Servlet} which handles requests to the page to create a sale.
 * 
 * @author Marin Taverniers
 */
@WebServlet("/sale/create")
public final class CreateSaleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        User user = new UserManager().getUser(1);
        request.setAttribute("isLogged", true);
        request.setAttribute("user", user);

        try {
            request.setAttribute("categories", new CategoryManager().getCategories());
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("errorCode", e.getCode());
        }
        if (request.getAttribute("startDate") == null) {
            request.setAttribute("startDate", LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
        if (request.getAttribute("street") == null) {
            request.setAttribute("street", user.getStreet());
            request.setAttribute("postalCode", user.getPostalCode());
            request.setAttribute("city", user.getCity());
        }
        try {
            request.getRequestDispatcher("/WEB-INF/pages/sale/Create.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        User user = new UserManager().getUser(1);
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String name = ParameterParser.getTrimmedString(request, "name");
        String description = ParameterParser.getTrimmedString(request, "description");
        Integer categoryId = ParameterParser.getInt(request, "categoryId");
        Integer price = ParameterParser.getInt(request, "price");
        LocalDate startDate = ParameterParser.getDate(request, "startDate");
        LocalDate endDate = ParameterParser.getDate(request, "endDate");
        String street = ParameterParser.getTrimmedString(request, "street");
        String postalCode = ParameterParser.getTrimmedString(request, "postalCode");
        String city = ParameterParser.getTrimmedString(request, "city");
        try {
            Item item = new ItemManager().addItem(user.getId(), name, description, categoryId, price, startDate, endDate, street,
                    postalCode, city);
            response.sendRedirect(request.getContextPath() + "/auction/item/" + item.getId());
            return;
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("errorCode", e.getCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        request.setAttribute("name", name);
        request.setAttribute("description", description);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("price", price);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("street", street);
        request.setAttribute("postalCode", postalCode);
        request.setAttribute("city", city);
        doGet(request, response);
    }
}