<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Articles</title>
    </head>

    <body>
        <%@ include file="fragment/Navigation.jspf" %>

        <!-- Content -->
        <section class="section">
            <p class="section-title">
                Liste des articles
            </p>

            <%@ include file="fragment/Error.jspf" %>

            <!-- Filters -->
            <form action="${pageContext.request.contextPath}/items" method="GET" class="form">
                <div class="form-section">
                    <fieldset>
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
                        <c:if test="${requestScope.isLogged}">
                            <div class="form-group">
                                <input
                                    type="radio"
                                    name="group"
                                    id="filter-purchases"
                                    value="purchases"
                                    class="form-input"
                                    ${requestScope.group == 'purchases' ? 'checked' : ''}
                                />
                                <label for="filter-purchases" class="form-label">
                                    Achats
                                </label>
                                <ul class="filter-list">
                                    <li class="filter-item">
                                        <input
                                            type="checkbox"
                                            name="purchases"
                                            id="filter-purchases-available"
                                            value="available"
                                            class="form-input"
                                            ${requestScope.purchases.available ? 'checked' : ''}
                                        />
                                        <label for="filter-purchases-available" class="form-label">
                                            Enchères disponibles
                                        </label>
                                    </li>
                                    <li class="filter-item">
                                        <input
                                            type="checkbox"
                                            name="purchases"
                                            id="filter-purchases-current"
                                            value="current"
                                            class="form-input"
                                            ${requestScope.purchases.current ? 'checked' : ''}
                                        />
                                        <label for="filter-purchases-current" class="form-label">
                                            Enchères en cours
                                        </label>
                                    </li>
                                    <li class="filter-item">
                                        <input
                                            type="checkbox"
                                            name="purchases"
                                            id="filter-purchases-won"
                                            value="won"
                                            class="form-input"
                                            ${requestScope.purchases.won ? 'checked' : ''}
                                        />
                                        <label for="filter-purchases-won" class="form-label">
                                            Enchères remportées
                                        </label>
                                    </li>
                                </ul>
                            </div>
                            <div class="form-group">
                                <input
                                    type="radio"
                                    name="group"
                                    id="filter-sales"
                                    value="sales"
                                    class="form-input"
                                    ${requestScope.group == 'sales' ? 'checked' : ''}
                                />
                                <label for="filter-sales" class="form-label">
                                    Ventes
                                </label>
                                <ul class="filter-list">
                                    <li class="filter-item">
                                        <input
                                            type="checkbox"
                                            name="sales"
                                            id="filter-sales-current"
                                            value="current"
                                            class="form-input"
                                            ${requestScope.sales.current ? 'checked' : ''}
                                        />
                                        <label for="filter-sales-current" class="form-label">
                                            Ventes en cours
                                        </label>
                                    </li>
                                    <li class="filter-item">
                                        <input
                                            type="checkbox"
                                            name="sales"
                                            id="filter-sales-future"
                                            value="future"
                                            class="form-input"
                                            ${requestScope.sales.future ? 'checked' : ''}
                                        />
                                        <label for="filter-sales-future" class="form-label">
                                            Ventes à venir
                                        </label>
                                    </li>
                                    <li class="filter-item">
                                        <input
                                            type="checkbox"
                                            name="sales"
                                            id="filter-sales-ended"
                                            value="ended"
                                            class="form-input"
                                            ${requestScope.sales.ended ? 'checked' : ''}
                                        />
                                        <label for="filter-sales-ended" class="form-label">
                                            Ventes terminées
                                        </label>
                                    </li>
                                </ul>
                            </div>
                        </c:if>

                        <!-- Form buttons -->
                        <div class="form-button-group">
                            <input type="submit" value="Rechercher" class="form-button" />
                            <a href="${pageContext.request.contextPath}/items" class="form-button">
                                Réinitialiser
                            </a>
                        </div>
                    </fieldset>
                </div>
            </form>

            <!-- Items -->
            <c:forEach var="itemGroup" items="${requestScope.itemGroups}">
                <div class="section">
                    <p class="section-title">
                        ${itemGroup.key}
                    </p>
                    <c:choose>
                        <c:when test="${!empty(itemGroup.value)}">
                            <ul class="item-list">
                                <c:forEach var="item" items="${itemGroup.value}">
                                    <li class="item">
                                        <p class="item-title">
                                            ${item.name}
                                        </p>
                                        <p class="item-info">
                                            Prix : ${item.sellingPrice}<br />
                                            Fin de l'enchère : ${item.endDate}<br />
                                            Vendeur : ${item.seller.nickname}<br />
                                        </p>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <p class="section-text">
                                Aucun article n'a été trouvé.
                            </p>
                        </c:otherwise>
                    </c:choose>
                </div>
                <hr />
            </c:forEach>
        </section>

        <%@ include file="fragment/Footer.jspf" %>
    </body>
</html>
