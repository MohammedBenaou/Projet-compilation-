/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetcompilation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Rasfa
 */
public class ProjetCompilation {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
       
        String Nom_fichier="NDSL01";
        Automate Aut= new Automate(Nom_fichier+".descr");
        Automate Aut1= new Automate();
        ExpressionReguliere exp = new  ExpressionReguliere();
        //HashSet <String>tab = new HashSet();
        Scanner sc = new Scanner(System.in);
        Scanner choix = new Scanner(System.in);
        List<String>msg= new ArrayList();
        String saisie = "";
       
        String c = "";
        
        do{ 
        System.out.println("1.Affichage de l'Automate.");
        System.out.println("2.Traitement des entrees.");
        System.out.println("3.Exporter fichier .Dot.");
        System.out.println("4.Determinisation.");
        System.out.println("5.créer un automate");
        System.out.println("6.Quitter");
        System.out.println("Validez votre choix : ");
        c = choix.nextLine();
        System.out.println("---------------------------------------");
        if (c.equals("1"))
            
            Aut.AfficherAutomate();
        else if (c.equals("2")){
        
        boolean bool;
        
            System.out.println("Ecrivez votre message : ");
        do{
                
            do{
                saisie=sc.nextLine();
                bool = Aut.verification(saisie);
               
                if(bool == false)
                        System.out.println("Les Variable d'entree sont :"+Aut.getVentres());
            }while(bool == false);
                if(saisie.compareTo("###") !=0)
                     msg.add(saisie);
        }while (saisie.compareTo("###") !=0); 
                Aut.traitement(msg);
           
        }
        
        else if (c.equals("3")){
            
            Aut.genere_fichier_dot(Nom_fichier);
            System.out.println("--------------------------------------");
        }
        else if (c.equals("4")){
            Aut1=Aut.Determinisation();
                do{

                    System.out.println("=>1.Afficher l'Automate Deterministe.");
                    System.out.println("=>2.Afficher les SuperEtats.");
                    System.out.println("=>3.Générer fichier Dot.");
                    System.out.println("=>4.Retour.");
                    
                    c = choix.nextLine();
                    System.out.println("--------------------------------------");
                    if(c.equals("1"))
                        Aut1.AfficherAutomate();
                    else if (c.equals("2")){
                        for(int i = 0;i<Aut.Etattraites.size();i++){
                        System.out.println(Aut.Etattraites.get(i)+"<==>"+i);
                        }
                    }
                    else if(c.equals("3"))
                    {
                    Aut1.genere_dot_AFD(Nom_fichier+"AFD");
                    System.out.println("--------------------------------------");
                    }
                }while(!c.equals("4"));
        }
       
        
        else if (c.equals("5")){
           exp.generer_fichier();
           
        }
            
       }while(!c.equals("6"));
            
         
    }
}

    
    

