var archivosList = [];
document.getElementById('courseContentInput').addEventListener('change', function() {
    var archivos = this.files;
    var texto = '';
    for (var i = 0; i < archivosList.length; i++) {
        texto += '<div>'+ archivosList[i].name + '<img data-nombre=\'' + archivosList[i].name + '\' class="bin"  style="width: 2em; height: 2em;" src="/assets/img/archivo-bin.png">'+'</div>'+'<br>';
    }
    for (var i = 0; i < archivos.length; i++) {
        texto += '<div>'+ archivos[i].name + '<img data-nombre=\'' + archivos[i].name + '\' class="bin"  style="width: 2em; height: 2em;" src="/assets/img/archivo-bin.png">'+'</div>'+'<br>';
        archivosList.push(archivos[i]);
    }
    document.getElementById('courseContent').innerHTML = texto || 'No se seleccionó archivo';
});


document.addEventListener('click', function(event) {
    if (event.target.classList.contains('bin')) {
        eliminarElemento(event.target.getAttribute('data-nombre'));
    }
});

function eliminarElemento(nombreArchivo) {
    for (var i = 0; i < archivosList.length; i++) {
        if (archivosList[i].name === nombreArchivo) {
            archivosList.splice(i, 1);
            break;
        }
    }
    actualizarContenido();
}

function actualizarContenido() {
    var texto = '';
    for (var i = 0; i < archivosList.length; i++) {
        texto += '<div>'+ archivosList[i].name + '<img class="bin" data-nombre="' + archivosList[i].name + '" style="width: 2em; height: 2em;" src="/assets/img/archivo-bin.png">'+'</div>'+'<br>';
    }
    document.getElementById('courseContent').innerHTML = texto || 'No se seleccionó archivo';
}
document.getElementById('courseSubmit').addEventListener('submit', function(event) {
    event.preventDefault(); // Evitar que el formulario se envíe automáticamente

    document.getElementById('textModified').innerHTML = "El curso se ha creado correctamente";
    document.getElementById('tituloCompletado').innerHTML = "Completado";

    // Obtener los datos del formulario
    var formData = new FormData(this);

    //Modificar FormData
   formData.delete('courseContentInput')
    
    formData.append('courseContentInput',Array.from(archivosList)); 
    

    // Realizar una solicitud POST al servidor
    fetch("/new/course", {
        method: 'POST',
        body: formData
    })
    
}); 

