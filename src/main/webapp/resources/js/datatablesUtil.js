function makeEditable() {
    $('.delete').click(function () {
        deleteRow($(this).parent().parent().attr("id"));
    });

    $('.update').change(function () {
        var isCheckbox = $(this).is(":checkbox");
        var value = isCheckbox ? $(this).prop("checked") : $(this).val();
        updateRow($(this).parent().parent().attr("id"), $(this).attr("name"), value);
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

function add() {
    $('#id').val(null);
    $('#editRow').modal();
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('Deleted');
        }
    });
}

function updateRow(id, field, value) {
    $.ajax({
        type: "POST",
        url: ajaxUrl + id + "/updateField",
        data: field+"="+value,
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

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    console.log(jqXHR);
    console.log(event);
    console.log(options);
    console.log(jsExc);
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}
