function disabletext(e) {
    e.preventDefault();
}
function reEnable(e) {
    e.preventDefault();
}
document.onselectstart = new Function("return false");




