<%-- 
    Document   : sesion
    Created on : 23-feb-2018, 12:15:51
    Author     : P3017OSI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta name="author" content="Coronado arquinigo, Ivan Basilio">
        <meta name="generator" content="NetBeans IDE 8.2">
        <title>Login-OSI</title>
        <link rel="stylesheet" href="<c:url value="PUBLIC/style/sesion.css" />">
        <link rel="stylesheet" href="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/css/bootstrap.css" />">
        <link href="<c:url value="PUBLIC/font-awesome-4.7.0/css/font-awesome.min.css" />" rel="stylesheet" type="text/css"/>
    </head>
    <body>  
        <jsp:scriptlet>
            if (request.getParameter("incorrecto") != null) {
        </jsp:scriptlet>
        <div class="alert alert-warning alert-dismissible fade show text-center" role="alert">
            <strong>Santo Guacamole!</strong> Al parecer la contraseña y el usuario no coincide. De no ser el caso reintente!.
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <jsp:scriptlet>
            }
        </jsp:scriptlet>

        <c:url value="sesion.htm" var="url" scope="page" />
        <div class="loginBox">
            <div class="glass">
                <img src="<c:url value="PUBLIC/img/user.png" />" class="user">
                <h3 style="font-family: 'go3v2';">SISGEIN</h3>
                <f:form  commandName="sesion" action="${url}" method="post">
                    <div class="input-box">
                        <input type="text" name="usuario" placeholder="Usuario" maxlength="8" required="" autofocus="" />
                        <span><i class="fa fa-user" aria-hidden="true"></i></span>
                    </div>
                    <div class="input-box">
                        <input type="password" name="password" placeholder="Contraseña" required=""/>
                        <span><i class="fa fa-lock" aria-hidden="true"></i></span>
                    </div>
                    <input type="submit" value="Iniciar Sesion">
                </f:form>
            </div>
        </div>
        <script type="text/javascript" src="<c:url value="PUBLIC/jquery 3.1.1/jquery-3.1.1.js"></c:url>"></script>
        <script type="text/javascript" src="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/js/Popper.js"></c:url>"></script>
        <script type="text/javascript" src="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/js/bootstrap.js"></c:url>"></script>
        <script src="<c:url value="PUBLIC/script/protegertextos.js" />" type="text/javascript"></script>        
    </body>
</html>
