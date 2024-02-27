function deletePhoto(userId) {
    fetch("/user/deleteImage?id="+userId, {
        method: 'Get',
    })
    .then(response => {

                window.location.reload();

        }
    ) 

}