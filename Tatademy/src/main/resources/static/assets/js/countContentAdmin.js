var archivosListAdmin = [];
document.getElementById('courseContentInput').addEventListener('change', function() {
    var archivos = this.files;
    var texto = '';
    for (var i = 0; i < archivosListAdmin.length; i++) {
        texto += '<div>'+ archivosListAdmin[i].name + '<img data-nombre=\'' + archivosListAdmin[i].name + '\' class="bin"  style="width: 2em; height: 2em;" src="/assets/img/archivo-bin.png">'+'</div>'+'<br>';
    }
    for (var i = 0; i < archivos.length; i++) {
        texto += '<div>'+ archivos[i].name + '<img data-nombre=\'' + archivos[i].name + '\' class="bin"  style="width: 2em; height: 2em;" src="/assets/img/archivo-bin.png">'+'</div>'+'<br>';
        archivosListAdmin.push(archivos[i]);
    }
    document.getElementById('courseContent').innerHTML = texto || 'No se seleccionó archivo';
});


document.addEventListener('click', function(event) {
    if (event.target.classList.contains('bin')) {
        eliminarElemento(event.target.getAttribute('data-nombre'));
    }
});

function eliminarElemento(nombreArchivo) {
    for (var i = 0; i < archivosListAdmin.length; i++) {
        if (archivosListAdmin[i].name === nombreArchivo) {
            archivosListAdmin.splice(i, 1);
            break;
        }
    }
    actualizarContenido();
}

function actualizarContenido() {
    var texto = '';
    for (var i = 0; i < archivosListAdmin.length; i++) {
        texto += '<div>'+ archivosListAdmin[i].name + '<img class="bin" data-nombre="' + archivosListAdmin[i].name + '" style="width: 2em; height: 2em;" src="/assets/img/archivo-bin.png">'+'</div>'+'<br>';
    }
    document.getElementById('courseContent').innerHTML = texto || 'No se seleccionó archivo';
}
document.getElementById('courseSubmit').addEventListener('submit', function(event) {
    event.preventDefault(); 



    var formData = new FormData(this);


    document.getElementById('textModified').innerHTML = "Todo correcto";
    document.getElementById('tituloCompletado').innerHTML = "Completado";
   formData.delete('courseContentInput')
    
    formData.append('courseContentInput',Array.from(archivosListAdmin)); 
    

    fetch("/admin/new/course", {
        method: 'POST',
        body: formData
    })
    
}); 

function cargarPagina() {

    location.reload();
}

