document.getElementById('image').addEventListener('change', function() {
    var archivos = this.files;
    var texto = '';
    for (var i = 0; i < archivos.length; i++) {
      texto += '<div>'+ archivos[i].name;
    }
    document.getElementById('filesImages-choosen').innerHTML = texto || 'No se seleccionó archivo';
  });


