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
        <title>Réinitialisation du mot de passe</title>
    </head>

    <body>
        <%@ include file="/WEB-INF/fragments/Navigation.jspf" %>

        <!-- Content -->
        <section class="section">
            <p class="section-title">
                Réinitialisation du mot de passe
            </p>

            <%@ include file="/WEB-INF/fragments/Error.jspf" %>

            <!-- Account -->
            <form action="${pageContext.request.contextPath}/account/reset-password" method="POST" class="form">
                <fieldset class="form-section">
                    <legend class="form-section-title">
                        Profil
                    </legend>
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
                        <label for="new-password" class="form-label">
                            Nouveau mot de passe*
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
                            Confirmation*
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
