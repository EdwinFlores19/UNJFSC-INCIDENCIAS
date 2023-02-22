
const Esc_id = 27;// CODIGO DE TECLADO DE LA TECLA Esc  
const N_id = 78;// CODIGO DE TECLADO DE LA TECLA N  nuevo
const M_id = 77;// CODIGO DE TECLADO DE LA TECLA M  modificar

ventanasOSI = {
    nuevo: null,
    modificar: null,
    cortina: null
};

function lanzarAtajos() {
    document.onkeydown = managerLanzarmantenimientos;
    function managerLanzarmantenimientos(e) {
        if (e.altKey) {// RETORNARA TRUE SI LA TECLA ALT ESTABA PULSADA CUANDO SE PRODUJO EL EVENTO
            if (e.keyCode === N_id) {
                $(ventanasOSI.nuevo).fadeIn(500);
                $(ventanasOSI.cortina).fadeIn(500);
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
                $(ventanasOSI.cortina).fadeOut(500);
            }
        }
    }
}

lanzarAtajos();
