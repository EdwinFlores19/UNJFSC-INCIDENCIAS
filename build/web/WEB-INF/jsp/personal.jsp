<%-- 
    Document   : personal
    Created on : 25-feb-2018, 15:03:27
    Author     : Ivan Basilio
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="MODELO.persona"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib  prefix="f" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="es">
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
        <title>Personal-OSI</title>
        <style type="text/css">
            #menu figure #btn-program-pract {
                cursor: pointer;
                transition: all 0.3s ease;
                opacity: 0.9
            }
            #menu figure #btn-program-pract:hover{
                transform: rotateY(180deg);
            }
            #contendor-datos-practicante{
                background-color: #1c3574;
                margin-bottom: 10px;
            }
            #contendor-datos-practicante >div:first-child{
                margin-top: 30px;
                padding: 15px 10px 15px 10px;
                cursor: pointer;
                background-color: #142244;
            }
            #contendor-datos-practicante >div:first-child:hover{
                background: #1c3574;
            }
            #contendor-datos-practicante > div:first-child span{
                color: #FFF;
            }
            #contendor-datos-practicante > div:first-child span:nth-child(3){
                float: right;
                transform: translateY(5px);
            }
            #contendor-datos-practicante-cuerpo{
                padding: 10px;
                background-color: #FFF;
                border-top: 2px solid #b41fd1;
                display: none;
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
            #btn-historia-asist-alu{
                display: block;
                width: 100%;
                font-size: 14px;
                border: none;
                background:none;
                padding: 5px;
                margin-bottom: 5px;
            }
            #btn-historia-asist-alu #img-pdf{
                display: inline-block;
                margin-right: 10px;
            }
            #btn-historia-asist-alu #pencil-pdf{
                margin-right: 10px;
            }
            #btn-historia-asist-alu:hover{
                background-color: #28a745;
                color: #FFF;
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
                                    <li class="breadcrumb-item active" aria-current="Personal" title="Estas en Mantenimiento del Personal">Personal</li>
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
                                    <button class="dropdown-item" type="button" id="btn-new-registro" ><span class="fa fa-user-plus"></span> Nuevo</button>
                                    <button class="dropdown-item" type="button" id="btn-edit-registro" ><span class="fa fa-edit"></span> Editar</button>
                                    <button class="dropdown-item" type="button" id="btn-delete-registro"><span class="fa fa-remove"></span> Desactivar</button>
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
                            <br><br><br>
                            <font color="#FFF"> <b><span class="fa fa-filter"></span> Filtrar :</b></font>
                            <form action="filtroPersonaPorcargo.htm" method="post" name="per_filto_cargo">
                                <table>
                                    <tr>
                                        <td>
                                            <input type="radio" name="cargo" id="radioFiltroAllContratados" value="2"> 
                                            <label for="radioFiltroAllContratados">Todos Contratados </label>
                                            <input type="radio" name="cargo" id="radioFiltroAllPracticantes" value="1">
                                            <label for="radioFiltroAllPracticantes"> Todos Practicantes</label><br>
                                            <input type="radio" name="cargo" id="radioFiltroAll" value="0">
                                            <label for="radioFiltroAll" > Todos</label><br>
                                            <button type="submit" class="btn btn-light"><span class="fa fa-search"></span> Aplicar</button>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col col-lg-12">
                            <div id="contendor-datos-practicante">
                                <div>
                                    <span class="fa fa-user-circle-o"></span><span> Opciones practicantes</span><span class="fa fa-caret-down"></span>
                                </div>
                                <div id="contendor-datos-practicante-cuerpo">
                                    <form name="form_programacion_practicantes" id="form_programacion_practicantes" method="post" action="updatePracticante.htm">
                                        <div class="form-group">
                                            <label class="col-form-label">id:</label>
                                            <input type="text" class="form-control" disabled="" required="" name="id_persona">
                                        </div>
                                        <div class="form-group">
                                            <label for="apelldios" class="col-form-label">Apellidos:</label>
                                            <input type="text" class="form-control"  required="" name="apellidos" disabled="" />
                                        </div>
                                        <div class="form-group">
                                            <label for="nombres" class="col-form-label">Nombres:</label>
                                            <input type="text" class="form-control"   required="" name="nombres" disabled="" />
                                        </div>
                                        <div class="row">
                                            <div class="col col-lg-6">
                                                <label  class="col-form-label">Horas por día:</label>
                                                <input type="text" pattern="[0-8]{1}" class="form-control"  required="" name="horas" />
                                            </div>
                                            <div class="col col-lg-6">
                                                <label  class="col-form-label">Total días laborables:</label>
                                                <input type="text" pattern="[0-9]{2,3}" class="form-control"  required="" name="dias"/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col col-lg-12">
                                                <br>
                                                <input type="submit" class="btn btn-primary btn-block" form="form_programacion_practicantes" value="Establecer" />
                                            </div>                                           
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
                            <h1 class="col col-lg-11 "><a href="#" title="Volver al Inicio"><span>Personal</span> <span>O.S.I</span></a></h1>    
                        </div>
                    </div>
                </header>
                <section>
                    <div class="container-fluid">
                        <div class="row text-center lista-cabezera">
                            <div class="col col-lg-1"><h5><span class="fa fa-id-badge"></span> Id</h5></div>
                            <div class="col col-lg-2"><h5><span class="fa fa-user-o"></span> Apellidos</h5></div>
                            <div class="col col-lg-3"><h5><span class="fa fa-user-o"></span> Nombres</h5></div>
                            <div class="col col-lg-2"><h5><span class="fa fa-id-card"></span> D.N.I</h5></div>
                            <div class="col col-lg-2"><h5><span class="fa fa-phone"></span> Telefono</h5></div>
                            <div class="col col-lg-2"><h5><span class="fa fa-gavel"></span> Cargo</h5></div>
                        </div>
                    </div>
                    <div class="container-fluid lista-cuerpo text-center">
                    <c:forEach items="${listaPersonaPrincipal}" var="p">
                        <div class="row">
                            <div class="col-lg-1"><p><c:out value="${p.getId_persona()}" /></p></div>
                            <div class="col-lg-2"><p><c:out value="${p.getApellidos()}" /></p></div>
                            <div class="col-lg-3"><p><c:out value="${p.getNombres()}" /></p></div>
                            <div class="col-lg-2"><p><c:out value="${p.getDni()}" /></p></div>
                            <div class="col-lg-2"><p><c:out value="${p.getTelefono()}" /></p></div>
                            <div class="col-lg-2"><p><c:out value="${p.getCargo()}" /></p></div>
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
        <f:form class="formulario-flotante" id="formulario-nuevo-registro" commandName="persona" name="persona" >
            <div class="row  formulario-flotante-cabezera">
                <div class="col col-lg-11">
                    <h6>Mantenimiento Personal</h6> 
                </div>
                <div class="col col-lg-1">
                    <h6><button type="button" class="btn btn-danger"><b><span class="fa fa-remove"></span></b></button></h6>
                </div>
            </div>
            <div class="formulario-flotante-cuerpo">
                <div class="row">
                    <div class="col offset-1 col-lg-3">
                        <div class="form-group">
                            <b>Codigo:</b><br><input type="text" name="id_persona" class="form-control"  disabled="" />
                        </div>
                    </div>
                    <div class="col col-lg-7">
                        <div class="form-group">
                            <b>Apellidos</b><br>
                            <input type="text" name="apellidos" class="form-control" required=""/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-5 offset-1">
                        <div class="form-group">
                            <b>Nombres</b><br>
                            <input type="text" name="nombres" class="form-control" required=""/>
                        </div>
                    </div>
                    <div class="col col-lg-5">
                        <div class="form-group">
                            <b>Sexo</b><br>
                            <input type="radio" name="sexo" value="M" id="radioSexM" /><label  for="radioSexM" title="Masculino">Masculino</label>
                            <input type="radio" name="sexo" value="F" id="radioSexF" /><label for="radioSexF" title="Femenino">Femenino</label>
                            <input type="radio" name="sexo" value="O"  checked="" id="radioSexO" /><label for="radioSexO" title="Otros">Otro</label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-5 offset-1">
                        <div class="form-group">
                            <b>D.N.I</b><br>
                            <input type="text" name="dni" class="form-control" maxlength="8" required=""/>
                        </div>
                    </div>
                    <div class="col col-lg-5">
                        <div class="form-group">
                            <b>Telefono:</b><br>
                            <input type="text" name="telefono" class="form-control" maxlength="11" placeholder="El telefono es opcional"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-5 offset-1">
                        <div class="form-group">
                            <b>Cargo</b><br>
                            <select name="cargo" class="form-control">
                                <option value="3" selected="ON">Administrativo</option>
                                <option value="1">Practicante</option>
                                <option value="2">Contratado</option>
                            </select>
                        </div>
                    </div>
                    <div class="col col-lg-5">
                        <div class="form-group">
                            <b>Direccion</b><br>
                            <textarea class="form-control" name="direccion" placeholder="La direccion es opcional"></textarea>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-10 offset-1">
                        <div class="form-group">
                            <b>Correo Electronico</b><br>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">@</div>
                                </div>
                                <input type="email" class="form-control" name="correo" placeholder="El Correo es opcional"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-6 offset-1 text-left caja-tenue">
                        <ul class="list-group" title="Si no marcas ningun dia se asumira todos los dias de la semana!">
                            <li class="list-group-item active">Selecciones los dias de trabajo</li>
                            <li class="list-group-item"><input type="checkbox" name="horario" class="" value="1" checked="" id="diaLunes" /><label for="diaLunes">Lunes</label></li>
                            <li class="list-group-item"><input type="checkbox" name="horario" class="" value="2" id="diaMartes" /><label for="diaMartes">Martes</label></li>
                            <li class="list-group-item"><input type="checkbox" name="horario" class="" value="3" id="diaMiercoles" /><label for="diaMiercoles">Miercoles</label></li>
                            <li class="list-group-item"><input type="checkbox" name="horario" class="" value="4" id="diaJueves" /><label for="diaJueves">Jueves</label></li>
                            <li class="list-group-item"><input type="checkbox" name="horario" class="" value="5" id="diaViernes" /><label for="diaViernes">Viernes</label></li>
                        </ul>
                    </div>
                    <div class="col col-lg-4 caja-tenue" id="diasLaborables" ></div>
                </div>
            </div>
            <div class="row formulario-flotante-footer">
                <div class="col offset-9 col-lg-1 text-right">
                    <button type="submit" class="btn btn-light"><span class="fa fa-save"></span> <b>GUARDAR</b></button>
                </div>
            </div>
        </f:form> 
        <div id="pop-menu" class="oculto">
            <button type="button" id="btn-historia-asist-alu" class="accion"><span class="fa fa-file-pdf-o accion" id="img-pdf"></span> Generar Ficha de Asistencias.</button>
        </div> 
        <form class="formulario-flotante" style="height: 300px" id="form-ficha-asistencia" name="form-ficha-asistencia" target="_blank" action="Ficha_Asistencia_Practicante.htm" method="post">
            <div class="row  formulario-flotante-cabezera">
                <div class="col col-lg-11">
                    <h6>Ficha de Asistencia Practicantes</h6> 
                </div>
                <div class="col col-lg-1">
                    <h6><button type="button" class="btn btn-danger"><b><span class="fa fa-remove"></span></b></button></h6>
                </div>
            </div>
            <div class="formulario-flotante-cuerpo">
                <div class="row" style="margin-top: 15px;">
                    <div class="col col-lg-1 offset-1">
                        <div class="form-group">
                            <label><b>Codigo:</b></label>
                            <input type="text" name="id_pers" disabled="" class="form-control" required="" />
                        </div>
                    </div>
                    <div class="col col-lg-4">
                        <div class="form-group">
                            <label><b>Practicante:</b></label>
                            <input type="text" name="nombres"  class="form-control" required="" disabled="" />
                        </div>
                    </div>
                    <div class="col col-lg-4">
                        <div class="form-group">
                            <label><b>Fecha Emisión Resolución:</b></label>
                            <input type="date" name="fecha" required="" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-10 offset-1">
                        <div class="form-group table-bordered" style="padding-left: 10px;">
                            <br> 
                            <label><b>RESOLUCIÓN DE DECANATO Nº</b></label>
                            <input type="text" pattern="[0-9]{3,4}" placeholder=" 000" name="nResolucion"  class="" required=""  style="display: inline-block;width: 60px"  />
                            <label><b>-</b></label>
                            <input type="text" pattern="[0-9]{4}" placeholder=" 20XX" name="year"  class="" required="" style="display: inline-block;width: 60px" title="Año"/>
                            <label><b>-FIISI-UNJFSC</b></label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row formulario-flotante-footer">
                <div class="col offset-1 col-lg-10 text-right">
                    <button type="submit" class="btn btn-light"><span class="fa fa-file-pdf-o"></span> <b>GENERAR FICHA</b></button>
                </div>
            </div>
        </form>
        <script src="<c:url value="PUBLIC/jquery 3.1.1/jquery-3.1.1.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/js/Popper.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/js/bootstrap.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/HeaderAndFooter.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/selectFila.js" />" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/persona.js"/>" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/fullScreen.js"/>" type="text/javascript"></script>
        <script src="<c:url value="PUBLIC/script/protegertextos.js" />" type="text/javascript"></script>
    </body>
</html>

