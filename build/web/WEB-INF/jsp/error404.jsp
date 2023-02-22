<%-- 
    Document   : error404
    Created on : 28-mar-2018, 13:01:31
    Author     : P3017OSI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="author" content="Coronado arquinigo, Ivan Basilio">
        <meta name="generator" content="NetBeans IDE 8.2">
        <link rel="stylesheet" href="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/css/bootstrap.css" />">
        <link rel="stylesheet" href="<c:url value="PUBLIC/font-awesome-4.7.0/css/font-awesome.min.css" />">
        <style type="text/css">
            *{
                margin: 0px;
                padding: 0px;
            }
            body{
                background-image: url('<c:url value="PUBLIC/img/image_404.png" />');
                background-size: cover;
                background-repeat: no-repeat;
                background-attachment: fixed;

            }
            .contendor{
                padding: 10px;
                margin-top:10px;
            }
            .contendor p:first-child{
                font-size: 2em;
                font-family: fantasy;
            }
            figure img{
                position: fixed;
                animation-iteration-count:infinite;
                animation-duration:10s;
                animation-direction: normal; 
                top: -100px;
            }
            figure:nth-child(2) img{
                animation-name: caer1;
                animation-delay: 1s;
                animation-duration:2s;
            }
            figure:nth-child(3) img{
                animation-name: caer2;
                animation-delay: 2s;
                animation-duration:2s;
            }
            figure:nth-child(4) img{
                animation-name: caer3;
                animation-delay: 3s;
                animation-duration:3s;
            }
            figure:nth-child(5) img{
                animation-name: caer4;
                animation-delay: 4s;
                animation-duration:3s;
            }
            figure:nth-child(6) img{
                animation-name: caer5;
                animation-delay: 5s;
                animation-duration:3s;
            }
            figure:nth-child(7) img{
                animation-name: caer6;
                animation-delay: 6s;
                animation-duration:2s;
            }
            figure:nth-child(8) img{
                animation-name: caer7;
                animation-delay: 7s;
                animation-duration:3s;
            }
            figure:nth-child(9) img{
                animation-name: caer8;
                animation-delay: 8s;
                animation-duration:2s;
            }
            @keyframes caer1 {
                from{
                    top:-100px;
                    left:47%;
                    opacity: 1;
                }
                to{
                    top:100%;
                    left: 45%;
                    opacity: 0.1;
                }
            }
            @keyframes caer2 {
                from{
                    top:-100px;
                    left:20%;
                    opacity: 1;
                }
                to{
                    top:100%;
                    left: 20%;
                    opacity: 0.1;
                }
            }
            @keyframes caer3 {
                from{
                    top:-100px;
                    left:80%;
                    opacity: 1;
                }
                to{
                    top:100%;
                    left: 80%;
                    opacity: 0.1;
                }
            }
            @keyframes caer4 {
                from{
                    top:-100px;
                    left:30%;
                    opacity: 1;
                }
                to{
                    top:100%;
                    left: 30%;
                    opacity: 0.1;
                }
            }
            @keyframes caer5 {
                from{
                    top:-100px;
                    left:70%;
                    opacity: 1;
                }
                to{
                    top:100%;
                    left: 70%;
                    opacity: 0.1;
                }
            }
            @keyframes caer6 {
                from{
                    top:-100px;
                    left:40%;
                    opacity: 1;
                }
                to{
                    top:100%;
                    left: 40%;
                    opacity: 0.1;
                }
            }
            @keyframes caer7 {
                from{
                    top:-100px;
                    left:10%;
                    opacity: 1;
                }
                to{
                    top:100%;
                    left: 10%;
                    opacity: 0.1;
                }
            }
            @keyframes caer8 {
                from{
                    top:-100px;
                    left:90%;
                    opacity: 1;
                }
                to{
                    top:100%;
                    left: 90%;
                    opacity: 0.1;
                }
            }
        </style>
    </head>
    <body>
        <section>
            <div class="contendor">
                <p><font color="red" size="40px">¡Oh, no!</font><br>
                    Esta Pagina no existe.</p>
                <p class="text-warning">Ve a la pagina principal y sigue tu camino<br>         
                    desde allí.
                </p>
                <p><a href="index.htm" class="btn btn-primary">Volver al Inicio</a></p>
            </div>
            <figure><img src="<c:url value="PUBLIC/img/lagrimas-de-hombre.png" />" alt="¡Lagrimas de hombre!" width="50" height="50"></figure>
            <figure><img src="<c:url value="PUBLIC/img/lagrimas-de-hombre.png" />" alt="¡Lagrimas de hombre!" width="50" height="50"></figure>
            <figure><img src="<c:url value="PUBLIC/img/lagrimas-de-hombre.png" />" alt="¡Lagrimas de hombre!" width="50" height="50"></figure>
            <figure><img src="<c:url value="PUBLIC/img/lagrimas-de-hombre.png" />" alt="¡Lagrimas de hombre!" width="50" height="50"></figure>
            <figure><img src="<c:url value="PUBLIC/img/lagrimas-de-hombre.png" />" alt="¡Lagrimas de hombre!" width="50" height="50"></figure>
            <figure><img src="<c:url value="PUBLIC/img/lagrimas-de-hombre.png" />" alt="¡Lagrimas de hombre!" width="50" height="50"></figure>
            <figure><img src="<c:url value="PUBLIC/img/lagrimas-de-hombre.png" />" alt="¡Lagrimas de hombre!" width="50" height="50"></figure>
            <figure><img src="<c:url value="PUBLIC/img/lagrimas-de-hombre.png" />" alt="¡Lagrimas de hombre!" width="50" height="50"></figure>
            <script src="<c:url value="PUBLIC/script/protegertextos.js" />" type="text/javascript"></script>
    </body>
</html>
