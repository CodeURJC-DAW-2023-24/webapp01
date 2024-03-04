let page = 1;
document.addEventListener('DOMContentLoaded', () => {
    const loadMoreBtn = document.getElementById("load-more-btn");

    loadMoreBtn.addEventListener('click', () => {
        fetch(`/admin/users?page=${page}`)
            .then(response => response.text())
            .then(html => {
                document.getElementById('projects-container').innerHTML += html;
                page++;
                fetch(`/admin/users?page=${page++}`)
            .then(response => response.text())
            .then(html => {
                page++;
                if (html.trim() === '') {
                    loadMoreBtn.remove();
                }
            })
            
    });
            })
            .catch(error => {
                console.error('Error fetching projects:', error);
            });
});


