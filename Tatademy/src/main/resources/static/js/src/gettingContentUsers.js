let page = 1;
document.addEventListener('DOMContentLoaded', () => {
    const loadMoreBtn = document.getElementById("load-more-btn");

    loadMoreBtn.addEventListener('click', () => {
        fetch(`/admin/users?page=${page}`)
            .then(response => response.text()) // Convertir la respuesta a texto
            .then(html => {
                document.getElementById('projects-container').innerHTML += html; // Insertar el HTML en el contenedor
                 // Incrementar el número de página para la próxima carga
                fetch(`/admin/users?page=${++page}`)
                
            .then(response => response.text()) // Convertir la respuesta a texto
            .then(html => {
             // Incrementar el número de página para la próxima carga
                if (html.trim() === '') {
                    // Si no hay más páginas, eliminar el botón
                    loadMoreBtn.remove();
                }
            })
    });
            })
            .catch(error => {
                console.error('Error fetching projects:', error);
            });
});


