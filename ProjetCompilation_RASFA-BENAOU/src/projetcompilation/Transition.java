/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetcompilation;

/**
 *
 * @author Rasfa
 */
public class Transition {
    private  int etatdebut;
    private String entree;
    private int etataccp;
    private String sortie;

    
public  Transition()   { 
    
    
}
public String getEntree(){
    return entree;
}
public int getEtatdebut(){
    return etatdebut;
}
public int getEtataccp(){
    return etataccp;
}
public String getSortie(){
    return sortie;
}
public void setEntree(String alphaentree ){
    this.entree=alphaentree;
}
public void setEtatdebut(int etatdebut){
    this.etatdebut=etatdebut;
}
public void setEtataccp(int etatfin){
    this.etataccp=etatfin;
}

public void setSortie (String alphasortie){
    this.sortie=alphasortie;
}
        
}
