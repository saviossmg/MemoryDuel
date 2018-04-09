package com.saviosvm.memoryduel;

import com.saviosvm.memoryduel.AndGraph.AGGameManager;
import com.saviosvm.memoryduel.AndGraph.AGInputManager;
import com.saviosvm.memoryduel.AndGraph.AGScene;
import com.saviosvm.memoryduel.AndGraph.AGScreenManager;
import com.saviosvm.memoryduel.AndGraph.AGSoundManager;
import com.saviosvm.memoryduel.AndGraph.AGSprite;

/**
 * Created by savio on 03/11/2016.
 */


public class TelaMenu extends AGScene
{
    //ATRIBUTOS DA CLASSE
    AGSprite botaoSobre = null;
    AGSprite botaoInstrucoes = null;
    AGSprite botaoPlay = null;
    AGSprite botaoSair = null;
    AGSprite fundoMenu = null;
    AGSprite ttl = null;

    //sons
    int codSom1 = 0;
    int codSom2 = 1;

    //CONSTRUTOR DA CLASSE (SEMPRE RECEBE O GERENCIADOR)
    TelaMenu(AGGameManager gerenciador)
    {
        super(gerenciador);
    }

    @Override
    public void init()
    {
        //coloca o que será necessário para a cena
        //configura a cor da cena para AMARELO (rgb)
        this.setSceneBackgroundColor(0,0,0);

        fundoMenu = createSprite(R.drawable.fundomenu,1,1);
        fundoMenu.setScreenPercent(100,100);
        fundoMenu.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/2);
        fundoMenu.bVisible = true;

        ttl = createSprite(R.drawable.ttl,1,1);
        ttl.setScreenPercent(75,10);
        ttl.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight - ttl.getSpriteHeight()/2);
        ttl.bVisible = true;

        //cria um elemento visual posicionado no centro da tela (20%, 10%)
        botaoPlay = createSprite(R.drawable.iniciar, 1, 1);
        botaoPlay.setScreenPercent(65, 10);
        botaoPlay.vrPosition.setXY(AGScreenManager.iScreenWidth/2, (float) (AGScreenManager.iScreenHeight*0.8));
        botaoPlay.fAlpha = 0.5f;

        botaoInstrucoes = createSprite(R.drawable.ajuda, 1, 1);
        botaoInstrucoes.setScreenPercent(65, 10);
        botaoInstrucoes.vrPosition.setXY(AGScreenManager.iScreenWidth/2, (float) (AGScreenManager.iScreenHeight*0.6));
        botaoInstrucoes.fAlpha = 0.5f;

        botaoSobre = createSprite(R.drawable.sobre, 1, 1);
        botaoSobre.setScreenPercent(65, 10);
        botaoSobre.vrPosition.setXY(AGScreenManager.iScreenWidth/2, (float) (AGScreenManager.iScreenHeight*0.4));
        botaoSobre.fAlpha = 0.5f;

        botaoSair = createSprite(R.drawable.sair, 1, 1);
        botaoSair.setScreenPercent(65, 10);
        botaoSair.vrPosition.setXY(AGScreenManager.iScreenWidth/2, (float) (AGScreenManager.iScreenHeight*0.2));
        botaoSair.fAlpha = 0.5f;

        //fade in
        botaoPlay.fadeIn(2000);
        botaoInstrucoes.fadeIn(2000);
        botaoSobre.fadeIn(2000);
        botaoSair.fadeIn(2000);


        codSom1 = AGSoundManager.vrSoundEffects.loadSoundEffect("accept.mp3");
        codSom2 = AGSoundManager.vrSoundEffects.loadSoundEffect("quit.mp3");

        if(!AGSoundManager.vrMusic.isPlaying()){
            AGSoundManager.vrMusic.loadMusic("menuTheme.mp3",true);
            AGSoundManager.vrMusic.play();
        }


        //finsounds.com
    }

    @Override
    //QUANDO A APLICAÇÃO VOLTA DE INTERRUPÇÃO
    public void restart()
    {
        //fazer o que for necessário para continuar a execução
    }

    @Override
    //QUANDO A APLICAÇÃO PARA
    public void stop()
    {
        //fazer o que for necessário para interromper a aplicação
    }

    @Override
    //CHAMADO X VEZES POR SEGUNDO
    public void loop()
    {
        if(AGInputManager.vrTouchEvents.screenClicked()){

            if(botaoPlay.collide(AGInputManager.vrTouchEvents.getLastPosition()))
            {
//                AGSoundManager.vrSoundEffects.play(codSom1);
                AGSoundManager.vrMusic.stop();
                vrGameManager.setCurrentScene(2);
                return;
            }
            if(botaoInstrucoes.collide(AGInputManager.vrTouchEvents.getLastPosition()))
            {
//                AGSoundManager.vrSoundEffects.play(codSom1);
                AGSoundManager.vrMusic.stop();
                vrGameManager.setCurrentScene(3);
                return;
            }
            if(botaoSobre.collide(AGInputManager.vrTouchEvents.getLastPosition()))
            {
//                AGSoundManager.vrSoundEffects.play(codSom1);
                AGSoundManager.vrMusic.stop();
                vrGameManager.setCurrentScene(4);
                return;
            }
            if(botaoSair.collide(AGInputManager.vrTouchEvents.getLastPosition()))
            {
//                AGSoundManager.vrSoundEffects.play(codSom2);
                AGSoundManager.vrMusic.stop();
                vrGameManager.vrActivity.finish();
            }
        }
    }

}
