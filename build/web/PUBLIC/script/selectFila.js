//ESTA FUNCION PERMITE SELECCIONAR FILAS
function evaluacion() {
    $("section .lista-cuerpo div.row").click(function (e) {
        var elemento = e.target;
        if (elemento.className !== "fa fa-refresh" && elemento.className !== "btn btn-danger" && elemento.className !== "btn btn-warning"
                && elemento.className !== "fa fa-check" && elemento.className !== "btn btn-info" && elemento.className !== "btn btn-success" && elemento.className !== "fa fa-warning"
                && elemento.className !== "btn btn-outline-warning" && elemento.className !== "fa fa-times-circle-o") {
            if (elemento.className === "row select") {
                selectRegistro(elemento, 0, e);
            } else {
                if (elemento.className === "row") {
                    selectRegistro(elemento, 1, e);
                } else {
                    var elemento2 = elemento.parentNode;
                    if (elemento2.className === "row select") {
                        selectRegistro(elemento2, 0, e);
                    } else {
                        if (elemento2.className === "row") {
                            selectRegistro(elemento2, 1, e);
                        } else {
                            var elemento3 = elemento2.parentNode;
                            if (elemento3.className === "row select") {
                                selectRegistro(elemento3, 0, e);
                            } else {
                                selectRegistro(elemento3, 1, e);
                            }
                        }
                    }
                }
            }
        }
    });
}

function selectRegistro(elemento, opcion, e) {
    if (opcion === 1) {
        //1 indica que hay que ponerle la clase .select
        $("section .lista-cuerpo div.row").removeClass("select");
        elemento.className = "row select";
        
        //ESTE ES UN POPMENU FLOTANTE QUE TANSOLO SE MUESTRA PARA INCIDECIAS.HTM Y EL METODO     mostrarAtencionEnviada ES LAMADO DESDE INCIDENCIA.JS
       // if (document.getElementById("pop-atencion") !== null) {
       if(document.querySelectorAll(".pop-atencion").length!==0){
       $(".pop-atencion").css({display:"none"});
            mostrarAtencionEnviada(e);
        }
     //   }

    } else {
        
           //POP FLOTANTE SOLO SI EXSITE SE LO REMUEVE
        //  if (document.getElementById("pop-atencion") !== null) {
        
        if( document.querySelector(".row.select .pop-atencion")!==null){
              document.querySelector(".row.select .pop-atencion").style.display="none";
        }
         
       // }
       // 
        //Quitamos la seleccion o mejor dicho la clase .select
        elemento.className = "row";
     
    }

}
function existeFilaSeleccionada() {
    var bandera = false;
    var filas = document.querySelectorAll("#cuerpo section .lista-cuerpo > div.row");
    for (var i = 0; i < filas.length; i++) {
        if (filas[i].className === "row select") {
            bandera = true;
            break;
        }
    }
    return bandera;
}
evaluacion();


