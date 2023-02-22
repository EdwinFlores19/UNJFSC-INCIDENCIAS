const Esc_id = 27;// CODIGO DE TECLADO DE LA TECLA Esc  
const N_id = 78;// CODIGO DE TECLADO DE LA TECLA N  nuevo
const M_id = 77;// CODIGO DE TECLADO DE LA TECLA M  modificar
const U_id = 85;// CODIGO DE TECLADO DE LA TECLA U  unidad

ventanasOSI = {
    nuevo: document.getElementById("formulario-nuevo-oficina"),
    cortina: document.getElementById("cortina"),
    unidad: document.getElementById("panel")
};

function openCloseForm() {
    document.getElementById("btn-new-oficina").addEventListener("click", function () {
        $("#cortina").fadeIn(500);
        $("#formulario-nuevo-oficina").fadeIn(500);
        document.forms["formulario-nuevo-oficina"].elements["oficina"].focus();
        sizeFormFlotanteCuerpo(1);//1 para darle altura al cuerpo del formulario nuevo registro
    });
    document.getElementById("btn-edit-oficina").addEventListener("click", function () {
        if (existeFilaSeleccionada()) {
            $("#cortina").fadeIn(500);
            $("#formulario-nuevo-oficina").fadeIn(500);
            detallesOficina();
            sizeFormFlotanteCuerpo(1);//1 para darle altura al cuerpo del formulario nuevo registro
            document.forms["formulario-nuevo-oficina"].elements["oficina"].focus();
        } else {
            alert("Seleccione una Fila!");
        }
    });
    document.getElementById("btn-show-unidad").addEventListener("click", mostrarAdministracionUnidad,false);
    document.getElementById("btn-adminstracion_unidad").addEventListener("click", mostrarAdministracionUnidad,false);
    
    function mostrarAdministracionUnidad(){
                if (existeFilaSeleccionada()) {
            $("#cortina").fadeIn(500);
            $("#panel").fadeIn(500);
            sizeFormFlotanteCuerpo(2);//1 para darle altura al cuerpo del formulario nuevo registro
            document.forms["formulariounidadSimple"].elements["unidad"].focus();
            var fila = document.querySelector(".row.select");
            document.forms["formulariounidadSimple"].elements["id_oficina"].value = fila.children[0].innerText.trim();
            document.forms["formulariounidadSimple"].elements["oficina"].value = fila.children[1].children[0].innerHTML;
            $.post("retornarUnidadesPorOficina.htm", {id_oficina: document.forms["formulariounidadSimple"].elements["id_oficina"].value}, function (response) {
                var html = JSON.parse(response);
                if (typeof html.SESIONFINALIZADA === "undefined") {
                    document.getElementById("tabla-lista-unidad").children[1].innerHTML = html.unidadHTML;
                    $("#tabla-lista-unidad").DataTable(
                            {
                                "language": {
                                    "url": "http://cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
                                }
                            }
                    );

                    $("#tabla-lista-unidad button.btn-warning").click(function () {
                        var TD = this.parentNode;
                        var ROW = TD.parentNode;
                        $.post("detallesUnidad.htm", {id: ROW.children[0].innerHTML}, function (response) {
                            var unidad = JSON.parse(response);
                            if (typeof unidad.SIONFINALIZADA === "undefined") {
                                document.forms["formulariounidadSimple"].elements["id_unidad"].value = unidad.id_unidad;
                                document.forms["formulariounidadSimple"].elements["unidad"].value = unidad.unidad;
                                document.forms["formulariounidadSimple"].elements["id_oficina"].value = unidad.id_oficina;
                                document.forms["formulariounidadSimple"].elements["unidad"].focus();
                            } else {
                                window.location.href = "sesion.htm";
                            }

                        }).fail(function () {
                            alert("Ups hubo un error!");
                        });
                    });
                    $("#tabla-lista-unidad button.btn-danger").click(function () {
                        var TD = this.parentNode;
                        var ROW = TD.parentNode;
                        var rpta = window.confirm("Estas Seguro?");
                        if (rpta === true) {
                            window.location.href = "desactivarUnidad.htm?id=" + ROW.children[0].innerHTML;
                        }
                    });
                } else {
                    window.location.assign("sesion.htm");
                }
            }).fail(function () {
                alert("Ups! no se pudo cargar las unidades de esta oficina");
            });
        } else {
            alert("Seleccione una oficina!!");
        }
    }
    document.getElementById("btn-delete-oficina").addEventListener("click", function () {
        if (existeFilaSeleccionada()) {
            let respuesta = window.confirm("Estas seguro de querer desativar esta oficina?");
            if (respuesta) {
                let id_oficina = document.querySelector("div.row.select > div:first-child p").innerHTML;
                window.location.assign("desactivarOficina.htm?id=" + id_oficina);
            }
        } else {
            alert("Seleccione una Fila!");
        }
    });
    $("#formulario-nuevo-oficina .formulario-flotante-cabezera div button").click(function () {
        $("#formulario-nuevo-oficina").fadeOut(500);
        $("#cortina").fadeOut(500);
        document.forms["formulario-nuevo-oficina"].reset();
    });
    $("#panel .formulario-flotante-cabezera div button").click(function () {
        $("#panel").fadeOut(500);
        $("#cortina").fadeOut(500);
        document.forms["formulariounidadSimple"].reset();
        document.querySelector("#panel .formulario-flotante-cuerpo > .row:last-child > div").innerHTML = "<table id='tabla-lista-unidad' class='table table-striped table-bordered' style='width:100%'>"
                + "<thead><tr><th  scope='col'>Id-Unidad</th><th  scope='col'>Unidad</th><th  scope='col'>Oficina</th><th  scope='col'>Acciones</th></tr></thead><tbody></tbody></table>";
    });
}
function submiFormOficina() {
    document.forms["formulario-nuevo-oficina"].addEventListener("submit", function (e) {
        e.preventDefault();
        if (document.forms["formulario-nuevo-oficina"].elements["id_oficina"].value === "") {
            document.forms["formulario-nuevo-oficina"].submit();
        } else {
            document.forms["formulario-nuevo-oficina"].elements["id_oficina"].removeAttribute("disabled");
            document.forms["formulario-nuevo-oficina"].submit();
        }
    }, false);
}
function submiFormUnidad() {
    document.forms["formulariounidadSimple"].addEventListener("submit", function (e) {
        e.preventDefault();
        if (document.forms["formulariounidadSimple"].elements["id_unidad"].value === "") {
            document.forms["formulariounidadSimple"].submit();
        } else {
            document.forms["formulariounidadSimple"].elements["id_unidad"].removeAttribute("disabled");
            document.forms["formulariounidadSimple"].submit();
        }
    }, false);
}
function sizeFormFlotanteCuerpo(formulario) {
    if (formulario === 1) {
        var altura = document.getElementById("formulario-nuevo-oficina").offsetHeight;
        document.querySelector("#formulario-nuevo-oficina .formulario-flotante-cuerpo").style.height = (altura - 100) + "px";
    } else if (formulario === 2) {
        var altura = document.getElementById("panel").offsetHeight;
        document.querySelector("#panel .formulario-flotante-cuerpo").style.height = (altura - 100) + "px";
    }
}
function detallesOficina() {
    var fila = document.querySelector("div.select");
    var codigo = fila.children[0].children[0].innerHTML;
    $.post("detallesoficina.htm", {id: codigo}, function (response) {
        var jsonOficina = JSON.parse(response);
        if (typeof jsonOficina.SIONFINALIZADA === "undefined") {
            document.forms["formulario-nuevo-oficina"].elements["id_oficina"].value = jsonOficina.id_oficina;
            document.forms["formulario-nuevo-oficina"].elements["oficina"].value = jsonOficina.oficina;
            document.forms["formulario-nuevo-oficina"].elements["encargado"].value = jsonOficina.encargado;
            document.forms["formulario-nuevo-oficina"].elements["abreviatura"].value = jsonOficina.abreviatura;
            document.forms["formulario-nuevo-oficina"].elements["detalles"].value = jsonOficina.detalles;
        } else {
            window.location.href = "index.htm";
        }

    }).fail(function () {
        alert("Up! al parecer hubo un error");
    });
}

function lanzarAtajos() {
    document.onkeydown = managerLanzarmantenimientos;
    function managerLanzarmantenimientos(e) {
        if (e.altKey) {// RETORNARA TRUE SI LA TECLA ALT ESTABA PULSADA CUANDO SE PRODUJO EL EVENTO
            if (e.keyCode === N_id) {
                $(ventanasOSI.nuevo).fadeIn(500);
                $(ventanasOSI.cortina).fadeIn(500);
                document.forms["formulario-nuevo-oficina"].elements["oficina"].focus();
                sizeFormFlotanteCuerpo(1);//1 para darle altura al cuerpo del formulario nuevo registro
            }
            if (e.keyCode === U_id) {
                $(ventanasOSI.unidad).fadeIn(500);
                $(ventanasOSI.cortina).fadeIn(500);
                sizeFormFlotanteCuerpo(2);//1 para darle altura al cuerpo del formulario nuevo registro
                document.forms["formulariounidadSimple"].elements["unidad"].focus();

            }
            if (e.keyCode === M_id) {
                if (existeFilaSeleccionada()) {
                    $(ventanasOSI.nuevo).fadeIn(500);
                    $(ventanasOSI.cortina).fadeIn(500);
                    sizeFormFlotanteCuerpo(1);//0 para darle altura al cuerpo del formulario editar registro
                    detallesOficina();
                    document.forms["formulario-nuevo-oficina"].elements["oficina"].focus();
                } else {
                    alert("Seleccione una Fila!");
                }
            }

        } else {
            if (e.keyCode === Esc_id) {
                $(ventanasOSI.nuevo).fadeOut(500);
                $(ventanasOSI.cortina).fadeOut(500);
                $(ventanasOSI.unidad).fadeOut(500);
                document.getElementById("pop-menu").classList.add("oculto");
                document.forms["formulario-nuevo-oficina"].reset();
                document.forms["formulariounidadSimple"].reset();
                document.querySelector("#panel .formulario-flotante-cuerpo > .row:last-child > div").innerHTML = "<table id='tabla-lista-unidad' class='table table-striped table-bordered' style='width:100%'>"
                        + "<thead><tr><th  scope='col'>Id-Unidad</th><th  scope='col'>Unidad</th><th  scope='col'>Oficina</th><th  scope='col'>Acciones</th></tr></thead><tbody></tbody></table>";
            }
        }
    }
}
function filtros() {
    document.forms["filtro-oficina-form"].addEventListener("submit", function (e) {
        e.preventDefault();
        $.post("filtroOficina.htm", $(this).serialize(), function (response) {
            var json = JSON.parse(response);
            if (typeof json.SESIONFINALIZADA === "undefined") {
                $("section .lista-cuerpo").html(json.oficinaHTML);
                evaluacion();
            } else {
                window.location.assign("sesion.htm");
            }
        });
    }, false);
}
function desplegarFiltro() {
    document.getElementById("content-filtro-head").addEventListener("click", function (e) {
        if (this.children[1].classList.contains("fa-caret-down")) {
            $("#content-filtro-body").slideDown(500);
            this.children[1].className = "fa fa-caret-up";
            this.parentNode.style.boxShadow = "0px 2px 3px black";
        } else {
            $("#content-filtro-body").slideUp(500);
            this.children[1].className = "fa fa-caret-down";
            this.parentNode.style.boxShadow = "none";
        }
    }, false);
}

//PARA LA GENERACION DE FICHAS DE SERVICIO PDF
function EventosParaClickDerecho(listener1, listener2, listener3) {
    if (listener1) {
        //SI EL CLICK SE DA EN EL BOTON QUE ABRE LA ADMINISTRACION DE UNIDADES NO SE REMOVERA EL #POP-MENU CASO CONTRARIO SE OCULTARA EL #POP-MENU
        document.documentElement.addEventListener("mousedown", function (e) {
            var button = null;
            if (e.target.classList.contains("accion")) {
                button = e.target;
            }
            if (button === null) {
                var pop = document.querySelector("#pop-menu");
                if (pop.className === "") {
                    pop.classList.add("oculto");
                }
            }
        }, false);
    }

    if (listener2) {
        //MUESTRA EL #POP-MENU
        $(".lista-cuerpo > .row").bind("contextmenu", function (e) {
            if (existeFilaSeleccionada()) {
                if (this.classList.contains("select")) {
                    var X = e.clientX;
                    var Y = e.clientY;
                    var pop = document.getElementById("pop-menu");
                    pop.style.left = X + "px";
                    pop.style.top = Y + "px";
                    pop.classList.remove("oculto");
                }
            }
        });
    }

    if (listener3) {
        //ABRE EL PANEL PARA GENERAR LLENAR LA FICHA DE SERVICO  DE SERVICIO
        document.querySelector("#btn-ficha-servicio-tecnico").addEventListener("click", function () {
            //VERIFICAMOS SI LA INCIDENCIA A FINALIZADO 
            $.post("estadoIncidencia.htm", {id: document.querySelector(".row.select .col:first-child p").innerText}, function (response) {
                var Json = JSON.parse(response);
                if (typeof Json.SESIONFINALIZADA === "undefined") {
                    if (typeof Json.Finalizado === "undefined") {
                        if (typeof Json.notExisteFicha === "undefined") {
                            document.forms["form-FST"].elements["id_incidencia"].value = document.querySelector(".row.select .col:first-child p").innerText;
                            document.forms["form-FST"].elements["id_periferico"].value = Json.ID_PERIFERICO;
                            document.forms["form-FST"].elements["periferico_detalle"].value = Json.PERIFERICO_DETALLE;
                            document.forms["form-FST"].elements["cod_equipo"].value = Json.COD_EQUIPO;
                            document.forms["form-FST"].elements["traslado"].value = Json.TRASLADO;
                            document.forms["form-FST"].elements["S_correctivo"].value = Json.S_CORRECTICO;
                            document.forms["form-FST"].elements["Recomendacion"].value = Json.RECOMENDACION;
                        }

                        $("#cortina").fadeIn(500);
                        $("#form-pdf").fadeIn(500);
                        document.forms["form-FST"].elements["id_incidencia"].value = document.querySelector(".row.select .col:first-child p").innerText;
                        document.forms["form-FST"].elements["id_periferico"].focus();
                        sizeFormFlotanteCuerpo(4);
                    } else {
                        alert("Debe finalizar la tarea!");
                    }
                } else {
                    window.location.assign("sesion.htm");
                }
            }).fail(function () {
                alert("Ups! hubo un error al traer los datos...");
            });
//            window.open("FichaServicioTecnico.htm");
//            document.getElementById("pop-menu").classList.add("oculto");
        }, false);
    }
}

openCloseForm();
submiFormOficina();
submiFormUnidad();
lanzarAtajos();
filtros();
desplegarFiltro();
EventosParaClickDerecho(true, true, false);

