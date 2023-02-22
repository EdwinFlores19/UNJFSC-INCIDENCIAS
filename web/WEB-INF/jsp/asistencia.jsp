<%-- 
    Document   : asistencia
    Created on : 02-mar-2018, 9:22:35
    Author     : P3017OSI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib  prefix="f" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta charset="UTF-8">
        <meta name="author" content="Coronado arquinigo, Ivan Basilio">
        <meta name="generator" content="NetBeans IDE 8.2">
        <link rel="stylesheet" href="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/css/bootstrap.css" />">
        <link rel="stylesheet" href="<c:url value="PUBLIC/font-awesome-4.7.0/css/font-awesome.min.css" />">
        <link href="<c:url value="PUBLIC/style/HeaderAndFooter.css" />" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="PUBLIC/style/scroll.css" />" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="PUBLIC/style/listas.css" />" rel="stylesheet" type="text/css"/>
         <link href="<c:url value="PUBLIC/style/letrasResponsive.css" />" rel="stylesheet" type="text/css"/>
        <title>Asistencia-OSI</title>
        <style type="text/css">
            #content-programacion{
                position: fixed;
                top: 5%;
                left: 0;
                width: 100%;
                z-index: 4;
            }
            #content-programacion .container{
                background-color: #fff3cd;
            }
            #content-programacion .container button{
                float: right;
            }
            #content-filtro #content-filtro-head{
                background-color: #142244;
                color: #FFF;
                padding: 10px;
                cursor: pointer;
            }
            #content-filtro #content-filtro-head span:last-child{
                float: right;
            }
            #content-filtro #content-filtro-body{
                background: #031527;
                padding: 0px 10px 10px 10px;
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
                                    <li class="breadcrumb-item active" aria-current="Personal" title="Estas en registro de asistencia">Asistencia</li>
                                </ol>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col col-lg-12">
                            <div class="dropdown">
                                <button class="btn btn-outline-light btn-block dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <span class="fa fa-universal-access"></span> Acciones
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                                    <button class="dropdown-item" type="button" id="btn-new-registro" ><span class="fa fa-user-plus"></span> Asistencia</button>
                                    <button class="dropdown-item" type="button" id="btn-edit-registro" ><span class="fa fa-edit"></span> Editar</button>
                                </div>
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
                    <div class="row">
                        <div class="col col-lg-12">
                            <br><br>
                            <div id="content-filtro" style="box-shadow: 0px 2px 3px black">
                                <div id="content-filtro-head">
                                    <span class="fa fa-filter"></span> Filtros <span class="fa fa-caret-up"></span>
                                </div>
                                <div id="content-filtro-body">
                                    <br>
                                    <f:form name="filtro-asistencia-form" action="#" method="post" commandName="asistencia">
                                    <table>
                                        <tr>
                                            <td>
                                                <b><font color="#bbbaba">De :</font></b>
                                                <input type="date" name="Fecha-inicio" class="form-control" placeholder="aaa-mm-dd" required=""><br> 
                                                <b><font color="#bbbaba">Hasta :</font></b>
                                                <input type="date" name="Fecha-fin" class="form-control" placeholder="aaa-mm-dd"  required=""><br> 
                                                <b><font color="#bbbaba"><span class="fa fa-user"></span> Practicante :</font></b>
                                                    <f:select path="id_prac" class="form-control" requiret="" >
                                                        <f:options items="${listaPracticantes}" />
                                                    </f:select>
                                                <br>
                                                <button type="submit" class="btn btn-primary"><span class="fa fa-search"></span> Buscar</button>
                                            </td>
                                        </tr>
                                    </table>
                                </f:form>
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
                        <h1 class="col col-lg-11 "><a href="#" title="Volver al Inicio"><span>Asistencia</span> <span>O.S.I</span></a></h1>    
                    </div>
                </div>
            </header>
            <section>
                <div class="container-fluid">
                    <div class="row text-center lista-cabezera">
                        <div class="col col-xl-2 col-md-2"><h5><span class="fa fa-calendar-o"></span> Fecha</h5></div>
                        <div class="col col-xl-3 col-md-3"><h5><span class="fa fa-user"></span> Practicante</h5></div>
                        <div class="col col-xl-2 col-md-2"><h5><span class="fa fa-clock-o"></span> Hora de Ingreso</h5></div>
                        <div class="col col-xl-2  col-md-2"><h5><span class="fa fa-clock-o"></span> Hora de Salida</h5></div>
                        <div class="col col-xl-3 col-md-3"><h5><span class="fa fa-sticky-note"></span> Observacion</h5></div>
                    </div>                   
                </div>
                <div class="container-fluid lista-cuerpo text-center">
                    <c:forEach items="${listaPrincipal}" var="Asist" >
                        <div class="row">
                            <div class="col col-lg-2"><p><c:out value="${Asist[0]}" /></p></div>
                            <div class="col col-lg-3"><p><c:out value="${Asist[1]}" /></p></div>
                            <div class="col col-lg-2"><p><c:out value="${Asist[2]}" /></p></div>
                            <div class="col col-lg-2"><p><c:out value="${Asist[3]}" /></p></div>
                            <div class="col col-lg-3"><p><c:out value="${Asist[4]}" /></p></div>
                        </div>
                    </c:forEach>
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
        <div id="cortina"></div>
        <f:form class="formulario-flotante" id="formulario-nuevo-registro" commandName="asistencia" name="asistencia">
            <div class="row  formulario-flotante-cabezera">
                <div class="col col-lg-11">
                    <h6>Asistencia Practicantes</h6> 
                </div>
                <div class="col col-lg-1">
                    <h6><button type="button" class="btn btn-danger"><b><span class="fa fa-remove"></span></b></button></h6>
                </div>
            </div>
            <div class="formulario-flotante-cuerpo">
                <div class="row">
                    <div class="col col-lg-10 offset-1">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert" style="margin-top: 10px;">
                            <strong>Santo guacamole!</strong> Pudes registrar mas de una asistencia a la vez, ten en cuenta que los datos que suministres seran iguales para todos.
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col offset-1 col-lg-3">
                        <div class="form-group">
                            <b>Fecha:</b><br><input type="date" name="fecha" class="form-control" value="<jsp:expression>new java.text.SimpleDateFormat("YYY-MM-dd").format(new java.util.Date())</jsp:expression>" />
                            </div>
                        </div>
                        <div class="col col-lg-3">
                            <div class="form-group">
                                <b>Hora de Ingreso:</b><br><input type="time" name="hora_de_ingreso" class="form-control" required=""/>
                            </div>
                        </div>
                        <div class="col col-lg-3">
                            <div class="form-group">
                                <b>Hora de Salida:</b><br><input type="time" name="hora_de_salida" class="form-control"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col col-lg-10 offset-1">
                            <div class="form-group">
                                <b>Observacion:</b><br>
                                <textarea maxlength="200" name="observacion" placeholder="Campo opcional" class="form-control"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col col-lg-6 offset-1">
                            <h3>Lista de  <span class="badge badge-secondary">Practicantes</span></h3>
                            <br>
                        </div>
                        <div class="col col-lg-3 offset-1 text-right">
                            <button type="button" class="btn btn-primary">
                                Total <span class="badge badge-light">${listaPracticanteSinAsistencia.size()}</span>
                        </button>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-10 offset-1">
                        <ul class="list-group">
                            <li class="list-group-item list-group-item-success"><input class="radioAllPracticantes" type="radio" name="all" id="todos-precticantes" value="Todos"><label class="radioAllPracticantes" for="todos-precticantes">Marcar Todos</label></li>
                                <c:forEach items="${listaPracticanteSinAsistencia}" var="pracAsist" >
                                <li class="list-group-item ">
                                    <input type="checkbox" name="practicantes" value="${pracAsist[0]}" id="prac${pracAsist[0]}">
                                    <label for="prac${pracAsist[0]}"><c:out value="${pracAsist[1]}"/></label>
                                </li>
                            </c:forEach>
                            <br>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row formulario-flotante-footer">
                <div class="col offset-1 col-lg-10 text-right">
                    <button type="submit" class="btn btn-light"><span class="fa fa-check"></span> <b>Marcar asistencia</b></button>
                </div>
            </div>
        </f:form>
        <f:form commandName="asistencia" class="formulario-flotante" id="formulario-editar-registro" name="formulario-editar-registro" action="updateAsistencia.htm" method="post">
            <div class="row  formulario-flotante-cabezera">
                <div class="col col-lg-11">
                    <h6>Modificar Asistencia</h6> 
                </div>
                <div class="col col-lg-1">
                    <h6><button type="button" class="btn btn-danger"><b><span class="fa fa-remove"></span></b></button></h6>
                </div>
            </div>
            <div class="formulario-flotante-cuerpo">
                <div class="row"  style="margin-top: 20px;">
                    <div class="col offset-1 col-lg-3">
                        <div class="form-group">
                            <b>Fecha:</b><br><input type="date" name="fecha" class="form-control" disabled="" />
                        </div>
                    </div>
                    <div class="col col-lg-2">
                        <div class="form-group">
                            <b>Hora de Ingreso:</b><br><input type="time" name="hora_de_ingreso" class="form-control" required=""/>
                        </div>
                    </div>
                    <div class="col col-lg-2">
                        <div class="form-group">
                            <b>Hora de Salida:</b><br><input type="time" name="hora_de_salida" class="form-control"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-1 offset-1">
                        <div class="form-group">
                            <b>Id</b><br>
                            <input type="text" name="id_prac" disabled="" class="form-control"/>
                        </div>
                    </div>
                    <div class="col col-lg-9">
                        <div class="form-group">
                            <b>Practicante</b><br>
                            <input type="text" name="practicante" disabled="" class="form-control" />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-10 offset-1">
                        <div class="form-group">
                            <b>Observacion:</b><br>
                            <textarea maxlength="200" name="observacion" placeholder="Campo opcional" class="form-control" rows="5"></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row formulario-flotante-footer">
                <div class="col offset-1 col-lg-10 text-right">
                    <button type="submit" class="btn btn-light"><span class="fa fa-refresh"></span> <b>Actualizar</b></button>
                </div>
            </div>
        </f:form>
        <script src="<c:url value="PUBLIC/jquery 3.1.1/jquery-3.1.1.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/js/Popper.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/js/bootstrap.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/HeaderAndFooter.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/selectFila.js" />" type="text/javascript"></script>
        <script type="text/javascript" src="<c:url value="PUBLIC/script/asistencia.js" />"></script>
        <script type="text/javascript" src="<c:url value="PUBLIC/script/fullScreen.js" />"></script>
        <script src="<c:url value="PUBLIC/script/protegertextos.js" />" type="text/javascript"></script>
    </body>
</html>
