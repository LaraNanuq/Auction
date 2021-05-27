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
        <title>Liste des articles</title>
    </head>

    <body>
        <%@ include file="/WEB-INF/fragments/Navigation.jspf" %>

        <!-- Content -->
        <section class="section">
            <p class="section-title">
                Liste des articles
            </p>

            <%@ include file="/WEB-INF/fragments/Error.jspf" %>

            <!-- Filters -->
            <form action="${pageContext.request.contextPath}/auction/list" method="GET" class="form">
                <fieldset class="form-section">
                    <legend class="form-section-title">
                        Filtres
                    </legend>
                    <div class="form-group">
                        <input
                            type="text"
                            name="name"
                            id="filter-name"
                            placeholder="Nom de l'article"
                            value="${requestScope.name}"
                            class="form-input"
                        />
                    </div>
                    <div class="form-group">
                        <label for="filter-category" class="form-label">
                            Catégorie
                        </label>
                        <select name="categoryId" id="filter-category" class="form-input">
                            <option value="" selected>
                                - Toutes -
                            </option>
                            <c:forEach var="category" items="${requestScope.categories}">
                                <option value="${category.id}" ${requestScope.categoryId == category.id ? 'selected' : ''}>
                                    ${category.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <c:if test="${sessionScope.isValid}">
                        <div>
                            <input
                                type="radio"
                                name="group"
                                id="filter-purchases"
                                value="purchases"
                                class="form-input-radio"
                                ${requestScope.group == 'purchases' ? 'checked' : ''}
                            />
                            <label for="filter-purchases" class="form-label">
                                Achats
                            </label>

                            <ul class="form-list">
                                <c:forEach var="subGroup" items="${requestScope.purchasesGroups}">
                                    <li class="form-list-item">
                                        <input
                                            type="checkbox"
                                            name="purchases"
                                            id="filter-purchases-${subGroup.key.groupName}"
                                            value="${subGroup.key.groupName}"
                                            class="form-input-checkbox"
                                            ${subGroup.value ? 'checked' : ''}
                                        />
                                        <label for="filter-purchases-${subGroup.key.groupName}" class="form-label">
                                            <fmt:message key="purchases_${subGroup.key.groupName}" />
                                        </label>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div>
                            <input
                                type="radio"
                                name="group"
                                id="filter-sales"
                                value="sales"
                                class="form-input-radio"
                                ${requestScope.group == 'sales' ? 'checked' : ''}
                            />
                            <label for="filter-sales" class="form-label">
                                Ventes
                            </label>

                            <ul class="form-list">
                                <c:forEach var="subGroup" items="${requestScope.salesGroups}">
                                    <li class="form-list-item">
                                        <input
                                            type="checkbox"
                                            name="sales"
                                            id="filter-sales-${subGroup.key.groupName}"
                                            value="${subGroup.key.groupName}"
                                            class="form-input-checkbox"
                                            ${subGroup.value ? 'checked' : ''}
                                        />
                                        <label for="filter-sales-${subGroup.key.groupName}" class="form-label">
                                            <fmt:message key="sales_${subGroup.key.groupName}" />
                                        </label>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>

                    <!-- Form buttons -->
                    <div class="form-button-group">
                        <input type="submit" value="Rechercher" class="form-button" />
                        <a href="${pageContext.request.contextPath}/auction/list" class="form-button-link">
                            Réinitialiser
                        </a>
                    </div>
                </fieldset>
            </form>

            <!-- Auction -->
            <c:forEach var="itemsGroup" items="${requestScope.items}">
                <div class="info-section">
                    <p class="info-section-title">
                        <fmt:message key="${requestScope.group}_${itemsGroup.key}" />
                    </p>
                    <c:choose>
                        <c:when test="${!empty(itemsGroup.value)}">
                            <ul class="info-list">
                                <c:forEach var="item" items="${itemsGroup.value}">
                                    <li class="info-list-item">
                                        <p class="info-value">
                                            <a href="${pageContext.request.contextPath}/auction/item/${item.id}" class="form-link">
                                                ${item.name}
                                            </a>
                                        </p>
                                        <div class="info-group">
                                            <label for="price" class="info-label">
                                                Prix
                                            </label>
                                            <span id="price" class="info-value">
                                                ${item.sellingPrice}
                                            </span>
                                        </div>
                                        <div class="info-group">
                                            <label for="price" class="info-label">
                                                Fin de l'enchère
                                            </label>
                                            <span id="price" class="info-value">
                                                ${item.endDate}
                                            </span>
                                        </div>
                                        <div class="info-group">
                                            <label for="price" class="info-label">
                                                Vendeur
                                            </label>
                                            <span id="price" class="info-value">
                                                <a href="${pageContext.request.contextPath}/user/profile/${item.seller.nickname}" class="form-link">
                                                    ${item.seller.nickname}
                                                </a>
                                            </span>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <p class="info-value">
                                Aucun article n'a été trouvé.
                            </p>
                        </c:otherwise>
                    </c:choose>
                </div>
                <hr />
            </c:forEach>
        </section>

        <%@ include file="/WEB-INF/fragments/Footer.jspf" %>
    </body>
</html>
