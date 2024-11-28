class CardCarousel{
    constructor(elemento, dados, elementoCards){
        this.elemento = elemento;
        this.cards = null;
        this.dados = dados;
        this.cardSelecionado = null;
        this.cardInicial = null;
        this.cardFinal = null;
        if(dados instanceof CircularDoublyLinkedList){
            this.quantidadeTotalDados = dados.size;
        }
        else if(variable instanceof Array){
            this.quantidadeTotalDados = dados.length;
        }
        this.quantidadeTotalCards = elementoCards.length;
        this.indexDivisorio = Math.floor(this.quantidadeTotalCards / 2);

        this._preencherCards(elementoCards);
        this._recalcularPosicoesCards();
        this.irParaCardSelecionado()
    }

    irParaCardInicial(){
        while(this.cards.currentNode.value != this.cardInicial){this.cards.prev()}
    }

    irParaCardSelecionado(){
        while(this.cards.currentNode.value != this.cardSelecionado){this.cards.next()}
    }

    selecionarCard(card){

        if(card == this.cardSelecionado) return;
        
        let diferencaPosicao = this.indexDivisorio - card.index;
        let totalMovidos = Math.abs(diferencaPosicao);

        for(let i = 0; i < totalMovidos; i++){
            if(this.isCardNaEsquerda(card))
                this.dados.prev();
            else
                this.dados.next();
        }
        
        for(let i = 0; i < this.indexDivisorio; i++){ this.dados.prev() }
        
        this.irParaCardInicial();
        
        for(let i = 0; i < this.quantidadeTotalCards; i++){
            this.cards.currentNode.value = new Card(
                this.cards.currentNode.value.elemento,
                i,
                this.dados.currentNode.value
            );
            if(i < this.quantidadeTotalCards - 1){
                this.dados.next();
                this.cards.next();
            }
        }
        
        this._recalcularPosicoesCards();
        
        this.irParaCardSelecionado();
        for(let i = 0; i < this.indexDivisorio; i++){ this.dados.prev() }
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
        return card.index > this.indexDivisorio;
    }

    isCardNaEsquerda(card){
        return card.index < this.indexDivisorio;
    }

    _preencherCards(elementoCards) {
        if(this.dados instanceof CircularDoublyLinkedList){

            this.cards = new CircularDoublyLinkedList();

            let noInicial = this.dados.currentNode;
            let indexCardAtual = 0;
            let indexDadoAtual = 0;
            
            do{
                if(indexDadoAtual < this.quantidadeTotalCards){
                    this.cards.insert(new Card(
                        elementoCards[indexCardAtual],
                        indexCardAtual++,
                        this.dados.currentNode.value
                    ));
                }

                indexDadoAtual++;
                this.dados.next();
            }
            while(this.dados.currentNode != noInicial);

            for(let i = 0; i < this.indexDivisorio; i++){ this.dados.next() }
        }
    }

    _recalcularPosicoesCards(){
        while(this.cards.currentNode.value.index != 0){this.cards.prev()}
        this.cardInicial = this.cards.currentNode.value;
        while(this.cards.currentNode.value.index != this.indexDivisorio){this.cards.next()}
        this.cardSelecionado = this.cards.currentNode.value;
        while(this.cards.currentNode.value.index != this.quantidadeTotalCards - 1){this.cards.next()}
        this.cardFinal = this.cards.currentNode.value;
    }
}