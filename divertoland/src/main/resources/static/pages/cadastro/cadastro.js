const ready = fn => document.readyState !== 'loading' ? fn() : document.addEventListener('DOMContentLoaded', fn);

ready(() => {
    "use strict";

    let INPUT_USUARIO = document.getElementById("input-usuario"),
        ERROR_INPUT_USUARIO = document.getElementById("error-input-usuario"),

        INPUT_EMAIL = document.getElementById("input-email"),
        ERROR_INPUT_EMAIL = document.getElementById("error-input-email"),

        INPUT_SENHA = document.getElementById("input-senha"),
        ERROR_INPUT_SENHA = document.getElementById("error-input-senha");

    let TERTIARY_THEME_COLOR = window.getComputedStyle(document.body).getPropertyValue('--tertiary-theme-color');

    document.forms["input-cadastro"].addEventListener("submit", function(e){

        e.preventDefault();

        let validateResult = true;

        let resultValidateUsuario = validateUsuario();
        if(resultValidateUsuario !== true){
            ERROR_INPUT_USUARIO.innerHTML = resultValidateUsuario;
            INPUT_USUARIO.style.borderColor = "red";
            validateResult = false;
        }
        else{
            INPUT_USUARIO.style.borderColor = TERTIARY_THEME_COLOR;
            ERROR_INPUT_USUARIO.innerHTML = ".";
        }

        let resultValidateEmail = validateEmail();
        if(resultValidateEmail !== true){
            ERROR_INPUT_EMAIL.innerHTML = resultValidateEmail;
            INPUT_EMAIL.style.borderColor = "red";
            validateResult = false;
        }
        else{
            INPUT_EMAIL.style.borderColor = TERTIARY_THEME_COLOR;
            ERROR_INPUT_EMAIL.innerHTML = ".";
        }

        let resultValidateSenha = validateSenha();
        if(resultValidateSenha !== true){
            INPUT_SENHA.style.borderColor = "red";
            ERROR_INPUT_SENHA.innerHTML = resultValidateSenha;
            validateResult = false;
        }
        else{
            INPUT_SENHA.style.borderColor = TERTIARY_THEME_COLOR;
            ERROR_INPUT_SENHA.innerHTML = ".";
        }

        if(validateResult === false){
            for(let input of document.getElementsByClassName("input-error-cadastro")){
                input.style.visibility = input.innerHTML.trim() !== "" ? "visible" : "hidden";
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

    return true;
}