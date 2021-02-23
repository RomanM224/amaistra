var deleteUrl = document.querySelector('[id^="/reviews/delete/"]');

function confirmDelete(){
	if(confirm("Вы действительно хотите удалить")) {
    	window.location.href = deleteUrl.id;
	}
}