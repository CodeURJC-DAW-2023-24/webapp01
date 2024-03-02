function deletePhoto(userId) {
    fetch("/admin/user/deleteImage?id="+userId, {
        method: 'Post',
    })
    .then(response => {

                window.location.reload();

        }
    ) 

}