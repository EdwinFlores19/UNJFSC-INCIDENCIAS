botonesFS = {
    btnActivar: document.getElementById("btn-active-fullscreen"),
    btnDesactivar: document.getElementById("btn-disable-fullscreen")
};

function startFullScreen() {
    const pantallaCompletaSoportada = document.fullscreenEnabled || document.webkitFullscreenEnabled || document.mozFullScreenEnabled;
    botonesFS.btnActivar.addEventListener("click", function (e) {
        e.preventDefault();
        console.log("primera fincion");
        if (pantallaCompletaSoportada) {
            launchFullScreen(document.documentElement);
        }
    }, false);
    botonesFS.btnDesactivar.addEventListener("click", function (e) {
        e.preventDefault();
        if (pantallaCompletaSoportada) {
            cancelFullscreen();
        }
    }, false);

}
function launchFullScreen(elemento) {
    if (elemento.requestFullScreen) {
        elemento.requestFullScreen();
    } else if (elemento.mozRequestFullScreen) {
        console.log("ejecutar en moz");
        elemento.mozRequestFullScreen();
    } else if (elemento.webkitRequestFullScreen) {
        elemento.webkitRequestFullScreen();
    }
    sizeLista(1);   
}
function cancelFullscreen() {
    if (document.cancelFullScreen) {
        document.cancelFullScreen();
    } else if (document.mozCancelFullScreen) {
        document.mozCancelFullScreen();
    } else if (document.webkitCancelFullScreen) {
        document.webkitCancelFullScreen();
    }
    sizeLista(1);
}
startFullScreen();


