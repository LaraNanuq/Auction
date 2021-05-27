<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="com.teamenchaire.auction.localization.localization" />

<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css" />
        <title>Article</title>
    </head>

    <body>
        <%@ include file="/WEB-INF/fragments/Navigation.jspf" %>

        <!-- Content -->
        <section class="section">
            <p class="section-title">
                Article
            </p>

            <%@ include file="/WEB-INF/fragments/Error.jspf" %>

            <!-- Auction -->
            <div class="info-section">
                <p class="info-value">
                    ${requestScope.item.name}
                </p>
                <div class="info-group">
                    <label for="description" class="info-label">
                        Description
                    </label>
                    <span id="description" class="info-value">
                        ${requestScope.item.description}
                    </span>
                </div>
                <div class="info-group">
                    <label for="category" class="info-label">
                        Catégorie
                    </label>
                    <span id="category" class="info-value">
                        ${requestScope.item.category.name}
                    </span>
                </div>
                <div class="info-group">
                    <label for="start-date" class="info-label">
                        Début de l'enchère
                    </label>
                    <span id="start-date" class="info-value">
                        ${requestScope.item.startDate}
                    </span>
                </div>
                <div class="info-group">
                    <label for="end-date" class="info-label">
                        Fin de l'enchère
                    </label>
                    <span id="end-date" class="info-value">
                        ${requestScope.item.endDate}
                    </span>
                </div>
                <div class="info-group">
                    <label for="start-price" class="info-label">
                        Prix de départ
                    </label>
                    <span id="start-price" class="info-value">
                        ${requestScope.item.startingPrice}
                    </span>
                </div>
                <div class="info-group">
                    <label for="sell-price" class="info-label">
                        Offre actuelle
                    </label>
                    <span id="sell-price" class="info-value">
                        ${requestScope.item.sellingPrice}
                    </span>
                </div>
                <div class="info-group">
                    <label for="withdrawal" class="info-label">
                        Retrait
                    </label>
                    <div id="withdrawal" class="info-value">
                        ${requestScope.item.withdrawalPoint.street}<br />
                        ${requestScope.item.withdrawalPoint.postalCode} ${requestScope.item.withdrawalPoint.city}
                    </div>
                </div>
                <div class="info-group">
                    <label for="seller" class="info-label">
                        Vendeur
                    </label>
                    <span id="seller" class="info-value">
                        <a href="${pageContext.request.contextPath}/user/profile/${item.seller.nickname}" class="form-link">
                            ${requestScope.item.seller.nickname}
                        </a>
                    </span>
                </div>

                <!-- Bid -->
                <c:choose>
                    <c:when test="${requestScope.canBid}">
                        <form action="${pageContext.request.contextPath}/auction/item/${requestScope.item.id}" method="POST" class="form">
                            <fieldset class="form-section">
                                <legend class="form-section-title">
                                    Enchère
                                </legend>
                                <div class="form-group">
                                    <label for="bid" class="info-label">
                                        Mon enchère
                                    </label>
                                    <input
                                        type="number"
                                        name="bid"
                                        id="bid"
                                        min="0"
                                        placeholder="Offre"
                                        value="${requestScope.bid}"
                                        class="form-input"
                                    />
                                </div>
                            </fieldset>
                        </form>
                    </c:when>
                    <c:when test="${requestScope.isEnded}">
                        <div class="info-group">
                            <span class="info-value">
                                Cette vente est terminée.
                            </span>
                        </div>
                    </c:when>
                </c:choose>

                <!-- Buttons -->
                <div class="form-button-group">
                    <c:choose>
                        <c:when test="${requestScope.canBid}">
                            <input type="submit" value="Enchérir" class="form-button" />
                        </c:when>
                        <c:when test="${requestScope.canEdit}">
                            <a href="${pageContext.request.contextPath}/sale/edit/${requestScope.item.id}" class="form-button-link">
                                Modifier
                            </a>
                        </c:when>
                    </c:choose>
                    <a onclick="history.go(-1)" class="form-button-link">
                        Retour
                    </a>
                </div>
            </div>
        </section>

        <%@ include file="/WEB-INF/fragments/Footer.jspf" %>
    </body>
</html>
