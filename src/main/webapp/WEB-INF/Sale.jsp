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
        </nav>

        <hr />
        <header>Nouvelle vente</header>

        <section>
            <!-- Errors -->
            <c:if test="${!empty(requestScope.errorCode)}">
                <div class="errors">
                    <p>${requestScope.errorCode}</p>
                </div>
            </c:if>

            <!-- Sale -->
            <div class="sale">
                <form action="${pageContext.request.contextPath}/Sale" method="POST">
                    <!-- Item -->
                    <div class="item">
                        <fieldset>
                            <legend>Article</legend>
                            <div class="form-group">
                                <label for="name">Nom</label>
                                <input
                                    type="text"
                                    name="name"
                                    id="name"
                                    placeholder="Nom de l'article"
                                    value="${requestScope.name}"
                                />
                            </div>
                            <div class="form-group">
                                <label for="description">Description</label>
                                <textarea
                                    name="description"
                                    id="description"
                                    cols="50"
                                    rows="5"
                                    placeholder="Description de l'article"
                                    style="resize: none;">${requestScope.description}</textarea>
                            </div>
                            <div class="form-group">
                                <label for="category-id">Catégorie</label>
                                <select name="categoryId" id="category-id">
                                    <option value="" disabled selected>- Sélectionnez -</option>
                                    <c:forEach var="category" items="${requestScope.categories}">
                                        <c:set var="selected" value="${requestScope.categoryId == category.id ? 'selected' : ''}" />
                                        <option value="${category.id}" ${selected}>${category.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <!-- 
                            <div class="form-group">
                                <label for="picture">Photo</label>
                                <input
                                    type="file"
                                    name="picture"
                                    id="picture"
                                />
                            </div>
                            -->
                        </fieldset>
                    </div>
                    
                    <!-- Bid -->
                    <div class="bid">
                        <fieldset>
                            <legend>Enchère</legend>
                            <div class="form-group">
                                <label for="price">Prix de départ</label>
                                <input
                                    type="number"
                                    name="price"
                                    id="price"
                                    min="0"
                                    placeholder="Prix"
                                    value="${requestScope.price}"
                                />
                            </div>
                            <div class="form-group">
                                <label for="start-date">Début de l'enchère</label>
                                <input
                                    type="date"
                                    name="startDate"
                                    id="start-date"
                                    value="${requestScope.startDate}"
                                />
                            </div>
                            <div class="form-group">
                                <label for="end-date">Fin de l'enchère</label>
                                <input
                                    type="date"
                                    name="endDate"
                                    id="end-date"
                                    value="${requestScope.endDate}"
                                />
                            </div>
                        </fieldset>
                    </div>
                    
                    <!-- Withdrawal -->
                    <div class="withdrawal">
                        <fieldset>
                            <legend>Retrait</legend>
                            <div class="form-group">
                                <label for="street">Rue</label>
                                <input
                                    type="text"
                                    name="street"
                                    id="street"
                                    placeholder="Rue"
                                    value="${requestScope.street}"
                                />
                            </div>
                            <div class="form-group">
                                <label for="postal-code">Code postal</label>
                                <input
                                    type="text"
                                    name="postalCode"
                                    id="postal-code"
                                    placeholder="Code postal"
                                    value="${requestScope.postalCode}"
                                />
                            </div>
                            <div class="form-group">
                                <label for="city">Ville</label>
                                <input
                                    type="text"
                                    name="city"
                                    id="city"
                                    placeholder="Ville"
                                    value="${requestScope.city}"
                                />
                            </div>
                        </fieldset>
                    </div>
                    
                    <!-- Form buttons -->
                    <div class="form-buttons">
                        <input type="submit" value="Valider" />
                        <a class="form-button" href="${pageContext.request.contextPath}/">
                            Annuler
                        </a>
                    </div>
                </form>
            </div>
        </section>
    </body>
</html>
