class CardCarousel{
    constructor(elemento, dados, elementoCards){
        this.elemento = elemento;
        this.cards = null;
        this.cardSelecionado = null;
        this.cardInicial = null;
        this.cardFinal = null;
        this.quantidadeTotalDados = dados.length;
        this.quantidadeTotalCards = elementoCards.length;
        this.indexDivisorio = Math.floor(this.quantidadeTotalCards / 2);

        this._preencherCards(dados, elementoCards);
    }

    irParaCardInicial(){
        while(this.cards.currentNode.value != this.cardInicial){this.cards.next()}
    }

    irParaCardSelecionado(){
        while(this.cards.currentNode.value != this.cardSelecionado){this.cards.next()}
    }

    selecionarCard(card){

        if(card == this.cardSelecionado) return;

        let diferencaPosicao = CAROUSEL.indexDivisorio - card.index;

        for(let i = 0; i < Math.abs(diferencaPosicao); i++){
            do{
                if(diferencaPosicao > 0){
                    
                }
            }
            while(this.cards.currentNode.prevNode.value != this.cardFinal);
        }

        CAROUSEL.irParaCardInicial();

        do{

            let cardComparado = CAROUSEL.cards.currentNode.value;
            let cardComparadoComputedStyle = window.getComputedStyle(cardComparado.elemento);
            let camadaCard = parseInt(cardComparadoComputedStyle.getPropertyValue('--layer'));
            let novaCamada = camadaCard;

            if(CAROUSEL.isCardCentral(cardComparado)) 
                novaCamada -= diferencaPosicao;
            else if(CAROUSEL.isCardNaEsquerda(cardComparado)){
                if(CAROUSEL.isCardNaEsquerda(card)){
                    if(cardComparado.index + diferencaPosicao > CAROUSEL.indexDivisorio)
                        novaCamada = CAROUSEL.indexDivisorio - (diferencaPosicao - (CAROUSEL.indexDivisorio - cardComparado.index));
                    else
                        novaCamada += diferencaPosicao;
                }
                else{
                    if(cardComparado.index - diferencaPosicao < 0)
                        novaCamada = 0;
                    else
                        novaCamada -= diferencaPosicao;
                }
            }
            else{
                if(CAROUSEL.isCardNaEsquerda(card)){
                    if(cardComparado.index + diferencaPosicao >= CAROUSEL.quantidadeTotalCards)
                        novaCamada = 0;
                    else
                        novaCamada -= diferencaPosicao;
                }
                else{
                    if(cardComparado.index - diferencaPosicao < CAROUSEL.indexDivisorio)
                        novaCamada = CAROUSEL.indexDivisorio - (diferencaPosicao - (cardComparado.index - CAROUSEL.indexDivisorio));
                    else
                        novaCamada += diferencaPosicao;
                }
            }

            cardComparado.elemento.style.setProperty("--layer", novaCamada);
            cardComparado.elemento.style.transform = ` 
                translateZ(calc(var(--layer) * 1em))
                translateX(calc(var(--largura-card) * ${diferencaPosicao * (card.index < CAROUSEL.indexDivisorio ? 1 : -1)}))
            `;

            CAROUSEL.cards.next();
        }
        while(CAROUSEL.cards.currentNode.value.elemento != null && CAROUSEL.cards.currentNode != CAROUSEL.cardInicial);
    }

    isCardCentral(card){
        return card.index == this.indexDivisorio;
    }

    isCardInicial(card){
        return card.index == 0;
    }

    isCardFinal(card){
        return card.index == this.quantidadeTotalCards - 1;
    }

    isCardNaDireita(card){
        return card.index < this.indexDivisorio;
    }

    isCardNaEsquerda(card){
        return card.index < this.indexDivisorio;
    }

    _preencherCards(dados, elementoCards) {
        if(dados instanceof CircularDoublyLinkedList){

            this.cards = [];

            let noInicial = dados.currentNode;
            let indexCardAtual = 0;
            let indexDadoAtual = 0;
            
            do{
                if(indexDadoAtual <= this.indexDivisorio || indexDadoAtual > this.quantidadeTotalDados - 1 - this.indexDivisorio){
                    
                    if(indexDadoAtual <= this.indexDivisorio)
                        elementoAtual = elementoCards[indexDadoAtual + this.indexDivisorio];
                    else if(indexDadoAtual > this.quantidadeTotalDados - 1 - this.indexDivisorio)
                        elementoAtual = elementoCards[this.indexDivisorio - (this.quantidadeTotalDados - indexDadoAtual - 1) - 1];
                    
                    this.cards.push(new Card(
                        atracaoAtual.elemento,
                        indexCardAtual++,
                        dados.currentNode.value
                    ));
                }
                
                indexDadoAtual++;
                dados.next();
            }while(dados.currentNode != noInicial);

            this._recalcularPosicoesCards();
        }
    }

    _recalcularPosicoesCards(){
        for(let i = 0; i < this.indexDivisorio; i++){this.cards.prev()}
        this.cardInicial = this.cards.currentNode.value;
        for(let i = 0; i < this.indexDivisorio; i++){this.cards.next()}
        this.cardSelecionado = this.cards.currentNode.value;
        for(let i = 0; i < this.indexDivisorio; i++){this.cards.next()}
        this.cardFinal = this.cards.currentNode.value;
        this.irParaCardSelecionado();
    }
}