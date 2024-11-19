const ready = fn => document.readyState !== 'loading' ? fn() : document.addEventListener('DOMContentLoaded', fn);

ready(() => {
    "use strict";

    let INPUT_USUARIO = document.getElementById("input-usuario"),
        ERROR_INPUT_USUARIO = document.getElementById("error-input-usuario"),

        INPUT_EMAIL = document.getElementById("input-email"),
        ERROR_INPUT_EMAIL = document.getElementById("error-input-email"),

        INPUT_SENHA = document.getElementById("input-senha"),
        ERROR_INPUT_SENHA = document.getElementById("error-input-senha"),

        INPUT_SUBMIT = document.getElementById("input-submit");

    document.forms["input-cadastro"].addEventListener("submit", function(e){

        e.preventDefault();

        let validateResult = true;

        let resultValidateUsuario = validateUsuario();
        if(resultValidateUsuario !== true){
            ERROR_INPUT_USUARIO.InnerHTML = resultValidateUsuario;
            validateResult = false;
        }
        else
            ERROR_INPUT_USUARIO.InnerHTML = "";

        let resultValidateEmail = validateEmail();
        if(resultValidateEmail !== true){
            ERROR_INPUT_EMAIL.InnerHTML = resultValidateEmail;
            validateResult = false;
        }
        else
            ERROR_INPUT_EMAIL.InnerHTML = "";

        let resultValidateSenha = validateSenha();
        if(resultValidateSenha !== true){
            ERROR_INPUT_SENHA.InnerHTML = resultValidateSenha;
            validateResult = false;
        }
        else
            ERROR_INPUT_SENHA.InnerHTML = "";

        if(validateResult === false){
            for(let input of document.getElementsByClassName("input-error-cadastro")){
                input.style.display = input.InnerHTML.trim() !== "" ? "inline" : "none";
            }
        }

    });

})

function validateUsuario(){

    let usuario = document.forms["input-cadastro"]["input-usuario"].value;

    if (usuario == null || usuario.trim() == "")
        return "Preencha o nome do usuário";

    return true;
}

function validateEmail(){

    let usuario = document.forms["input-cadastro"]["input-email"].value;

    if (usuario == null || usuario.trim() == "")
        return "Preencha o email";

    return true;
}

function validateSenha(){

    let usuario = document.forms["input-cadastro"]["input-senha"].value;

    if (usuario == null || usuario.trim() == "")
        return "Preencha a senha";

    return true;
}