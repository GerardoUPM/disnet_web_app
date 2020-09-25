let userNodes = JSON.parse(JSON.stringify(preNodes))
let idName = {}
userNodes.forEach(n=>{
    idName[n.id] = n.name
    n.id=n.name
    delete n.name
    n.type==="feature"&&(n.type=type)
})

let userLinks = JSON.parse(JSON.stringify(preLinks))

userLinks.forEach(l => {
    l.source = idName[l.source]
    l.target = idName[l.target]
})

const userGraph = {
    'nodes': userNodes,
    'edges': userLinks
}
const userGraphStr = JSON.stringify(userGraph)
const fileName = 'graph.json'

const dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(userGraph));

function download(content, fileName, contentType) {
    const a = document.createElement("a");
    const file = new Blob([content], { type: contentType });
    a.href = URL.createObjectURL(file);
    a.download = fileName;
    a.click();
}

function onDownload(){
    download(JSON.stringify(userGraph, null, "\t"), fileName, "text/plain");
}
