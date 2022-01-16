const getJsonUsers = "/adminPage/json-users";
const token = $('#_csrf').attr('content');
const header = $('#_csrf_header').attr('content');

let userIdToDelete;

$.ajaxSetup({
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'X-CSRF-TOKEN': token
    }
});

function setRowIndexAndUserId(id) {
    userIdToDelete = id;
    console.log(id);
}

function closeModal(nameOfTheModal) {
    $(nameOfTheModal).modal('hide');
}

function deleteEntity() {
    let deleteUserUrl = '/adminPage/json-users/delete/' + userIdToDelete;
    console.log(deleteUserUrl);
    $.ajax({
        url: deleteUserUrl,
        type: 'DELETE',
        success: function () {
            closeModal('#deleteModal');
            userIdToDelete = "";
            location.reload();
        }
    });
}
