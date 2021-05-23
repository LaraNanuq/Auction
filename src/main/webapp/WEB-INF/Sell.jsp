<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Vendre</title>
    </head>

    <body>
        <%@ include file="fragment/Navigation.jspf" %>

        <!-- Content -->
        <section class="section">
            <p class="section-title">
                Nouvelle vente
            </p>

            <%@ include file="fragment/Error.jspf" %>

            <!-- Item information -->
            <form action="${pageContext.request.contextPath}/sell" method="POST" class="form">
                <!-- Item -->
                <div class="form-section">
                    <fieldset>
                        <legend class="form-section-title">
                            Article
                        </legend>
                        <div class="form-group">
                            <label for="name" class="form-label">
                                Nom
                            </label>
                            <input
                                type="text"
                                name="name"
                                id="name"
                                placeholder="Nom de l'article"
                                value="${requestScope.name}"
                                class="form-input"
                            />
                        </div>
                        <div class="form-group">
                            <label for="description" class="form-label">
                                Description
                            </label>
                            <textarea
                                name="description"
                                id="description"
                                cols="50"
                                rows="5"
                                placeholder="Description de l'article"
                                style="resize: none"
                                class="form-input form-area"
                            >${requestScope.description}</textarea>
                        </div>
                        <div class="form-group">
                            <label for="category-id" class="form-label">
                                Catégorie
                            </label>
                            <select name="categoryId" id="category-id" class="form-input">
                                <option value="" disabled selected>
                                    - Sélectionnez -
                                </option>
                                <c:forEach var="category" items="${requestScope.categories}">
                                    <option value="${category.id}" ${requestScope.categoryId == category.id ? 'selected' : ''}>
                                        ${category.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </fieldset>
                </div>

                <!-- Bid -->
                <div class="form-section">
                    <fieldset>
                        <legend class="form-section-title">
                            Enchère
                        </legend>
                        <div class="form-group">
                            <label for="price" class="form-label">
                                Prix de départ
                            </label>
                            <input
                                type="number"
                                name="price"
                                id="price"
                                min="0"
                                placeholder="Prix"
                                value="${requestScope.price}"
                                class="form-input"
                            />
                        </div>
                        <div class="form-group">
                            <label for="start-date" class="form-label">
                                Début de l'enchère
                            </label>
                            <input
                                type="date"
                                name="startDate"
                                id="start-date"
                                value="${requestScope.startDate}"
                                class="form-input"
                            />
                        </div>
                        <div class="form-group">
                            <label for="end-date" class="form-label">
                                Fin de l'enchère
                            </label>
                            <input
                                type="date"
                                name="endDate"
                                id="end-date"
                                value="${requestScope.endDate}"
                                class="form-input"
                            />
                        </div>
                    </fieldset>
                </div>

                <!-- Withdrawal -->
                <div class="form-section">
                    <fieldset>
                        <legend class="form-section-title">
                            Retrait
                        </legend>
                        <div class="form-group">
                            <label for="street" class="form-label">
                                Rue
                            </label>
                            <input
                                type="text"
                                name="street"
                                id="street"
                                placeholder="Rue"
                                value="${requestScope.street}"
                                class="form-input"
                            />
                        </div>
                        <div class="form-group">
                            <label for="postal-code" class="form-label">
                                Code postal
                            </label>
                            <input
                                type="text"
                                name="postalCode"
                                id="postal-code"
                                placeholder="Code postal"
                                value="${requestScope.postalCode}"
                                class="form-input"
                            />
                        </div>
                        <div class="form-group">
                            <label for="city" class="form-label">
                                Ville
                            </label>
                            <input
                                type="text"
                                name="city"
                                id="city"
                                placeholder="Ville"
                                value="${requestScope.city}"
                                class="form-input"
                            />
                        </div>
                    </fieldset>
                </div>

                <!-- Form buttons -->
                <div class="form-button-group">
                    <input type="submit" value="Valider" class="form-button" />
                    <a href="${pageContext.request.contextPath}" class="form-button">
                        Annuler
                    </a>
                </div>
            </form>
        </section>

        <%@ include file="fragment/Footer.jspf" %>
    </body>
</html>
