$(function() {

    $(".well").on("click", "#delete-link", function(e) {
        e.preventDefault();

        var storyDeleteDialogTempate = Handlebars.compile($("#template-delete-confirmation-dialog").html());

        $("#view-holder").append(storyDeleteDialogTempate());
        $("#delete-confirmation-dialog").modal();
    })

    $("#view-holder").on("click", "#cancel-button", function(e) {
        e.preventDefault();

        var deleteConfirmationDialog = $("#delete-confirmation-dialog")
        deleteConfirmationDialog.modal('hide');
        deleteConfirmationDialog.remove();
    });

    $("#view-holder").on("click", "#delete-button", function(e) {
        e.preventDefault();
        var deleteUrl = $("#delete-url").val();
        window.location.href = deleteUrl;
    });
});
