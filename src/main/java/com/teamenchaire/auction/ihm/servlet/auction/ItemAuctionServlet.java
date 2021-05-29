package com.teamenchaire.auction.ihm.servlet.auction;

import java.time.LocalDate;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bll.ItemManager;
import com.teamenchaire.auction.bo.Item;
import com.teamenchaire.auction.ihm.session.UserSession;
import com.teamenchaire.auction.ihm.util.ServletDispatcher;
import com.teamenchaire.auction.ihm.util.ServletPathParser;

/**
 * A {@code Servlet} which handles requests to the page to view an item.
 * 
 * @author Marin Taverniers
 */
@WebServlet("/auction/item/*")
public final class ItemAuctionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ServletDispatcher dispatcher = new ServletDispatcher(request, response);
        UserSession session = new UserSession(request);
        Integer id = new ServletPathParser(request).getInt();
        try {
            Item item = new ItemManager().getItemById(id);
            if (item == null) {
                dispatcher.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Get state
            LocalDate today = LocalDate.now();
            boolean isSeller = item.getSeller().getId().equals(session.getUserId());
            boolean isStarted = today.isAfter(item.getStartDate().minusDays(1));
            boolean isEnded = today.isAfter(item.getEndDate());
            if (isSeller) {
                // if (isEnded) {
                // dispatcher.redirectToServlet("/sale/finalize/" + item.getId());
                // return;
                // }
                if (!isStarted) {
                    request.setAttribute("canEdit", true);
                }
            } else {
                if (!isStarted) {
                    dispatcher.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                if ((!isEnded) && (session.isOpen())) {
                    request.setAttribute("canBid", true);
                }
            }
            request.setAttribute("item", item);
            request.setAttribute("isEnded", isEnded);
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("exception", e);
        }
        dispatcher.forwardToJsp("/pages/auction/Item.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}