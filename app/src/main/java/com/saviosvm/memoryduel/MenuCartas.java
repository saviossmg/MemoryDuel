package com.saviosvm.memoryduel;

import com.saviosvm.memoryduel.AndGraph.AGGameManager;
import com.saviosvm.memoryduel.AndGraph.AGInputManager;
import com.saviosvm.memoryduel.AndGraph.AGScene;
import com.saviosvm.memoryduel.AndGraph.AGScreenManager;
import com.saviosvm.memoryduel.AndGraph.AGSoundManager;
import com.saviosvm.memoryduel.AndGraph.AGSprite;

import java.util.ArrayList;

/**
 * Created by savio on 06/12/2016.
 */

public class MenuCartas extends AGScene {

    //contadores de ponto
    AGSprite[] ataque = null;
    AGSprite[] defesa = null;
    AGSprite auxFigura = null;

    //outras figuras
    AGSprite btnVoltar = null;
    AGSprite ataqueL = null;
    AGSprite defesaL = null;
    AGSprite fundoAjuda = null;
    AGSprite fgFundo = null;

    //Objetos
    Figura figuras = null;
    ArrayList<Figura> vetorFiguras = null;
    int clickFg, clickAux;

    //proporções da figura
    int widthX;
    int heigthY;
    int maxHY;
    int maxWX;

    //posicionadores
    int desHY;
    int desWX;
    int posHY;
    int posWX;

    //sons
    int back = 0;
    int shuff = 1;

    //construtor da classe (recebe o gerenciador)
    MenuCartas(AGGameManager gerenciador){
        super(gerenciador);
    }

    @Override
    public void init() {
        //CAPTURA O TAMANHO MAXIMO E MINIMO
        maxWX = AGScreenManager.iScreenWidth;
        maxHY = AGScreenManager.iScreenHeight;

        fundoAjuda = createSprite(R.drawable.fundoajuda,1,1);
        fundoAjuda.setScreenPercent(100,100);
        fundoAjuda.vrPosition.setXY(maxWX/2, maxHY/2);
        fundoAjuda.bVisible = true;

        fgFundo = createSprite(R.drawable.bgr, 1, 1);
        fgFundo.setScreenPercent(100, 100);
        fgFundo.fAlpha = 0.6f;
        fgFundo.vrPosition.setXY(maxWX / 2, maxHY / 2);
        fgFundo.bVisible = true;


        //DEFINE AS PROPORÇÕES DAS FIGURAS
        widthX = 25;
        heigthY = 16;

        //CHAMA O METODO DE CRIAR AS FIGURAS
        vetorFiguras = new ArrayList<>();
        criarFiguras();

        //CHAMA O METODO DE CRIAR O PLACAR
        ataque = new AGSprite[4];
        defesa = new AGSprite[4];
        criarContadores();

        //CHAMA O METODO PARA DESENHAR AS CARTAS
        desenharCartas();

        //CHAMA O METODO PARA DESENHAR CONTADORES
        desenharContador();

        //DESEHA O BOTÃO DE VOLTAR
        btnVoltar = createSprite(R.drawable.btnmenu,1,1);
        btnVoltar.setScreenPercent(40,10);
        btnVoltar.vrPosition.setXY(maxWX/2,(btnVoltar.getSpriteHeight()/2));

        //DESEHA OS LABELS
        ataqueL = createSprite(R.drawable.ataque,1,1);
        ataqueL.setScreenPercent(40,10);
        ataqueL.vrPosition.setXY(maxWX/4, maxHY - (ataqueL.getSpriteHeight()/2));

        defesaL = createSprite(R.drawable.defesa,1,1);
        defesaL.setScreenPercent(40,10);
        defesaL.vrPosition.setXY(maxWX*0.75f, maxHY - (defesaL.getSpriteHeight()/2));

        //fade in
        ataqueL.fadeIn(2000);
        defesaL.fadeIn(2000);
        btnVoltar.fadeIn(2000);

        //CHAMA OS SONS
        shuff = AGSoundManager.vrSoundEffects.loadSoundEffect("shuffle.mp3");
        back =  AGSoundManager.vrSoundEffects.loadSoundEffect("quit.mp3");

        //INCIALIZA O CLICK
        clickFg = 0;
        clickAux = 0;


    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {

        if (AGInputManager.vrTouchEvents.backButtonClicked()) {
//            AGSoundManager.vrSoundEffects.play(back);
            vrGameManager.setCurrentScene(3);
            return;
        }

        if(AGInputManager.vrTouchEvents.screenClicked()) {

            if (vetorFiguras.get(0).getCorpoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                clickFg = 0;

            if (vetorFiguras.get(1).getCorpoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                clickFg = 1;

            if (vetorFiguras.get(2).getCorpoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                clickFg = 2;

            if (vetorFiguras.get(3).getCorpoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                clickFg = 3;

            if (vetorFiguras.get(4).getCorpoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                clickFg = 4;

            if (vetorFiguras.get(5).getCorpoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                clickFg = 5;

            if (vetorFiguras.get(6).getCorpoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                clickFg = 6;

            if (vetorFiguras.get(7).getCorpoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                clickFg = 7;

            if (vetorFiguras.get(8).getCorpoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                clickFg = 8;

            if (vetorFiguras.get(9).getCorpoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                clickFg = 9;

            if (vetorFiguras.get(10).getCorpoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                clickFg = 10;

            if (vetorFiguras.get(11).getCorpoFigura().collide(AGInputManager.vrTouchEvents.getLastPosition()))
                clickFg = 11;

            if(clickFg != clickAux){
                AGSoundManager.vrSoundEffects.play(shuff);
                clickAux = clickFg;
                atualizarContador();
            }

            if (btnVoltar.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
//                AGSoundManager.vrSoundEffects.play(back);
                vrGameManager.setCurrentScene(3);
                return;
            }
        }
    }

    //renderiza os placares
    public void render() {
        super.render();
        for (int i=0; i< ataque.length;i++){
            ataque[i].render();
            defesa[i].render();
        }
    }

    //metodo que vai atualizar o contador
    private void atualizarContador(){
        int axnA, axnD;
        //verifica se o valor nao é 0, pois nao se pode dividir por 0
        axnA = vetorFiguras.get(clickFg).getPtsAtk();
        axnD = vetorFiguras.get(clickFg).getPtsDef();

        if(axnA > 0){
            ataque[0].setCurrentAnimation(axnA / 1000);
            ataque[1].setCurrentAnimation((axnA % 1000) / 100);
            ataque[2].setCurrentAnimation((axnA % 100) / 10);
            ataque[3].setCurrentAnimation(axnA % 10);
        }
        else{
            for (AGSprite dd : ataque)
                dd.setCurrentAnimation(0);
        }

        if(axnD > 0){
            defesa[0].setCurrentAnimation(axnD / 1000);
            defesa[1].setCurrentAnimation((axnD % 1000) / 100);
            defesa[2].setCurrentAnimation((axnD % 100) / 10);
            ataque[3].setCurrentAnimation(axnD % 10);
        }
        else{
            for (AGSprite dd : defesa)
                dd.setCurrentAnimation(0);
        }
    }

    //metodo que vai popular os arrays
    private void criarFiguras() {
        int p = 1;
        for(int i = 0; i < 12; i++){
            //Cria um novo objeto figura
            figuras = new Figura();
            auxFigura = null;
            if (p == 1) {
                //Blue Eyes
                auxFigura = createSprite(R.drawable.p01, 1, 1);
                figuras.setPtsAtk(3000);
                figuras.setPtsDef(2500);
            }
            if (p == 2) {
                //Dark Magician
                auxFigura = createSprite(R.drawable.p02, 1, 1);
                figuras.setPtsAtk(2500);
                figuras.setPtsDef(2100);
            }
            if (p == 3) {
                //black magician girl
                auxFigura = createSprite(R.drawable.p03, 1, 1);
                figuras.setPtsAtk(2100);
                figuras.setPtsDef(1700);
            }
            if (p == 4) {
                //Magician's Valkiryan
                auxFigura = createSprite(R.drawable.p04, 1, 1);
                figuras.setPtsAtk(1600);
                figuras.setPtsDef(1800);
            }
            if (p == 5) {
                //Red Eyes Black Dragon
                auxFigura = createSprite(R.drawable.p05, 1, 1);
                figuras.setPtsAtk(2400);
                figuras.setPtsDef(2000);
            }
            if (p == 6) {
                //Gaia The Fierce
                auxFigura = createSprite(R.drawable.p06, 1, 1);
                figuras.setPtsAtk(2300);
                figuras.setPtsDef(2100);
            }
            if (p == 7) {
                //Alexandrite Dragon
                auxFigura = createSprite(R.drawable.p07, 1, 1);
                auxFigura.bVisible = false;
                figuras.setPtsAtk(2000);
                figuras.setPtsDef(100);
            }
            if (p == 8) {
                //milenium shield
                auxFigura = createSprite(R.drawable.p08, 1, 1);
                figuras.setPtsAtk(0);
                figuras.setPtsDef(3000);
            }
            if (p == 9) {
                //Nighty Dragon
                auxFigura = createSprite(R.drawable.p09, 1, 1);
                figuras.setPtsAtk(3000);
                figuras.setPtsDef(2500);
            }
            if (p == 10) {
                //x head cannon
                auxFigura = createSprite(R.drawable.p10, 1, 1);
                figuras.setPtsAtk(1800);
                figuras.setPtsDef(1500);
            }
            if (p == 11) {
                //y dragon head
                auxFigura = createSprite(R.drawable.p11, 1, 1);
                figuras.setPtsAtk(1600);
                figuras.setPtsDef(1800);
            }
            if (p == 12) {
                auxFigura = createSprite(R.drawable.p12, 1, 1);
                figuras.setPtsAtk(1500);
                figuras.setPtsDef(1300);
            }
            //informações globais da figura
            auxFigura.setScreenPercent(widthX, heigthY);
            auxFigura.bVisible = false;
            figuras.setCorpoFigura(auxFigura);
            figuras.setIdFigura(p);
            //adiciona no vetor
            vetorFiguras.add(figuras);
            //Incrementa o P
            p++;
        }
    }

    //metodo que vai criar os contadores
    private void criarContadores(){
        for (int i = 0; i < ataque.length; i++) {
            ataque[i] = createSprite(R.drawable.fonte, 4, 4);
            ataque[i].setScreenPercent(10, 10);
            ataque[i].bAutoRender = false;

            defesa[i] = createSprite(R.drawable.fonte, 4, 4);
            defesa[i].setScreenPercent(10, 10);
            defesa[i].bAutoRender = false;

            for (int quadro = 0; quadro < 10; quadro++) {
                ataque[i].addAnimation(1, true, quadro);
                defesa[i].addAnimation(1, true, quadro);
            }
        }
    }

    //metodo que vai desenhar as cartas na tela
    private void desenharCartas(){
        //Variaveis de controle para controlar o desenho
        int auxX = 0;
        int eixoX = 4;
        //A PARTIR DO PRIMEIRO ELEMENTO DO ARRAYU QUE FOI POPULADO
        desHY = (int) (vetorFiguras.get(0).getCorpoFigura().getSpriteHeight() / 2);
        desWX = (int) (vetorFiguras.get(0).getCorpoFigura().getSpriteWidth() / 2);
        posHY = desHY*2;
        posWX = desWX;

        //LAÇO RENDERIZADOR
        for (Figura renderFg : vetorFiguras) {
            renderFg.getCorpoFigura().vrPosition.setXY(posWX, posHY);
            renderFg.getCorpoFigura().bVisible = true;
            posWX += (desWX * 2);
            auxX++;
            //sobe a figura
            if (auxX == eixoX) {
                posHY += (desHY * 2);
                posWX = (desWX);
                auxX = 0;
            }
        }
    }

    //metodo que vai desenhar as cartas na tela
    private void desenharContador(){
        //Coordenadas X serão especificas
        int xA, xD;
        //Posições iniciais X de cada contador
        xA = (int) ((maxWX/4)-( ataque[0].getSpriteWidth()*1.5f));
        xD = (int) ((maxWX*0.75f) - ( defesa[0].getSpriteWidth()*1.5f));
        posHY = maxHY - (maxHY/4);

        //Percore toda a linha desenhando
        for (int i = 0; i < ataque.length; i++) {
            ataque[i].vrPosition.setXY(xA,posHY);
            defesa[i].vrPosition.setXY(xD,posHY);
            xA += ataque[i].getSpriteWidth();
            xD += defesa[i].getSpriteWidth();
        }


    }

}
