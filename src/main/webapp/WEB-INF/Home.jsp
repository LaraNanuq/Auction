<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Enchères</title>
    </head>
    <body>
        <!-- Menubar -->
        <nav class="menubar">
            <div class="menubar-title">ENI-Enchères</div>
            <div class="menubar-link">
                <a href="${pageContext.request.contextPath}/Login">S'inscrire - Se connecter</a>
            </div>
        </nav>

        <hr />
        <header>Liste des enchères</header>

        <section>
            <!-- Errors -->
            <c:if test="${!empty(requestScope.errorCode)}">
                <div class="errors">
                    <p>${requestScope.errorCode}</p>
                </div>
            </c:if>

            <!-- Filters -->
            <div class="filters">
                <form action="${pageContext.request.contextPath}/" method="POST">
                    <input
                        type="text"
                        name="filterName"
                        id="filter-name"
                        placeholder="Le nom de l'article contient"
                        value="${requestScope.filterName}"
                    />
                    <label for="filter-category">Catégorie</label>
                    <select name="filterCategoryId" id="filter-category">
                        <option value="" selected>- Toutes -</option>
                        <c:forEach var="category" items="${requestScope.categories}">
                            <c:set var="selected" value="${requestScope.filterCategoryId == category.id ? 'selected' : ''}" />
                            <option value="${category.id}" ${selected}>${category.name}</option>
                        </c:forEach>
                    </select>

                    <!-- Form buttons -->
                    <div class="form-buttons">
                        <input type="submit" value="Rechercher" />
                        <a class="form-button" href="${pageContext.request.contextPath}/">
                            Réinitialiser
                        </a>
                    </div>
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
