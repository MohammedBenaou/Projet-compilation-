/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetcompilation;
/**
 *
 * @author Rasfa/Bennaou
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ExpressionReguliere extends Automate{
    private ArrayList<Character>valeur;
    public ExpressionReguliere(){
       Automate aut = new Automate();  
    }
     public String nom="";
    public ExpressionReguliere (String expression){
       super();
       this.expression="(a+b)((a)*+(ba)";
       nom="(a+b)((a)*+(ba)";
       this.valeur = new ArrayList<Character>();
    }
    
    public void detecter(String expression)
    {
       String[] lettres ={expression}; 
        
       
        for(int i=0;i<lettres.length;i++)
        {
           if( lettres[i].contains("(") || lettres[i].contains(")")||lettres[i].contains("+")||lettres[i].contains("*"))
               
               
         System.out.println(lettres[i].toString());
        }
            
    }
            
            
public boolean zero (String expression){
    String chaine;
    boolean resultat=false;
    if(expression.contains("(")){
        chaine = expression.replace("(", ":");
            chaine = chaine.replace(")*", ":*:");
            chaine = chaine.replace(")", ":");
            
            String decompose[] = chaine.split(":");
            System.out.println(chaine.toString());
            System.out.println(decompose.toString());
            for (int i = 0; i < decompose.length; i++) {

                if (resultat){
                    return resultat;
                }
            }
            return resultat;
    }
    //System.out.println(expression.contains('#'+""));
    
    return expression.contains('#'+"") || expression.contains('*'+"");
    }

    /*************Fonction qui permet de créer le fichier DESC************************/
    public void generer_fichier() throws FileNotFoundException, IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("////******* Création du fichier .descr *******\\\\");
        System.out.println("Entrer le Nom du fichier : ");
        String filename =sc.nextLine()+".descr";
        
        File fichier =new File(filename); 
        try{
            // Creation du fichier
            fichier .createNewFile();
            try ( // creation d'un writer (un Ã©crivain)
                    FileWriter writer = new FileWriter(fichier) // quoiqu'il arrive, on ferme le fichier
             ) {
          System.out.println(this.expression);
         Automate T = new Automate();
         T =this.Determinisation();
         
            writer.write("C \'"+nom+"'");
            writer.write("\r\n");
            writer.write("V \""+String.join("",T.ventres)+"\"");
            writer.write("\r\n");
            T.nbretats++;
             writer.write("O \"");
            writer.write("\r\n");
            writer.write("E "+T.nbretats);
             writer.write("\r\n");
             writer.write("I ");
             for(int t:T.getEtatinit()){
             writer.write(t+"");
             }
             writer.write("\r\n");
             writer.write("F ");
             for(int M:T.getEtataccp()){
             writer.write(M+" ");
             }
            writer.write("\r\n");
         for (int i=0; i<T.transition.size(); i++){
             String P;
             
                    Transition tran = T.transition.get(i);
                    if(tran.getEtataccp() == -1){
                        writer.write( "T "+
                            tran.getEtatdebut()+" "+"\'"+
                            tran.getEntree()+"\'"+ " "+
                             "P");
                    writer.write("\r\n");}
                    else {
                        writer.write( "T "+
                            tran.getEtatdebut()+" "+"\'"+
                            tran.getEntree() +"\'"+" "+
                             tran.getEtataccp());
                    writer.write("\r\n"); }                 
                    }
                        
            }
        } catch (Exception e) {
            System.out.println("Impossible de générer le fichier");
        }
    }
}
 