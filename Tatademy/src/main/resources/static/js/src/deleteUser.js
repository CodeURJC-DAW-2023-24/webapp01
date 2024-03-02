function deleteUser(userId) {


        fetch("/admin/delete?userId=" + userId, {
            method: 'Post',
        })
        .then(response => {

            window.location.reload();

    }
)


}
