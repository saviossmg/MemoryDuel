package com.saviosvm.memoryduel;

import com.saviosvm.memoryduel.AndGraph.AGGameManager;
import com.saviosvm.memoryduel.AndGraph.AGInputManager;
import com.saviosvm.memoryduel.AndGraph.AGScene;
import com.saviosvm.memoryduel.AndGraph.AGScreenManager;
import com.saviosvm.memoryduel.AndGraph.AGSoundManager;
import com.saviosvm.memoryduel.AndGraph.AGSprite;

/**
 * Created by savio on 06/12/2016.
 */

public class MenuHud extends AGScene {

    //ATRIBUTOS DA CLASSE
    AGSprite r2 = null;
    AGSprite botaoSair = null;
    AGSprite fundoAjuda = null;
    AGSprite fgFundo = null;

    //tamanho maximo das telas
    int maxHY;
    int maxWX;

    //sons
    int codSom2 = 1;

    //construtor da classe (recebe o gerenciador)
    MenuHud(AGGameManager gerenciador){
        super(gerenciador);
    }

    @Override
    public void init() {
        this.setSceneBackgroundColor(0,0,0);

        maxHY = AGScreenManager.iScreenHeight;
        maxWX = AGScreenManager.iScreenWidth;

        fundoAjuda = createSprite(R.drawable.fundoajuda,1,1);
        fundoAjuda.setScreenPercent(100,100);
        fundoAjuda.vrPosition.setXY(maxWX/2, maxHY/2);
        fundoAjuda.bVisible = true;

        fgFundo = createSprite(R.drawable.bgr, 1, 1);
        fgFundo.setScreenPercent(100, 100);
        fgFundo.fAlpha = 0.6f;
        fgFundo.vrPosition.setXY(maxWX / 2, maxHY / 2);
        fgFundo.bVisible = true;

        r2 = createSprite(R.drawable.hud,1,1);
        r2.setScreenPercent(100,45);
        r2.vrPosition.setXY((maxWX/2),(maxHY/2)+(r2.getSpriteHeight()*0.25f));

        botaoSair = createSprite(R.drawable.btnmenu,1,1);
        botaoSair.setScreenPercent(50,10);
        botaoSair.vrPosition.setXY(maxWX/2,(botaoSair.getSpriteHeight()/2));

        //fade in
        r2.fadeIn(2000);
        botaoSair.fadeIn(2000);

        codSom2 = AGSoundManager.vrSoundEffects.loadSoundEffect("quit.mp3");
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
            vrGameManager.setCurrentScene(3);
            return;
        }

        if(AGInputManager.vrTouchEvents.screenClicked()) {

            if (botaoSair.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
//                AGSoundManager.vrSoundEffects.play(codSom2);
                vrGameManager.setCurrentScene(3);
                return;
            }
        }

    }
}
