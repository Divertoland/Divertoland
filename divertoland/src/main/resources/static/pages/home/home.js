const ready = fn => document.readyState !== 'loading' ? fn() : document.addEventListener('DOMContentLoaded', fn);

ready(() => {
    "use strict";

    const listaAtracoes = new CircularDoublyLinkedList([
        { "nome": "Montanha-Russa" },
        { "nome": "Carrossel" },
        { "nome": "Roda-Gigante" },
        { "nome": "Casa do Terror" },
        { "nome": "Mega Tobogã" },
        { "nome": "Magnetron" },
        { "nome": "Skyrix" }
    ]);
    const ELEMENTOS_CARDS = document.getElementsByClassName("atracao-card");

    const CAROUSEL = new CardCarousel(document.getElementsByClassName("atracoes-card-carousel")[0], listaAtracoes, ELEMENTOS_CARDS);

    do{
        let card = CAROUSEL.cards.currentNode.value;
        card.elemento.getElementsByClassName("atracao-card-footer")[0].innerHTML = card.conteudo.nome;
    }while(CAROUSEL.cards.currentNode != CAROUSEL.cardSelecionado);

    CAROUSEL.irParaCardInicial();
    let cardInicial = CAROUSEL.cards.currentNode.value;

    do{

        let cardClicado = CAROUSEL.cards.currentNode.value;
    
        cardClicado.elemento.addEventListener("click", e => {
    
            if(CAROUSEL.isCardInicial(cardClicado) || CAROUSEL.isCardCentral(cardClicado) || CAROUSEL.isCardFinal(cardClicado)) 
                return;

            CAROUSEL.selecionarCard(cardClicado)
        });

        for(let i = CAROUSEL.quantidadeTotalCards - 1; i < cardClicado.index; i--){CAROUSEL.cards.prev();}
    
        CAROUSEL.cards.next();
    }
    while(CAROUSEL.cards.currentNode.value.elemento != null && CAROUSEL.cards.currentNode.value != cardInicial);

    CAROUSEL.irParaCardSelecionado();

    CAROUSEL.cards.log()
});