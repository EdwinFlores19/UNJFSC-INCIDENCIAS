const Esc_id = 27;// CODIGO DE TECLADO DE LA TECLA Esc  
const N_id = 78;// CODIGO DE TECLADO DE LA TECLA N  nuevo
const M_id = 77;// CODIGO DE TECLADO DE LA TECLA M  modificar

ventanasOSI = {
    nuevo: document.getElementById("formulario-nuevo-registro"),
    modificar: document.getElementById("formulario-editar-registro"),
    cortina: document.getElementById("cortina"),
    atencion: document.getElementById("form-prgramacion_atencion")
};

function openCloseFormularios() {

    // ABRE EL FORMULARIO NUEVA TAREA(REGISTRO)
    document.getElementById("btn-new-registro").addEventListener("click", function () {
        $("#cortina").fadeIn(500);
        $("#formulario-nuevo-registro").fadeIn(500);
        sizeFormFlotanteCuerpo(1);

        //LE DAMOS EL FOCO A LA CAJA DE TEXTO FECHA
        document.forms["nuevaProgramacion"].elements["fecha_progra"].focus();
        sizeFormFlotanteCuerpo(1);
    }, false);

    // ABRE EL FORMULARIO EDITAR TAREA TAREA(REGISTRO)
    document.getElementById("btn-edit-registro").addEventListener("click", function () {
        if (existeFilaSeleccionada()) {
            $("#cortina").fadeIn(500);
            $("#formulario-editar-registro").fadeIn(500);
            PedirDatos();
            sizeFormFlotanteCuerpo(2);//le enviamos dos para hacer referencia al fumrluario editar registro

            //LE DAMOS EL FOCO A LA CAJA DE TEXTO FECHA
            document.forms["nuevaProgramacion"].elements["fecha_progra"].focus();
        } else {
            alert("Seleccione una fila!!!");
        }
    }, false);

    //CIERRA EL FORMULARIO NUEVO TAREA(REGISTRO) 
    document.querySelector("#formulario-nuevo-registro .formulario-flotante-cabezera div:last-child h6 button.btn-danger").addEventListener("click", function () {
        $("#cortina").fadeOut(500);
        $("#formulario-nuevo-registro").fadeOut(500);
        //RESETEAMOS LOS FORMULARIOS AL CERRARLOS
        document.forms["nuevaProgramacion"].reset();
    }, false);
    //CIERRA EL FORMULARIO EDITAR TAREA(REGISTRO) 
    document.querySelector("#formulario-editar-registro .formulario-flotante-cabezera div:last-child h6 button.btn-danger").addEventListener("click", function () {
        $("#cortina").fadeOut(500);
        $("#formulario-editar-registro").fadeOut(500);
        //RESETEAMOS LOS FORMULARIOS AL CERRARLOS
        document.forms["editarProgramacion"].reset();
    }, false);



    asignarListenerBtnEspera();

    // CIERRA EL FORMULARIO  PROGRAMACION ATENCION
    $("#form-prgramacion_atencion > .formulario-flotante-cabezera div:last-child button.btn-danger").click(function () {
        $("#form-prgramacion_atencion").fadeOut(500);
        $("#cortina").fadeOut(500);
        //RESETEAMOS EL FORMULARIO ATENCION
        document.forms["form-prgramacion_atencion"].reset();
    });

}

// ABRE EL FORMULARIO PROGRAMACION ATENCION
function asignarListenerBtnEspera() {
    $(".lista-cuerpo > .row > .col p button.btn-warning").click(function () {
        $("#cortina").fadeIn(500);
        $("#form-prgramacion_atencion").fadeIn(500);
        //EXTRAEMOS EL ID DE LA FILA SELECCIONA Y SE LO INSERTAMOSEN EL CAMPO ID DEL FORMULARIO ATENCION
        document.forms["form-prgramacion_atencion"].elements["id_programacion"].value = this.parentNode.parentNode.parentNode.children[0].children[0].innerHTML;
        sizeFormFlotanteCuerpo(3);
    });
}

function sizeFormFlotanteCuerpo(formulario) {
    if (formulario === 1) {
        var altura = document.getElementById("formulario-nuevo-registro").offsetHeight;
        document.querySelector("#formulario-nuevo-registro .formulario-flotante-cuerpo").style.height = (altura - 100) + "px";
    }
    if (formulario === 2) {
        var altura = document.getElementById("formulario-editar-registro").offsetHeight;
        document.querySelector("#formulario-editar-registro .formulario-flotante-cuerpo").style.height = (altura - 100) + "px";
    }
    if (formulario === 3) {
        var altura = document.getElementById("form-prgramacion_atencion").offsetHeight;
        document.querySelector("#form-prgramacion_atencion .formulario-flotante-cuerpo").style.height = (altura - 100) + "px";
    }

}

function enviarFormularioAtencion() {
    document.forms["form-prgramacion_atencion"].addEventListener("submit", function (e) {
        e.preventDefault();
        this.elements["id_programacion"].removeAttribute("disabled");
        this.submit();
    }, false);
}

function FinalizarTarea() {

    // ASIGNAMOS UNA FUNCION AL BOTON FINALIZAR TAREA
    $(".lista-cuerpo > .row > .col p button.btn-success").click(function () {
        var respuesta = confirm("Estas seguro de finalizar tarea?");
        if (respuesta) {
            window.location.href = "finalizarTarea.htm?id=" + this.parentNode.parentNode.parentNode.children[0].children[0].innerHTML;
        }
    });
}

function CancelarTarea() {

// ASIGNAMOS UNA FUNCION AL BOTON CANCELAR TAREA
    $("#btn-cancelar-registro").click(function () {

        if (existeFilaSeleccionada()) {
            var id_programacion = document.querySelector(".lista-cuerpo > .row.select > .col:first-child p").innerHTML;
            $.get("sePuedeCancelarTarea.htm", {id: id_programacion}, function (response) {
                if (JSON.parse(response)) {
                    var respuesta = confirm("Estas seguro de cancelar esta tarea?");
                    if (respuesta) {
                        window.location.href = "cancelarTarea.htm?id=" + id_programacion;
                    }
                } else {
                    alert("No se puede cancelar esta tarea!");
                }
            });
        } else {
            alert("Seleccione una fila!");
        }

    });
}

function cargarComboBoxUnidad() {

    //carg el id segun el nombre de la oficina seleccionada
    document.nuevaProgramacion.nomb_oficina.addEventListener("change", function () {

        if (this.value !== "") {
            $.post("returnIdOficina.htm", {nombre: this.value}, function (response) {
                let detalles = JSON.parse(response);
                if (detalles.oficina !== null) {
                    document.nuevaProgramacion.id_oficina.value = detalles.id_oficina;
                    $.post("unidad_por_oficina.htm", {id_oficina: document.nuevaProgramacion.id_oficina.value}, function (response) {
                        document.nuevaProgramacion.id_unidad.innerHTML = response;
                    });
                } else {
                    alert("la oficina especificada no existe!!!");
                    document.nuevaProgramacion.nomb_oficina.value = "";
                    document.nuevaProgramacion.id_oficina.value = "";
                    document.nuevaProgramacion.id_unidad.innerHTML = "";
                    document.nuevaProgramacion.nomb_oficina.focus();
                }

            }).fail(function () {
                alert("Ocurrio un error!");
            });
        } else {
            document.nuevaProgramacion.id_oficina.value = "";
            document.nuevaProgramacion.id_unidad.innerHTML = "";
        }
    }, false);

    // CADA VES QUE SE SELECCIONE UNA NUEVA OFICINA SE CARGARA SUS RESPECTIVAS UNIDADES
    document.forms["nuevaProgramacion"].elements["id_oficina"].addEventListener("change", function () {
        if (this.value !== 0) {
            $.post("unidad_por_oficina.htm", {id_oficina: this.value}, function (response) {
                document.forms["nuevaProgramacion"].elements["id_unidad"].innerHTML = response;
            }).fail(function () {
                alert("No se pudo cargar la unidad");
            });
        } else {
            document.forms["nuevaProgramacion"].elements["id_unidad"].innerHTML = "";
        }
    }, false);
    document.forms["editarProgramacion"].elements["id_oficina"].addEventListener("change", function () {
        if (this.value !== 0) {
            $.post("unidad_por_oficina.htm", {id_oficina: this.value}, function (response) {
                document.forms["editarProgramacion"].elements["id_unidad"].innerHTML = response;
            }).fail(function () {
                alert("No se pudo cargar la unidad");
            });
        } else {
            document.forms["editarProgramacion"].elements["id_unidad"].innerHTML = "";
        }
    }, false);
}

function submitFormNuevaProgramacion() {
    document.nuevaProgramacion.addEventListener("submit", function (e) {
        e.preventDefault();
        this.id_oficina.removeAttribute("disabled");
        this.submit();
    }, false);
}

function filtros() {
    document.forms["programacion-filtro-form"].addEventListener("submit", function (e) {
        e.preventDefault();
        $.post("filtroProgramacion.htm", $(this).serialize(), function (response) {
            var json = JSON.parse(response);
            if (typeof json.SESIONFINALIZADA === "undefined") {
                $("section .lista-cuerpo").html(json.PROGRAMACIONROW);
                $("#fecha").html(json.FECHA);
                $("#leyenda > a.proceso span:last-child").html(json.PROCESO);
                $("#leyenda > a.espera span:last-child").html(json.PENDIENTE);
                $("#leyenda > a.finalizado span:last-child").html(json.FINALIZADO);
                $("#leyenda > a.cancelado span:last-child").html(json.CANCELADO);
                evaluacion();
                asignarListenerBtnEspera();
                $(".pop-atencion").addClass("oculto");
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
    $.post("filtroProgramacion.htm", {estado: estate, fecha: now}, function (response) {
        var json = JSON.parse(response);
        if (typeof json.SESIONFINALIZADA === "undefined") {
            $("section .lista-cuerpo").html(json.PROGRAMACIONROW);
            $("#fecha").html(json.FECHA);
            $("#leyenda > a.proceso span:last-child").html(json.PROCESO);
            $("#leyenda > a.espera span:last-child").html(json.PENDIENTE);
            $("#leyenda > a.finalizado span:last-child").html(json.FINALIZADO);
            $("#leyenda > a.cancelado span:last-child").html(json.CANCELADO);
            evaluacion();
            asignarListenerBtnEspera();
            $(".pop-atencion").addClass("oculto");
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

function lanzarAtajos() {
    document.onkeydown = managerLanzarmantenimientos;
    function managerLanzarmantenimientos(e) {
        if (e.altKey) {// RETORNARA TRUE SI LA TECLA ALT ESTABA PULSADA CUANDO SE PRODUJO EL EVENTO
            if (e.keyCode === N_id) {
                $(ventanasOSI.nuevo).fadeIn(500);
                $(ventanasOSI.cortina).fadeIn(500);
                document.forms["nuevaProgramacion"].elements["fecha_progra"].focus();
                 sizeFormFlotanteCuerpo(1);
            }
            if (e.keyCode === M_id) {
                if (existeFilaSeleccionada()) {
                    $(ventanasOSI.modificar).fadeIn(500);
                    $(ventanasOSI.cortina).fadeIn(500);
                    PedirDatos();
                    sizeFormFlotanteCuerpo(2);//0 para darle altura al cuerpo del formulario editar registro
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
                document.forms["form-prgramacion_atencion"].reset();
                document.forms["editarProgramacion"].reset();

            }
        }
    }
}

//realiza un peticion post al servidor y obtiene un json con los detalles de la programacion
function PedirDatos() {
    var id_programacion = $("div.select > div:first-child").text();
    $.post("detallesProgramacion.htm", {id_progra: id_programacion}, function (response) {
        llenarCajas(JSON.parse(response));
    });
}

function llenarCajas(detalles) {
    document.forms["editarProgramacion"].elements["id_progra"].value = detalles.id_progra;
    document.forms["editarProgramacion"].elements["fecha_progra"].value = detalles.fecha_progra;
    document.forms["editarProgramacion"].elements["empleado"].value = detalles.id_pers;
    document.forms["editarProgramacion"].elements["id_progra_tarea"].value = detalles.id_progra_tarea;
    if (detalles.progra_tarea_deta === null) {
        document.forms["editarProgramacion"].elements["progra_tarea_deta"].value = "";
    } else {
        document.forms["editarProgramacion"].elements["progra_tarea_deta"].value = detalles.progra_tarea_deta;
    }
    document.forms["editarProgramacion"].elements["id_oficina"].value = detalles.id_ofic;
    if (document.editarProgramacion.id_oficina.value !== "0") {
        $.post("unidad_por_oficina.htm", {id_oficina: document.editarProgramacion.id_oficina.value}, function (response) {
            document.forms["editarProgramacion"].elements["id_unidad"].innerHTML = response;
            document.forms["editarProgramacion"].elements["id_unidad"].value = detalles.id_unidad;
        }).fail(function () {
            alert("No se pudo cargar la unidad");
        });
    }

    document.forms["editarProgramacion"].elements["encargado"].value = detalles.encargado;
    if (detalles.comentario === null) {
        document.forms["editarProgramacion"].elements["comentario"].value = "";
    } else {
        document.forms["editarProgramacion"].elements["comentario"].value = detalles.comentario;
    }
    var atencionHTML = "";
    for (var A in detalles.atencion) {
        atencionHTML += "<li class='list-group-item'>";
        atencionHTML += detalles.atencion[A];
        atencionHTML += "</li>";
    }
    //llenamos la lista con la atencion enviada
    $("#collapseOne ul.list-group").html(atencionHTML);
    document.forms["editarProgramacion"].elements["id_progra_tarea"].focus();
}

function submit_Form_Editar_Programacion() {
    document.forms["editarProgramacion"].addEventListener("submit", function (e) {
        e.preventDefault();
        document.forms["editarProgramacion"].elements["id_progra"].removeAttribute("disabled");
        this.submit();
    }, false);
}

//si presionas este  input de tipo radio se marcaran todolos los inputs atencion
function seleccionarTodaAtencion() {
    document.getElementById("toda_atencion").addEventListener("change", function () {
        if (this.checked) {
            var atencion = document.querySelectorAll("#form-prgramacion_atencion .formulario-flotante-cuerpo .list-group-item input[type='checkbox']");
            for (var i = 0; i < atencion.length; i++) {
                atencion[ i ].checked = true;
            }
        }

    }, false);

    function seleccionInputAtencion() {
        $("#form-prgramacion_atencion .formulario-flotante-cuerpo .list-group-item input[type='checkbox']").bind("change", function () {
            var todosSeleccionados;
            var atencion = document.querySelectorAll("#form-prgramacion_atencion .formulario-flotante-cuerpo .list-group-item input[type='checkbox']");
            for (var i = 0; i < atencion.length; i++) {
                if (atencion[i].checked) {
                    todosSeleccionados = true;
                } else {
                    todosSeleccionados = false;
                    break;
                }
            }
            if (todosSeleccionados) {
                document.getElementById("toda_atencion").setAttribute("checked", "checked");
                document.getElementById("toda_atencion").checked = true;
            } else {
                document.getElementById("toda_atencion").removeAttribute("checked");
                document.getElementById("toda_atencion").checked = false;
            }
        });
    }

    seleccionInputAtencion();
}

//ESTAS FUNCIONES MODIFICAN EL CONTENIDO PARA QUE SE PUEDAN MOSTRAR CORRECTAMNETE EN RESOLUCIONES MENORE DE 1366PX
function cambiarContenido() {
    if (window.screen.width <= 1366) {
        document.querySelector(" section > div:first-child .lista-cabezera > div.col:nth-child(2) h5").innerHTML = "<span class='fa fa-calendar'></span> Fecha";
        document.querySelector(" section > div:first-child .lista-cabezera > div.col:nth-child(3) h5").innerHTML = "<span class='fa fa-user'></span> Programador";

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

    $.post("atencionEnviadaProgramacion.htm", {id: id_incidencia}, function (response) {
        let atencion = JSON.parse(response);
        if (typeof atencion.SIONFINALIZADA === "undefined") {
            if (atencion.per_enviado === "") {
                pop.children[1].innerHTML = "Aun no se designo atencion";
            } else {
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

openCloseFormularios();
enviarFormularioAtencion();
submitFormNuevaProgramacion();
FinalizarTarea();
CancelarTarea();
cargarComboBoxUnidad();
filtros();
lanzarAtajos();
submit_Form_Editar_Programacion();
seleccionarTodaAtencion();
cambiarContenido();
desplegarFiltro();