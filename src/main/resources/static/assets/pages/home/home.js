const ready = fn => document.readyState !== 'loading' ? fn() : document.addEventListener('DOMContentLoaded', fn);

ready(async () => {
    "use strict";

    let atracoes = null;
    let usuario = null;

    await fetch(`${Constants.API_BASE_URL}/atracao/list`)
        .then(result => result.json())
        .then(bodyResult => atracoes = bodyResult);

    getUser();
    const listaAtracoes = new CircularDoublyLinkedList(atracoes);
    const ELEMENTOS_CARDS = document.getElementsByClassName("atracao-card");
    const CAROUSEL = new CardCarousel(document.getElementsByClassName("atracoes-card-carousel")[0], listaAtracoes, ELEMENTOS_CARDS);
    
    const LOADER = document.getElementsByClassName("loader-atracoes-card-carousel")[0];
    const MAIN_BODY = document.getElementsByClassName("main-body")[0];

    const SIDEBAR_ATRACAO_TITLE = document.getElementById("sidebar-atracao-title");
    const SIDEBAR_ATRACAO_IMG = document.getElementById("sidebar-atracao-img");
    const SIDEBAR_ATRACAO_DESCRICAO = document.getElementById("sidebar-atracao-descricao");
    const SIDEBAR_ATRACAO_CAPACIDADE = document.getElementById("sidebar-atracao-capacidade");
    const SIDEBAR_ATRACAO_DURACAO = document.getElementById("sidebar-atracao-duracao");
    const SIDEBAR_USUARIO_TEMPO_ESPERA = document.getElementById("sidebar-usuario-tempo-espera");

    setConteudoCardSelecionado()

    let msgEntrarFila =document.getElementById("msg-entrar-fila")    
    msgEntrarFila.style.display = 'none';
    let msgPosicao = document.getElementById("position");
    msgPosicao.style.display = 'none';
    let btnPosicao = document.getElementById("position-queue");
    btnPosicao.style.display = 'none';

    document.getElementById("enqueue").addEventListener("click", async function(e){
        e.preventDefault();

        let atracaoDTO = null;
        if(usuario?.atracao == null || usuario?.atracao == undefined){
            atracaoDTO = new AtracaoDTO(CAROUSEL.cards.currentNode.value.conteudo.id,CAROUSEL.cards.currentNode.value.conteudo.nome);
        }
        else{
            atracaoDTO = new AtracaoDTO(usuario?.atracao?.id,usuario?.atracao?.nome);
        }
         
        let usuarioDTO = new UsuarioDTO(usuario.id,usuario.nome,atracaoDTO);
        let entrarFilaRequestDTO = new EntrarFilaRequestDTO(usuarioDTO,CAROUSEL.cards.currentNode.value.conteudo.id)
        if(atracaoDTO.id != null || atracaoDTO.id != undefined){
            msgEntrarFila.style.display = CAROUSEL.cards.currentNode.value.conteudo.id != atracaoDTO.id ? 'block': 'none'
            msgEntrarFila.innerHTML = `Você já está na fila da atração: ${usuario?.atracao?.nome}`;
        }        
        if (usuario?.atracao?.id == null || usuario?.atracao?.id == undefined) {
            const response = await fetch(`${Constants.API_BASE_URL}/usuario/entrar-fila-brinquedo`, {
                method: 'POST', 
                headers: {
                    'Content-Type': 'application/json',
                    'X-XSRF-TOKEN': Utils.getCookie('XSRF-TOKEN')
                },
                body: JSON.stringify(entrarFilaRequestDTO)
            });
            if(response.ok){            
                setTimeout(()=>{
                    getUser();
                    let btnDequeue = document.getElementById("dequeue");        
                    btnDequeue.style.display = 'block';
                    btnPosicao.style.display = 'block';
                },3000)
            }
        }
        
    })
    document.getElementById("dequeue").addEventListener("click", async function(e){
        e.preventDefault();        
        const response = await fetch(`${Constants.API_BASE_URL}/usuario/id/${usuario.id}/atracao/id/${CAROUSEL.cards.currentNode.value.conteudo.id}/sair-fila-brinquedo`, {
            method: 'DELETE', 
            headers: {
                'Content-Type': 'application/json',
                'X-XSRF-TOKEN': Utils.getCookie('XSRF-TOKEN')
            }
        });
        if(response.ok){
            setTimeout(()=>{
                let btnDequeue = document.getElementById("dequeue");        
                btnDequeue.style.display = 'none'
                btnPosicao.style.display = 'none';
                msgPosicao.style.display = 'none';
                getUser();
           },3000)
           
        }
    });  
    
    document.getElementById("position-queue").addEventListener("click", async function(e){
        e.preventDefault();
        getUser();
        msgPosicao.style.display = 'block';     
        msgPosicao.innerHTML = `Sua posição na fila: ${usuario?.posicaoFila}`
        })
         
    document.getElementById("main-l-sidebar-close-btn").addEventListener("click",async function name(e) {
        e.preventDefault();
        getUser();       
    })

    let transicaoCardAcontecendo = false;
    let movimentosRestantes = 0;
    let moverCardsParaDireita = true;

    CAROUSEL.irParaCardInicial();

    do{

        let cardAtual = CAROUSEL.cards.currentNode.value;
        cardAtual.elemento.getElementsByClassName("atracao-card-footer")[0].innerHTML = cardAtual.conteudo.nome;
        cardAtual.elemento.style.backgroundImage = `url("data:image/jpeg;base64,${cardAtual.conteudo.imagemBase64}")`;    

        // Configuração inicial            
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
                        card.elemento.style.backgroundImage = `url("data:image/jpeg;base64,${card.conteudo.imagemBase64}")`;
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

    LOADER.style.display = "none";

    MAIN_BODY.style.justifyContent = "space-between";
    MAIN_BODY.style.alignItems = "normal";

    CAROUSEL.elemento.style.display = "flex";

    function moverParaProximoCard(){
        movimentosRestantes--;

         msgEntrarFila.style.display = 'none'
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
        setConteudoCardSelecionado()
    }

    function setConteudoCardSelecionado(){
        let capacidadeAtracao = CAROUSEL.cardSelecionado.conteudo.capacidade;
        SIDEBAR_ATRACAO_TITLE.innerHTML = CAROUSEL.cardSelecionado.conteudo.nome;
        SIDEBAR_ATRACAO_IMG.setAttribute("src", `data:image/jpeg;base64,${CAROUSEL.cardSelecionado.conteudo.imagemBase64}`);
        SIDEBAR_ATRACAO_DESCRICAO.innerHTML = CAROUSEL.cardSelecionado.conteudo.descricao;
        SIDEBAR_ATRACAO_CAPACIDADE.innerHTML = `${capacidadeAtracao} pessoa(s) por vez`;

        let stringDuracao = "";
        let tempoDuracaoEmMin = CAROUSEL.cardSelecionado.conteudo.duracao;
        stringDuracao += tempoDuracaoEmMin > 1 ? tempoDuracaoEmMin + " minutos" : tempoDuracaoEmMin + " minuto";
        SIDEBAR_ATRACAO_DURACAO.innerHTML = stringDuracao;  
        
        if (usuario?.posicaoFila === null || usuario?.atracao?.id != CAROUSEL.cardSelecionado.conteudo.id) {
            let tempoEsperaAtracao = Math.round((CAROUSEL.cardSelecionado.conteudo.tamanhoFila / capacidadeAtracao) * tempoDuracaoEmMin);
            SIDEBAR_USUARIO_TEMPO_ESPERA.innerHTML = tempoEsperaAtracao <= 1 ? "Tempo de espera:  Menos de 1 minuto!" : "Tempo de espera: " + tempoEsperaAtracao + " minutos";
        } else {
            let tempoEsperaAtracao = Math.round((usuario?.posicaoFila / capacidadeAtracao) * tempoDuracaoEmMin);
            if (usuario?.posicaoFila <= capacidadeAtracao) {
                SIDEBAR_USUARIO_TEMPO_ESPERA.innerHTML = "Sua vez já chegou! Vá até a atração!";
            } else if ((usuario?.posicaoFila - capacidadeAtracao) <= capacidadeAtracao) {
                SIDEBAR_USUARIO_TEMPO_ESPERA.innerHTML = "Você é o próximo! Vá até a atração!"
            } else {
                SIDEBAR_USUARIO_TEMPO_ESPERA.innerHTML = "Tempo de espera: " + tempoEsperaAtracao + " minutos";
            }
        }
        
        let btnDequeue = document.getElementById("dequeue");        
        btnDequeue.style.display = CAROUSEL.cards.currentNode.value.conteudo.id == usuario?.atracao?.id ? 'block' : 'none'
        let btnPosicao = document.getElementById("position-queue");
        btnPosicao.style.display = CAROUSEL.cards.currentNode.value.conteudo.id == usuario?.atracao?.id ? 'block' : 'none'
        let msgPosicao = document.getElementById("position");
        msgPosicao.style.display = 'none';
        getUser();
    }

    async function getUser() {
        let userEmail = localStorage.getItem('email');
        let response = await fetch(`${Constants.API_BASE_URL}/usuario/email/${userEmail}`,{
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        usuario = await response.json()
    }
});