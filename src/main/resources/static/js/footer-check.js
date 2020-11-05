const vph = Math.max(document.documentElement.clientHeight || 0, window.innerHeight || 0);
const ftr = document.getElementById("footer");
const ft = ftr.offsetTop;
const fh = ftr.offsetHeight;
if (ft+fh<vph){
    ftr.style.height=vph-ft+'px'
}