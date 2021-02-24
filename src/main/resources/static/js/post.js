var deleteUrl = document.querySelector('[id^="/posts/delete/"]');

function confirmDelete(){
	if(confirm("Вы действительно хотите удалить")) {
    	window.location.href = deleteUrl.id;
	}
}
console.log(deleteUrl);