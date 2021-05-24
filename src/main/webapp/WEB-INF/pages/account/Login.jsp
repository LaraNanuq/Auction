<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css" />
        <title>Connexion</title>
    </head>

    <body>
        <%@ include file="/WEB-INF/fragments/Navigation.jspf" %>

        <!-- Content -->
        <section class="section">
            <p class="section-title">
                Connexion
            </p>

            <%@ include file="/WEB-INF/fragments/Error.jspf" %>

            <!-- Account -->
            <form action="${pageContext.request.contextPath}/account/login" method="POST" class="form">
                <fieldset class="form-section">
                    <legend class="form-section-title">
                        Profil existant
                    </legend>
                    <div class="form-group">
                        <label for="nickname" class="form-label">
                            Pseudo
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
                        <label for="password" class="form-label">
                            Mot de passe
                        </label>
                        <input
                            type="password"
                            name="password"
                            id="password"
                            placeholder="Mot de passe"
                            class="form-input"
                        />
                        <a href="${pageContext.request.contextPath}/account/login" class="form-link">
                            Mot de passe oublié
                        </a>
                    </div>
                    <div class="form-group">
                        <input
                            type="checkbox"
                            name="rememberMe"
                            id="remember-me"
                            class="form-input"
                            ${requestScope.rememberMe ? 'checked' : ''}
                        />
                        <label for="remember-me" class="form-label">
                            Se souvenir de moi
                        </label>
                    </div>

                    <!-- Form buttons -->
                    <div class="form-button-group">
                        <input type="submit" value="Valider" class="form-button" />
                    </div>
                </fieldset>
            </form>

            <!-- Create account -->
            <fieldset class="form-section">
                <legend class="form-section-title">
                    Nouveau profil
                </legend>
                <div class="form-button-group">
                    <a href="${pageContext.request.contextPath}/account/create" class="form-button">
                        Créer un compte
                    </a>
                </div>
            </fieldset>
        </section>

        <%@ include file="/WEB-INF/fragments/Footer.jspf" %>
    </body>
</html>
