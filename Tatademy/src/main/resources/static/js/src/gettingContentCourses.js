let page = 0;

document.addEventListener('DOMContentLoaded', () => {
    const moreCourses = document.getElementById("more-courses");
    const searcher = document.getElementById("searcher");
    const sellist = document.getElementById("options");
    const admin = document.getElementById("admin");
    const filter = document.getElementById("filter");
	if(admin != null){
		fetch(`/courses-panel/${page}`)
            .then(response => response.text()) // Convertir la respuesta a texto
            .then(html => {
                document.getElementById('courses').innerHTML += html; // Insertar el HTML en el contenedor
                 // Incrementar el número de página para la próxima carga
                fetch(`/courses-panel/${++page}`)
                
            .then(response => response.text()) // Convertir la respuesta a texto
            .then(html => {
             // Incrementar el número de página para la próxima carga
                if (html.trim() === '') {
                    // Si no hay más páginas, eliminar el botón
                    moreCourses.remove();
                }
            })
    });
    filter.addEventListener('click', () => {
		
	})
    moreCourses.addEventListener('click', () => {
        fetch(`/courses-panel/${page}`)
            .then(response => response.text()) // Convertir la respuesta a texto
            .then(html => {
                document.getElementById('courses').innerHTML += html; // Insertar el HTML en el contenedor
                 // Incrementar el número de página para la próxima carga
                fetch(`/courses-panel/${++page}`)
                
            .then(response => response.text()) // Convertir la respuesta a texto
            .then(html => {
             // Incrementar el número de página para la próxima carga
                if (html.trim() === '') {
                    // Si no hay más páginas, eliminar el botón
                    moreCourses.remove();
                }
            })
    });
            })
	}
	else{
    if(searcher.value === ''){
	fetch(`/courses/${page}`)
            .then(response => response.text()) // Convertir la respuesta a texto
            .then(html => {
                document.getElementById('courses').innerHTML += html; // Insertar el HTML en el contenedor
                 // Incrementar el número de página para la próxima carga
                fetch(`/courses/${++page}`)
                
            .then(response => response.text()) // Convertir la respuesta a texto
            .then(html => {
             // Incrementar el número de página para la próxima carga
                if (html.trim() === '') {
                    // Si no hay más páginas, eliminar el botón
                    moreCourses.remove();
                }
            })
    });
    moreCourses.addEventListener('click', () => {
        fetch(`/courses/${page}`)
            .then(response => response.text()) // Convertir la respuesta a texto
            .then(html => {
                document.getElementById('courses').innerHTML += html; // Insertar el HTML en el contenedor
                 // Incrementar el número de página para la próxima carga
                fetch(`/courses/${++page}`)
                
            .then(response => response.text()) // Convertir la respuesta a texto
            .then(html => {
             // Incrementar el número de página para la próxima carga
                if (html.trim() === '') {
                    // Si no hay más páginas, eliminar el botón
                    moreCourses.remove();
                }
            })
    });
            })
            
    
            .catch(error => {
                console.error('Error fetching projects:', error);
            });
            }
            else{
				fetch(`/course-search/${page}?name=${searcher.value}&sellist1=${sellist.value}`)
            .then(response => response.text()) // Convertir la respuesta a texto
            .then(html => {
                document.getElementById('courses').innerHTML += html; // Insertar el HTML en el contenedor
                 // Incrementar el número de página para la próxima carga
                fetch(`/course-search/${++page}?name=${searcher.value}&sellist1=${sellist.value}`)
                
            .then(response => response.text()) // Convertir la respuesta a texto
            .then(html => {
             // Incrementar el número de página para la próxima carga
                if (html.trim() === '') {
                    // Si no hay más páginas, eliminar el botón
                    moreCourses.remove();
                }
            })
    });
    moreCourses.addEventListener('click', () => {
        fetch(`/courses/${page}`)
            .then(response => response.text()) // Convertir la respuesta a texto
            .then(html => {
                document.getElementById('courses').innerHTML += html; // Insertar el HTML en el contenedor
                 // Incrementar el número de página para la próxima carga
                fetch(`/courses/${++page}`)
                
            .then(response => response.text()) // Convertir la respuesta a texto
            .then(html => {
             // Incrementar el número de página para la próxima carga
                if (html.trim() === '') {
                    // Si no hay más páginas, eliminar el botón
                    moreCourses.remove();
                }
            })
    });
            })
            
    
            .catch(error => {
                console.error('Error fetching projects:', error);
            });
			}
}});


