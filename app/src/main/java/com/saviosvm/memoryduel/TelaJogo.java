package com.saviosvm.memoryduel;

/**
 * Created by savio on 03/11/2016.
 */

import android.util.Log;

import com.saviosvm.memoryduel.AndGraph.AGGameManager;
import com.saviosvm.memoryduel.AndGraph.AGInputManager;
import com.saviosvm.memoryduel.AndGraph.AGScene;
import com.saviosvm.memoryduel.AndGraph.AGSoundManager;
import com.saviosvm.memoryduel.AndGraph.AGSprite;
import com.saviosvm.memoryduel.AndGraph.AGScreenManager;
import com.saviosvm.memoryduel.AndGraph.AGTimer;

import java.util.ArrayList;
import java.util.Collections;

public class TelaJogo extends AGScene {

    //temporizadores para efeitos
    AGTimer temporizador;
    AGTimer tempFim;
    AGTimer mesas;
    AGTimer apresentacao;

    //Variaveis auxiliares para adicionar no vetor
    AGSprite auxVerso = null;
    AGSprite auxFigura = null;

    //contadores de ponto
    AGSprite[] placar = null;
    AGSprite[] acert = null;
    AGSprite[] err = null;
    AGSprite[] rtk = null;
    AGSprite[] cronometro = null;
    int estado;

    //figuras de final
    AGSprite fgFinal = null;
    AGSprite fgFundo = null;

    //variaveis de controle do click
    int paramArray[];
    int idFigura[];
    int cont;
    int mesa;

    //porcentagem x e y nas cartas
    int widthX;
    int heigthY;

    //pegar o valor maximo de x e Y
    int maxW;
    int maxH;

    //incremente de posição x e y
    int desWX;
    int desHY;
    int posWX;
    int posHY;

    //controle da matriz
    int eixoX;
    int auxX;
    int auxId;

    //indices do jogo
    int pontos;
    int acertos;
    int erros;
    int retake;
    int apresent;
    int tempo;

    //Objetos
    Figura figuras = null;
    ArrayList<Figura> vetorFiguras = null;

    //sons
    int flip = 0;
    int shuff = 1;
    int jogadaEr = 2;
    int jogadaAc = 3;
    int jogadaTs = 4;

    //construtor da classe (recebe o gerenciador)
    TelaJogo(AGGameManager gerenciador) {
        super(gerenciador);
    }

    //usado para ser chamada cena
    @Override
    public void init() {
        //temporizadores
        temporizador = new AGTimer(1000);
        tempFim = new AGTimer();
        apresentacao = new AGTimer(1000);
        mesas = new AGTimer(100);
        this.setSceneBackgroundColor(0, 0, 0);
        //INSTANCIA UM ARRAY DE OBJETOS DO TIPO AGSPRITE
        vetorFiguras = new ArrayList<>();
        //CRIA OS PLACARES
        placar = new AGSprite[4];
        acert = new AGSprite[4];
        err = new AGSprite[4];
        rtk = new AGSprite[4];
        cronometro = new AGSprite[4];
        //DEFINE O TAMANHO DO EIXO X E Y NO TOTAL DE POSIÇÕES
        eixoX = 4;
        //DEFINE AS PROPORÇÕES DAS FIGURAS
        widthX = 25;
        heigthY = 16;
        //PEGA O TAMANHO DA TELA
        maxW = AGScreenManager.iScreenWidth;
        maxH = AGScreenManager.iScreenHeight;
        //INICIALIZA OS VETORES
        paramArray = new int[2];
        idFigura = new int[2];
        //CHAMA O METODO PARA CRIAR AS FIGURAS
        criarFiguras();
        //EMBARALHA O ARRAY LIST
        Collections.shuffle(vetorFiguras);
        //PEGA A POSIÇÃO INICIAL DE X E Y, VAI SETAR AS POSIÇÕES DE ACORDO COM ESSE CONJUNTO  DE COORDENADAS
        //A PARTIR DO PRIMEIRO ELEMENTO DO ARRAYU QUE FOI POPULADO
        desHY = (int) (vetorFiguras.get(0).getCorpoFigura().getSpriteHeight() / 2);
        desWX = (int) (vetorFiguras.get(0).getCorpoFigura().getSpriteWidth() / 2);
        posHY = desHY;
        posWX = desWX;
        //
        auxX = 0;
        auxId = -1;
        //LAÇO RENDERIZADOR
        for (Figura renderFg : vetorFiguras) {
            renderFg.getCorpoFigura().vrPosition.setXY(posWX, posHY);
            renderFg.getVersoFigura().vrPosition.setXY(posWX, posHY);
            renderFg.getCorpoFigura().bVisible = true;
            renderFg.getVersoFigura().bVisible = false;
            renderFg.setClicada(false);
            renderFg.setContabilizada(false);
            posWX += (desWX * 2);
            auxX++;
            if (auxX == eixoX) {
                posHY += (desHY * 2);
                posWX = (desWX);
                auxX = 0;
            }
        }
        //CRIA OS PLACARES
        criarMarcadores();
        desHY = maxH - (int) (rtk[0].getSpriteHeight() / 2);
        desWX = (int) (rtk[0].getSpriteWidth() / 2);
        //placar principal posicao x
        posWX = (maxW / 2);
        posHY = maxW - (maxW / 4);
        for (int i = 0; i < placar.length; i++) {
            if (i == 0) {
                rtk[i].vrPosition.setXY(maxW - desWX, desHY);
                acert[i].vrPosition.setXY(desWX, desHY);
                err[i].vrPosition.setXY(maxW / 4, desHY);
                cronometro[i].vrPosition.setXY(posHY, desHY);
            } else if (i == 1) {
                acert[i].vrPosition.setXY(desWX + (float) (desWX * 1.5), desHY);
                err[i].vrPosition.setXY(((maxW / 4) + (float) (desWX * 1.5)), desHY);
                cronometro[i].vrPosition.setXY(posHY, desHY);
            } else if (i == 2) {
                cronometro[i].vrPosition.setXY(posHY, desHY);
            }
            placar[i].vrPosition.setXY(posWX, desHY);
            posWX += (desWX * 1.5);
            posHY += (desWX * 1.5);

        }
        //SCORES DO JOGO
        pontos = 8000;
        acertos = 0;
        erros = 0;
        cont = 0;
        retake = 2;
        tempo = 60;
        estado = 0;
        /**
         * valores de identificação do estado do jogo
         * 0 :jogo normal
         * -1: perdedo o jogo
         * 1: ganhando o jogo
         * 2: ganhou
         * -2: perdeu
         */
        mesa = 0;
        apresent = 0;
        //sons
        flip = AGSoundManager.vrSoundEffects.loadSoundEffect("flip.mp3");
        shuff = AGSoundManager.vrSoundEffects.loadSoundEffect("shuffle.mp3");
        jogadaEr = AGSoundManager.vrSoundEffects.loadSoundEffect("fail.mp3");
        jogadaAc = AGSoundManager.vrSoundEffects.loadSoundEffect("success.mp3");
        jogadaTs = AGSoundManager.vrSoundEffects.loadSoundEffect("request.mp3");

        mudarMusica();

    }

    //usada para reestart da cena
    @Override
    public void restart() {

    }

    //usada para parar a cena
    @Override
    public void stop() {

    }

    @Override
    public void loop() {
        //faz uma apresentação das cartas no jogo
        //faz tambem o contador do cronometro
        apresentacao.update();
        if (apresentacao.isTimeEnded()) {
            //se a apresentação não tiver ocorrido, ele vai fazer a apresentação
            if (apresent == 0) {
                //apresenta na velocidade da luz
                for(Figura treta: vetorFiguras)
                        treta.getCorpoFigura().bVisible = false;
                //enquanto a variavel for menor que o tamanho do arry ela vai executar
                if (mesa < vetorFiguras.size()) {
                    //tempo de embaralhamento
                    mesas.update();
                    if (mesas.isTimeEnded()) {
                        AGSoundManager.vrSoundEffects.play(shuff);
                        vetorFiguras.get(mesa).getVersoFigura().bVisible = true;
                        mesas.restart();
                        //incrementa toda vez que chama
                        if (mesa < vetorFiguras.size())
                            mesa++;

                        //quando terminar, muda o valorda variavel para nao chamar o metodo de novo
                        if (mesa == vetorFiguras.size()){
                            apresent = 1;
                            apresentacao.restart(1000);
                        }
                    }
                }
            }
            //se não ele apenas faz o papel de contador
            else {
                if (apresentacao.isTimeEnded()) {
                    tempo -= 1;
                    apresentacao.restart();
                }
            }
        }

        //atualiza sempre que o jogo esta ativo
        if (estado > -2 && estado < 2)
            atualizaMarcadores();

        //Se clicar em voltar, ele sai do jogo de volta pro menu
        if (AGInputManager.vrTouchEvents.backButtonClicked()) {
            AGSoundManager.vrMusic.stop();
            vrGameManager.setCurrentScene(1);
            return;
        }

        //click das cartas começa aqui, ficou grandinho mesmo
        //não deixa clicks caso o jogo esteja parado
        if (AGInputManager.vrTouchEvents.screenClicked() && cont < 2 && mesa == vetorFiguras.size()) {
            //pra não deixar clicar caso o jogo acabe
            if (cont < 2 && (estado > -2 && estado < 2)) {
                if (vetorFiguras.get(0).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 0;

                if (vetorFiguras.get(1).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 1;

                if (vetorFiguras.get(2).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 2;

                if (vetorFiguras.get(3).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 3;

                if (vetorFiguras.get(4).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 4;

                if (vetorFiguras.get(5).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 5;

                if (vetorFiguras.get(6).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 6;

                if (vetorFiguras.get(7).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 7;

                if (vetorFiguras.get(8).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 8;

                if (vetorFiguras.get(9).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 9;

                if (vetorFiguras.get(10).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 10;

                if (vetorFiguras.get(11).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 11;

                if (vetorFiguras.get(12).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 12;

                if (vetorFiguras.get(13).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 13;

                if (vetorFiguras.get(14).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 14;

                if (vetorFiguras.get(15).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 15;

                if (vetorFiguras.get(16).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 16;

                if (vetorFiguras.get(17).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 17;

                if (vetorFiguras.get(18).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 18;

                if (vetorFiguras.get(19).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 19;

                if (vetorFiguras.get(20).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 20;

                if (vetorFiguras.get(21).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 21;

                if (vetorFiguras.get(22).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 22;

                if (vetorFiguras.get(23).getVersoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                    auxId = 23;

                //chama o metodo de checar se a figura esta virada
                figuraVirada(auxId);
            }

        }

        if (cont == 2) {
            temporizador.update();
            if (temporizador.getCurrentTime() >= 500 && temporizador.getCurrentTime() <= 535)
                AGSoundManager.vrSoundEffects.play(jogadaTs);

            if (temporizador.isTimeEnded()) {
                acaoJogo();
            }
        }

        //verifica se o tempo está abaixo do coisa
        if (tempo <= 30 && estado >= 0 && acertos < 7) {
            estado = -1;
            mudarMusica();
        }

        //se ele terminar  GANHNDO o jogo entra aqui
        if (acertos == 12) {
            //tocar a musica de vitoria
            if (estado != 2) {
                //seta o estado pois ela não vai entrar aqui de novo
                estado = 2;
                //reinicia o temporizador
                tempFim.restart(13000);
                //faz a figura final aparecer
                mostraFinal();
                //faz a musica mudar
                mudarMusica();
                //atualiza o marcador uma ultima vez
                atualizaMarcadores();
            }
            tempFim.update();
            if (tempFim.isTimeEnded()) {
                AGSoundManager.vrMusic.stop();
                vrGameManager.setCurrentScene(1);
            }
        }

        //se ele terminar PERDENDO o jogo entra aqui
        if (tempo <= 0 || pontos <= 0) {
            if (estado != -2) {
                //seta o estado pois ela não vai entrar aqui de novo
                estado = -2;
                //reinicia o temporizador
                tempFim.restart(7000);
                //faz a figura final aparecer
                mostraFinal();
                //faz a musica mudar
                mudarMusica();
                //atualiza o marcador uma ultima vez
                atualizaMarcadores();

            }
            tempFim.update();
            if (tempFim.isTimeEnded()) {
                AGSoundManager.vrMusic.stop();
                vrGameManager.setCurrentScene(1);
            }
        }


    }

    //INTERFACES
    //renderiza os placares
    public void render() {
        super.render();
        rtk[0].render();
        acert[0].render();
        acert[1].render();
        err[0].render();
        err[1].render();
        placar[0].render();
        placar[1].render();
        placar[2].render();
        placar[3].render();
        cronometro[0].render();
        cronometro[1].render();
        cronometro[2].render();
    }

    //metodo ingrato que atualiza os marcadores
    private void atualizaMarcadores() {
        //retakes
        rtk[0].setCurrentAnimation(retake);
        //erros
        err[0].setCurrentAnimation(erros / 10);
        err[1].setCurrentAnimation(erros % 10);
        //acertos
        acert[0].setCurrentAnimation(acertos / 10);
        acert[1].setCurrentAnimation(acertos % 10);
        //placar
        if (pontos > 0) {
            placar[0].setCurrentAnimation(pontos / 1000);
            placar[1].setCurrentAnimation((pontos % 1000) / 100);
            placar[2].setCurrentAnimation((pontos % 100) / 10);
            placar[3].setCurrentAnimation(pontos % 10);
        } else {
            for (AGSprite dd : placar)
                dd.setCurrentAnimation(0);

        }
        //cronometro
        if (tempo > 0) {
            cronometro[0].setCurrentAnimation(tempo / 100);
            cronometro[1].setCurrentAnimation((tempo % 100) / 10);
            cronometro[2].setCurrentAnimation((tempo % 10));
        } else {
            for (AGSprite dd : cronometro)
                dd.setCurrentAnimation(0);
        }


    }

    //verifica se aquela figura está clicada no momento
    private void figuraVirada(int cod) {
        if (!vetorFiguras.get(cod).isClicada()) {
            AGSoundManager.vrSoundEffects.play(flip);
            vetorFiguras.get(cod).setClicada(true);
            vetorFiguras.get(cod).getVersoFigura().bVisible = false;
            vetorFiguras.get(cod).getCorpoFigura().bVisible = true;
            checarViradas();
        } else
            //só pode executar essa ação numero x de vezes
            if (retake > 0) {
                if (!vetorFiguras.get(cod).isContabilizada()) {
                    AGSoundManager.vrSoundEffects.play(flip);
                    vetorFiguras.get(cod).setClicada(false);
                    vetorFiguras.get(cod).getVersoFigura().bVisible = true;
                    vetorFiguras.get(cod).getCorpoFigura().bVisible = false;
                    retake--;
                    if (retake <= 0)
                        retake = 0;
                }
            }
    }

    //Metodo ingrato que checa click
    private void checarViradas() {
        //conta quantos tem viradas
        cont = 0;
        for (int incV = 0; incV < vetorFiguras.size(); incV++) {
            if (vetorFiguras.get(incV).isClicada() && !vetorFiguras.get(incV).isContabilizada()) {
                idFigura[cont] = vetorFiguras.get(incV).getIdFigura();
                paramArray[cont] = incV;
                cont++;
            }
        }
        if (cont == 2)
            temporizador.restart();
    }

    //Metodo ingrato que vai fazer a magica acontecer
    private void acaoJogo() {
        cont = 0;
        //verifica se é os ids são iguais
        // se for ele contabiliza os pontos de defesa da primeira carta
        //e soma aos pontos totais
        if (idFigura[0] == idFigura[1]) {
            vetorFiguras.get(paramArray[0]).setContabilizada(true);
            vetorFiguras.get(paramArray[1]).setContabilizada(true);
            acertarFg(vetorFiguras.get(paramArray[0]).getPtsDef());

            AGSoundManager.vrSoundEffects.play(jogadaAc);

            if (acertos >= 7 && pontos > 2000 && estado < 1) {
                estado = 1;
                mudarMusica();
            } else if (acertos <= 7 && pontos > 2000 && estado == -1) {
                estado = 0;
                mudarMusica();
            }
        }
        //se ele errar, vai contabilizar os pontos de ataque da segunda carta da pontuação
        else {
            vetorFiguras.get(paramArray[0]).getCorpoFigura().bVisible = false;
            vetorFiguras.get(paramArray[0]).getVersoFigura().bVisible = true;
            vetorFiguras.get(paramArray[0]).setClicada(false);

            vetorFiguras.get(paramArray[1]).getCorpoFigura().bVisible = false;
            vetorFiguras.get(paramArray[1]).getVersoFigura().bVisible = true;
            vetorFiguras.get(paramArray[1]).setClicada(false);

            AGSoundManager.vrSoundEffects.play(jogadaEr);

            errarFg();

            if (pontos < 2000 && estado >= 0) {
                estado = -1;
                mudarMusica();
            }
        }

    }

    //calcula acerto
    private void acertarFg(int pts) {
        acertos += 1;
        retake += 1;
        pontos += pts;
        tempo += 15;
        //pontuação maxima é 9999
        if (pontos >= 9999)
            pontos = 9999;

        if (retake >= 9)
            retake = 9;

    }

    //calcula erro
    private void errarFg() {
        int desconto;
        //compara para fazer a regra
        if(vetorFiguras.get(paramArray[0]).getPtsAtk() >= vetorFiguras.get(paramArray[1]).getPtsAtk())
            desconto = vetorFiguras.get(paramArray[0]).getPtsAtk();
        else
            desconto =  vetorFiguras.get(paramArray[1]).getPtsAtk();

        erros += 1;
        pontos -= desconto;
        //pontuação minima é 0
        if (pontos <= 0)
            pontos = 0;

        if (tempo < 0)
            tempo = 0;

    }

    //metodo que vai popular os arrays
    private void criarFiguras() {
        int i, p, f;
        f = 0;
        p = 1;
        //é uma constanste o verso
        for (i = 0; i < 24; i++) {
            //cria um novo sprite
            auxVerso = createSprite(R.drawable.verso, 1, 1);
            auxVerso.setScreenPercent(widthX, heigthY);
            auxVerso.bVisible = false;

            //Cria um novo objeto digura
            figuras = new Figura();
            figuras.setVersoFigura(auxVerso);
            auxFigura = null;

            //Incrementa os ids a cada duas figuras
            if (f == 2) {
                p += 1;
                f = 0;
            }
            f++;
            //chama de acordo com a posição, e adiciona no arraylists
            if (p == 1) {
                //Blue Eyes
                auxFigura = createSprite(R.drawable.p01, 1, 1);
                auxFigura.setScreenPercent(widthX, heigthY);
                auxFigura.bVisible = false;
                figuras.setCorpoFigura(auxFigura);
                figuras.setIdFigura(p);
                figuras.setPtsAtk(3000);
                figuras.setPtsDef(2500);
                vetorFiguras.add(figuras);
            } else if (p == 2) {
                //Dark Magician
                auxFigura = createSprite(R.drawable.p02, 1, 1);
                auxFigura.setScreenPercent(widthX, heigthY);
                auxFigura.bVisible = false;
                figuras.setCorpoFigura(auxFigura);
                figuras.setVersoFigura(auxVerso);
                figuras.setIdFigura(p);
                figuras.setPtsAtk(2500);
                figuras.setPtsDef(2100);
                vetorFiguras.add(figuras);
            } else if (p == 3) {
                //black magician girl
                auxFigura = createSprite(R.drawable.p03, 1, 1);
                auxFigura.setScreenPercent(widthX, heigthY);
                auxFigura.bVisible = false;
                figuras.setCorpoFigura(auxFigura);
                figuras.setVersoFigura(auxVerso);
                figuras.setIdFigura(p);
                figuras.setPtsAtk(2100);
                figuras.setPtsDef(1700);
                vetorFiguras.add(figuras);
            } else if (p == 4) {
                //Magician's Valkiryan
                auxFigura = createSprite(R.drawable.p04, 1, 1);
                auxFigura.setScreenPercent(widthX, heigthY);
                auxFigura.bVisible = false;
                figuras.setCorpoFigura(auxFigura);
                figuras.setVersoFigura(auxVerso);
                figuras.setIdFigura(p);
                figuras.setPtsAtk(1600);
                figuras.setPtsDef(1800);
                vetorFiguras.add(figuras);
            } else if (p == 5) {
                //Red Eyes Black Dragon
                auxFigura = createSprite(R.drawable.p05, 1, 1);
                auxFigura.setScreenPercent(widthX, heigthY);
                auxFigura.bVisible = false;
                figuras.setCorpoFigura(auxFigura);
                figuras.setVersoFigura(auxVerso);
                figuras.setIdFigura(p);
                figuras.setPtsAtk(2400);
                figuras.setPtsDef(2000);
                vetorFiguras.add(figuras);
            } else if (p == 6) {
                //Gaia The Fierce
                auxFigura = createSprite(R.drawable.p06, 1, 1);
                auxFigura.setScreenPercent(widthX, heigthY);
                auxFigura.bVisible = false;
                figuras.setCorpoFigura(auxFigura);
                figuras.setVersoFigura(auxVerso);
                figuras.setIdFigura(p);
                figuras.setPtsAtk(2300);
                figuras.setPtsDef(2100);
                vetorFiguras.add(figuras);
            } else if (p == 7) {
                //Alexandrite Dragon
                auxFigura = createSprite(R.drawable.p07, 1, 1);
                auxFigura.setScreenPercent(widthX, heigthY);
                auxFigura.bVisible = false;
                figuras.setCorpoFigura(auxFigura);
                figuras.setVersoFigura(auxVerso);
                figuras.setIdFigura(p);
                figuras.setPtsAtk(2000);
                figuras.setPtsDef(100);
                vetorFiguras.add(figuras);
            } else if (p == 8) {
                //milenium shield
                auxFigura = createSprite(R.drawable.p08, 1, 1);
                auxFigura.setScreenPercent(widthX, heigthY);
                auxFigura.bVisible = false;
                figuras.setCorpoFigura(auxFigura);
                figuras.setVersoFigura(auxVerso);
                figuras.setIdFigura(p);
                figuras.setPtsAtk(0);
                figuras.setPtsDef(3000);
                vetorFiguras.add(figuras);
            } else if (p == 9) {
                //Nighty Dragon
                auxFigura = createSprite(R.drawable.p09, 1, 1);
                auxFigura.setScreenPercent(widthX, heigthY);
                auxFigura.bVisible = false;
                figuras.setCorpoFigura(auxFigura);
                figuras.setVersoFigura(auxVerso);
                figuras.setIdFigura(p);
                figuras.setPtsAtk(3000);
                figuras.setPtsDef(2500);
                vetorFiguras.add(figuras);
            } else if (p == 10) {
                //x head cannon
                auxFigura = createSprite(R.drawable.p10, 1, 1);
                auxFigura.setScreenPercent(widthX, heigthY);
                auxFigura.bVisible = false;
                figuras.setCorpoFigura(auxFigura);
                figuras.setVersoFigura(auxVerso);
                figuras.setIdFigura(p);
                figuras.setPtsAtk(1800);
                figuras.setPtsDef(1500);
                vetorFiguras.add(figuras);
            } else if (p == 11) {
                //y dragon head
                auxFigura = createSprite(R.drawable.p11, 1, 1);
                auxFigura.setScreenPercent(widthX, heigthY);
                auxFigura.bVisible = false;
                figuras.setCorpoFigura(auxFigura);
                figuras.setVersoFigura(auxVerso);
                figuras.setIdFigura(p);
                figuras.setPtsAtk(1600);
                figuras.setPtsDef(1800);
                vetorFiguras.add(figuras);
            } else if (p == 12) {
                auxFigura = createSprite(R.drawable.p12, 1, 1);
                auxFigura.setScreenPercent(widthX, heigthY);
                auxFigura.bVisible = false;
                figuras.setCorpoFigura(auxFigura);
                figuras.setVersoFigura(auxVerso);
                figuras.setIdFigura(p);
                figuras.setPtsAtk(1500);
                figuras.setPtsDef(1300);
                vetorFiguras.add(figuras);
            }

        }
    }

    //metodo que vai criar os placares
    private void criarMarcadores() {
        for (int i = 0; i < placar.length; i++) {
            placar[i] = createSprite(R.drawable.fonte, 4, 4);
            placar[i].setScreenPercent(4, 4);
            placar[i].bAutoRender = false;

            rtk[i] = createSprite(R.drawable.fonte, 4, 4);
            rtk[i].setScreenPercent(4, 4);
            rtk[i].bAutoRender = false;

            acert[i] = createSprite(R.drawable.fonte, 4, 4);
            acert[i].setScreenPercent(4, 4);
            acert[i].bAutoRender = false;

            err[i] = createSprite(R.drawable.fonte, 4, 4);
            err[i].setScreenPercent(4, 4);
            err[i].bAutoRender = false;

            cronometro[i] = createSprite(R.drawable.fonte, 4, 4);
            cronometro[i].setScreenPercent(4, 4);
            cronometro[i].bAutoRender = false;

            for (int quadro = 0; quadro < 10; quadro++) {
                rtk[i].addAnimation(1, true, quadro);
                placar[i].addAnimation(1, true, quadro);
                acert[i].addAnimation(1, true, quadro);
                err[i].addAnimation(1, true, quadro);
                cronometro[i].addAnimation(1, true, quadro);

            }


        }

    }

    //vai mudar o toque das musicas
    private void mudarMusica() {
        //para uma musica se estiver outra em execução
        if (AGSoundManager.vrMusic.equals(true))
            AGSoundManager.vrMusic.stop();

        if (estado == -2) {
            AGSoundManager.vrMusic.loadMusic("lose.mp3", false);
            AGSoundManager.vrMusic.play();
        } else if (estado == -1) {
            AGSoundManager.vrMusic.loadMusic("gameThemeL.mp3", true);
            AGSoundManager.vrMusic.play();
        } else if (estado == 0) {
            AGSoundManager.vrMusic.loadMusic("gameThemeN.mp3", true);
            AGSoundManager.vrMusic.play();
        } else if (estado == 1) {
            AGSoundManager.vrMusic.loadMusic("gameThemeW.mp3", true);
            AGSoundManager.vrMusic.play();
        } else if (estado == 2) {
            AGSoundManager.vrMusic.loadMusic("win.mp3", false);
            AGSoundManager.vrMusic.play();
        }
    }

    //mostra mensagem no final
    private void mostraFinal() {
        //faz o fundo preto que vai aparecer no final
        fgFundo = createSprite(R.drawable.bgr, 1, 1);
        fgFundo.setScreenPercent(100, 100);
        fgFundo.fAlpha = 0.4f;
        fgFundo.vrPosition.setXY(maxW / 2, maxH / 2);
        fgFundo.bVisible = true;
        //se ganhou
        if (estado == 2)
            fgFinal = createSprite(R.drawable.venceu, 1, 1);

        //se perdeu
        else if (estado == -2)
            fgFinal = createSprite(R.drawable.perdeu, 1, 1);

        fgFinal.setScreenPercent(80, 60);
        fgFinal.vrPosition.setXY(maxW / 2, maxH / 2);
        fgFinal.bVisible = true;
    }

}