document.getElementById('image').addEventListener('change', function() {
    var archivos = this.files;
    var texto = '';
    for (var i = 0; i < archivos.length; i++) {
      texto += '<div>'+ archivos[i].name + '<img data-nombre=\'' + archivos[i].name + '\' class="bin"  style="width: 2em; height: 2em;" src="assets/img/archivo-bin.png">'+'</div>'+'<br>';
    }
    document.getElementById('filesImages-choosen').innerHTML = texto || 'No se seleccionó archivo';
  });

  document.addEventListener('click', function(event) {
    if (event.target.classList.contains('bin')) {
      texto=''
      document.getElementById('filesImages-choosen').innerHTML = texto || 'No se seleccionó archivo';
    }
});


