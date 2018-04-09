package com.saviosvm.memoryduel;

import com.saviosvm.memoryduel.AndGraph.AGAnimation;
import com.saviosvm.memoryduel.AndGraph.AGSprite;

/**
 * Created by savio on 23/11/2016.
 */

public class Figura {

    private AGSprite corpoFigura;
    private AGSprite versoFigura;
    private int idFigura;
    private int ptsAtk;
    private int ptsDef;
    private boolean clicada;
    private boolean contabilizada;

    //CONSTRUTOR
    public Figura() {
        this.corpoFigura = corpoFigura;
        this.versoFigura = versoFigura;
        this.idFigura = idFigura;
        this.ptsAtk = ptsAtk;
        this.ptsDef = ptsDef;
        this.clicada = clicada;
        this.contabilizada = contabilizada;
    }

    //GETTERS

    public AGSprite getCorpoFigura() {
        return corpoFigura;
    }

    public AGSprite getVersoFigura() {
        return versoFigura;
    }

    public int getIdFigura() {
        return idFigura;
    }

    public int getPtsAtk() {
        return ptsAtk;
    }

    public int getPtsDef() {
        return ptsDef;
    }

    public boolean isClicada() {
        return clicada;
    }

    public boolean isContabilizada() {
        return contabilizada;
    }

    //SETTERS
    public void setCorpoFigura(AGSprite corpoFigura) {
        this.corpoFigura = corpoFigura;
    }

    public void setVersoFigura(AGSprite versoFigura) {
        this.versoFigura = versoFigura;
    }

    public void setIdFigura(int idFigura) {
        this.idFigura = idFigura;
    }

    public void setPtsAtk(int ptsAtk) {
        this.ptsAtk = ptsAtk;
    }

    public void setPtsDef(int ptsDef) {
        this.ptsDef = ptsDef;
    }

    public void setClicada(boolean clicada) {
        this.clicada = clicada;
    }

    public void setContabilizada(boolean contabilizada) {
        this.contabilizada = contabilizada;
    }
}
