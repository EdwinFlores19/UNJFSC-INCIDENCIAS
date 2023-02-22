const Esc_id = 27;// CODIGO DE TECLADO DE LA TECLA Esc  
const N_id = 78;// CODIGO DE TECLADO DE LA TECLA N  nuevo
const M_id = 77;// CODIGO DE TECLADO DE LA TECLA M  modificar

ventanasOSI = {
    nuevo: document.getElementById("formulario-nuevo-registro"),
    modificar: document.getElementById("formulario-editar-registro"),
    cortina: document.getElementById("cortina"),
    atencion: document.getElementById("form-incidec-proceso")
};

function openCloseForm() {
    document.getElementById("btn-new-registro").addEventListener("click", function () {
        $("#cortina").fadeIn(500);
        $("#formulario-nuevo-registro").fadeIn(500);
        sizeFormFlotanteCuerpo(1);//1 para darle altura al cuerpo del formulario nuevo registro
        document.nuevaIncidencia.id_inci_detalle.focus();
    });

    $("#btn-edit-registro").click(function () {
        if (existeFilaSeleccionada()) {
            $("#cortina").fadeIn(500);
            $("#formulario-editar-registro").fadeIn(500);
            sizeFormFlotanteCuerpo(2);//0 para darle altura al cuerpo del formulario editar registro
            pedirDetallesIncidencia();
        } else {
            alert("Seleccione una Fila!");
        }
    });
    $("#btn-cancelar-registro").click(function () {
        //FUNCION ANONIMA PARA CANCELAR UNA INCIDENCIA
        if (existeFilaSeleccionada()) {
            $.post("cancelarIncidencia.htm", {id_incidencia: $(".select div:first-child p").html()}, function (response) {
                var objeto = JSON.parse(response);
                if (objeto.SESIONFINALIZADA === false) {
                    if (objeto.RESPUESTA === true) {
                        window.location.href = "incidencia.htm";
                    } else {
                        alert("No se puede cancelar!");
                    }
                } else {
                    window.location.href = "sesion.htm";
                }
            }).fail(function () {
                alert("Ups! ocurrio un error, Recarge la Pagina.");
            });

        } else {
            alert("Seleccione una Fila!");
        }
    });


    $("#formulario-nuevo-registro .formulario-flotante-cabezera div button").click(function () {
        $("#formulario-nuevo-registro").fadeOut(500);
        $("#cortina").fadeOut(500);
        document.nuevaIncidencia.reset();
        document.nuevaIncidencia.id_unidad.innerHTML = "";

    });

    $("#formulario-editar-registro .formulario-flotante-cabezera div button").click(function () {
        $("#formulario-editar-registro").fadeOut(500);
        $("#cortina").fadeOut(500);
        document.editarIncidencia.reset();
        document.querySelector("#accordion #collapseOne div ul").innerHTML = "";
    });
    asignarListenerBtnEspera();

}

function asignarListenerBtnEspera() {
    $(".lista-cuerpo .row .col button.btn-warning").click(function (e) {
        var elementos = {
            span: null,
            button: null,
            p: null,
            div: null,
            row: null
        };
        switch (e.target.tagName) {
            case "SPAN":
                elementos.span = e.target;
                elementos.button = elementos.span.parentNode;
                elementos.p = elementos.button.parentNode;
                elementos.div = elementos.p.parentNode;
                elementos.row = elementos.div.parentNode;
                break;
            case "BUTTON":
                elementos.button = e.target;
                elementos.p = elementos.button.parentNode;
                elementos.div = elementos.p.parentNode;
                elementos.row = elementos.div.parentNode;
                break;
        }
        document.forms["form-incidec-proceso"].elements["id_incidencia"].value = elementos.row.children[0].children[0].innerHTML;
        $("#form-incidec-proceso").fadeIn(500);
        $("#cortina").fadeIn(500);
        sizeFormFlotanteCuerpo(3);
    });
    $("#form-incidec-proceso .formulario-flotante-cabezera div button").click(function () {
        $("#form-incidec-proceso").fadeOut(500);
        $("#cortina").fadeOut(500);
        document.forms["form-incidec-proceso"].reset();
    });
}


//en funcion al tamaño del formulario le dara al cuerpo un tamaño para que pueda ser efectivo la propiedad scroll 
function sizeFormFlotanteCuerpo(formulario) {
    if (formulario === 1) {
        var altura = document.getElementById("formulario-nuevo-registro").offsetHeight;
        document.querySelector("#formulario-nuevo-registro .formulario-flotante-cuerpo").style.height = (altura - 100) + "px";
    } else if (formulario === 2) {
        var altura = document.getElementById("formulario-editar-registro").offsetHeight;
        document.querySelector("#formulario-editar-registro .formulario-flotante-cuerpo").style.height = (altura - 100) + "px";
    } else if (formulario === 3) {
        var altura = document.getElementById("form-incidec-proceso").offsetHeight;
        document.querySelector("#form-incidec-proceso .formulario-flotante-cuerpo").style.height = (altura - 100) + "px";
    } else if (formulario === 4) {
        var altura = document.getElementById("form-pdf").offsetHeight;
        document.querySelector("#form-pdf .formulario-flotante-cuerpo").style.height = (altura - 100) + "px";
    }
}

function cerrarAlertaProgramacion() {
    $("#content-programacion .container button").click(function () {
        $("#content-programacion").fadeOut(500);
        $("#cortina").fadeOut(500);
    });
}



function cargarUnidad() {
    //carg el id segun el nombre de la oficina seleccionada
    document.nuevaIncidencia.nomb_oficina.addEventListener("change", function () {

        if (this.value !== "") {
            $.post("returnIdOficina.htm", {nombre: this.value}, function (response) {
                let detalles = JSON.parse(response);
                if (typeof detalles.SIONFINALIZADA === "undefined") {
                    if (detalles.oficina !== null) {
                        document.nuevaIncidencia.id_oficina.value = detalles.id_oficina;
                        $.post("unidad.htm", {id_oficina: document.nuevaIncidencia.id_oficina.value}, function (response) {
                            let lista = JSON.parse(response);
                            if (typeof lista.SESIONFINALIZADA === "undefined") {
                                document.nuevaIncidencia.id_unidad.innerHTML = lista.unidades;
                            } else {
                                window.location.assign("sesion.htm");
                            }
                        });
                    } else {
                        alert("la oficina especificada no existe!!!");
                        document.nuevaIncidencia.nomb_oficina.value = "";
                        document.nuevaIncidencia.id_oficina.value = "";
                        document.nuevaIncidencia.id_unidad.innerHTML = "";
                        document.nuevaIncidencia.nomb_oficina.focus();
                    }
                } else {
                    window.location.assign("sesion.htm");
                }

            }).fail(function () {
                alert("Ocurrio un error!");
            });
        } else {
            document.nuevaIncidencia.id_oficina.value = "";
            document.nuevaIncidencia.id_unidad.innerHTML = "";
        }
    }, false);

    document.nuevaIncidencia.id_oficina.addEventListener("change", function () {
        if (document.nuevaIncidencia.id_oficina.value !== "") {
            $.post("unidad.htm", {id_oficina: document.nuevaIncidencia.id_oficina.value}, function (response) {
                document.nuevaIncidencia.id_unidad.innerHTML = response;
            });
        } else {
            document.nuevaIncidencia.id_unidad.innerHTML = "";
        }
    }, false);
    document.editarIncidencia.id_oficina.addEventListener("change", function () {
        if (document.editarIncidencia.id_oficina.value !== "0") {
            $.post("unidad.htm", {id_oficina: document.editarIncidencia.id_oficina.value}, function (response) {
                document.editarIncidencia.id_unidad.innerHTML = response;
            });
        } else {
            document.editarIncidencia.id_unidad.innerHTML = "";
        }
    }, false);
}


function pedirDetallesIncidencia() {
    var fila = document.querySelector("section .lista-cuerpo div.row.select");
    var codigo = document.querySelector("section .lista-cuerpo div.row.select > div:first-child p").innerHTML;
    $.post("detallesIncidencia.htm", {id: codigo}, function (response) {
        var detalles = JSON.parse(response);
        if (typeof detalles.SESIONFINALIZADA !== "undefined") {
            window.location.assign("sesion.htm");
        } else {
            llenarFormulario(detalles);
        }
    });
}

function llenarFormulario(detalles) {
    document.forms["editarIncidencia"].elements["id_inci"].value = detalles.id;
    document.forms["editarIncidencia"].elements["fecha"].value = detalles.fecha;
    document.forms["editarIncidencia"].elements["hora"].value = detalles.hora;
    document.forms["editarIncidencia"].elements["empleado"].value = detalles.registrador;
    document.forms["editarIncidencia"].elements["id_inci_detalle"].value = detalles.incidencia;
    document.forms["editarIncidencia"].elements["inci_detalle"].value = detalles.detalle;
    document.forms["editarIncidencia"].elements["id_oficina"].value = detalles.oficina;

    if (document.editarIncidencia.id_oficina.value !== "0") {
        $.post("unidad.htm", {id_oficina: document.editarIncidencia.id_oficina.value}, function (response) {
            document.editarIncidencia.id_unidad.innerHTML = response;
            document.forms["editarIncidencia"].elements["id_unidad"].value = detalles.unidad;
        });
    } else {
        document.editarIncidencia.id_unidad.innerHTML = "";
    }
    document.forms["editarIncidencia"].elements["encargado"].value = detalles.encargado;
    document.forms["editarIncidencia"].elements["id_inci_medio"].value = detalles.medio;
    document.forms["editarIncidencia"].elements["comentario"].value = detalles.comentario;

    var HTML = "";
    for (var atte in detalles.atencion) {
        HTML += "<li class='list-group-item'>" + detalles.atencion[atte] + "</li>";
    }
    document.querySelector("#accordion #collapseOne div ul").innerHTML = HTML;
}
function submitFormNuevaIncidencia() {
    document.nuevaIncidencia.addEventListener("submit", function (e) {
        e.preventDefault();
        this.id_oficina.removeAttribute("disabled");
        this.submit();
    }, false);
}

function submitFormIncidenciaProceso() {
    //FUNCION PARA PONER EN PROCESO UNA INCIDENCIA EN ESPERA
    document.forms["form-incidec-proceso"].addEventListener("submit", function (e) {
        e.preventDefault();
        document.forms["form-incidec-proceso"].elements["id_incidencia"].removeAttribute("disabled");
        $.post("inicidenciaEnProceso.htm", $(this).serialize(), function (response) {
            var objeto = JSON.parse(response);
            if (objeto.SESIONFINALIZADA === false) {
                if (objeto.RESPUESTA === true) {
                    window.location.href = "incidencia.htm";
                }
            } else {
                window.location.href = "sesion.htm";
            }
        }).fail(function () {
            alert("Ups! ocurrio un error, Recarge la Pagina.");
        });
    }, false);
}
//asigna un liostener al boton proceso para finalizar incidencias
function FinalizarIncidencia() {
    //FUNCION PARA FINALIZAR UNA INCIDECNIAS
    $(".lista-cuerpo .row .col button.btn-success").click(function (e) {
        var comentario = confirm("Desea Finalizar Incidecnia?");
        if (comentario === true) {
            elementos = {
                p: null,
                div: null,
                row: null
            };
            elementos.p = this.parentNode;
            elementos.div = elementos.p.parentNode;
            elementos.row = elementos.div.parentNode;
            var incidencia = elementos.row.children[0].children[0].innerHTML;

            $.post("inicidenciaFinalizado.htm", {id_incidencia: incidencia}, function (response) {
                var objeto = JSON.parse(response);
                if (objeto.SESIONFINALIZADA === false) {
                    if (objeto.RESPUESTA === true) {
                        window.location.reload();
                    }
                } else {
                    window.location.href = "sesion.htm";
                }
            }).fail(function () {
                alert("Ups! ocurrio un error, Recarge la Pagina.");
            });
        }
    });
}
function lanzarAtajos() {
    document.onkeydown = managerLanzarmantenimientos;
    function managerLanzarmantenimientos(e) {
        if (e.altKey) {// RETORNARA TRUE SI LA TECLA ALT ESTABA PULSADA CUANDO SE PRODUJO EL EVENTO
            if (e.keyCode === N_id) {
                $(ventanasOSI.nuevo).fadeIn(500);
                $(ventanasOSI.cortina).fadeIn(500);
                sizeFormFlotanteCuerpo(1);
                document.nuevaIncidencia.id_inci_detalle.focus();
            }
            if (e.keyCode === M_id) {
                if (existeFilaSeleccionada()) {
                    $(ventanasOSI.modificar).fadeIn(500);
                    $(ventanasOSI.cortina).fadeIn(500);
                    sizeFormFlotanteCuerpo(2);//0 para darle altura al cuerpo del formulario editar registro
                    pedirDetallesIncidencia();
                } else {
                    alert("Seleccione una Fila!");
                }
            }

        } else {
            if (e.keyCode === Esc_id) {
                $(ventanasOSI.nuevo).fadeOut(500);
                $(ventanasOSI.modificar).fadeOut(500);
                $(ventanasOSI.cortina).fadeOut(500);
                $(ventanasOSI.atencion).fadeOut(500);
                document.editarIncidencia.reset();
                document.querySelector("#accordion #collapseOne div ul").innerHTML = "";
                $("#content-programacion").fadeOut(500);
                $("#form-pdf").fadeOut(500);
                document.forms["form-FST"].reset();
                document.getElementById("pop-menu").classList.add("oculto");
                document.forms["editarIncidencia"].reset();
                document.forms["form-incidec-proceso"].reset();
                document.nuevaIncidencia.reset();
                document.nuevaIncidencia.id_unidad.innerHTML = "";
                document.forms["form-FST"].elements["periferico_detalle"].setAttribute("disabled", "disabled");
            }
        }
    }
}

//PARA LA GENERACION DE FICHAS DE SERVICIO PDF
function EventosParaClickDerecho(listener1, listener2, listener3) {
    if (listener1) {
        //SI EL CLICK SE DA EN EL BOTON QUE GENERA EL PDF FICHA DE SERVICIO NO SE REMOVERA EL #POP-MENU CASO CONTRARIO SE OCULTARA EL #POP-MENU
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

//CIERRA EL APNEL PARA GENERAR FICHA DE SERVICFIO
    document.querySelector("#form-pdf .formulario-flotante-cabezera > div:last-child button").addEventListener("click", function () {
        $("#cortina").fadeOut(500);
        $("#form-pdf").fadeOut(500);
        document.forms["form-FST"].reset();
        document.forms["form-FST"].elements["periferico_detalle"].setAttribute("disabled", "disabled");
    }, false);
}
//QUITAMOS EL ATRIBUTO DISABLED DEL CAMPO ID INCIDENCIA Y ENVIAMOS EL FORMULARIO
function submitform_FST() {
    document.forms["form-FST"].addEventListener("submit", function (e) {
        e.preventDefault();
        this.elements["id_incidencia"].removeAttribute("disabled");
        this.elements["periferico_detalle"].removeAttribute("disabled");
        this.submit();

        //cerramos el formulario y reseteamos
        $("#cortina").fadeOut(500);
        $("#form-pdf").fadeOut(500);
        this.elements["id_incidencia"].setAttribute("disabled", "disabled");
        this.elements["periferico_detalle"].setAttribute("disabled", "disabled");
        document.forms["form-FST"].reset();
    }, false);

}

function filtros() {
    document.forms["filtro-incidencia-form"].addEventListener("submit", function (e) {
        e.preventDefault();
        $.post("filtroIncidencias.htm", $(this).serialize(), function (response) {
            var json = JSON.parse(response);
            if (typeof json.SESIONFINALIZADA === "undefined") {
                $("section .lista-cuerpo").html(json.INCIDENCIASROW);
                $("#fecha").html(json.FECHA);
                $("#leyenda > a.proceso span:last-child").html(json.PROCESO);
                $("#leyenda > a.espera span:last-child").html(json.ESPERA);
                $("#leyenda > a.finalizado span:last-child").html(json.FINALIZADO);
                $("#leyenda > a.cancelado span:last-child").html(json.CANCELADO);
                asignarListenerBtnEspera();
                FinalizarIncidencia();
                evaluacion();
                EventosParaClickDerecho(false, true, false);
                $(".-pop-atencion").css({display: "none"});

            } else {
                window.location.assign("sesion.htm");
            }
        });
    }, false);

    document.querySelector("a.proceso").addEventListener("click", function () {
        filtros_now(1);
    }, false);
    document.querySelector("a.espera").addEventListener("click", function () {
        filtros_now(2);
    }, false);
    document.querySelector("a.finalizado").addEventListener("click", function () {
        filtros_now(3);
    }, false);
    document.querySelector("a.cancelado").addEventListener("click", function () {
        filtros_now(4);
    }, false);
}

//ESTE FILTRO HACE LO MISMO QUE EL ANTERIOR PERO CON LA CONDICION QUE LOS RESULTADOS SON DE ÑLA FECHA ACTUAL
function filtros_now(estate) {
    var fecha = new Date();
    var now = fecha.getFullYear() + "-" + returnMES(fecha.getMonth() + 1) + "-" + fecha.getDate();
    $.post("filtroIncidencias.htm", {estado: estate, fecha: now}, function (response) {
        var json = JSON.parse(response);
        if (typeof json.SESIONFINALIZADA === "undefined") {
            $("section .lista-cuerpo").html(json.INCIDENCIASROW);
            $("#fecha").html(json.FECHA);
            $("#leyenda > a.proceso span:last-child").html(json.PROCESO);
            $("#leyenda > a.espera span:last-child").html(json.ESPERA);
            $("#leyenda > a.finalizado span:last-child").html(json.FINALIZADO);
            $("#leyenda > a.cancelado span:last-child").html(json.CANCELADO);
            asignarListenerBtnEspera();
            FinalizarIncidencia();
            evaluacion();
            EventosParaClickDerecho(false, true, false);
            $(".-pop-atencion").css({display: "none"});

        } else {
            window.location.assign("sesion.htm");
        }
    });
}

function returnMES(mes) {
    if (mes < 10) {
        return "0" + mes;
    } else {
        return mes;
    }
}

//ESTE FILTRO HACE LO MISMO QUE EL ANTERIOR PERO CON LA CONDICION QUE LOS RESULTADOS SON DE ÑLA FECHA ACTUAL
function filtros_now(estate) {
    var fecha = new Date();
    var now = fecha.getFullYear() + "-" + returnMES(fecha.getMonth() + 1) + "-" + fecha.getDate();
    $.post("filtroIncidencias.htm", {estado: estate, fecha: now}, function (response) {
        var json = JSON.parse(response);
        if (typeof json.SESIONFINALIZADA === "undefined") {
            $("section .lista-cuerpo").html(json.INCIDENCIASROW);
            $("#fecha").html(json.FECHA);
            $("#leyenda > a.proceso span:last-child").html(json.PROCESO);
            $("#leyenda > a.espera span:last-child").html(json.ESPERA);
            $("#leyenda > a.finalizado span:last-child").html(json.FINALIZADO);
            $("#leyenda > a.cancelado span:last-child").html(json.CANCELADO);
            asignarListenerBtnEspera();
            FinalizarIncidencia();
            evaluacion();
            EventosParaClickDerecho(false, true, false);
            $(".-pop-atencion").css({display: "none"});

        } else {
            window.location.assign("sesion.htm");
        }
    });
}

function returnMES(mes) {
    if (mes < 10) {
        return "0" + mes;
    } else {
        return mes;
    }
}

//si presionas este  input de tipo radio se marcaran todolos los inputs atencion
function seleccionarTodaAtencion() {
    document.getElementById("todos-prac-trab").addEventListener("change", function () {
        if (this.checked) {
            var atencion = document.querySelectorAll("#form-incidec-proceso .formulario-flotante-cuerpo .list-group-item input[type='checkbox']");
            for (var i = 0; i < atencion.length; i++) {
                atencion[ i ].checked = true;
            }
        }

    }, false);

    function seleccionInputAtencion() {
        $("#form-incidec-proceso .formulario-flotante-cuerpo .list-group-item input[type='checkbox']").bind("change", function () {
            var todosSeleccionados;
            var atencion = document.querySelectorAll("#form-incidec-proceso .formulario-flotante-cuerpo .list-group-item input[type='checkbox']");
            for (var i = 0; i < atencion.length; i++) {
                if (atencion[i].checked) {
                    todosSeleccionados = true;
                } else {
                    todosSeleccionados = false;
                    break;
                }
            }
            if (todosSeleccionados) {
                document.getElementById("todos-prac-trab").setAttribute("checked", "checked");
                document.getElementById("todos-prac-trab").checked = true;
            } else {
                document.getElementById("todos-prac-trab").removeAttribute("checked");
                document.getElementById("todos-prac-trab").checked = false;
            }
        });
    }

    seleccionInputAtencion();
}

//LAS SIGUIENTES FUNCIONES MODIFICAN EL CONTENIDO PARA QUE SE PUEDAN VER MEJOR EN UNA RESOLUCION MENOR A 1366PX
function cambiarContenido() {
    if (window.screen.width <= 1366) {
        var labels = document.querySelectorAll("#filtro-incidencia-form input[type='radio'] + label");
        labels[0].innerHTML = "Finalizados";
        labels[1].innerHTML = "Espera";
        labels[2].innerHTML = "Proceso";

        document.querySelector("#content-filtro-head > span:last-child").className = "fa fa-caret-down";
        document.getElementById("content-filtro-body").style.display = "none";
        document.getElementById("content-filtro").style.boxShadow = "none";
    }

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

function desactivarActivar_inputPerifericoDetalle() {
    document.forms["form-FST"].elements["id_periferico"].addEventListener("change", function () {
        if (this.value === "0") {
            document.forms["form-FST"].elements["periferico_detalle"].removeAttribute("disabled");
        } else {
            document.forms["form-FST"].elements["periferico_detalle"].setAttribute("disabled", "disabled");
        }
    }, false);
}

function mostrarAtencionEnviada(e) {
    var boll = false;
    var X = e.clientX;
    var Y = e.clientY;
    var fila = document.querySelector(".row.select");
    var filas = document.querySelectorAll(".lista-cuerpo > .row");

    var id_incidencia = fila.children[0].innerText.trim();
    var pop = fila.children[7];

    pop.style.border = "none";

    for (let i = 0; i < filas.length; i++) {
        if (fila === filas[i] & i <= 2) {
            boll = true;
            break;
        }
    }
    if (boll) {
        pop.style.top = "70%";
        pop.classList.add("top");
    } else {
        pop.style.bottom = "50%";
    }
    var espacioIsquierdo = window.innerWidth - fila.offsetWidth;
    pop.style.left = X - espacioIsquierdo + "px";

    $.post("atencionEnviada.htm", {id: id_incidencia}, function (response) {
        let atencion = JSON.parse(response);
        if (typeof atencion.SIONFINALIZADA === "undefined") {
            if (atencion.per_enviado === "") {
                pop.children[0].innerHTML = "Solicitante: <span style='font-family:arial;'>" + atencion.solicitante + "</span><br><br>Atencion Enviada:";
                pop.children[1].innerHTML = "Aun no se designo atencion";
            } else {
                pop.children[0].innerHTML = "Solicitante: <span style='font-family:arial;'>" + atencion.solicitante + "</span><br><br>Atencion Enviada:";
                pop.children[1].innerHTML = atencion.per_enviado;
            }
            pop.style.display = "block";
        } else {
            window.location.assign("sesion.htm");
        }

    }).fail(function () {
        alert("Ups! al parecer hubo un error al cargar la atencion.");
    });

}

//CAMBIARA EL ESTADO DE UNA INCIDENCIA A `PROCESO SOLO SI ESTA A FINALIZADO
function poner_proceso_incidencia_finalizada() {
    document.getElementById("btn-volver-a-proceso").addEventListener("click", function () {
        let rpta = window.confirm("Estas seguro de poner es proceso esta incidencia?. Solo funcionara si esta a finalizado!");
        if (rpta) {
            var id_incidencia = document.querySelector(".row.select").children[0].innerText.trim();
            window.location.assign("reconciderarIncidenciaFilnalizada.htm?id=" + id_incidencia);
        }
    }, false);
}

function actualizarIncidencia() {
    document.editarIncidencia.addEventListener("submit", function (e) {
        e.preventDefault();
        this.id_inci.removeAttribute("disabled");
        this.submit();
    }, false);
}

function Enable_desable_input_incidencia_detalle() {
    document.forms["nuevaIncidencia"].elements["id_inci_detalle"].addEventListener("change", function () {
        if (this.value !== 0) {
            document.nuevaIncidencia.inci_detalle.value = "";
        }
    }, false);
    document.forms["editarIncidencia"].elements["id_inci_detalle"].addEventListener("change", function () {
        if (this.value !== 0) {
            document.editarIncidencia.inci_detalle.value = "";
        }
    }, false);
}

cerrarAlertaProgramacion();
openCloseForm();
submitFormNuevaIncidencia();
cargarUnidad();
submitFormIncidenciaProceso();
FinalizarIncidencia();
lanzarAtajos();
EventosParaClickDerecho(true, true, true);
submitform_FST();
filtros();
seleccionarTodaAtencion();
cambiarContenido();
desplegarFiltro();
desactivarActivar_inputPerifericoDetalle();
poner_proceso_incidencia_finalizada();
actualizarIncidencia();
Enable_desable_input_incidencia_detalle();

