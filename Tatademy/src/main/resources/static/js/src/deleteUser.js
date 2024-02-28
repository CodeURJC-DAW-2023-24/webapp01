function deleteImage(userId) {


        fetch("/user/deleteImage?userId=" + userId, {
            method: 'POST',
        })
        .then(response => {

            window.location.reload();

    }
)


}
