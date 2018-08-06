$(document).ready(function () {
    $("#conv").on("click", function () {
        $(".ocultar2").css("display", "none");
        $(".ocultar").css("display", "block");

    });

    $("#other").on("click", function () {
        $(".ocultar").css("display", "none");
        $(".ocultar2").css("display", "block");
    });
});
