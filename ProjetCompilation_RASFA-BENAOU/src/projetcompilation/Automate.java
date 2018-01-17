/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetcompilation;
/**
 *
 * @author Mohammed
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Automate {
    public ArrayList<String>  ventres = new ArrayList(); /*Conteneur du vocabulaire d'entrée*/
    private ArrayList<String>  Nom = new ArrayList();  /*Conteneur du nom de l'automate*/
    private HashSet<String> sorties = new HashSet(); /*Conteneur du vocabulaire de sortie*/
    public int nbretats; /*Nombre d'état du conteneur*/
    public String expression; /*Conteneur de l'expression régulière*/
    private HashSet<Integer> etatinit = new HashSet(); /*Conteneur des états initiaux*/    
    private HashSet<Integer> etataccp = new HashSet();/*Conteneur des états acceptants*/
    public List<Transition> transition = new ArrayList(); /*Conteneur des Transitions*/    
    private String lambda; /*Conteneur du metacaractère*/
    public ArrayList <ArrayList<Integer>>  Etattraites = new ArrayList(); /*Conteneur des états traités*/   
//------------ l'ensemble des accesseurs ------------ //  
    
    public ArrayList<String> getNom(){  // récupération du nom d'automate
    return this.Nom;
 }
public ArrayList<String> getVentres(){ // récupération du vocabulaire d'entrée
    return this.ventres;
 }
 public HashSet<String> getSortie(){  // récupération du vocabulaire de sortie
    return this.sorties;
 }
 public int getNbretats(){   // récupération du nombre d'etat
    return this.nbretats;
 }
 public HashSet<Integer> getEtatinit(){  // récupération d'etat(s) initial (s)
    return this.etatinit;
 }
 public List<Transition> getTran(){    // récupération d'ensemble de transitions
    return this.transition;
 }
  public HashSet<Integer> getEtataccp(){ // récupération d'etat(s) final (s)
    return this.etataccp;
 }
 public String getLambda(){  // récupération du metacaractère  
    return this.lambda; 
 }
 public void setEtataccp(HashSet<Integer> list){
 
 this.etataccp = list;
 }
 public Automate(){
 }
    //methode pour lire le fichier 
    public Automate (String fileName) throws FileNotFoundException, IOException{
              // on effectue la lecture à l'aide du BufferedReader
             // l'utilisation d'éxpessions régulières vu en site openclassrooms
            // la vérification est faite à l'aide du site regex
            BufferedReader buff;  
            Pattern m =Pattern.compile("^M\\s*(#)$");
            Pattern v =Pattern.compile("^V\\s*\"([0-1a-zA-Z]+)\"$");
            Pattern c =Pattern.compile("^C\\s*([0-9]+)\\s*'([a-zA-Z0-1#])'\\s*([0-9]+)\\s*(?:'([a-zA-Z0-9])')?$");
            Pattern etatI = Pattern.compile("^I\\s?([0-9\\s?]+)$");
            Pattern etatF = Pattern.compile("^F\\s?([0-9\\s?]+)$");
            Pattern nbret = Pattern.compile("^E\\s?([0-9]+)$");
            Pattern o = Pattern.compile("^O\\s*\"([a-zA-Z0-1]+)\"$");
            Pattern transi = Pattern.compile("^T\\s*([0-9]+)\\s*'([a-zA-Z0-1#])'\\s*([0-9]+)\\s*(?:'([a-zA-Z0-9#])')?$");
try{
            // la fonction pour lire un fichier ligne par ligne 
            buff=new BufferedReader (new InputStreamReader(new FileInputStream(fileName)));   
            String ligne;
            int i;
            this.etataccp= new HashSet();
        
    while((ligne = buff.readLine())!=null){
        Transition tran = new Transition();
            
     // on test si le premier caractere est M et on prend le 3eme caractere de la ligne et on le stocke dans l'attribut lambda   
            if(ligne.split(" ")[0].equals("M")){  
                Matcher match = m.matcher(ligne);
               if(match.lookingAt())
                this.lambda = match.group(1);
               
              
            }
            
     // on teste si le premier caractere est C qui est de l'entree et on prend le 3eme caractere de la ligne et on le stocke dans l'attribut ( les inputs)   
           
            if(ligne.split(" ")[0].charAt(0)=='C'){
                
                this.Nom.add(ligne.split("\'")[1]);
            }
           
            if(ligne.split(" ")[0].equals("V")){
                    Matcher match = v.matcher(ligne);
                     if(match.lookingAt()) { 
                        for( i=0;i<match.group(1).length();i++)
                        {
                            this.ventres.add(match.group(1).substring(i,i+1));
                        }
                        
                    }
                
                
            }
            //on teste si le premier caractere est O et on prend le 3eme caractere de la ligne et on le stoke dans l'attribut des sorties.       
            else if(ligne.split(" ")[0].equals("O")){
               Matcher match = o.matcher(ligne);
                     if(match.lookingAt()) { 
                        for( i=0;i<match.group(1).length();i++)
                        {
                            this.sorties.add(match.group(1).substring(i,i+1));
                        }
                        
                    }
                     
            }
            // on teste si le premier caractere est E et on prend le 3eme caractere de la ligne et on le stoke dans l'attribut nombre d'etat
            else if (ligne.split(" ")[0].equals("E")){
                Matcher match = nbret.matcher(ligne);
                     if(match.lookingAt()) 
                        
                         this.nbretats = Integer.parseInt(match.group(1));
                 
            }
            // on teste si le premier caractere est I et on prend le 3eme caractere de la ligne et on le stoke dans l'attribut de l'etat initial

            else if (ligne.split(" ")[0].equals("I")){
                this.etatinit.clear();
                Matcher match = etatI.matcher(ligne);
                     if(match.lookingAt()) { 
                          String str[]=match.group(1).split(" ");
                        for( int j=0;j<str.length;j++)
                        {
                           this.etatinit.add(Integer.parseInt(str[j]));
                        }
                       
                     }
                     
                     
            }  
               if  (this.etatinit.size()==0)   // on prend 0 comme état initial si le fichie ne contient pas I 
                     {
                          this.etatinit.add(0);
                     }         
             
           // on teste si le premier caractere est F et on prend le 3eme caractere de la ligne et on le stoke dans l'attribut de l'état Final
             else if (ligne.split(" ")[0].equals("F")){
                  Matcher match = etatF.matcher(ligne);
                     if(match.lookingAt()) { 
                          String str[]=match.group(1).split(" ");
                        for( i=0;i<str.length;i++)
                        {
                           this.etataccp.add(Integer.parseInt(str[i]));
                        }
                        
                    }
                    
             }
            // // on teste si le premier caractere est E et on prend le 3eme caractere de la ligne et on le stoke dans l'attribut des Transitions 
             else if (ligne.split(" ")[0].equals("T")){
                   Matcher match = transi.matcher(ligne);
                    if(match.lookingAt()) {
                        
                         Transition trans = new Transition();
                         trans.setEtatdebut(Integer.parseInt(match.group(1)));
                         trans.setEntree((match.group(2).substring(0,1)));
                         trans.setEtataccp(Integer.parseInt(match.group(3)));
                         if(match.group(4) != null)
                         {
                             trans.setSortie(match.group(4).substring(0,1));
                             if(trans.getSortie().charAt(0)== '#')
                                    this.lambda="#";
                         }
                            if(trans.getEntree().charAt(0) == '#' )
                                            this.lambda="#";
                             
                         this.transition.add(trans);
                         
             
                    }
             
             }
             
    }//fin while
  
}catch(FileNotFoundException e){  // si le programme n'a pas pu trouver le fichier 
        System.out.println("Fichier non trouvé");
        System.exit(1);
        
}
}//fin methode lire fichier 
    
    public void AfficherAutomate() throws IOException{ // methode de l'affichage de l'automate
                
            Transition transition; //variable temporaire
            
                System.out.println("Le nom de l'automate est : "+this.Nom.toString());
                System.out.println("Meta-Caractere : "+this.lambda); // affichage du metacaractère           
                System.out.println("les entrees sont : "+this.ventres.toString()); // affichage des entree           
                System.out.println("les sorties sont : "+this.sorties.toString());// affichage des sorties              
                System.out.println("nombre d'etats : "+this.nbretats);   // affichage de E nombre de sorties            
                System.out.println("l'etat(s) initial(s) est : "+this.etatinit); // affichage de l'etat initial (I)            
                System.out.println("l'etat(s) acceptant(s) est : "+this.etataccp); // affichage de l'etat acceptant (F)               
                System.out.println("Les Transitions de l'automate sont :");  
                // affichage de l'ensemble des Transitions separer avec ',' à l'aide de la boucle  
                for (int i=0; i<this.transition.size(); i++){
                    transition = this.transition.get(i);
                        System.out.print( "(" +
                            transition.getEtatdebut() +","+
                            transition.getEntree() +","+
                            transition.getEtataccp() +","+
                            transition.getSortie() +")");
                        System.out.print("\n");
        }
    }
 // methode pour verifier si les inputs saisie sont les mêmes que les alphabets d'entrée
    public boolean verification(String lecture){
        Transition tran = new Transition();
        boolean bool = false;
        
        if(lecture==null) 
            bool=true;
        if(lecture != null){
        for(int i=0;i<lecture.length();i++)
        {
                char caractere=lecture.charAt(i);
                    for(int j=0;j<transition.size();j++){
                            tran = transition.get(j);
                            // après qu'on boucle sur la lecture on test si la lecture est égal à getentree de la transition et différent de #
                            if (caractere == tran.getEntree().charAt(0) || caractere == '#'){
                               bool = true;
                               break; 
                            }
                            else 
                               bool = false;
                    } 
            if(bool == false)
                break;
        }
        }
        return bool;
    }
 //Traitement des phrases lues
public void traitement (List <String> list){
            String lecture="";
            Transition tran = new Transition();
            List<String> msg = new ArrayList(); 
            boolean test = false;
            String sortie ="";
            
            tran = transition.get(0);
            int etat = tran.getEtatdebut();
            int etatCourant = tran.getEtatdebut();
            // boucle pour traiter les phrases entrés 
           for(int z=0; z<list.size();z++){
               lecture=list.get(z);
               // boucle pour traiter chaque mot seul
            for(int i=0;i<lecture.length();i++){
                char caractere = lecture.charAt(i);
                // boucle pour traiter chaque alphabet seul
                for(int j=0;j<transition.size();j++) {
                        tran = transition.get(j);
                        // traitement des etats et des alphabets selon des conditions d'égalité avec la liste des transitions
                    if(etatCourant == tran.getEtatdebut()){
                       
                        if(caractere == tran.getEntree().charAt(0)){ 
                                System.out.println("Etat courant : "+etatCourant+", Entree : "+tran.getEntree());
                                if(tran.getSortie()!= null){
                                   sortie+=tran.getSortie().charAt(0);
                                   etatCourant = tran.getEtataccp();
                                   System.out.println("Sortie : "+tran.getSortie()+", Transition trouvee");
                                   test=true;
                                   break;
                                }
                                // traitement de l'affichage si la transition est trouvé
                                else{
                                etatCourant=tran.getEtataccp();
                                System.out.println("Transition trouvee");
                                test=true;
                                break;
                                }
                            }
                            else 
                                test=false;
                          
                    }                    
                }             
                            //  affichage si la transition est non trouvée
                            if(test == false){
                                System.out.println("Etat courant : "+etatCourant+", Entree : "+caractere);
                                System.out.println("Transition non trouvee");
                            }
            }                // si le mot est commence par l'état initial et est lu completement jusqu'ai atteindre l'état final 
                            // affichage de l'entree est acceptante 
                                if(etataccp.contains(etatCourant))
                                        System.out.println("Entree acceptante.");
                                else
                                    // si ce n'est pas le cas l'entrée non acceptante
                                        System.out.println("Entree non acceptante.");
                                 System.out.println("--->La sortie de cette phrase est : "+sortie);
                                 System.out.println("---Fin de phrase---");
                                 System.out.println("-Fin de traitement-");
                                 sortie="";
                                 etatCourant=0;
           }
            
        
    }//fin methode Traitement

// Automate NonDéterministe =>Lambda fermeture (Utilisation de l'algorithme  du cours)
public ArrayList<Integer> lambdafermeture ( HashSet<Integer> T ) { //  Ensemble d'etat
        ArrayList<Integer> pile = new ArrayList<Integer>() ;
        ArrayList<Integer> listsupp = new ArrayList<Integer>(); // l'ensemble des états à supprimer 
       ArrayList<Integer> listajout = new ArrayList<Integer>(); // l'ensemble des états à ajouter 

       for ( Integer e : T ){ // on ajoute tous les etats de T dans la pile  
            pile.add( e );
        }
        ArrayList<Integer> F = new ArrayList<Integer>();
        while ( ! pile.isEmpty() ){
            for ( Integer t : pile ){  // t est le sommet de la pile si t n'est pas dans la liste F on ajoute le sommet à F
                if ( ! F.contains( t ) ){
                    F.add( t );
                    
                    for ( Transition transition : this.transition ){ // on empile chaque u tel que t -> u avec lambda en transition 
                        if ( transition.getEtatdebut() == t && transition.getEntree().equals("#") )
                            listajout.add ( transition.getEtataccp() );
                    }
                }
                listsupp.add( t );
            }
            pile.addAll( listajout); // Empiler u sur p
            pile.removeAll( listsupp );
        }
        Collections.sort(F);
        return F;
    }//Fin Lambdafermeture


// Fonction Transiter le meme algorithle que celle du cours  
public HashSet<Integer> transiter( ArrayList<Integer> T , String a ){
        HashSet<Integer> F = new HashSet<Integer>();
            for ( Integer t : T ){ // t etat de T
                for ( Transition transition : this.transition ){
                    // on boucle sur chaque u tel que t ->u avec a comme transition
                    if ( transition.getEtatdebut() == t && transition.getEntree().compareTo( a ) == 0){
                        if ( ! F.contains( transition.getEtataccp() ) )
                             F.add ( transition.getEtataccp() ); // ajouter u dans F
                        }
                    }
                }
        return F;
            }
// Fonction de determinisation                     
public Automate Determinisation(){
Automate T = new Automate();
Transition tran = new Transition();
ArrayList <ArrayList<Integer>> Superetat = new ArrayList();
HashSet <Integer> Newetataccp = new HashSet();
ArrayList <ArrayList<Integer>> Etat = new ArrayList();
ArrayList <Integer> etat = new ArrayList();

ArrayList <Integer> U = new ArrayList();

int newetat = 0;
int etatfin = 1;

if(this.etatinit.size()==0) 
    this.etatinit.add(0);

        Superetat.add(this.lambdafermeture(this.etatinit)); // Lambdafermeture de l'etat initial 
        Etat.add(Superetat.get(0));
        
        //On recupere les lambda
        while(!Superetat.isEmpty()){
           if(Superetat.isEmpty()&&Etattraites.contains(Superetat.get(0)))
               break;
                
                etat = Superetat.get(0);
                // On recupère des transitions par variable d'entree
                for(int j =0;j<this.ventres.size();j++){  
                    if(!Etattraites.contains(etat)){
                            Etattraites.add(etat);
                            
                         }
                    
                      U=this.lambdafermeture(this.transiter(etat,this.ventres.get(j)));
                     
                      tran.setEntree(this.ventres.get(j));
                   
                    
                     if(U.isEmpty())
                           etatfin=-1;
                       else{
                       
                        if(!Etat.contains(U)&&!U.isEmpty()){
                            Etat.add(U);
                            
                        }
                       
                           etatfin=Etat.indexOf(U);
                 }
                    
                     
                      newetat=Etattraites.indexOf(etat);
                      tran.setEtatdebut(newetat);
                   
                      
                      tran.setEtataccp(etatfin);
                      T.transition.add(tran);
                       
                      tran=new Transition();
                    
                   if(!Superetat.contains(U)&&!U.isEmpty()&&!Etattraites.contains(U))
                       Superetat.add(U);
                    
            }    
                Superetat.remove(0);
                if(Etattraites.contains(U))
                    Superetat.remove(U);
         }
       
        for(int k : this.etataccp){
            for(int j=0;j<Etattraites.size();j++){
                        if(Etattraites.get(j).contains(k))
                            Newetataccp.add(j); 
                   }
        }
        
        T.ventres=this.ventres;
        T.sorties=this.sorties;
        T.etatinit.add(0);
        T.nbretats= Etattraites.size();
       T.setEtataccp(Newetataccp);
                 
     return T;
     
}

public void ecrire_fichier(String nomFichier, String texte){
		String adresse_fichier = System.getProperty("user.dir") + "/" + nomFichier;
		try {
			FileWriter fichier = new FileWriter(adresse_fichier,true);
			BufferedWriter buffer = new BufferedWriter(fichier);
			buffer.write(texte);
			buffer.flush();
			buffer.close();
		} catch (Exception e) {
			System.out.println("Erreur de fichier !! ");
			e.printStackTrace();
		}
	}

public void genere_fichier_dot(String nomFichier){
        // si le programme n'a pas pu trouver le fichier
    try
    {
    File fichier = new File(nomFichier + ".dot");
    int fin=transition.size()-1;
    if(fichier.exists()){
        fichier.delete();
    }
    ecrire_fichier(nomFichier + ".dot","digraph " + nomFichier + " {\n");
    if(!this.transition.isEmpty()){
        ecrire_fichier(nomFichier + ".dot","rankdir=LR; \n v [style=invisible];\n v -> " +this.transition.get(0).getEtatdebut()+ ";\n" +this.transition.get(0).getEtatdebut()+ " [color=red];\n");
        
        for(int i=0;i<this.transition.size();i++){
            if(transition.get(i).getSortie()==null)
                                    {
                            ecrire_fichier(nomFichier + ".dot", this.transition.get(i).getEtatdebut()+ " -> " + this.transition.get(i).getEtataccp()+ " [label =\" " + this.transition.get(i).getEntree()+"\"];\n");
                                     }
            else
            ecrire_fichier(nomFichier + ".dot", this.transition.get(i).getEtatdebut()+ " -> " + this.transition.get(i).getEtataccp()+ " [label =\" " + this.transition.get(i).getEntree()+" / " + this.transition.get(i).getSortie()+ "\"];\n");
            if(this.etataccp.contains(this.transition.get(i).getEtataccp()) /*|| this.F.contains(this.T.get(i).getEtatDepart())*/){
                ecrire_fichier(nomFichier + ".dot", this.transition.get(i).getEtataccp()+ " [peripheries=2,color=blue];\n");
            }
        }
        
    }
    ecrire_fichier(nomFichier + ".dot", "}");
    System.out.println("génération réussite ");
    
    }catch(Exception e){
            System.out.print("Erreur de génération "+e);
        }
}

public void genere_dot_AFD(String nomFichier){
    // generation du fichier dot de l'automate déterministe
    Automate Aut = new Automate();
    Aut=this.Determinisation(); // appel de la fonction Determinisation pour determiniser l'automate 
    try
    {
    File fichier = new File(nomFichier + ".dot");
    int fin=Aut.transition.size()-1;
    if(fichier.exists()){
        fichier.delete();
    }
    ecrire_fichier(nomFichier + ".dot","digraph " + nomFichier + " {\n");
    if(!Aut.transition.isEmpty()){
        ecrire_fichier(nomFichier + ".dot","rankdir=LR; \n v [style=invisible];\n v -> " +Aut.transition.get(0).getEtatdebut()+ ";\n" +Aut.transition.get(0).getEtatdebut()+ " [color=red];\n");
        
        for(int i=0;i<Aut.transition.size();i++){
            if(Aut.transition.get(i).getSortie()==null)
                                    {
            ecrire_fichier(nomFichier + ".dot", Aut.transition.get(i).getEtatdebut()+ " -> " + Aut.transition.get(i).getEtataccp()+ " [label =\" " + Aut.transition.get(i).getEntree()+"\"];\n");
                                     }
            
            else
            ecrire_fichier(nomFichier + ".dot", Aut.transition.get(i).getEtatdebut()+ " -> " + Aut.transition.get(i).getEtataccp()+ " [label =\" " + Aut.transition.get(i).getEntree()+" / " + Aut.transition.get(i).getSortie()+ "\"];\n");
            if(Aut.etataccp.contains(Aut.transition.get(i).getEtataccp())){
                ecrire_fichier(nomFichier + ".dot", Aut.transition.get(i).getEtataccp()+ " [peripheries=2,color=blue];\n");
            }
        }
        
    }
    ecrire_fichier(nomFichier + ".dot", "}"); 
    System.out.println("génération réussite "); 
    
    }catch(Exception e){
            System.out.print("Erreur de génération "+e); 
        }
}

}


  

    
        
        
     


        
