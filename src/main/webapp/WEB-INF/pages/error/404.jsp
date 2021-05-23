<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Page introuvable</title>
    </head>

    <body>
        <%@ include file="/WEB-INF/fragments/Navigation.jspf" %>

        <!-- Content -->
        <section class="section">
            <p class="section-title">
                Page introuvable
            </p>

            <!-- Error -->
            <div class="error">
                <p class="error-text">
                    Désolé, cette page n'existe pas ou a été déplacée.
                </p>
            </div>
        </section>

        <%@ include file="/WEB-INF/fragments/Footer.jspf" %>
    </body>
</html>
