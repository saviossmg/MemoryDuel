package com.saviosvm.memoryduel;

import android.os.Bundle;
import com.saviosvm.memoryduel.AndGraph.AGGameManager;
import com.saviosvm.memoryduel.AndGraph.AGActivityGame;

public class Principal extends AGActivityGame {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //instancia o objeto responsavel pelo gerencimento de cenas
        //parametro booleano serve apenas para projetos que usam
        //acelerometro e serão testados no aparelho
        vrManager = new AGGameManager(this,false);

        //inicializando as telas das cenase add o gerenciador
        TelaAbertura abertura = new TelaAbertura(vrManager);
        TelaMenu menu = new TelaMenu(vrManager);
        TelaJogo jogo = new TelaJogo(vrManager);
        TelaAjuda ajuda = new TelaAjuda(vrManager);
        TelaSobre sobre = new TelaSobre(vrManager);
        MenuRegras mRegras = new MenuRegras(vrManager);
        MenuHud mHud = new MenuHud(vrManager);
        MenuCartas mCartas = new MenuCartas(vrManager);

        //registrando as cenas no regente de cenas
        vrManager.addScene(abertura);//responde pelo indice 0
        vrManager.addScene(menu);//responde pelo indice 1
        vrManager.addScene(jogo);//responde pelo indice 2
        vrManager.addScene(ajuda);//responde pelo indice 3
        vrManager.addScene(sobre);//responde pelo indice 4
        vrManager.addScene(mRegras);//responde pelo indice 5
        vrManager.addScene(mHud);//responde pelo indice 6
        vrManager.addScene(mCartas);//responde pelo indice 7

    }
}
