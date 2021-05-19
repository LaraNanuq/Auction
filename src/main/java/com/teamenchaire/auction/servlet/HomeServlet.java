package com.teamenchaire.auction.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bll.CategoryManager;
import com.teamenchaire.auction.bll.ItemManager;
import com.teamenchaire.auction.bo.Category;
import com.teamenchaire.auction.bo.Item;
import com.teamenchaire.auction.bo.User;
import com.teamenchaire.auction.bo.Withdrawal;

/**
 * A {@code Servlet} which handles requests to the home page.
 * 
 * @author Marin Taverniers
 */
@WebServlet("")
public final class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public HomeServlet() {
        // Default constructor
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) {
        final CategoryManager categoryManager = new CategoryManager();
        final ItemManager itemManager = new ItemManager();
        try {
            List<Category> categories = categoryManager.getCategories();
            request.setAttribute("categories", categories);

            //User user = new User(2, "surn", "lastn", "firstn", "ema@", "user_pass", "phone", "stre", "post", "cit", 123, false);
            //Withdrawal point = new Withdrawal(user.getStreet(), user.getPostalCode(), user.getCity());
            //itemManager.addItem(new Item("it_na", "it_de", LocalDate.now(), LocalDate.now().plusDays(2), 1, 789, user, categories.get(2), point));


            final List<Item> items = itemManager.getItems();
            request.setAttribute("items", items);
            
            for (Item item : items) {
                System.out.println(item);
            }
            
        } catch (final BusinessException e) {
            e.printStackTrace();
        }
        final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Home.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (final ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) {
        doGet(request, response);
    }
}