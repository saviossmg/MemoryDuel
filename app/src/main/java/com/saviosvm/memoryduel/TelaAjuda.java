package com.saviosvm.memoryduel;

import com.saviosvm.memoryduel.AndGraph.AGGameManager;
import com.saviosvm.memoryduel.AndGraph.AGInputManager;
import com.saviosvm.memoryduel.AndGraph.AGScene;
import com.saviosvm.memoryduel.AndGraph.AGScreenManager;
import com.saviosvm.memoryduel.AndGraph.AGSoundManager;
import com.saviosvm.memoryduel.AndGraph.AGSprite;

/**
 * Created by savio on 22/11/2016.
 */

public class TelaAjuda extends AGScene {

    //sprite dos botões dessa tela
    AGSprite fundoAjuda = null;
    AGSprite bRegras = null;
    AGSprite bHud = null;
    AGSprite bCartas = null;
    AGSprite bMenu = null;

    //tamanho maximo das telas
    int maxHY;
    int maxWX;

    //sons
    int codSom1 = 0;
    int codSom2 = 1;

    //construtor da classe (recebe o gerenciador)
    TelaAjuda(AGGameManager gerenciador){
        super(gerenciador);
    }

    @Override
    public void init(){
        this.setSceneBackgroundColor(0,0,0);

        maxHY = AGScreenManager.iScreenHeight;
        maxWX = AGScreenManager.iScreenWidth;

        fundoAjuda = createSprite(R.drawable.fundoajuda,1,1);
        fundoAjuda.setScreenPercent(100,100);
        fundoAjuda.vrPosition.setXY(maxWX/2, maxHY/2);
        fundoAjuda.bVisible = true;

        bRegras = createSprite(R.drawable.btnregras,1,1);
        bRegras.setScreenPercent(65,10);
        bRegras.vrPosition.setXY((maxWX/2),(maxHY/2)+(bRegras.getSpriteHeight()*2));
        bRegras.bVisible = true;

        bHud = createSprite(R.drawable.btnhud,1,1);
        bHud.setScreenPercent(65,10);
        bHud.vrPosition.setXY(maxWX/2, maxHY/2);

        bCartas = createSprite(R.drawable.btncartas,1,1);
        bCartas.setScreenPercent(65,10);
        bCartas.vrPosition.setXY(maxWX/2,(maxHY/2)-(bRegras.getSpriteHeight()*2));

        bMenu = createSprite(R.drawable.btnmenu,1,1);
        bMenu.setScreenPercent(65,10);
        bMenu.vrPosition.setXY(maxWX/2,bMenu.getSpriteHeight()/2);

        //fade in
        bRegras.fadeIn(2000);
        bHud.fadeIn(2000);
        bCartas.fadeIn(2000);
        bMenu.fadeIn(2000);

        codSom1 = AGSoundManager.vrSoundEffects.loadSoundEffect("accept.mp3");
        codSom2 = AGSoundManager.vrSoundEffects.loadSoundEffect("quit.mp3");

        if (!AGSoundManager.vrMusic.isPlaying()){
            AGSoundManager.vrMusic.loadMusic("tutorialTheme.mp3",true);
            AGSoundManager.vrMusic.play();
        }
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {

        //Se clicar em voltar, ele sai do jogo de volta pro menu
        if (AGInputManager.vrTouchEvents.backButtonClicked()) {
            AGSoundManager.vrMusic.stop();
            vrGameManager.setCurrentScene(1);
            return;
        }

        if(AGInputManager.vrTouchEvents.screenClicked()) {

            if (bRegras.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
//                AGSoundManager.vrSoundEffects.play(codSom1);
                vrGameManager.setCurrentScene(5);
                return;
            }
            if (bHud.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
//                AGSoundManager.vrSoundEffects.play(codSom1);
                vrGameManager.setCurrentScene(6);
                return;
            }
            if (bCartas.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
//                AGSoundManager.vrSoundEffects.play(codSom1);
                vrGameManager.setCurrentScene(7);
                return;
            }
            if (bMenu.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                AGSoundManager.vrMusic.stop();
//                AGSoundManager.vrSoundEffects.play(codSom2);
                vrGameManager.setCurrentScene(1);
                return;
            }
        }


    }
}
