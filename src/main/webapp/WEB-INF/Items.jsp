<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        <!-- Menubar -->
        <nav class="menubar">
            <div class="menubar-title">ENI-Enchères</div>
            <div class="menubar-link">
                <a href="${pageContext.request.contextPath}/login">S'inscrire - Se connecter</a>
            </div>
        </nav>

        <hr />
        <header>Liste des articles</header>

        <section>
            <!-- Errors -->
            <c:if test="${!empty(requestScope.errorCode)}">
                <div class="errors">
                    <p>${requestScope.errorCode}</p>
                </div>
            </c:if>

            <!-- Filters -->
            <div class="filters">
                <form action="${pageContext.request.contextPath}" method="GET">
                    <fieldset>
                        <legend>Filtres</legend>
                        <div class="form-group">
                            <input
                                type="text"
                                name="name"
                                id="filter-name"
                                placeholder="Le nom de l'article contient"
                                value="${requestScope.name}"
                            />
                        </div>
                        <div class="form-group">
                            <label for="filter-category">Catégorie</label>
                            <select name="categoryId" id="filter-category">
                                <option value="" selected>- Toutes -</option>
                                <c:forEach var="category" items="${requestScope.categories}">
                                    <c:set var="selected" value="${requestScope.categoryId == category.id ? 'selected' : ''}" />
                                    <option value="${category.id}" ${selected}>${category.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <c:if test="${requestScope.isLogged}">
                            <div class="form-group">
                                <input type="radio" name="type" id="filter-type-purchases" value="purchases" checked>
                                <label for="filter-type-purchases">Achats</label>
                                <input type="checkbox" name="purchases" id="filter-purchases-available" value="available" ${requestScope.purchases.available ? 'checked' : ''}>
                                <label for="filter-purchases-available">Enchères disponibles</label>
                                <input type="checkbox" name="purchases" id="filter-purchases-current" value="current" ${requestScope.purchases.current ? 'checked' : ''}>
                                <label for="filter-purchases-current">Enchères en cours</label>
                                <input type="checkbox" name="purchases" id="filter-purchases-won" value="won" ${requestScope.purchases.won ? 'checked' : ''}>
                                <label for="filter-purchases-won">Enchères remportées</label>
                            </div>
                            <div class="form-group">
                                <input type="radio" name="type" id="filter-type-sales" value="sales" ${requestScope.type == 'sales' ? 'checked' : ''}>
                                <label for="filter-type-sales">Ventes</label>
                                <input type="checkbox" name="sales" id="filter-sales-current" value="current" ${requestScope.sales.current ? 'checked' : ''}>
                                <label for="filter-sales-current">Ventes en cours</label>
                                <input type="checkbox" name="sales" id="filter-sales-future" value="future" ${requestScope.sales.future ? 'checked' : ''}>
                                <label for="filter-sales-future">Ventes à venir</label>
                                <input type="checkbox" name="sales" id="filter-sales-ended" value="ended" ${requestScope.sales.ended ? 'checked' : ''}>
                                <label for="filter-sales-ended">Ventes terminées</label>
                            </div>
                        </c:if>

                        <!-- Form buttons -->
                        <div class="form-buttons">
                            <input type="submit" value="Rechercher" />
                            <a class="form-button" href="${pageContext.request.contextPath}">
                                Réinitialiser
                            </a>
                        </div>
                    </fieldset>
                </form>
            </div>

            <!-- Items -->
            <hr />
            <div class="items">
                <c:choose>
                    <c:when test="${!empty(requestScope.items)}">
                        <c:forEach var="item" items="${requestScope.items}">
                            <div class="item">
                                ${item.name}<br />
                                Prix : ${item.sellingPrice}<br />
                                Fin de l'enchère : ${item.endDate}<br />
                                Vendeur : ${item.seller.nickname}
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>Aucune enchère n'a été trouvée.</c:otherwise>
                </c:choose>
            </div>
        </section>
    </body>
</html>
