package com.saviosvm.memoryduel;

/**
 * Created by savio on 03/11/2016.
 */
import com.saviosvm.memoryduel.AndGraph.AGGameManager;
import com.saviosvm.memoryduel.AndGraph.AGInputManager;
import com.saviosvm.memoryduel.AndGraph.AGScene;
import com.saviosvm.memoryduel.AndGraph.AGScreenManager;
import com.saviosvm.memoryduel.AndGraph.AGSoundManager;
import com.saviosvm.memoryduel.AndGraph.AGSprite;
import com.saviosvm.memoryduel.AndGraph.AGTimer;

public class TelaAbertura extends AGScene {

    AGTimer temporizador = null;
    AGSprite logoSi = null;
    AGSprite logoUnitins = null;
    int tempo;

    TelaAbertura(AGGameManager gerenciador){
        super(gerenciador);
    }

    //usado para ser chamada cena
    @Override
    public void init() {
        //cor de fundo da cena vermelha
        this.setSceneBackgroundColor(1,1,1);

        //maximo X e Y da tela
        int x = AGScreenManager.iScreenWidth;
        int y = AGScreenManager.iScreenHeight;

        //cria temporizador de 8 segundos
        tempo = 8000;
        temporizador = new AGTimer(tempo);

        //logo Unitins
        logoUnitins = createSprite(R.drawable.logounitins,1,1);
        logoUnitins.setScreenPercent(75,25);
        logoUnitins.vrPosition.setXY(x/2,y*0.75f);
        logoUnitins.fadeIn(tempo/2);

        //logo SI
        logoSi = createSprite(R.drawable.logosi,1,1);
        logoSi.setScreenPercent(50,50);
        logoSi.vrPosition.setXY(x/2,y - (y*0.75f));
        logoSi.fadeIn(tempo/2);

        AGSoundManager.vrMusic.loadMusic("menuTheme.mp3",true);
        AGSoundManager.vrMusic.play();
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
        temporizador.update();

        if( logoSi.fadeEnded()){
            logoSi.fadeOut((tempo/2)-500);
            logoUnitins.fadeOut((tempo/2)-500);
        }
        if (temporizador.isTimeEnded()){
            vrGameManager.setCurrentScene(1);
            return;
        }

        if (AGInputManager.vrTouchEvents.screenClicked()){
            vrGameManager.setCurrentScene(1);
            return;
        }
    }
}
