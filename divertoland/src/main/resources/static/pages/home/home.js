const ready = fn => document.readyState !== 'loading' ? fn() : document.addEventListener('DOMContentLoaded', fn);

ready(() => {
    "use strict";

    const atracoes = [
        {
            "nome": "Montanha-Russa",
            "elemento": null
        },
        {
            "nome": "Carrossel",
            "elemento": null
        },
        {
            "nome": "Roda-Gigante",
            "elemento": null
        },
        {
            "nome": "Casa do Terror",
            "elemento": null
        },
        {
            "nome": "Mega Tobogã",
            "elemento": null
        },
        {
            "nome": "Magnetron",
            "elemento": null
        },
        {
            "nome": "Skyrix",
            "elemento": null
        }
    ];

    const CARDS_ATRACOES = document.getElementsByClassName("atracao-card");
    const QTD_DIVISAO_CARDS = Math.floor(CARDS_ATRACOES.length / 2);
    const listaAtracoes = new CircularDoublyLinkedList();
    let elementoAtual = null;
    let atracaoAtual = null;
    for(let i = 0; i < atracoes.length; i++){

        atracaoAtual = atracoes[i];

        if(i <= QTD_DIVISAO_CARDS || i > atracoes.length - 1 - QTD_DIVISAO_CARDS){

            if(i <= QTD_DIVISAO_CARDS){
                elementoAtual = CARDS_ATRACOES[i + QTD_DIVISAO_CARDS];
            }
            else if(i > atracoes.length - 1 - QTD_DIVISAO_CARDS){
                elementoAtual = CARDS_ATRACOES[QTD_DIVISAO_CARDS - (atracoes.length - i - 1) - 1];
            }

            elementoAtual.getElementsByClassName("atracao-card-footer")[0].innerHTML = atracaoAtual.nome;
            atracaoAtual.elemento = elementoAtual;
        }

        listaAtracoes.insert(atracaoAtual);
    };

    // listaAtracoes.log();

    // console.log(getComputedStyle(document.getElementsByClassName("atracao-card")[2]).getPropertyValue('--layer'));

});