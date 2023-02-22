<%-- 
    Document   : incidencia
    Created on : 27-feb-2018, 12:46:06
    Author     : P3017OSI
--%>

<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib  prefix="f" uri="http://www.springframework.org/tags/form"%>
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
        <link href="<c:url value="PUBLIC/style/HeaderAndFooter.css" />" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="PUBLIC/style/scroll.css" />" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="PUBLIC/style/listas.css" />" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css?family=Roboto+Mono" rel="stylesheet">
         <link href="<c:url value="PUBLIC/style/letrasResponsive.css" />" rel="stylesheet" type="text/css"/>
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
            #leyenda span{
                transition: all 0.5s ease-in;
            }
            #leyenda span.fa{
                display: inline-block;
                font-size: 2em;
            }
            #leyenda a{
                display: inline-block;
                padding: 10px 10px;
                width: 70%;
                font-weight: bold;
                transition: all 0.1s ease;
                border:1px solid #0074D9;
                color: #0074D9;
                border-radius: 5px;

            }
            #leyenda a:hover{
                text-decoration: none;
                border-radius: 0px 5px 5px 0px;
            }
            #leyenda a span:last-child{
                float: right;
            }
            #leyenda a.proceso{
                color: #28a745;              
            }
            #leyenda a.proceso:hover{
                background-color: #28a745;
                color: #FFF;
                border:1px solid #28a745;
            }
            #leyenda a.finalizado{
                color: #dc3545;  
                margin-left: 20px;
            }
            #leyenda a.finalizado:hover{
                background-color: #dc3545;
                color: #FFF;
                border:1px solid #dc3545;
            }
            #leyenda a.espera{ 
                color: #ffc107;  
                margin-left: 40px;
            }
            #leyenda a.espera:hover{
                background-color: #ffc107;
                color: #FFF;
                border:1px solid #ffc107;
            }
            #leyenda a.cancelado{
                color: #17a2b8;  
                margin-left: 60px;
                margin-bottom: 15px;

            }
            #leyenda a.cancelado:hover{
                background-color: #17a2b8;
                color: #FFF;
                border:1px solid #17a2b8;
            }
            #pop-menu{
                position: fixed;
                width: 250px;
                padding: 20px 0px 10px 0px;
                background-color: #FFF;
                box-shadow: 1px 1px 2px black;
            }
            #pop-menu.oculto{
                display: none;
            }
            #btn-ficha-servicio-tecnico, #btn-volver-a-proceso{
                display: block;
                width: 100%;
                font-size: 14px;
                border: none;
                background:none;
                padding: 5px;
                margin-bottom: 5px;
            }
            #btn-ficha-servicio-tecnico #img-pdf, #btn-volver-a-proceso #volver-a-proceso-img{
                display: inline-block;
                margin-right: 10px;
            }

            #btn-ficha-servicio-tecnico:hover, #btn-volver-a-proceso:hover{
                background-color: #28a745;
                color: #FFF;
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
                padding: 0px 10px;
            }

            .pop-atencion{
                position: absolute;
                background-color: #ebbe03;
                width: 350px;
                border-radius: 5px;
                display: none;
                padding: 5px;
                border: none;
                z-index: 1;
                color: black;
            }
            .pop-atencion:before{
                content: "";
                position: absolute;
                border-bottom: 10px solid transparent;
                border-left: 10px solid transparent;
                border-right: 10px solid transparent;
                border-top: 10px solid #ebbe03;
                bottom: -20px;
                left: 5%;
            }
            .pop-atencion.top:before,.pop-atencion.top:before,.pop-atencion.top:before{
                bottom: 99%;
                  border-bottom: 10px solid #ebbe03;
                border-left: 10px solid transparent;
                border-right: 10px solid transparent;
                border-top: 10px solid transparent;
            }

            .pop-atencion-titulo{
                font-family: cursive;
            }
            @media screen and (max-width:1366px){

                section > div.container-fluid > div.row > div:last-child h5 span{
                    display: none;
                }

            }
        </style>
    </head>
    <body>
        <div class="container-fluid" id="barra-Institucional">
            <table>
                <tr align="center"><th colspan="2"><jsp:expression>session.getAttribute("usuario")</jsp:expression></th></tr>
                    <tr align="center"><td><a href="changePassword.htm"><u>Cambiar contraseña</u></a></td><td><a href="salir.htm"><u>Cerrar Sesión</u></a></td></tr>
                </table>
            </div>
            <nav id="menu">
                <div class="container-fluid">
                    <div class="row text-left">
                        <div class="col col-lg-12">
                            <div aria-label="breadcrumb" >
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item"><a href="index.htm" title="Volver al Inicio">Inicio</a></li>
                                    <li class="breadcrumb-item active" aria-current="Personal" title="Estas en Registros de Incidencias"> Incidencias</li>
                                </ol>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col colg-lg-12">
                            <div class="dropdown">
                                <button class="btn btn-outline-light btn-block dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <span class="fa fa-universal-access"></span> Acciones
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                                    <button class="dropdown-item" type="button" id="btn-new-registro"><span class="fa fa-file"></span> Nuevo</button>
                                    <button class="dropdown-item" type="button" id="btn-edit-registro"><span class="fa fa-edit"></span> Editar</button>
                                    <button class="dropdown-item" type="button" id="btn-cancelar-registro"><span class="fa fa-remove"></span> Cancelar</button>
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
                            <div id="content-filtro" style="box-shadow: 0px 2px 3px black;">
                                <div id="content-filtro-head">
                                    <span class="fa fa-filter"></span> Filtros <span class="fa fa-caret-up"></span>
                                </div>
                                <div id="content-filtro-body">
                                    <form name="filtro-incidencia-form" id="filtro-incidencia-form" action="filtroIncidencias.htm" method="post">
                                        <table>
                                            <tr>
                                                <td>
                                                    <label style="color: #bbbaba;margin-top: 10px;"><span class="fa fa-calendar"></span> Fecha:</label>
                                                    <input type="date" class="form-control" placeholder="aaaa-mm-dd" title="Fecha" name="fecha" required="" value="<jsp:expression>new java.text.SimpleDateFormat("YYYY-MM-dd").format(new java.util.GregorianCalendar().getTime())</jsp:expression>" />
                                                    <br>
                                                    <input type="radio" name="estado" value="3" id="radioFiltroAllFinalizados">
                                                    <label for="radioFiltroAllFinalizados">Todos los Finalizados </label> 
                                                    <input type="radio" name="estado" value="2" id="radioFiltroAllRegistrados">
                                                    <label for="radioFiltroAllRegistrados"> Todos en espera</label><br> 
                                                    <input type="radio" name="estado" value="1" id="radioFiltroAllProceso">
                                                    <label for="radioFiltroAllProceso"> Todos en Proceso</label><br> 
                                                    <input type="radio" name="estado" value="0"  id="radioFiltroAll" checked="checked">
                                                    <label for="radioFiltroAll" > Todos</label><br> 
                                                    <div class="form-group">
                                                        <button type="submit" class="btn btn-light"><span class="fa fa-search"></span> Buscar</button>
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col col-lg-12">
                            <br>
                            <b><font color="#fff"> Leyenda :</font></b>
                            <div class="list-group" id="leyenda">
                                <a href="#" class="proceso"><span class="fa fa-refresh"></span> <span>Proceso</span> <span class="badge badge-primary badge-pill"><c:out value="${listaCantidadIncidenciaxEstado.PROCESO}" default="0" /></span></a>
                            <a href="#" class="espera"><span class="fa fa-warning"></span> <span>En espera</span> <span class="badge badge-primary badge-pill"><c:out value="${listaCantidadIncidenciaxEstado.ESPERA}" default="0" /></span></a>
                            <a href="#" class="finalizado"><span class="fa fa-check"></span> <span>Finalizado</span> <span class="badge badge-primary badge-pill"><c:out value="${listaCantidadIncidenciaxEstado.FINALIZADO}" default="0" /></span></a>
                            <a href="#" class="cancelado"><span class="fa fa-remove"></span> <span>Cancelado</span> <span class="badge badge-primary badge-pill"><c:out value="${listaCantidadIncidenciaxEstado.CANCELADO}" default="0" /></span></a>
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
                        <h1 class="col col-lg-11"><a href="#" title="Volver al Inicio"><span>Incidencias</span> <span>O.S.I</span></a> <span id="fecha"><jsp:expression> new SimpleDateFormat("dd-MM-YYYY").format(new GregorianCalendar().getTime())</jsp:expression></span></h1>

                        </div>
                    </div>
                </header>
                <section>   
                    <div class="container-fluid">
                        <div class="row text-center lista-cabezera">
                            <div class="col col-lg-1"><h5>Codigo</h5></div>
                            <div class="col col-lg-2"><h5><span class="fa fa-calendar"></span> Fecha y hora</h5></div>
                            <div class="col col-lg-2"><h5><span class="fa fa-user"></span> Registrado Por</h5></div>
                            <div class="col col-lg-2"><h5><span class="fa fa-file-text"></span> Incidencia</h5></div>
                            <div class="col col-lg-2"><h5><span class="fa fa-building"></span> Oficina</h5></div>
                            <div class="col col-lg-2"><h5><span class="fa fa-users"></span> Unidad</h5></div>
                            <div class="col col-lg-1"><h5><span class="fa fa-info"></span> Estado</h5></div>
                        </div>
                    </div>
                    <div class="container-fluid lista-cuerpo text-center">
                    <c:forEach items="${listaPrincipalIncidencias}" var="I">
                        <div class="row">
                            <div class="col col-lg-1"><p><c:out value="${I[0]}" /></p></div>
                            <div class="col col-lg-2"><p><c:out value="${I[1]}" /></p></div>
                            <div class="col col-lg-2"><p><c:out value="${I[2]}" /></p></div>
                            <div class="col col-lg-2"><p><c:out value="${I[3]}" /></p></div>
                            <div class="col col-lg-2"><p><c:out value="${I[4]}" /></p></div>
                            <div class="col col-lg-2"><p><c:out value="${I[5]}" /></p></div>
                                    <c:choose>
                                        <c:when test="${I[6] eq 1 }">
                                    <div class="col col-lg-1">
                                        <p>
                                            <button type="button" class="btn btn-success" title="Proceso. Pulsar Para Finalizar" ><span class="fa fa-refresh"></span></button>
                                        </p>
                                    </div>
                                </c:when>
                                <c:when test="${I[6] eq 2 }">
                                    <div class="col col-lg-1">
                                        <p>
                                            <button type="button" class="btn btn-warning" title="En Espera. Pulsar para poner en proceso" ><span class="fa fa-warning"></span></button>
                                        </p>
                                    </div>
                                </c:when>
                                <c:when test="${I[6] eq 3 }">
                                    <div class="col col-lg-1">
                                        <p>
                                            <button type="button" class="btn btn-danger"><span title="Finalizado" class="fa fa-check"></span></button>
                                        </p>
                                    </div>
                                </c:when>
                                <c:when test="${I[6] eq 4 }">
                                    <div class="col col-lg-1">
                                        <p>
                                            <button type="button" class="btn btn-info"><span title="Cancelado" class="fa fa-times-circle-o" style="font-size: 1.3em;"></span></button>
                                        </p>
                                    </div>
                                </c:when>
                            </c:choose>
                            <div class="pop-atencion">
                                <h6 class="pop-atencion-titulo">Personal Enviado:</h6>
                                <ul class="pop-atencion-lista"></ul>
                            </div>
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
        <f:form id="formulario-editar-registro" class="formulario-flotante" name="editarIncidencia" method="post" commandName="incidenciaModelo" >
            <div class="row formulario-flotante-cabezera">
                <div class="col col-lg-11">
                    <h6>Incidencia Detalle</h6>
                </div>
                <div class="col col-lg-1">
                    <h6><button type="button" class="btn btn-danger"><b><span class="fa fa-remove"></span></b></button></h6>
                </div>
            </div>
            <div class="formulario-flotante-cuerpo">
                <div class="row ">
                    <div class="col col-lg-2 offset-1">
                        <div class="form-group">
                            <br>
                            <b>Id:</b><br><input type="text" name="id_inci" class="form-control" disabled=""/>
                        </div>
                    </div>
                    <div class="col col-lg-3">
                        <div class="form-group">
                            <br>
                            <b>Fecha de Registro:</b><br><input type="date" name="fecha" class="form-control" disabled="" />
                        </div>
                    </div>
                    <div class="col col-lg-3">
                        <div class="form-group">
                            <br>
                            <b>Hora de Registro:</b><br><input type="time" name="hora" class="form-control"  disabled=""/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col offset-1 col-lg-10">
                        <div class="form-group">
                            <b>Registrado Por:</b><br><input type="text" name="empleado" class="form-control" disabled="" value="<jsp:expression>session.getAttribute("usuario")</jsp:expression>"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col offset-1 col-lg-5">
                            <div class="form-group">
                                <b>Incidencia:</b><br>
                            <f:select class="form-control" path="id_inci_detalle">
                                <f:options items="${incidenciaDetalleLista}" />
                            </f:select>
                            </select>
                        </div>
                    </div>
                    <div class="col col-lg-5">
                        <div class="form-group">
                            <b>Incidencia Detalle:</b><br>
                            <textarea class="form-control" name="inci_detalle"></textarea>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col offset-1 col-lg-5">
                        <div class="form-group">
                            <b>Oficina:</b><br>
                            <f:select  class="form-control" path="id_oficina" required="" >
                                <f:option value="0">-- Seleccione Oficina --</f:option>
                                <f:options items="${oficinaLista}" />
                            </f:select>
                        </div>
                    </div>
                    <div class="col col-lg-5">
                        <div class="form-group">
                            <b>Unidad:</b><br>
                            <select name="id_unidad" class="form-control" required="">  
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col offset-1 col-lg-5">
                        <div class="form-group">
                            <b>Usuario afectado:</b><br><input type="text" name="encargado" class="form-control" />
                        </div>
                    </div>
                    <div class="col col-lg-5">
                        <div class="form-group">
                            <b>Medio:</b><br>
                            <f:select  class="form-control" required="" path="id_inci_medio">
                                <f:options items="${medioLista}" />
                            </f:select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col offset-1 col-lg-10">
                        <div class="form-group">
                            <b>Comentario:</b><br><textarea placeholder="Comentario Opcional" maxlength="200" rows="5" name="comentario" class="form-control" ></textarea>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col offset-1 col-lg-10">
                        <div id="accordion">
                            <div class="card">
                                <div class="card-header" id="headingOne">
                                    <h5 class="mb-0">
                                        <button class="btn btn-link" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                            Personal Enviado
                                        </button>
                                    </h5>
                                </div>

                                <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion">
                                    <div class="card-body">
                                        <ul class="list-group"></ul>
                                    </div>
                                </div>
                            </div>
                            <div class="card">
                                <div class="card-header" id="headingTwo">
                                    <h5 class="mb-0">
                                        <button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                            Personal Disponible de la Semana
                                        </button>
                                    </h5>
                                </div>
                                <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion">
                                    <div class="card-body">
                                        <ul class="list-group">

                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row formulario-flotante-footer">
                <div class="col offset-1 col-lg-10 text-right">
                    <button type="submit" class="btn btn-light"><span class="fa fa-save"></span> <b>GUARDAR</b></button>
                </div>
            </div>
        </f:form>
        <f:form class="formulario-flotante" id="formulario-nuevo-registro" name="nuevaIncidencia" method="post" commandName="incidenciaModelo" >
            <div class="row  formulario-flotante-cabezera">
                <div class="col col-lg-11">
                    <h6>Nueva Incidencia</h6>
                </div>
                <div class="col col-lg-1">
                    <h6><button type="button" class="btn btn-danger"><b><span class="fa fa-remove"></span></b></button></h6>
                </div>
            </div>
            <div class="formulario-flotante-cuerpo">
                <div class="row ">
                    <div class="col offset-1 col-lg-3">
                        <div class="form-group">
                            <br>
                            <b>Fecha:</b><br><input type="date" name="fecha" class="form-control" disabled="" />
                        </div>
                    </div>
                    <div class="col col-lg-3">
                        <div class="form-group">
                            <br>
                            <b>Hora:</b><br><input type="time" name="hora" class="form-control"  disabled=""/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col offset-1 col-lg-10">
                        <div class="form-group">
                            <b>Registrado Por:</b><br><input type="text" name="empleado" class="form-control" disabled="" value="<jsp:expression>session.getAttribute("usuario")</jsp:expression>"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col offset-1 col-lg-5">
                            <div class="form-group">
                                <b>Incidencia:</b><br>
                            <f:select class="form-control" path="id_inci_detalle">
                                <f:options items="${incidenciaDetalleLista}" />
                            </f:select>
                        </div>
                    </div>
                    <div class="col col-lg-5">
                        <div class="form-group">
                            <b>Incidencia Detalle:</b><br>
                            <textarea class="form-control" name="inci_detalle" maxlength="200" rows="4"></textarea>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col offset-1 col-lg-1">
                        <b>Id:</b><br>
                        <input type="text" name="id_oficina" required="" title="Seleccione una Oficina Valida!" disabled="" class="form-control" />
                    </div>
                    <div class="col col-lg-4">
                        <div class="form-group">
                            <b>Oficina:</b><br>
                            <input type="text" required="" name="nomb_oficina" list="autoCompeltarOficina" class="form-control">
                            <datalist id="autoCompeltarOficina">
                                <c:forEach items="${oficinaLista}" var="ListOfi" >
                                    <option value="${ListOfi.value}" label="${ListOfi.value}"></option>
                                </c:forEach>
                            </datalist>
                        </div>
                    </div>
                    <div class="col col-lg-5">
                        <div class="form-group">
                            <b>Unidad:</b><br>
                            <select name="id_unidad" class="form-control" required="">
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col offset-1 col-lg-5">
                        <div class="form-group">
                            <b>Usuario Afectado:</b><br><input type="text" name="encargado" class="form-control" />
                        </div>
                    </div>
                    <div class="col col-lg-5">
                        <div class="form-group">
                            <b>Medio:</b><br>
                            <f:select  class="form-control" required="" path="id_inci_medio">
                                <f:options items="${medioLista}" />
                            </f:select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col offset-1 col-lg-10">
                        <div class="form-group">
                            <b>Comentario:</b><br><textarea placeholder="Comentario Opcional" maxlength="200" rows="5" name="comentario" class="form-control" ></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row formulario-flotante-footer">
                <div class="col offset-1 col-lg-10 text-right">
                    <button type="submit" class="btn btn-light"><span class="fa fa-save"></span> <b>REGISTRAR</b></button>
                </div>
            </div>
        </f:form>
        <form  class="formulario-flotante" id="form-incidec-proceso" name="form-incidec-proceso">
            <div class="row  formulario-flotante-cabezera">
                <div class="col col-lg-11">
                    <h6>Atencion</h6>
                </div>
                <div class="col col-lg-1">
                    <h6><button type="button" class="btn btn-danger"><b><span class="fa fa-remove"></span></b></button></h6>
                </div>
            </div>
            <div class="formulario-flotante-cuerpo">
                <div class="row">
                    <div class="col col-lg-3 offset-1">
                        <div class="form-group">
                            <label>Codigo Incidencia</label>
                            <input type="text" class="form-control" name="id_incidencia" disabled=""/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-6 offset-1">
                        <h3><span class="badge badge-secondary">Personal</span> Disponible Hoy</h3>
                        <br>
                    </div>
                    <div class="col col-lg-3 offset-1 text-right">
                        <button type="button" class="btn btn-primary">
                            Total <span class="badge badge-light"><c:out value="${listapPracticanteAndContratados.size()}" default="0" /></span>
                        </button>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-10 offset-1">
                        <ul class="list-group">
                            <li class="list-group-item list-group-item-success"><input class="radioAllPracticantes" type="radio" name="all" id="todos-prac-trab" value="Todos"><label class="radioAllPracticantes" for="todos-prac-trab">Marcar Todos</label></li>
                                <c:forEach items="${listapPracticanteAndContratados}" var="personal" >
                                <li class="list-group-item ">
                                    <input type="checkbox" name="id_pers" value="${personal.key}" id="personal${personal.key}">
                                    <label for="personal${personal.key}"><c:out value="${personal.value}"/></label>
                                </li>
                            </c:forEach>
                            <br>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row formulario-flotante-footer">
                <div class="col offset-9 col-lg-1">
                    <button type="submit" class="btn btn-light"><span class="fa fa-refresh"></span> <b>Asignar Atencion</b></button>
                </div>
            </div>
        </form>
        <c:choose>
            <c:when test="${not empty tareasProgramadas}">
                <div id="content-programacion">
                    <div class="container">
                        <div class="row">
                            <div class="col col-lg-12">
                                <div class="alert alert-warning" role="alert">
                                    <strong>¡Santo guacamole!</strong> Hoy tenemos <a href="programacion.htm" class="alert-link">Tareas Programadas</a>.
                                    <button type="button" class="close"><span class="fa fa-remove"></span></button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="cortina" style="display: block;"></div>
            </c:when>
            <c:when test="${empty tareasProgramadas}">
                <div id="cortina"></div>
            </c:when>
        </c:choose>
        <div id="pop-menu" class="oculto">
            <button type="button" id="btn-ficha-servicio-tecnico" class="accion"><span class="fa fa-file-pdf-o accion" id="img-pdf"></span> Generar Ficha de Servicio T.</button>
            <button type="button" id="btn-volver-a-proceso" class="accion" title="Solo funcionara si la incidencia a finalizado!"><span class="fa fa-refresh" id="volver-a-proceso-img"></span> Poner en proceso</button>
        </div>  
        <f:form  class="formulario-flotante" id="form-pdf" name="form-FST" action="FichaServicioTecnico.htm" method="post" commandName="fichaServicioTecnico" target="_blank">   
            <div class="row  formulario-flotante-cabezera" >
                <div class="col col-lg-11">
                    <h6>Ficha de Servicio Tecnico</h6>
                </div>
                <div class="col col-lg-1">
                    <h6><button type="button" class="btn btn-danger"><b><span class="fa fa-remove"></span></b></button></h6>
                </div>
            </div>
            <div class="formulario-flotante-cuerpo">
                <div class="row">
                    <div class="col col-lg-2 offset-1">
                        <div class="form-group">
                            <label><b>Incidencia:</b></label>
                            <input type="text" name="id_incidencia" disabled="" class="form-control" />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-4 offset-1" >
                        <div class="form-group">                            
                            <label><b>Equipo:</b></label>
                            <f:select path="id_periferico" class="form-control" required="" >
                                <f:options items="${listaPerifericos}" />
                            </f:select>
                        </div>
                    </div>
                    <div class="col col-lg-2" >
                        <div class="form-group">
                            <label><b>Codigo equipo:</b></label>
                            <input type="text" class="form-control" name="cod_equipo" placeholder="12345678-1234" required="" />
                        </div>
                    </div>
                    <div class="col col-lg-4" >
                        <div class="form-group">
                            <label><b>Traslado al taller para revisión ?</b></label><br>
                            <input type="radio" name="traslado" class="form-control" id="formFSTtraslado-SI" value="SI" />
                            <label for="formFSTtraslado-SI"><b>SI</b></label>
                            <input type="radio" name="traslado" class="form-control" id="formFSTtraslado-NO" value="NO" checked="" />
                            <label for="formFSTtraslado-NO"><b>NO</b></label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-10 offset-1">
                        <div class="form-group">
                            <label><b>Equipo Detalle:</b></label>
                            <input type="text" class="form-control" name="periferico_detalle" placeholder="Campo opcional" disabled="" />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-10 offset-1" >
                        <div class="form-group">
                            <label><b>Servicio Correctivo:</b></label>
                            <textarea maxlength="100" rows="2" class="form-control" name="S_correctivo" required=""></textarea>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-10 offset-1" >
                        <div class="form-group">
                            <label><b>Recomendacion:</b></label>
                            <textarea maxlength="200" rows="5" name="Recomendacion" class="form-control" required=""></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row formulario-flotante-footer">
                <div class="col offset-10 col-lg-1">
                    <button type="submit" class="btn btn-light"><span class="fa fa-file-pdf-o"></span> <b>Generar</b></button>
                </div>
            </div>
        </f:form> 

        <script src="<c:url value="PUBLIC/jquery 3.1.1/jquery-3.1.1.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/jquery 3.1.1/jquery.idle.js" />" type="text/javascript"></script>      
        <script src="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/js/Popper.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/js/bootstrap.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/HeaderAndFooter.js"/>" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/selectFila.js" />" type="text/javascript"></script>
        <script type="text/javascript" src="<c:url value="PUBLIC/script/incidencia.js" />"></script>
        <script type="text/javascript" src="<c:url value="PUBLIC/script/inactividad.js" />"></script>
        <script type="text/javascript" src="<c:url value="PUBLIC/script/fullScreen.js" />"></script>
        <script src="<c:url value="PUBLIC/script/protegertextos.js" />" type="text/javascript"></script>
    </body>
</html>
