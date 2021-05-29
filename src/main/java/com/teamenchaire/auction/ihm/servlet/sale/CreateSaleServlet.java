package com.teamenchaire.auction.ihm.servlet.sale;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
import com.teamenchaire.auction.ihm.session.UserSession;
import com.teamenchaire.auction.ihm.util.ServletDispatcher;
import com.teamenchaire.auction.ihm.util.ServletParameterParser;

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
        ServletDispatcher dispatcher = new ServletDispatcher(request, response);
        UserSession session = new UserSession(request);
        try {
            request.setAttribute("categories", new CategoryManager().getCategories());
            request.setAttribute("startDate", LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
            User user = new UserManager().getUserById(session.getUserId());
            request.setAttribute("street", user.getStreet());
            request.setAttribute("postalCode", user.getPostalCode());
            request.setAttribute("city", user.getCity());
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("exception", e);
        }
        dispatcher.forwardToJsp("/pages/sale/Create.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        ServletDispatcher dispatcher = new ServletDispatcher(request, response);
        ServletParameterParser parser = new ServletParameterParser(request);
        Integer userId = new UserSession(request).getUserId();
        String name = parser.getTrimmedString("name");
        String description = parser.getTrimmedString("description");
        Integer categoryId = parser.getInt("categoryId");
        Integer price = parser.getInt("price");
        LocalDate startDate = parser.getDate("startDate");
        LocalDate endDate = parser.getDate("endDate");
        String street = parser.getTrimmedString("street");
        String postalCode = parser.getTrimmedString("postalCode");
        String city = parser.getTrimmedString("city");
        try {
            request.setAttribute("categories", new CategoryManager().getCategories());
            Item item = new ItemManager().addItem(userId, name, description, categoryId, price, startDate, endDate,
                    street, postalCode, city);
            dispatcher.redirectToUrl("/auction/item/" + item.getId());
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("exception", e);
            request.setAttribute("name", name);
            request.setAttribute("description", description);
            request.setAttribute("categoryId", categoryId);
            request.setAttribute("price", price);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("street", street);
            request.setAttribute("postalCode", postalCode);
            request.setAttribute("city", city);
            dispatcher.forwardToJsp("/pages/sale/Create.jsp");
        }
    }
}