/*ESTABLECER TAMAÑO DEL BODY*/
function SizeBody() {
    var altoventana = window.innerHeight;
    var anchoventana = window.innerWidth;
    document.body.style.height = altoventana + "px";
    document.body.style.width = anchoventana + "px";
}

/*CERRA Y HABRIR NAV*/
function openCloseNav() {
    if (document.querySelector("#cabezera #btn-cerrar-nav") !== null) {
        document.querySelector("#cabezera #btn-cerrar-nav").addEventListener("click", function () {
            if (document.getElementById("btn-cerrar-nav").className === "fa fa-remove") {
                if (window.screen.width <= 1366) {
                    document.querySelector("nav").style.left = "-20%";
                } else {
                    document.querySelector("nav").style.left = "-18%";
                }
                document.querySelector("#cuerpo").style.left = "0%";
                document.querySelector("#cuerpo").style.width = "100%";
                document.getElementById("btn-cerrar-nav").className = "fa fa-bars";
                document.getElementById("btn-cerrar-nav").title = "Desplegar Menu";
                //document.getElementsByTagName("footer")[0].style.display = "none";
                sizeLista(1);
            } else {
                document.querySelector("nav").style.left = "0%";
                if (window.screen.width <= 1366) {
                    document.querySelector("#cuerpo").style.left = "20%";
                    document.querySelector("#cuerpo").style.width = "80%";
                } else {
                    document.querySelector("#cuerpo").style.left = "18%";
                    document.querySelector("#cuerpo").style.width = "82%";
                }
                document.getElementById("btn-cerrar-nav").className = "fa fa-remove";
                document.getElementById("btn-cerrar-nav").title = "Contraer Menu";
                //document.getElementsByTagName("footer")[0].style.display = "block";
                sizeLista(2);
            }
        }, false);
    }
}
/*ALTO INICIAL DE LA .LISTA-CABEzERA*/
if (document.querySelector(".lista-cabezera") !== null) {
    listaCabezaHeight = document.querySelector(".lista-cabezera").offsetHeight;
}
/*SI LA LISTA EXISTE ESTABLECER EL TAMAÑO DE LA MISMA*/
function sizeLista(opcion) {
    if (document.querySelector(".lista-cuerpo") !== null) {
        if (opcion === 1) {
            console.log("siseLista opcion 1...");
            var cuerpoHeight = document.getElementById("cuerpo").offsetHeight - 55;
            var headerHeight = document.getElementById("cabezera").offsetHeight;
            var footerHeight = document.getElementsByTagName("footer")[0].offsetHeight + 10;
            document.querySelector(".lista-cuerpo").style.height = cuerpoHeight - headerHeight - footerHeight - 54 + "px";
        } else {
            var cuerpoHeight = document.getElementById("cuerpo").offsetHeight - 55;
            var headerHeight = document.getElementById("cabezera").offsetHeight;
            var footerHeight = document.getElementsByTagName("footer")[0].offsetHeight + 10;
            document.querySelector(".lista-cuerpo").style.height = cuerpoHeight - headerHeight - footerHeight - listaCabezaHeight + "px";
        }
    }
}

//PREVENIR LA ACCION DEL CLICK DERECHO 
document.documentElement.addEventListener("contextmenu", function (e) {
    e.preventDefault();
}, false);


SizeBody();
openCloseNav();
sizeLista(1);



