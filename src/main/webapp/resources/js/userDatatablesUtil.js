function updateEntity(id, field){
    var form = $('#entity'+id);
    console.log(form.serialize());
    $.ajax({
        type: "POST",
        url: ajaxUrl + id + "/"+ field,
        data: form.serialize(),
        success: function () {
            updateTable();
            successNoty('Entity updated');
        }
    });
}

function save() {
    var form = $('#detailsForm');
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
}
