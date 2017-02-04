function updateEntity(id, field){
    var form = $('#entity'+id);
    console.log(form.serialize());
    $.ajax({
        type: "POST",
        url: ajaxUrl + id + "/"+ field,
        data: form.serialize(),
        success: function () {
            filterTable();
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
            filterTable();
            successNoty('Saved');
        }
    });
}

function filterTable() {
    var form = $('#filterForm');
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: form.serialize(),
        success: function (data) {
            datatableApi.clear();
            $.each(data, function (key, item) {
                datatableApi.row.add(item);
            });
            datatableApi.draw();
            successNoty('Filtered');
        }
    });
}
