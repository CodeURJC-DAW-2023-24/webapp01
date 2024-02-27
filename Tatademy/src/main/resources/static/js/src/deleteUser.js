function confirmDelete(userId) {
    console.log("Funciona")
    var confirmDelete = confirm("¿Estás seguro de que deseas eliminar este usuario?");

    if (confirmDelete) {
        fetch("/delete/"+userId, {
            method: 'PUT',
        })
        .then(response => {

                    window.location.reload();

            }
        ) // Aquí faltaba cerrar un paréntesis y una llave
    }
}
