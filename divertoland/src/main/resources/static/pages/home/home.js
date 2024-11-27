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
        { "nome": "Skyrix" },
        { "nome": "Skyrix" },
        { "nome": "Skyrix" },
        { "nome": "Skyrix" },
        { "nome": "Skyrix" },
    ]);
    const ELEMENTOS_CARDS = document.getElementsByClassName("atracao-card");
    const CAROUSEL = new CardCarousel(document.getElementsByClassName("atracoes-card-carousel")[0], listaAtracoes, ELEMENTOS_CARDS);

    let transicaoCardAcontecendo = false;
    let movimentosRestantes = 0;
    let moverCardsParaDireita = true;

    CAROUSEL.irParaCardInicial();

    do{

        let cardAtual = CAROUSEL.cards.currentNode.value;

        // Configuração inicial
        cardAtual.elemento.getElementsByClassName("atracao-card-footer")[0].innerHTML = cardAtual.conteudo.nome;
    
        cardAtual.elemento.addEventListener("click", e => {
    
            // Extremidades possuem cards invisíveis, e o meio é para visualizar a atração
            // Se tiver no meio de uma animação cancela
            if(CAROUSEL.isCardInicial(cardAtual) || CAROUSEL.isCardCentral(cardAtual) || CAROUSEL.isCardFinal(cardAtual) || transicaoCardAcontecendo) 
                return;

            let diferencaPosicao = CAROUSEL.indexDivisorio - cardAtual.index;
            moverCardsParaDireita = diferencaPosicao > 0;
            movimentosRestantes = Math.abs(diferencaPosicao);
            transicaoCardAcontecendo = true;
            moverParaProximoCard();
        });

        cardAtual.elemento.addEventListener("transitionend", e => {
            if(e.target.classList.contains("started-transition")){

                e.target.classList.remove("started-transition");

                if(document.getElementsByClassName("started-transition").length == 0){

                    let cardSelecionadoAtual = CAROUSEL.cards.currentNode.value;
    
                    CAROUSEL.irParaCardInicial();
    
                    do{
                        let card = CAROUSEL.cards.currentNode.value;
                        card.elemento.classList.add("no-transition");
                        card.elemento.getElementsByClassName("atracao-card-footer")[0].innerHTML = card.conteudo.nome;
                        card.elemento.style.setProperty("--layer", card.index <= CAROUSEL.indexDivisorio ? card.index : CAROUSEL.quantidadeTotalCards - card.index - 1);
                        card.elemento.style.transform = `
                            translateZ(calc(var(--layer) * 1em))
                        `;
                        card.elemento.offsetHeight; // Dá um reflush no CSS
                        card.elemento.classList.remove("no-transition");
                        card.elemento.offsetHeight; // Dá um reflush no CSS
                        CAROUSEL.cards.next();
                    }
                    while(CAROUSEL.cards.currentNode.value != CAROUSEL.cardInicial);
    
                    while(CAROUSEL.cards.currentNode.value != cardSelecionadoAtual){ CAROUSEL.cards.next(); }

                    if(movimentosRestantes > 0)
                        moverParaProximoCard();
                    else
                        transicaoCardAcontecendo = false;
                }
            }
        });

        for(let i = CAROUSEL.quantidadeTotalCards - 1; i < cardAtual.index; i--){CAROUSEL.cards.prev();}
    
        CAROUSEL.cards.next();
    }
    while(CAROUSEL.cards.currentNode.value != CAROUSEL.cardInicial);

    CAROUSEL.irParaCardSelecionado();

    function moverParaProximoCard(){

        movimentosRestantes--;

        CAROUSEL.irParaCardInicial();
        
        do{
            let cardComparado = CAROUSEL.cards.currentNode.value;
            let cardComparadoComputedStyle = window.getComputedStyle(cardComparado.elemento);
            let camadaCard = parseInt(cardComparadoComputedStyle.getPropertyValue('--layer'));
            let novaCamada = camadaCard;
            
            if(CAROUSEL.isCardCentral(cardComparado))
                novaCamada--;
            else if(CAROUSEL.isCardNaEsquerda(cardComparado)){
                if(moverCardsParaDireita)
                    novaCamada++;
                else
                novaCamada = cardComparado.index - 1 < 0 ? 0 : novaCamada - 1;
        }
        else{
            if(moverCardsParaDireita)
                novaCamada = cardComparado.index + 1 >= CAROUSEL.quantidadeTotalCards ? 0 : novaCamada - 1;
            else
                novaCamada++;
        }

        if(novaCamada != camadaCard)
            cardComparado.elemento.classList.add("started-transition"); 

        cardComparado.elemento.style.setProperty("--layer", novaCamada);
            cardComparado.elemento.style.transform = `
                translateZ(calc(var(--layer) * 1em))
                translateX(calc(var(--largura-card) * ${moverCardsParaDireita ? 1 : -1}))
            `;

            CAROUSEL.cards.next();
        }
        while(CAROUSEL.cards.currentNode.value != CAROUSEL.cardInicial);

        while(CAROUSEL.cards.currentNode.value.index != CAROUSEL.indexDivisorio + (moverCardsParaDireita ? -1 : 1))
            CAROUSEL.cards.next();

        CAROUSEL.selecionarCard(CAROUSEL.cards.currentNode.value)
    }
});