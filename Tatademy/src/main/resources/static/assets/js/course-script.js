document.addEventListener('DOMContentLoaded', function () {
	var form = document.getElementById('search-form');
	var orderBy = document.getElementById('options');
	orderBy.addEventListener('change', function () {
		form.submit(); 
	});
});