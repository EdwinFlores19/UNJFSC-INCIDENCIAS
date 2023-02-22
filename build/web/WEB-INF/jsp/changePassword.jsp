<%-- 
    Document   : changePassword
    Created on : 09-abr-2018, 12:46:23
    Author     : P3017OSI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib  prefix="f" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="author" content="Coronado arquinigo, Ivan Basilio">
        <meta name="generator" content="NetBeans IDE 8.2">
        <link rel="stylesheet" href="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/css/bootstrap.css" />">
        <link rel="stylesheet" href="<c:url value="PUBLIC/Bootstrap v4.0.0-beta/css/dataTables.bootstrap4.min.css" />">
        <link rel="stylesheet" href="<c:url value="PUBLIC/font-awesome-4.7.0/css/font-awesome.min.css" />">
        <title>Change password - OSI</title>
        <style type="text/css">
            body{
                background: linear-gradient(to left,#ccc,#fff,#ccc,#ccc,#fff,#ccc);
                overflow: hidden;
            }
            /*BARRA INSTITUCIONAL*/
            #barra-Institucional{
                position: fixed;
                background-image: url('<c:url value="PUBLIC/img/barra.png" />');
                z-index: 2;
                top: 0;
                left: 0;
                height: 55px;
                border-bottom: #1b517d 1px solid;

            }
            #barra-Institucional table{
                float: right;
            }
            #barra-Institucional table tr th{
                color: #c4b971;

            }
            #barra-Institucional table tr td a{
                color: #FFF;
                font-size: 10px;
            }
            #barra-Institucional table tr:last-child td:first-child a{
                padding-right: 10px;
                border-right: solid 1px #525252;
            }
            /*ESTILOS PARA EL CUERPO*/
            #cuerpo{
                margin: 0px;
                box-sizing: border-box;
                transition: all 0.5s ease-in-out;
                padding-top: 55px;
                height: 100%;
            }
            /*ESTILOS PARA LA CABEZERA*/
            #cabezera{
                border-bottom: 2px solid #1a517f;
            }
            #cabezera h1{
                color: black;
                font-size: 3em;
                margin: 0;
                padding-top: 5px;
            }
            #cabezera h1 a{
                color: #a50e0e;
                color: #e0dcdc;
                text-decoration: none;
                text-shadow:1px 1px 3px black;
            }
            #cabezera h1 a span:first-child{
                font-family: "trebuchet MS",calendar;
            }
            #cabezera h1 a span:last-child{
                font-family: 'trebuchet MS',calendar;
            }
            #cabezera h1 a:hover{
                text-decoration: none;
            }
            #cabezera span.fa-remove,#cabezera span.fa-bars{
                margin-top: 20px;
                font-size: 1.5em;
                cursor: pointer;
                transition: all 0.5s ease-in-out; 
            }
            /*ESTILOS PARA EL FOOTER*/
            footer{
                background-image: url(<c:url value="PUBLIC/img/fondo_footer.jpg" />) ;  
                height: 55px;
                transition: all 0.5s ease-in-out;
            }
            footer table{
                margin: auto;
            }
            footer table tr td{
                color: #fff;
                font-family: monospace;
                font-size: 10px;
            }

        </style>
    </head>
    <body>
        <div class="container-fluid" id="barra-Institucional">
            <table>
                <tr align="center"><th  colspan="2"><jsp:expression>session.getAttribute("usuario")</jsp:expression></th></tr>
                <tr align="center"><td align="right"><a href="index.htm"><u>Inicio</u></a></td><td align="center"><a href="salir.htm"><u>Cerrar Sesión</u></a></td></tr>
            </table>
        </div>
        <div id="cuerpo">
            <section>
                <div class="container">
                    <br>
                    <div class="row">
                        <div class="col col-lg-3">
                            <figure>
                                <img src="<c:url value="PUBLIC/img/ImagenAlternativa-Menu.jpg" />" />
                            </figure>
                        </div>
                        <div class="col col-lg-9">
                            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                <strong>Por su seguridad!</strong> Evite utilizar contraseñas de otras cuentas como Gmail ó Facebook.
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <form name="changePassworform" action="changePassword.htm" method="post"> 
                                <div class="form-group row">
                                    <label for="PresentePassword" class="col-sm-2 col-form-label">Contraseña Actual</label>
                                    <div class="col-sm-5">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <div class="input-group-text"><span class="fa fa-lock"></span></div>
                                            </div>
                                            <input type="password" class="form-control" id="oldPassword" name="currentPass" placeholder="Contraseña Actual" autofocus="" required="" />
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="NewPassword" class="col-sm-2 col-form-label">Nueva Contraseña</label>
                                    <div class="col-sm-5">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <div class="input-group-text"><span class="fa fa-lock"></span></div>
                                            </div>
                                            <input type="password" class="form-control" id="NewPassword" name="newPass" placeholder="Nueva Contraseña!" required="">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="RepitPassword" class="col-sm-2 col-form-label">Confirmar Contraseña</label>
                                    <div class="col-sm-5">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <div class="input-group-text"><span class="fa fa-lock"></span></div>
                                            </div>
                                            <input type="password" class="form-control" id="RepitPassword" name="newPass2"  placeholder="Repetir Nueva Contraseña!" required="" >
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-sm-10">
                                        <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                                    </div>
                                </div>
                            </form>
                        </div>
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
        </div>
        <script src="<c:url value="PUBLIC/jquery 3.1.1/jquery-3.1.1.js" />" type="text/javascript"></script>
        <script>
            document.changePassworform.addEventListener("submit", function (e) {
                e.preventDefault();
                if (this.newPass.value === this.newPass2.value) {
                    $.post("changePassword.htm", $(this).serialize(), function (response) {
                        var respuesta = JSON.parse(response);
                        if (typeof respuesta.SESIONFINALIZADA === "undefined") {
                            if (respuesta.ESTADO === true) {
                                alert("Se cambio la contraseña!!!");
                                window.location.assign("salir.htm");
                            } else {
                                alert("No se pudo cambiar la contraseña!!!");
                            }
                        } else {
                           window.location.assign("sesion.htm");
                        }
                    });
                } else {
                    alert("las nuevas contraseñas no coinciden!!!");
                }
            }, false);
        </script>
    </body>
</html>
