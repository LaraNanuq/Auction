package com.teamenchaire.auction.servlet.sale;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.servlet.ServletDispatcher;

/**
 * A {@code Servlet} which handles requests to the page to edit a sale.
 * 
 * @author Marin Taverniers
 */
@WebServlet("/sale/edit/*")
public final class EditSaleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        new ServletDispatcher(request, response).sendError(HttpServletResponse.SC_NOT_FOUND);

        /*
        ServletPathParser parser = new ServletPathParser(request);
        Integer itemId = parser.getInt();
        ServletDispatcher dispatcher = new ServletDispatcher(request, response);
        try {
            request.setAttribute("categories", new CategoryManager().getCategories());
            Item item = null;
            if (itemId != null) {
                item = new ItemManager().getItemById(itemId);
            }
            if (item == null) {
                dispatcher.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            request.setAttribute("name", item.getName());
            request.setAttribute("description", item.getDescription());
            request.setAttribute("categoryId", item.getCategory().getId());
            request.setAttribute("price", item.getStartingPrice());
            request.setAttribute("startDate", item.getStartDate());
            request.setAttribute("endDate", item.getEndDate());
            request.setAttribute("street", item.getWithdrawalPoint().getStreet());
            request.setAttribute("postalCode", item.getWithdrawalPoint().getPostalCode());
            request.setAttribute("city", item.getWithdrawalPoint().getCity());
            request.setAttribute("isDeletable", true);
            request.setAttribute("itemId", item.getId());
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("exception", e);
        }
        dispatcher.forwardToJsp("/pages/sale/Create.jsp");
        */
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}