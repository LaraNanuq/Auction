<!-- Author: Marin Taverniers -->

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
        <title>Mon compte</title>
    </head>

    <body>
        <%@ include file="/WEB-INF/fragments/Navigation.jspf" %>

        <!-- Content -->
        <section class="section">
            <p class="section-title">
                Mon compte
            </p>

            <%@ include file="/WEB-INF/fragments/Error.jspf" %>

            <!-- Account -->
            <form action="${pageContext.request.contextPath}/account/edit" method="POST" class="form">
                <fieldset class="form-section">
                    <legend class="form-section-title">
                        Profil
                    </legend>
                    <div class="form-group">
                        <label for="nickname" class="form-label">
                            Pseudo*
                        </label>
                        <input
                            type="text"
                            name="nickname"
                            id="nickname"
                            placeholder="Pseudo"
                            value="${requestScope.nickname}"
                            class="form-input"
                            autofocus
                        />
                    </div>
                    <div class="form-group">
                        <label for="last-name" class="form-label">
                            Nom*
                        </label>
                        <input
                            type="text"
                            name="lastName"
                            id="last-name"
                            placeholder="Nom"
                            value="${requestScope.lastName}"
                            class="form-input"
                        />
                    </div>
                    <div class="form-group">
                        <label for="first-name" class="form-label">
                            Prénom*
                        </label>
                        <input
                            type="text"
                            name="firstName"
                            id="first-name"
                            placeholder="Prénom"
                            value="${requestScope.firstName}"
                            class="form-input"
                        />
                    </div>
                    <div class="form-group">
                        <label for="email" class="form-label">
                            Email*
                        </label>
                        <input
                            type="email"
                            name="email"
                            id="email"
                            placeholder="Email"
                            value="${requestScope.email}"
                            class="form-input"
                        />
                    </div>
                    <div class="form-group">
                        <label for="phone-number" class="form-label">
                            Téléphone
                        </label>
                        <input
                            type="tel"
                            name="phoneNumber"
                            id="phone-number"
                            placeholder="Téléphone"
                            value="${requestScope.phoneNumber}"
                            class="form-input"
                        />
                    </div>
                    <div class="form-group">
                        <label for="street" class="form-label">
                            Rue*
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
                            Code postal*
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
                            Ville*
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
                    <div class="form-group">
                        <label for="old-password" class="form-label">
                            Mot de passe actuel*
                        </label>
                        <input
                            type="password"
                            name="oldPassword"
                            id="old-password"
                            placeholder="Mot de passe actuel"
                            class="form-input"
                        />
                    </div>
                    <div class="form-group">
                        <label for="new-password" class="form-label">
                            Nouveau mot de passe
                        </label>
                        <input
                            type="password"
                            name="newPassword"
                            id="new-password"
                            placeholder="Nouveau mot de passe"
                            class="form-input"
                        />
                    </div>
                    <div class="form-group">
                        <label for="new-password-check" class="form-label">
                            Confirmation
                        </label>
                        <input
                            type="password"
                            name="newPasswordCheck"
                            id="new-password-check"
                            placeholder="Nouveau mot de passe"
                            class="form-input"
                        />
                    </div>
                    <div class="info-section">
                        <p class="info-value">
                            * Champs obligatoires
                        </p>
                    </div>

                    <!-- Buttons -->
                    <div class="form-button-group">
                        <input type="submit" value="Valider" class="form-button" />
                        <a href="${pageContext.request.contextPath}/account/delete" class="form-button-link">
                            Supprimer mon compte
                        </a>
                        <a onclick="history.go(-1)" class="form-button-link">
                            Retour
                        </a>
                    </div>
                </fieldset>
            </form>
        </section>

        <%@ include file="/WEB-INF/fragments/Footer.jspf" %>
    </body>
</html>
