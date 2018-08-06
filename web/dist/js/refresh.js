$(document).ready(function () {

    function alerta() {
        remotelist();
    }
    $("#alertasid").on("click", function () {
        if ($("#cnalerts").hasClass('control-sidebar-open')) {
            alerta();
        }
    });
    
});
