const ready = fn => document.readyState !== 'loading' ? fn() : document.addEventListener('DOMContentLoaded', fn);

ready(() => {
    "use strict";

    let INPUT_EMAIL = document.getElementById("input-email"),    
        ERROR_INPUT_EMAIL = document.getElementById("error-input-email"),

        INPUT_SENHA = document.getElementById("input-senha"),
        ERROR_INPUT_SENHA = document.getElementById("error-input-senha");

    let TERTIARY_THEME_COLOR = window.getComputedStyle(document.body).getPropertyValue('--tertiary-theme-color');

    document.forms["input-login"].addEventListener("submit", function(e){

        e.preventDefault();

        let validateResult = true;        
        let email = document.forms["input-login"]["input-email"].value
        let senha = document.forms["input-login"]["input-senha"].value

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
            for(let input of document.getElementsByClassName("input-error-login")){
                input.style.visibility = input.innerHTML.trim() !== "." ? "visible" : "hidden";
            }
        }else{     
            loginRequest(email,senha)                          
        }
    });

})

function validateEmail(){
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    let email = document.forms["input-login"]["input-email"].value;

    if (email == null || email.trim() == ""){
        return "Preencha o e-mail";
    }
    if(!emailRegex.test(email)){
        return "E-mail inválido"
    }
    return true;
}

function validateSenha(){
    const passwordRegex = /^(?=.*[!@#$%^&*(),.?":{}|<>])[A-Za-z\d!@#$%^&*(),.?":{}|<>]{8,}$/;
    let senha = document.forms["input-login"]["input-senha"].value;
    if (!passwordRegex.test(senha)) {
        return "Senha inválida";
    }
    return true;
}
async function loginRequest(email,senha) {
    try {
        const response = await fetch(`${Constants.BASE_URL}/data/user/login`, {
            method: 'POST', 
            headers: {
                'Content-Type': 'application/json' 
            },
            body: JSON.stringify({
                email:email,
                senha:senha
            })
        })

        if (!response.ok) {
            throw new Error(`Erro: ${response.status}`);
        }

        document.forms["input-login"].reset();
        let retorno = await response.json();
        localStorage.setItem('userId', retorno.id);       
        return window.location.href = "/"
    } catch (error) {
        console.error('Erro ao realizar loginRequest com body:', error);
        throw error;
    }
}




