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
        <title>Suppression du compte</title>
    </head>

    <body>
        <%@ include file="/WEB-INF/fragments/Navigation.jspf" %>

        <!-- Content -->
        <section class="section">
            <p class="section-title">
                Suppression du compte
            </p>

            <%@ include file="/WEB-INF/fragments/Error.jspf" %>

            <!-- Account -->
            <form action="${pageContext.request.contextPath}/account/delete" method="POST" class="form">
                <fieldset class="form-section">
                    <legend class="form-section-title">
                        Profil
                    </legend>
                        <div class="info-group">
                            <input
                                type="checkbox"
                                name="confirmation"
                                id="confirmation"
                                class=""
                            />
                            <label for="confirmation" class="info-label">
                                Je confirme la suppression définitive de mon compte.
                            </label>
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
