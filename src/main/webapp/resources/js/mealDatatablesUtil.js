function updateTable() {
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
