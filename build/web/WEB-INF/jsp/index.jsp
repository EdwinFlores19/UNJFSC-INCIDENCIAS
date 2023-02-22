<%-- 
    Document   : index
    Created on : 24-feb-2018, 19:18:18
    Author     : Ivan Basilio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Incidencias-OSI</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author" content="Coronado arquinigo, Ivan Basilio">
        <meta name="generator" content="NetBeans IDE 8.2">
        <link rel="stylesheet" href="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/css/bootstrap.css" />">
        <link rel="stylesheet" href="<c:url value="PUBLIC/font-awesome-4.7.0/css/font-awesome.min.css" />">
        <link href="<c:url value="PUBLIC/style/index.css" />" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="PUBLIC/style/scroll.css" />" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container-fluid" id="barra-Institucional">
            <table>
                <tr align="center"><th colspan="2"><jsp:expression>(String) session.getAttribute("usuario")</jsp:expression></th></tr>
                    <tr align="center"><td><a href="changePassword.htm"><u>Cambiar contraseña</u></a></td><td align="left"><a href="salir.htm"><u>Cerrar Sesión</u></a></td></tr>
                </table>
            </div>
            <div class="container" id="cabezera">
                <header class="row">
                    <h1 class="col col-lg-10">SISGEIN</h1>
                </header>
            </div>
            <br>
            <section class="container">
                <div class="row">
                    <div class="col col-lg-4 text-center" title="Personal">
                        <a href="personal.htm" class="fa fa-child" id="btn-personal"></a>
                        <br>
                        <span>Personal</span>
                    </div>
                    <div class="col col-lg-4 text-center" title="Incidencias">
                        <a href="incidencia.htm" class="fa fa-th" id="btn-incidencias"></a>
                        <br>
                        <span>Incidencias</span>
                    </div>
                    <div class="col col-lg-4 text-center" title="Programacion">
                        <a href="programacion.htm" class="fa fa-bookmark"></a>
                        <br>
                        <span>Programacion</span>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-4 text-center" title="Oficina y unidad">
                        <a href="oficina.htm" class="fa fa-building-o"></a>
                        <br>
                        <span>Oficina y Unidad</span>
                    </div>
                    <div class="col col-lg-4 text-center" title="Unidad">
                        <a class="fa fa-calendar-check-o" href="registrarAsistencia.htm" style=""></a>
                        <br>
                        <span>Asistencia</span>
                    </div>
                    <div class="col col-lg-4 text-center" title="Asistencia">
                        <a href="asistencia.htm" class="fa fa-calendar"></a>
                        <br>
                        <span>Diponibilidad</span>
                    </div>
                </div>
            </section>
            <footer class="container text-center">
                <table>
                    <tr><td>Universidad Nacional José Faustino Sánchez Carrión</td></tr>
                    <tr><td>Ciudad Universitaria - Av. Mercedes Indacochea N 609</td></tr>
                    <tr><td>Teléfono: 232-1338 - Huacho - Perú</td></tr>
                </table>
            </footer>

            <script type="text/javascript" src="<c:url value="PUBLIC/jquery 3.1.1/jquery-3.1.1.js" />"></script>
        <script src="<c:url value="PUBLIC/script/index.js"/>" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/protegertextos.js" />" type="text/javascript"></script>
    </body>
</html>

