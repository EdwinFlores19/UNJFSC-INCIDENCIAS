const Esc_id = 27;// CODIGO DE TECLADO DE LA TECLA Esc  
const N_id = 78;// CODIGO DE TECLADO DE LA TECLA N  nuevo
const M_id = 77;// CODIGO DE TECLADO DE LA TECLA M  modificar

ventanasOSI = {
    nuevo: document.getElementById("formulario-nuevo-registro"),
    modificar: document.getElementById("formulario-editar-registro"),
    cortina: document.getElementById("cortina")
};

function openCloseFormularios() {

    //ABRE FORMULARIO NUEVA ASISTENCIA
    document.getElementById("btn-new-registro").addEventListener("click", function () {
        $("#cortina").fadeIn(500);
        $("#formulario-nuevo-registro").fadeIn(500);
        sizeFormFlotanteCuerpo(1);//1 para darle altura al cuerpo del formulario nuevo registro
    });

    //ABRE FORMULARIO EDITAR ASISTENCIA
    document.getElementById("btn-edit-registro").addEventListener("click", function () {
        if (existeFilaSeleccionada()) {
            pedirDetalles();
        } else {
            alert("Seleccione una Fila!");
        }
    });


    //CIERRA FORMULARIO NUEVA ASISTENCIAS
    $("#formulario-nuevo-registro .formulario-flotante-cabezera div button").click(function () {
        $("#formulario-nuevo-registro").fadeOut(500);
        $("#cortina").fadeOut(500);
    });

    //CIERRA FORMULARIO EDITAR ASISTENCIAS
    $("#formulario-editar-registro .formulario-flotante-cabezera div button").click(function () {
        $("#formulario-editar-registro").fadeOut(500);
        $("#cortina").fadeOut(500);
        document.forms["formulario-editar-registro"].reset();
    });
}

function pedirDetalles() {
    var fecha = document.querySelector(".lista-cuerpo .row.select").children[0].children[0].innerText;
    var nombre = document.querySelector(".lista-cuerpo .row.select").children[1].children[0].innerText;
    $.post("detallesAsistencia.htm", {date: fecha, name: nombre}, function (response) {
        var detalles = JSON.parse(response);
        if (typeof detalles.SesionFinalizada === "undefined") {
            document.forms["formulario-editar-registro"].elements["fecha"].value = detalles.FECHA;
            document.forms["formulario-editar-registro"].elements["hora_de_ingreso"].value = detalles.H_I;
            document.forms["formulario-editar-registro"].elements["hora_de_salida"].value = detalles.H_S;
            document.forms["formulario-editar-registro"].elements["id_prac"].value = detalles.ID_PRAC;
            document.forms["formulario-editar-registro"].elements["practicante"].value = detalles.NAME_PRAC;
            document.forms["formulario-editar-registro"].elements["observacion"].value = detalles.OBSERVACION;
            $("#cortina").fadeIn(500);
            $("#formulario-editar-registro").fadeIn(500);
            document.forms["formulario-editar-registro"].elements["hora_de_ingreso"].focus();
        } else {
            window.location.assign("sesion.htm");
        }
    }).fail(function () {
        alert("Ups Ocurrio un error!");
    });
}

function sizeFormFlotanteCuerpo(formulario) {
    if (formulario === 1) {
        var altura = document.getElementById("formulario-nuevo-registro").offsetHeight;
        document.querySelector("#formulario-nuevo-registro .formulario-flotante-cuerpo").style.height = (altura - 100) + "px";//ALTO CUERPO= ALTUTA-50 DE CABESERA-50 DE PIE
    }
}

function submitFormEditarAsistencia() {
    document.forms["formulario-editar-registro"].addEventListener("submit", function (e) {
        e.preventDefault();
        this.elements["fecha"].removeAttribute("disabled");
        this.elements["id_prac"].removeAttribute("disabled");
        this.submit();
    }, false);

}
function lanzarAtajos() {
    document.onkeydown = managerLanzarmantenimientos;
    function managerLanzarmantenimientos(e) {
        if (e.altKey) {// RETORNARA TRUE SI LA TECLA ALT ESTABA PULSADA CUANDO SE PRODUJO EL EVENTO
            if (e.keyCode === N_id) {
                $(ventanasOSI.nuevo).fadeIn(500);
                $(ventanasOSI.cortina).fadeIn(500);
                sizeFormFlotanteCuerpo(1);//1 para darle altura al cuerpo del formulario nuevo registro
            }
            if (e.keyCode === M_id) {
                if (existeFilaSeleccionada()) {
                    $(ventanasOSI.modificar).fadeIn(500);
                    $(ventanasOSI.cortina).fadeIn(500);
                    pedirDetalles();
                } else {
                    alert("Seleccione una Fila!");
                }
            }

        } else {
            if (e.keyCode === Esc_id) {
                $(ventanasOSI.nuevo).fadeOut(500);
                $(ventanasOSI.modificar).fadeOut(500);
                $(ventanasOSI.cortina).fadeOut(500);
                document.forms["formulario-editar-registro"].reset();
                document.forms["formulario-nuevo-registro"].reset();
            }
        }
    }
}

function filtros() {
    document.forms["filtro-asistencia-form"].addEventListener("submit", function (e) {
        e.preventDefault();
        $.post("filtroAsistenciaPracticante.htm", $(this).serialize(), function (response) {
            var json = JSON.parse(response);
            if (typeof json.SESIONFINALIZADA === "undefined") {
                $("section .lista-cuerpo").html(json.asistenciaHTML);
                evaluacion();
            } else {
                window.location.assign("sesion.htm");
            }
        });
    }, false);
}

function seleccionarTodosPracticantes() {
    document.getElementById("todos-precticantes").addEventListener("change", function () {
        if (this.checked) {
            var atencion = document.querySelectorAll("#formulario-nuevo-registro  .formulario-flotante-cuerpo .list-group-item input[type='checkbox']");
            for (var i = 0; i < atencion.length; i++) {
                atencion[ i ].checked=true;
            }
        }

    }, false);

    function seleccionInputAtencion() {
        $("#formulario-nuevo-registro  .formulario-flotante-cuerpo .list-group-item input[type='checkbox']").bind("change", function () {
            var todosSeleccionados;
            var atencion = document.querySelectorAll("#formulario-nuevo-registro  .formulario-flotante-cuerpo .list-group-item input[type='checkbox']");
            for (var i = 0; i < atencion.length; i++) {
                if (atencion[i].checked) {
                    todosSeleccionados = true;
                } else {
                    todosSeleccionados = false;
                    break;
                }
            }
            if (todosSeleccionados) {
                document.getElementById("todos-precticantes").setAttribute("checked", "checked");
                document.getElementById("todos-precticantes").checked = true;
            } else {
                document.getElementById("todos-precticantes").removeAttribute("checked");
                document.getElementById("todos-precticantes").checked = false;
            }
        });
    }

    seleccionInputAtencion();
}

// LAS SIGUIENTES FUNCIONES AYUDAN A CAMBIAR EL CONTENIDO PARA QUE SE AJUSTEN MEJOR CUANDO LA RESOLUCION DE LA PANTALLA ES MENOR A 1366PX
function cambiarContenido() {
    if (window.screen.width <= 1366) {
        document.querySelector("section .lista-cabezera > div.col:nth-child(3) h5").innerHTML = "<span class='fa fa-clock-o'></span> Hora Ingreso";
        
        document.querySelector("#formulario-nuevo-registro .formulario-flotante-cuerpo > div.row:nth-child(4) >div:first-child h3").style.fontSize="20px";
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

openCloseFormularios();
submitFormEditarAsistencia();
lanzarAtajos();
filtros();
seleccionarTodosPracticantes();
cambiarContenido();
desplegarFiltro();


