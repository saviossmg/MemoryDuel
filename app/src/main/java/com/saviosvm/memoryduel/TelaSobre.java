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

public class TelaSobre extends AGScene {

    //Sprites
    AGSprite fundo = null;
    AGSprite texto = null;
    AGSprite logo = null;

    int maxHY;
    int maxWX;

    //construtor da classe (recebe o gerenciador)
    TelaSobre(AGGameManager gerenciador){
        super(gerenciador);
    }

    @Override
    public void init() {
        maxHY = AGScreenManager.iScreenHeight;
        maxWX = AGScreenManager.iScreenWidth;

        fundo = createSprite(R.drawable.fundoabout,1,1);
        fundo.setScreenPercent(100,100);
        fundo.vrPosition.setXY(maxWX/2, maxHY/2);
        fundo.bVisible = true;

        texto = createSprite(R.drawable.about,1,1);
        texto.setScreenPercent(100,80);
        texto.vrPosition.setXY(maxWX/2, maxHY - texto.getSpriteHeight()/2);

        logo = createSprite(R.drawable.logounitins,1,1);
        logo.setScreenPercent(70,20);
        logo.vrPosition.setXY(maxWX/2,logo.getSpriteHeight()/2);

        //fade in
        texto.fadeIn(3000);
        logo.fadeIn(3000);

        AGSoundManager.vrMusic.loadMusic("aboutTheme.mp3",true);
        AGSoundManager.vrMusic.play();

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

        if (AGInputManager.vrTouchEvents.screenClicked()){
            AGSoundManager.vrMusic.stop();
            vrGameManager.setCurrentScene(1);
            return;
        }

    }
}
