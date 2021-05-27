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
        <title>Profil utilisateur</title>
    </head>

    <body>
        <%@ include file="/WEB-INF/fragments/Navigation.jspf" %>

        <!-- Content -->
        <section class="section">
            <p class="section-title">
                Profil utilisateur
            </p>

            <%@ include file="/WEB-INF/fragments/Error.jspf" %>

            <!-- User -->
            <div class="info-section">
                <div class="info-group">
                    <label for="nickname" class="info-label">
                        Pseudo
                    </label>
                    <span id="nickname" class="info-value">
                        ${requestScope.user.nickname}
                    </span>
                </div>
                <div class="info-group">
                    <label for="last-name" class="info-label">
                        Nom
                    </label>
                    <span id="last-name" class="info-value">
                        ${requestScope.user.lastName}
                    </span>
                </div>
                <div class="info-group">
                    <label for="first-name" class="info-label">
                        Prénom
                    </label>
                    <span id="first-name" class="info-value">
                        ${requestScope.user.firstName}
                    </span>
                </div>
                <div class="info-group">
                    <label for="email" class="info-label">
                        Email
                    </label>
                    <span id="email" class="info-value">
                        ${requestScope.user.email}
                    </span>
                </div>
                <div class="info-group">
                    <label for="phone-number" class="info-label">
                        Téléphone
                    </label>
                    <span id="phone-number" class="info-value">
                        ${requestScope.user.phoneNumber}
                    </span>
                </div>
                <div class="info-group">
                    <label for="street" class="info-label">
                        Rue
                    </label>
                    <span id="street" class="info-value">
                        ${requestScope.user.street}
                    </span>
                </div>
                <div class="info-group">
                    <label for="postal-code" class="info-label">
                        Code postal
                    </label>
                    <span id="postal-code" class="info-value">
                        ${requestScope.user.postalCode}
                    </span>
                </div>
                <div class="info-group">
                    <label for="city" class="info-label">
                        Ville
                    </label>
                    <span id="city" class="info-value">
                        ${requestScope.user.city}
                    </span>
                </div>
            </div>
            
            <!-- Buttons -->
            <div class="form-button-group">
                <c:if test="${requestScope.isEditable}">
                    <a href="${pageContext.request.contextPath}/account/edit" class="form-button-link">
                        Modifier
                    </a>
                </c:if>
                <a onclick="history.go(-1)" class="form-button-link">
                    Retour
                </a>
            </div>
        </section>

        <%@ include file="/WEB-INF/fragments/Footer.jspf" %>
    </body>
</html>
