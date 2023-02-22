const Esc_id = 27;// CODIGO DE TECLADO DE LA TECLA Esc  
const N_id = 78;// CODIGO DE TECLADO DE LA TECLA N  nuevo
const M_id = 77;// CODIGO DE TECLADO DE LA TECLA M  modificar

ventanasOSI = {
    nuevo: document.getElementById("formulario-nuevo-registro"),
    modificar: null,
    cortina: document.getElementById("cortina")
};

function openCloseForm() {
    document.getElementById("btn-new-registro").addEventListener("click", function () {
        $("#cortina").fadeIn(500);
        $("#formulario-nuevo-registro").fadeIn(500);
        document.persona.apellidos.focus();
        sizeFormFlotanteCuerpo(1);//1 para darle altura al cuerpo del formulario nuevo registro
    });
    document.getElementById("btn-edit-registro").addEventListener("click", function () {
        if (existeFilaSeleccionada()) {
            $("#cortina").fadeIn(500);
            $("#formulario-nuevo-registro").fadeIn(500);
            sizeFormFlotanteCuerpo(1);//1 para darle altura al cuerpo del formulario nuevo registro
            pedirDetallesPersona();
            document.persona.apellidos.focus();
        } else {
            alert("Seleccione una Fila!");
        }
    });

//PEDIR DATOS PRACTICANTES solo practicantes
    document.querySelector("#contendor-datos-practicante div:first-child").addEventListener("click", function () {
        //comprobamos si ya esta desplegado el formulario a partir de la clase fa-caret-up
        if (this.children[2].classList.contains("fa-caret-up")) {
            $("#contendor-datos-practicante-cuerpo").slideToggle(500);
            document.forms["form_programacion_practicantes"].reset();
            this.children[2].classList.replace("fa-caret-up", "fa-caret-down");
        } else {
            if (existeFilaSeleccionada()) {
                var fila = document.querySelector("section .lista-cuerpo div.row.select");
                var codigo = document.querySelector("section .lista-cuerpo div.row.select > div:first-child p").innerHTML;
                $.post("detallesPracticantes.htm", {id: codigo}, function (response) {

                    var detalles = JSON.parse(response);
                    if (detalles.SESIONFINALIZADA === false) {
                        if (detalles.ISPRACTICANTE === true) {
                            if ($("#contendor-datos-practicante div:first-child").children()[2].classList.contains("fa-caret-down")) {
                                $("#contendor-datos-practicante-cuerpo").slideToggle(500);
                                $("#contendor-datos-practicante div:first-child").children()[2].classList.replace("fa-caret-down", "fa-caret-up");
                                document.forms["form_programacion_practicantes"].elements["id_persona"].value = detalles.id_persona;
                                document.forms["form_programacion_practicantes"].elements["nombres"].value = detalles.nombres;
                                document.forms["form_programacion_practicantes"].elements["apellidos"].value = detalles.apellidos;
                                document.forms["form_programacion_practicantes"].elements["horas"].value = detalles.horas;
                                document.forms["form_programacion_practicantes"].elements["horas"].focus();
                                document.forms["form_programacion_practicantes"].elements["dias"].value = detalles.dias;
                                $("#menu").animate({scrollTop: "200"}, 1000);
                            }
                        } else {
                            alert("No es un practicante!");
                        }
                    } else {
                        window.location.href = "sesion.htm";
                    }
                }).fail(function () {
                    alert("ocurrio un error!");
                });

            } else {
                alert("Seleccione una Fila!");
            }
        }

    });


    document.getElementById("btn-delete-registro").addEventListener("click", function () {
        if (existeFilaSeleccionada()) {
            var rpta = window.confirm("Estas seguro?");
            if (rpta === true) {
                elimiarPersona();
            }
        } else {
            alert(function () {
                alert("ocurrio un error!");
            });
        }
    });
    $("#formulario-nuevo-registro .formulario-flotante-cabezera div button").click(function () {
        $("#formulario-nuevo-registro").fadeOut(500);
        $("#cortina").fadeOut(500);
        document.persona.reset();
        document.getElementById("diaLunes").setAttribute("checked", "checked");
        $("#diaMartes").removeAttr("checked");
        $("#diaMiercoles").removeAttr("checked");
        $("#diaJueves").removeAttr("checked");
        $("#diaViernes").removeAttr("checked");
        $("#formulario-nuevo-registro .formulario-flotante-cuerpo").scrollTop(0);
    });
    $("#form-programa-practicante .formulario-flotante-cabezera div button").click(function () {
        $("#form-programa-practicante").fadeOut(500);
        $("#cortina").fadeOut(500);
        document.persona.reset();
    });
}

function sizeFormFlotanteCuerpo(formulario) {
    if (formulario === 1) {
        var altura = document.getElementById("formulario-nuevo-registro").offsetHeight;
        document.querySelector("#formulario-nuevo-registro .formulario-flotante-cuerpo").style.height = (altura - 100) + "px";
    }
}

function elimiarPersona() {
    var codigo = document.querySelector("section .lista-cuerpo div.row.select > div:first-child p").innerHTML;
    $.post("eliminarPersona.htm", {id: codigo}, function (response) {
        var json = JSON.parse(response);
        if (json.SESIONFINALIZADA === false) {
            document.querySelector("section .lista-cuerpo").innerHTML = json.RESPUESTA;
            evaluacion();
        } else {
            window.location.href = "sesion.htm";
        }
    }).fail(function () {
        alert("ocurrio un error!");
    });
}


function pedirDetallesPersona() {
    var fila = document.querySelector("section .lista-cuerpo div.row.select");
    var codigo = document.querySelector("section .lista-cuerpo div.row.select > div:first-child p").innerHTML;
    $.post("detallesPersona.htm", {id: codigo}, function (response) {

        var detalles = JSON.parse(response);
        if (detalles.SESIONFINALIZADA === false) {
            llenarFormulario(detalles);
        } else {
            window.location.href = "sesion.htm";
        }
    }).fail(function () {
        alert("ocurrio un error!");
    });
}
function llenarFormulario(detalles) {
    document.forms["persona"].elements["id_persona"].value = detalles.id_persona;
    document.forms["persona"].elements["apellidos"].value = detalles.apellidos;
    document.forms["persona"].elements["nombres"].value = detalles.nombres;
    document.forms["persona"].elements["dni"].value = detalles.dni;
    document.forms["persona"].elements["cargo"].value = detalles.cargo;
    if (detalles.direccion !== "null") {
        document.forms["persona"].elements["direccion"].value = detalles.direccion;
    }
    if (detalles.telefono !== "null") {
        document.forms["persona"].elements["telefono"].value = detalles.telefono;
    }
    if (detalles.correo !== "null") {
        document.forms["persona"].elements["correo"].value = detalles.correo;
    }
    if (detalles.sexo === "M") {
        document.forms["persona"].elements["sexo"].value = "M";
    } else {
        if (detalles.sexo === "F") {
            document.forms["persona"].elements["sexo"].value = "F";
        } else {
            document.forms["persona"].elements["sexo"].value = "O";
        }
    }

    $("#diaLunes").removeAttr("checked");
    for (var h in detalles.horario) {
        switch (detalles.horario[h]) {
            case 1:
                document.getElementById("diaLunes").setAttribute("checked", "checked");
                break;
            case 2:
                document.getElementById("diaMartes").setAttribute("checked", "checked");
                break;
            case 3:
                document.getElementById("diaMiercoles").setAttribute("checked", "checked");
                break;
            case 4:
                document.getElementById("diaJueves").setAttribute("checked", "checked");
                break;
            case 5:
                document.getElementById("diaViernes").setAttribute("checked", "checked");
                break;
        }
    }
}

function submmitFormulario() {
    document.forms["persona"].addEventListener("submit", function (e) {
        e.preventDefault();
        if (document.forms["persona"].elements["id_persona"].value !== "") {
            document.forms["persona"].elements["id_persona"].removeAttribute("disabled");
            document.forms["persona"].submit();
        } else {
            document.forms["persona"].submit();
        }
    }, false);

}
function submmitFormularioProgramacionPracticante() {
    document.forms["form_programacion_practicantes"].addEventListener("submit", function (e) {
        e.preventDefault();
        document.forms["form_programacion_practicantes"].elements["id_persona"].removeAttribute("disabled");
        document.forms["form_programacion_practicantes"].submit();

    }, false);

}

function lanzarAtajos() {
    document.onkeydown = managerLanzarmantenimientos;
    function managerLanzarmantenimientos(e) {
        if (e.altKey) {// RETORNARA TRUE SI LA TECLA ALT ESTABA PULSADA CUANDO SE PRODUJO EL EVENTO
            if (e.keyCode === N_id) {
                $(ventanasOSI.nuevo).fadeIn(500);
                $(ventanasOSI.cortina).fadeIn(500);
                document.persona.apellidos.focus();
                sizeFormFlotanteCuerpo(1);//1 para darle altura al cuerpo del formulario nuevo registro
            }
            if (e.keyCode === M_id) {
                if (existeFilaSeleccionada()) {
                    $(ventanasOSI.nuevo).fadeIn(500);
                    $(ventanasOSI.cortina).fadeIn(500);
                    sizeFormFlotanteCuerpo(1);//1 para darle altura al cuerpo del formulario nuevo registro
                    pedirDetallesPersona();
                    document.persona.apellidos.focus();
                } else {
                    alert("Seleccione una Fila!");
                }
            }

        } else {
            if (e.keyCode === Esc_id) {
                $(ventanasOSI.nuevo).fadeOut(500);
                $(ventanasOSI.cortina).fadeOut(500);
                document.persona.reset();
                document.getElementById("diaLunes").setAttribute("checked", "checked");
                $("#diaMartes").removeAttr("checked");
                $("#diaMiercoles").removeAttr("checked");
                $("#diaJueves").removeAttr("checked");
                $("#diaViernes").removeAttr("checked");
                $("#formulario-nuevo-registro .formulario-flotante-cuerpo").scrollTop(0);

                //cerramos ficha de asistencia
                $("#form-ficha-asistencia").fadeOut(500);
                document.forms["form-ficha-asistencia"].reset();
                document.forms["form-ficha-asistencia"].elements["id_pers"].setAttribute("disabled", "disabled");
                document.forms["form-ficha-asistencia"].elements["nombres"].setAttribute("disabled", "disabled");
                document.getElementById("pop-menu").classList.add("oculto");

                //contramos en formulario datosPracticante
                $("#contendor-datos-practicante-cuerpo").slideUp(500);
                document.forms["form_programacion_practicantes"].reset();
                document.querySelector("#contendor-datos-practicante div:first-child").children[2].classList.replace("fa-caret-up", "fa-caret-down");
            }
        }
    }
}

//PARA TODO LO RELACIONADO A LA GENERACION DE FICHAS DE ASISTENCIAS DE PRACTICANTES PDF
function EventosParaClickDerecho(manejador1, manejador2, manejador3, manejador4, manejador5) {

    //MUESTRA EL #POP-MENU
    if (manejador1) {
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

    if (manejador2) {
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

    if (manejador3) {
        //ABRE EL PANEL PARA GENERAR FICHA ASISTENCIA PRACTICANTE
        document.getElementById("btn-historia-asist-alu").addEventListener("click", function () {
            var codigo = document.querySelector("section .lista-cuerpo div.row.select > div:first-child p").innerHTML;
            //comprobamos que sea un practicante un no un contratado
            $.post("esPracticante.htm", {id: codigo}, function (response) {
                var objJSON = JSON.parse(response);
                if (typeof objJSON.SESIONFINALIZADA !== "undefined") {
                    window.location.assign("sesion.htm");
                } else if (objJSON.ESPRACTICANTE === true) {
                    var filaSeleccionada = $(".row.select").children();
                    document.forms["form-ficha-asistencia"].elements["id_pers"].value = $(filaSeleccionada[0]).text();
                    document.forms["form-ficha-asistencia"].elements["nombres"].value = $(filaSeleccionada[1]).text() + " " + $(filaSeleccionada[2]).text();

                    $("#form-ficha-asistencia").fadeIn(500);
                    $("#cortina").fadeIn(500);
                    document.forms["form-ficha-asistencia"].elements["fecha"].focus();
                } else {
                    alert("No es Practicante!!!");
                    document.querySelector("#pop-menu").classList.add("oculto");
                }
            });

        }, false);
    }

    if (manejador4) {
        //CIERRA EL APNEL PARA GENERAR FICHA ASISTENCIA PRACTICANTE
        document.querySelector("#form-ficha-asistencia .formulario-flotante-cabezera > div:last-child button").addEventListener("click", function () {
            $("#cortina").fadeOut(500);
            $("#form-ficha-asistencia").fadeOut(500);
            document.forms["form-ficha-asistencia"].reset();
            document.forms["form-ficha-asistencia"].elements["id_pers"].setAttribute("disabled", "disabled");
            document.forms["form-ficha-asistencia"].elements["nombres"].setAttribute("disabled", "disabled");
        }, false);
    }

    if (manejador5) {
        //AL ENVIAR EL FORMULARIO DETIENE EL PROCESO DESBLOQUEA LAS CASILLAS Y RECIEN CONTINUA EL PROCESO DE ENVIADO DE FORMULARIO
        document.forms["form-ficha-asistencia"].addEventListener("submit", function (e) {
            e.preventDefault();
            document.forms["form-ficha-asistencia"].elements["id_pers"].removeAttribute("disabled");
            document.forms["form-ficha-asistencia"].elements["nombres"].removeAttribute("disabled");
            this.submit();
            $("#cortina").fadeOut(500);
            $("#form-ficha-asistencia").fadeOut(500);
            document.forms["form-ficha-asistencia"].elements["id_pers"].setAttribute("disabled", "disabled");
            document.forms["form-ficha-asistencia"].elements["nombres"].setAttribute("disabled", "disabled");
        }, false);
    }
}

function filtros() {
    document.forms["per_filto_cargo"].addEventListener("submit", function (e) {
        e.preventDefault();
        $.post("filtroPersonaPorcargo.htm", $(this).serialize(), function (response) {
            var json = JSON.parse(response);
            if (typeof json.SESIONFINALIZADA === "undefined") {
                $("section .lista-cuerpo").html(json.perHTML);
                evaluacion();
                EventosParaClickDerecho(true,false,false,false,false);
            } else {
                window.location.assign("sesion.htm");
            }
        });
    }, false);
}
submmitFormulario();
openCloseForm();
submmitFormularioProgramacionPracticante();
lanzarAtajos();
EventosParaClickDerecho(true,true,true,true,true);
filtros();



