<%-- 
    Document   : asistenciaAlumno
    Created on : 26-mar-2018, 9:39:40
    Author     : P3017OSI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib  prefix="f" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author" content="Coronado arquinigo, Ivan Basilio">
        <meta name="generator" content="NetBeans IDE 8.2">
        <link rel="stylesheet" href="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/css/bootstrap.css" />">
        <link rel="stylesheet" href="<c:url value="PUBLIC/font-awesome-4.7.0/css/font-awesome.min.css" />">
        <link href="<c:url value="PUBLIC/style/HeaderAndFooter.css" />" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="PUBLIC/style/scroll.css" />" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="PUBLIC/style/listas.css" />" rel="stylesheet" type="text/css"/>
         <link href="<c:url value="PUBLIC/style/letrasResponsive.css" />" rel="stylesheet" type="text/css"/>
        <title>registarAsistencia-OSI</title>
        <style type="text/css">
            section{
                overflow-y: auto;
            }
            .cabeza-historia{
                background: #FFF;
                font-weight: bold;
                border-bottom: 1px solid #ccc;
                padding-top: 10px;
                margin-top: 15px;
                box-shadow: 1px 0px 2px #262626;
            }
            .cabeza-historia span{
                float: right;
            }
            .cuerpo-historia{
                background: #FFF;
                padding: 15px;
                box-shadow: 1px 1px 2px #262626;
            }
            .cuerpo-historia .row{
                padding: 10px 0px;
            }
            .cuerpo-historia .row div:first-child,
            .cuerpo-historia .row div:last-child{
                color: #a6a4a6;
            }
            .cuerpo-historia .row div:nth-child(2){
                color: #4d4c4d;
            }
            .container > .row:nth-child(2) > div{
                padding-top: 10px; 
                margin-top: 20px;   
                box-shadow: 1px 1px 2px #262626;
                background: #FFF;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid" id="barra-Institucional">
            <table>
                <tr align="center"><th colspan="2"><jsp:expression>(String) session.getAttribute("usuario")</jsp:expression></th></tr>
                    <tr align="center"><td align="right"><a href="changePassword.htm"><u>Cambiar contraseña</u></a></td><td align="center"><a href="salir.htm"><u>Cerrar Sesión</u></a></td></tr>
                </table>
            </div>
            <nav id="menu">
                <div class="container-fluid">
                    <div class="row text-left">
                        <div class="col col-lg-12">
                            <div aria-label="breadcrumb" >
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item"><a href="index.htm" title="Volver al Inicio">Inicio</a></li>
                                    <li class="breadcrumb-item active" aria-current="Personal" title="Estas en Registros de Incidencias">Registro asistencia practicante</li>
                                </ol>
                            </div>
                        </div>
                    </div>
                      <div class="row">
                        <div class="col col-lg-12">
                             <div class="dropdown">
                                 <br>
                                <button class="btn btn-info btn-block dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <span class="fa fa-desktop"></span> Full Screen
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                                    <button class="dropdown-item" type="button" id="btn-active-fullscreen"><span class=""></span> Activar</button>
                                    <button class="dropdown-item" type="button" id="btn-disable-fullscreen"><span class=""></span> Desacticar</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </nav>
            <div id="cuerpo">
                <header id="cabezera" class="">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col col-lg-1 text-left"><span class="fa fa-remove" id="btn-cerrar-nav" title="Contraer Menu" ></span></div>
                            <h1 class="col col-lg-11 "><a href="#" title="Volver al Inicio"><span>Registro de Asistencia</span> <span>O.S.I</span></a></h1>    
                        </div>
                    </div>
                </header>
                <section>
                    <div class="container">
                        <div class="row">
                            <div class="col col-lg-10 offset-1 cabeza-historia">
                                <p>Hoy - <jsp:expression> new java.text.SimpleDateFormat("EEEEEEEEE dd 'de' MMMMM 'del' yyyy").format(new java.util.Date())</jsp:expression> <span>Ultimos registros</span></p>
                            </div>
                            <div class="col col-lg-10 offset-1 cuerpo-historia">
                            <c:forEach items="${listaPrincipal}" var="lista" >
                                <div class="row">
                                    <div class="col col-lg-2" title="hora de Ingreso">
                                        <c:out value="${lista[2]}" />
                                    </div>
                                    <div class="col col-lg-8" title="Practicante">
                                       <c:out value="${lista[1]}" />
                                    </div>
                                    <div class="col col-lg-2" title="Hora de salida">
                                     <c:out value="${lista[3]}" />
                                    </div>
                                </div>
                            </c:forEach>

                        </div>
                    </div>
                            <div class="row" style="margin-bottom: 20px;">
                        <div class="col col-lg-10 offset-1">
                            <h6>Ingresa tu D.N.I.</h6>
                            <form action="registrarAsistencia.htm" method="post">
                                <div class="form-row">
                                    <div class="form-group col-md-3">
                                        <input type="text" class="form-control" name="dni" placeholder="12345678" pattern="[0-9]{8}" required="" title="DNI" autofocus="" />
                                    </div>
                                    <div class="form-group col-md-3">
                                        <button type="submit" class="btn btn-info"><span class="fa fa-check"></span> aceptar</button>
                                    </div>
                                </div>
                            </form>   
                        </div>
                    </div>
            </section>
            <footer class="container-fluid text-center">
                <table>
                    <tr><td>Universidad Nacional José Faustino Sánchez Carrión</td></tr>
                    <tr><td>Ciudad Universitaria - Av. Mercedes Indacochea N 609</td></tr>
                    <tr><td>Teléfono: 232-1338 - Huacho - Perú</td></tr>
                </table>
            </footer>
        </div>
        <script src="<c:url value="PUBLIC/jquery 3.1.1/jquery-3.1.1.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/js/Popper.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/js/bootstrap.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/HeaderAndFooter.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/selectFila.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/fullScreen.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/protegertextos.js" />" type="text/javascript"></script>
        <script>
            function establecer_tamano_section(){
                var altoHeader=document.getElementById("barra-Institucional").offsetHeight;
                var altoTitulo=document.getElementById("cabezera").offsetHeight;
                var altoFooter=document.getElementsByTagName("footer")[0].offsetHeight;
                var altoBody=document.getElementsByTagName("body")[0].offsetHeight;
                document.getElementsByTagName("section")[0].style.height=altoBody-(altoHeader+altoTitulo+altoFooter)+"px";
            }
            establecer_tamano_section();
        </script>
    </body>
</html>
