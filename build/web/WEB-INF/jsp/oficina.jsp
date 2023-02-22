<%-- 
    Document   : oficina
    Created on : 23-mar-2018, 9:45:54
    Author     : P3017OSI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib  prefix="f" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="author" content="Coronado arquinigo, Ivan Basilio">
        <meta name="generator" content="NetBeans IDE 8.2">
        <link rel="stylesheet" href="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/css/bootstrap.css" />">
        <link rel="stylesheet" href="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/css/dataTables.bootstrap4.min.css" />">
        <link rel="stylesheet" href="<c:url value="PUBLIC/font-awesome-4.7.0/css/font-awesome.min.css" />">
        <link href="<c:url value="PUBLIC/style/HeaderAndFooter.css" />" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="PUBLIC/style/scroll.css" />" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="PUBLIC/style/listas.css" />" rel="stylesheet" type="text/css"/>
         <link href="<c:url value="PUBLIC/style/letrasResponsive.css" />" rel="stylesheet" type="text/css"/>
        <title>Oficinas-OSI</title>
        <style>

            #panel .formulario-flotante-cuerpo > .row:first-child > div{
                border: 1px solid #ccc;
                margin: 40px auto 30px auto;
                width: 90%;
                border-radius:4px;
            }
            #panel .formulario-flotante-cuerpo > .row:first-child > div::before{
                content: "Insert Simplificado";
                position: absolute;
                top: -25px;
                left: 40%;
                padding: 10px;
                border-radius: 5px;
                background-color: #e3e3e6;
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
            #btn-adminstracion_unidad{
                display: block;
                width: 100%;
                font-size: 14px;
                border: none;
                background:none;
                padding: 5px;
                margin-bottom: 5px;
            }
            #btn-adminstracion_unidad #img-pdf{
                display: inline-block;
                margin-right: 10px;
            }

            #btn-adminstracion_unidad:hover{
                background-color: #28a745;
                color: #FFF;
            }
            button.btn-warning{
                margin-right: 5px;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid" id="barra-Institucional">
            <table>
                <tr align="center"><th colspan="2"><jsp:expression>session.getAttribute("usuario")</jsp:expression></th></tr>
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
                                    <li class="breadcrumb-item active" aria-current="Personal" title="Estas en Mantenimiento del Personal">Oficinas y Unidad</li>
                                </ol>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col col-lg-12">
                            <div class="dropdown">
                                <button class="btn btn-outline-light btn-block dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <span class="fa fa-universal-access"></span> Acciones Oficina
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                                    <button class="dropdown-item" type="button" id="btn-new-oficina" ><span class="fa fa-building-o"></span> Nuevo</button>
                                    <button class="dropdown-item" type="button" id="btn-edit-oficina" ><span class="fa fa-edit"></span> Editar</button>
                                    <button class="dropdown-item" type="button" id="btn-delete-oficina"><span class="fa fa-remove"></span> Desactivar</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row" style="margin-top:15px;">
                        <div class="col col-lg-12">
                            <button id="btn-show-unidad" type="button" class="btn btn-info btn-block"><span class="fa fa-users"></span> Unidad</button>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col col-lg-12">
                            <div class="dropdown">
                                <br>
                                <button class="btn btn-danger btn-lg dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
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
                                    <b><font color="#fff"><span class="fa fa-building-o"></span> Oficina :</font></b>
                                    <form name="filtro-oficina-form" method="post" action="#" >                                            
                                        <input type="text" class="form-control" name="oficina" placeholder="Oficina" title="Oficina" />
                                        <br>
                                        <div class="form-group">
                                            <button type="submit" class="btn btn-outline-light" ><span class="fa fa-search"></span> Buscar</button>
                                        </div>                                             
                                    </form>
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
                            <h1 class="col col-lg-11 "><a href="#" title="Volver al Inicio"><span>Oficinas y Unidades</span> <span>O.S.I</span></a></h1>    
                        </div>
                    </div>
                </header>
                <section>
                    <div class="container-fluid">
                        <div class="row text-center lista-cabezera">
                            <div class="col col-lg-1"><h5><span class="fa fa-id-card"></span> Id</h5></div>
                            <div class="col col-lg-4"><h5><span class="fa fa-building"></span> Oficina</h5></div>
                            <div class="col col-lg-4"><h5><span class="fa fa-user"></span> Encargado</h5></div>
                            <div class="col col-lg-3"><h5><span class=""></span> Abreviatura</h5></div>
                        </div>
                    </div>
                    <div class="container-fluid lista-cuerpo text-center">
                    <c:forEach items="${listaPrincipalOficina}" var="L">
                        <div class="row">
                            <div class="col-lg-1"><p><c:out value="${L.getId_oficina()}" /></p></div>
                            <div class="col-lg-4"><p><c:out value="${L.getOficina()}" /></p></div>
                            <div class="col-lg-4"><p><c:out value="${L.getEncargado()}" default="Sin Definir" /></p></div>
                            <div class="col-lg-3"><p><c:out value="${L.getAbreviatura()}" /></p></div>
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
        <f:form class="formulario-flotante" id="formulario-nuevo-oficina" name="formulario-nuevo-oficina" action="oficina.htm" method="post" commandName="oficina" >
            <div class="row  formulario-flotante-cabezera">
                <div class="col col-lg-11">
                    <h6>Mantenimiento Oficina</h6> 
                </div>
                <div class="col col-lg-1">
                    <h6><button type="button" class="btn btn-danger"><b><span class="fa fa-remove"></span></b></button></h6>
                </div>
            </div>
            <div class="formulario-flotante-cuerpo">
                <div class="row">
                    <div class="col col-lg-3 offset-1">
                        <div class="form-group">
                            <b>Codigo</b><br>
                            <input type="text" name="id_oficina" class="form-control" disabled=""/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-10 offset-1">
                        <div class="form-group">
                            <b>Oficina</b><br>
                            <input type="text" name="oficina" class="form-control" placeholder="ejemplo: Recursos humanos" required=""/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-5 offset-1">
                        <div class="form-group">
                            <b>Encargado</b><br>
                            <input type="text" name="encargado" class="form-control" placeholder="Campo opcional"/>
                        </div>
                    </div>
                    <div class="col col-lg-5">
                        <div class="form-group">
                            <b>Abreviatura</b><br>
                            <input type="text" name="abreviatura" class="form-control" placeholder="Campo opcional"/> 
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-10 offset-1">
                        <div class="form-group">
                            <b>Detalles</b><br>
                            <textarea class="form-control" placeholder="Campo opcional" name="detalles" maxlength="200" rows="5"></textarea> 
                        </div>
                    </div>
                </div>
            </div>
            <div class="row formulario-flotante-footer">
                <div class="col offset-10 col-lg-1 text-right">
                    <button type="submit" class="btn btn-light" style="transform: translateX(-20px); "><span class="fa fa-save"></span> <b>GUARDAR</b></button>
                </div>
            </div>
        </f:form>
        <div id="panel" class="formulario-flotante">
            <div class="row  formulario-flotante-cabezera">
                <div class="col col-lg-11">
                    <h6>Mantenimiento Unidad</h6> 
                </div>
                <div class="col col-lg-1">
                    <h6><button type="button" class="btn btn-danger"><b><span class="fa fa-remove"></span></b></button></h6>
                </div>
            </div>
            <div class="formulario-flotante-cuerpo">
                <div class="row">
                    <div class="position-relative">
                        <div class="col col-lg-12">
                            <f:form style="display: block; margin-top: 20px;" action="mantenimientoUnidad.htm" commandName="unidad" id="formulariounidadSimple" name="formulariounidadSimple">
                                <div class="form-row">
                                    <div class="form-group col-md-5">
                                        <label><b>Oficina:</b></label>
                                        <input type="hidden" name="id_oficina" required="" />
                                        <input type="text" name="oficina" disabled="" class="form-control" />
                                    </div>
                                    <div class="form-group col-md-1">
                                        <label><b>id_undad</b></label>
                                        <input type="text" class="form-control" disabled="" placeholder="" name="id_unidad">
                                    </div>
                                    <div class="form-group col-md-5">
                                        <label><b>Nombre de la Nueva Unidad:</b></label>
                                        <input type="text" class="form-control" name="unidad">
                                    </div>
                                    <div class="form-group col-md-1">
                                        <label style="opacity: 0;">boton</label>
                                        <button type="submit" class="btn btn-success" title="Guardar"><span class="fa fa-check"></span></button>
                                    </div>
                                </div>
                            </f:form>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-10 offset-1">
                        <table id="tabla-lista-unidad" class="table table-striped table-bordered" style="width:100%">
                            <thead>
                                <tr>
                                    <th scope="col">Id-Unidad</th>
                                    <th scope="col">Unidad</th>
                                    <th scope="col">Oficina</th>
                                    <th scope="col">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="row formulario-flotante-footer"></div>
        </div>
        <div id="pop-menu" class="oculto">
            <button type="button" id="btn-adminstracion_unidad" class="accion"><span class="fa fa-file-pdf-o accion" id="img-pdf"></span> Insertar Unidad</button>
        </div>  
        <script src="<c:url value="PUBLIC/jquery 3.1.1/jquery-3.1.1.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/jquery 3.1.1/jquery.dataTables.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/js/Popper.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/js/bootstrap.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/js/dataTables.bootstrap4.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/HeaderAndFooter.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/selectFila.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/oficina.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/fullScreen.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/protegertextos.js" />" type="text/javascript"></script>
        <script>


            //LAS SIGUIENTES FUNCIONES AYUDAN A CAMBIAR LAS CLASES DE ALGUNOS DIV PARA QUE SE AJUSTEN MEJOR CUANDO LA PANTALLA ES MENOR A 1366PX
            function cambiarClaseDivListaDeUnidades() {
                if (window.screen.width <= 1366) {
                    document.querySelector("#panel .formulario-flotante-cuerpo >  div.row:nth-child(2) >div").className = "col col-lg-12";
                }
            }
            cambiarClaseDivListaDeUnidades();
        </script>
    </body>
</html>
