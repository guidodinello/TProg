/*Verificar campos vacios*/
const verificarCamposMostrarError = (campo) => {
    $(`#${campo}IniciarSesionText`).on("focusout", function(){
        if ($(this).val() == ""){
            $(this).addClass("is-invalid");
            $(`#${campo}IniciarSesionDiv`).removeClass("mb-4");
            $(`#${campo}IniciarSesionDiv`).addClass("mb-0");
        }else{
            $(this).removeClass("is-invalid");
            $(`#${campo}IniciarSesionDiv`).addClass("mb-4");
            $(`#${campo}IniciarSesionDiv`).removeClass("mb-0");
        }
            
    })
}

/*Mostrar obligatorios cuando le da sumbit sin tenerlos todos llenos*/
const marcarObligatorios = (campo) => {
    if($(`#${campo}IniciarSesionText`).val() == ""){
        $(`#${campo}IniciarSesionText`).addClass("is-invalid");
        $(`#${campo}IniciarSesionDiv`).removeClass("mb-4");
        $(`#${campo}IniciarSesionDiv`).addClass("mb-0");
    }
}

verificarCamposMostrarError("NicknameOEmail");
verificarCamposMostrarError("Contrasenia");

/*Comportamiento de boton sumbit cuando no tiene todo lo requerido*/
$("#btnIniciarSesion").on("click", function(e){
    if(($("#NicknameOEmailIniciarSesionText").val() == "washington" || $("#NicknameOEmailIniciarSesionText").val() == "washington@turismorocha.gub.uy") && $("#ContraseniaIniciarSesionText").val() == "asdfg654"){
        $("#FormularioIniciarSesion").attr("action","./homeLogueadoProv.html")
    }
    if(($("#NicknameOEmailIniciarSesionText").val() == "lachiqui" || $("#NicknameOEmailIniciarSesionText").val() == "mirtha.legrand.ok@hotmail.com.ar") && $("#ContraseniaIniciarSesionText").val() == "awdrg543"){
        $("#FormularioIniciarSesion").attr("action","./homeLogueado.html")
    }
    marcarObligatorios("NicknameOEmail");
    marcarObligatorios("Contrasenia");
})

//**//

const inputs = document.querySelectorAll(".input");


function addcl(){
    let parent = this.parentNode.parentNode;
    parent.classList.add("focus");
}

function remcl(){
    let parent = this.parentNode.parentNode;
    if(this.value == ""){
        parent.classList.remove("focus");
    }
}


inputs.forEach(input => {
    input.addEventListener("focus", addcl);
    input.addEventListener("blur", remcl);
});