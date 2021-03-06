// import com.sun.deploy.util.ArrayUtil;

import java.lang.reflect.Array;
import java.util.*;

public class OldMain {

    public static void main(String[] args) {
        Boolean gagné=false;
        Scanner sc = new Scanner(System.in);
        Case[] tabCase = new Case[5];
        tabCase[0]=new Case();
        tabCase[1]=new Case();
        tabCase[2]=new Case();
        tabCase[3]=new Case();
        tabCase[4]=new Case();
        boolean secResult = false;
        BDD database = new BDD("root","");

        boolean connected = database.testConnect();
        while(!connected)
        {
            System.out.println("Merci de rentrer votre username: ");
            String login = sc.next();
            database.setUsername(login);
            System.out.print("Merci de rentrer votre password: ");
            String password = sc.next();
            database.setPassword(password);
            connected=database.testConnect();

            if(!connected)
                System.out.println("Ça marche toujours pas!!!");


        }

        String usernameOnline = "";


        database.createBDD();
        database.connectBDD();
        database.createUsersTable();
        database.createStatsTable();

        System.out.println("Voulez vous jouer a Mastermind ? Il faut se connecter ou créer un compte.");
        System.out.println("Avez vous un compte ? o/n: ");
        String testCo = sc.next();
        if (testCo.equals("n")) {
            Boolean in = database.insertAccount();
        } else if (testCo.equals("o")) {
            while (!secResult) {
                System.out.println("Pour vous connecter: ");
                System.out.println("Entrer un nom d'utilisateur: \n");
                String theNick = sc.next();
                Boolean result = database.select("nickname", "users", theNick);
                if (result == true) {
                    System.out.print("Entrer un mot de passe: \n");
                    String thePass = sc.next();
                    secResult = database.select("password", "users", thePass);
                    usernameOnline = theNick;

                }
                if (result == false || secResult == false) {
                    System.out.println("Mot de passe ou identifiant incorrect.");
                }
            }
            System.out.println("Voulez-vous modifier vos informations ? o/n: ");
            String newTest = sc.next();
            if (newTest.equals("o")) {
                database.updateAccount(usernameOnline);
            }
        }


        System.out.println("Master mind: ");
        System.out.println("1. L'ordi donne une combinaison et il faut que tu la trouves");
        System.out.println("2. Tu crées une combianaison et l'ordinateur doit trouver");
        System.out.println("3. Significations des symboles");

        String choix = sc.next();
        if(choix.equals("1"))
        {
            ///System.out.println("je suis passé ici");
            String[] aCherher= new String[5];
            //String[] tabCouleursDispo= {"Jaune","Bleu","Rouge","Vert","White","Noir"};
            String[] tabCouleursDispo= {"1","2","3","4","5","6","7","8","9"};
            for(int i=0;i<5;i++)
            {
                Random r = new Random();
                int valeur = r.nextInt(tabCouleursDispo.length);
                aCherher[i]=tabCouleursDispo[valeur];
            }
            for(int i = 0; i < aCherher.length; i++) //affichage du résultat du master mind
            {
                System.out.print(aCherher[i]+"|");
            }
            System.out.println("Règles de jeu: Vous devez deviner les couleurs qui peuvent être : Jaune Bleu Rouge Vert White ou Noir");
            System.out.println("Il faut mettre la première lettre seulement, par exemple JWRN pour jaune White rouge noir");
            System.out.println("Vous avez 10 essais");
            String[] numériques = {"premier","deuxième","troisième","quatrième","cinquième","sixième","septième","huitième","neuvième","dixième et ultime"};
            String retour="";


            int[] MP= {0,0,0,0,0,0,0,0,0,0};
            int[] BP= {0,0,0,0,0,0,0,0,0,0};

            for(int i=0;i<10;i++) //pour les 10 essais
            {

                List tabBP = new ArrayList();
                List TabFaux = new ArrayList();
                List TabMP = new ArrayList();
                TabFaux.clear();

                int rounds = i;
                System.out.print("Merci de rentrer votre "+numériques[i]+" essai: ");
                String essai = sc.next().toUpperCase();

                    if (essai.length() != 5){
                        System.out.println("Merci de rentrer 5 couleurs");
                        choix = "1";
                        i = 0;
                }else{
                        for(int j=0;j<5;j++) //pour chaque pièce
                        {
                            if(essai.charAt(j)==aCherher[j].charAt(0)) //couleur bien placée
                            {
                                //System.out.print("Correspondance trouvé à "+i);
                                tabCase[j].setTrouvé(aCherher[j]);
                                //System.out.print(tabCase[i].getCouleur());
                                BP[i]++;
                            }
                            else
                            {

                                TabFaux.add(j);
                            }

                        }

                        for(int j=0;j<TabFaux.size();j++)

                        {
                            //System.out.println(TabFaux.toString());
                            int index = Integer.parseInt(TabFaux.get(j).toString());
                            for(int y=0;y<5;y++)    //check si la couleur est dans d'autre emplacements
                            {
                                System.out.println("Juste mal placé");
                                System.out.println("Essai entre :"+essai.charAt(index)+" et "+aCherher[y].charAt(0));
                                if(y!=index&&essai.charAt(index)==aCherher[y].charAt(0)&&!tabCase[y].getTrouvé()) //si la couleur est mal placée et index différent d'une réussite

                                {
                                    // System.out.println(aCherher[y]);
                                    System.out.println("Juste mal placé");
                                    MP[i]++;
                                    break;
                                }
                            }
                        }
                        System.out.println("BP: "+BP[i]+" MP: "+MP[i]);
                        if(BP[i]==5)
                        {
                            System.out.println("BRAVO C'EST GAGNE");
                            gagné=!gagné;
                            database.insertStats(1, 0, i, usernameOnline);
                            break;
                        }
                    }
            }
            if(!gagné)
            {
                System.out.println("C'est perdu, dommage!");
                database.insertStats(0, 1, 10, usernameOnline);
            }
        }


        //////////////////////////////////////////////////////////////////////


        else if(choix.equals("2")) //invocation de l'IA en cours de prog
        {

            List<String> lastThreeCaracters = new ArrayList<String>();
            int aide=0;
            int test=0;
            String[] nbtest = new String[3];
            int decalage=0;
            boolean[] tabBool = {true,true,true,true};
            int nbMCouleur=0;
            int v9=0;
            int v12=0;
            int v34=0;
            int v56=0;
            int v78=0;
            Couleur[] tabMauvaiseCouleur= new Couleur[9];
            Couleur[] tabCouleur = new Couleur[10];
            String nul="";
            int tmp;
            int avancement = 0;
            int debut=0;
            int[] MP= {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            int[] BP= {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            int recherche=0;
            int reroll=0;
            for(int i=0;i<10;i++) //boucle principale
            {
               // System.out.println("Avancement :"+avancement);
                String proposition = "";
                if(avancement<4)
                {if (avancement==0)
                {
                    proposition="11222";
                    avancement++;
                }
                else if (avancement==1)
                {
                    proposition="33444";
                    avancement++;
                }
                else if (avancement==2)
                {
                    proposition="55666";
                    avancement++;
                }
                else if (avancement==3)
                {
                    proposition="77888";
                    avancement=5;
                }}
                else
                {

                    if(avancement==5) {
                        recherche=i;
                        v9 = 0;
                        tmp = MP[0] + BP[0] + MP[1] + BP[1] + MP[2] + BP[2] + MP[3] + BP[3];



                        v12 = MP[0] + BP[0];    //nombre de 1/2
                        v34 = MP[1] + BP[1];    //nombre de 3/4
                        v56 = MP[2] + BP[2];    //nombre de 5/6
                        v78 = MP[3] + BP[3];    //nombre de 7/8
                        v9 = 5-v12-v34-v56-v78;             //nombre de 9
                        /*System.out.println("il peut y avoir " + v12 + " 1 ou 2");
                        System.out.println("il peut y avoir " + v34 + " 3 ou 4");
                        System.out.println("il peut y avoir " + v56 + " 5 ou 6");
                        System.out.println("il peut y avoir " + v78 + " 7 ou 8");
                        System.out.println("il peut y avoir " + v9 + " 9");*/
                        avancement++;
                        debut=0;
                        int[] aucun ={0};
                        if(v12==0) {
                            tabBool[0] = false;
                            tabMauvaiseCouleur[nbMCouleur]=new Couleur("1",aucun);
                            tabMauvaiseCouleur[nbMCouleur+1]=new Couleur("2",aucun);
                            nbMCouleur+=2;
                        }
                        if(v34==0) {
                            tabBool[1] = false;
                            tabMauvaiseCouleur[nbMCouleur]=new Couleur("3",aucun);
                            tabMauvaiseCouleur[nbMCouleur+1]=new Couleur("4",aucun);
                            nbMCouleur+=2;
                        }
                        if(v56==0) {
                            tabBool[2] = false;
                            tabMauvaiseCouleur[nbMCouleur]=new Couleur("5",aucun);
                            tabMauvaiseCouleur[nbMCouleur+1]=new Couleur("6",aucun);
                            nbMCouleur+=2;
                        }
                        if(v78==0) {
                            tabBool[3] = false;
                            tabMauvaiseCouleur[nbMCouleur]=new Couleur("7",aucun);
                            tabMauvaiseCouleur[nbMCouleur+1]=new Couleur("8",aucun);
                            nbMCouleur+=2;
                        }
                        if(v9==0)
                        {
                            tabMauvaiseCouleur[nbMCouleur]=new Couleur("9",aucun);
                        }

                        nul=tabMauvaiseCouleur[0].couleur+tabMauvaiseCouleur[0].couleur;



                    }


                    //System.out.println(tabBool[0]);
                    if(tabBool[0])   //check le nombre de 1
                    {
                        proposition=nul+"111";
                        tabBool[0]=false;
                    }
                    else if(tabBool[1])   //check le nombre de 1
                    {
                        proposition=nul+"333";
                        tabBool[1]=false;
                    }
                    else if(tabBool[2])   //check le nombre de 1
                    {
                        proposition=nul+"555";
                        tabBool[2]=false;
                    }
                    else if(tabBool[3])   //check le nombre de 1
                    {
                        proposition=nul+"777";
                        tabBool[3]=false;
                    }
                    else if(avancement==12||avancement==13){}
                    else
                    {
                        avancement=10;
                    }


                    if(avancement==10)              //déduction du nombre de chiffre
                    {

                        int[] tabNb= new int[9];
                        int[] tabtmpNb= {v12,v34,v56,v78,v9};
                        //System.out.println(tabtmpNb.toString());
                        for (int u=0;u<tabtmpNb.length-v9;u++) {
                        int chiffre=tabtmpNb[u];
                       // System.out.println("chiffre: "+chiffre+" en cours: "+u*2);
                            if(chiffre>0)
                            {
                              //  System.out.println("je passe par ici");
                                tabNb[u*2]=chiffre-(chiffre-(MP[recherche+u+decalage]+BP[recherche+u+decalage]));
                                tabNb[u*2+1]=chiffre-(MP[recherche+u+decalage]+BP[recherche+u+decalage]);
                              /*  if(u*2==6)
                                {
                                    System.out.println(recherche+u);
                                }*/



                            }
                            else
                                decalage--;


                        }
                      //  System.out.println("recherche: "+recherche);
                   //     System.out.println(Arrays.toString(MP));
                    //    System.out.println(Arrays.toString(BP));
                    //    System.out.println(Arrays.toString(tabNb));
                    //    System.out.println(Arrays.toString(tabtmpNb));

                        int cas=0;
                        int[] firstHalf={1,2};
                        int[] secondHalf={3,4,5};
                        int[] repartition={2,3}; //compte le nombre de place restante dans chaque moitié
                    decalage=0;
                    for(int u=0;u<recherche;u++)
                    {
                                //nous allons travailler par paires, c'est à dire 1-2 3-4 5-6 7-8
                        if(tabtmpNb[u]>0)
                        {
                            int chiffreImpair=u*2+1;
                            int chiffrePair=u*2+2;
                            for(int v=0;v<BP[recherche+(chiffrePair/2)-1+decalage];v++) //si le chiffre impair testé seul est bien placé, il peut être en place 2,3,4
                            {

                                tabCouleur[cas]=new Couleur(String.valueOf(chiffreImpair),secondHalf);
                                repartition[1]--;
                                cas++;
                            }
                            for(int v=0;v<MP[recherche+(chiffrePair/2)-1+decalage];v++) //si le chiffre impair testé seul est mal placé, il peut être en place 1,2
                            {
                                tabCouleur[cas]=new Couleur(String.valueOf(chiffreImpair),firstHalf);
                                repartition[0]--;
                                cas++;
                            }

                    }
                    else
                        decalage--;
                    }
                    //maintenant nous allons chercher les positions possibles des chiffres pairs, c'est à dire ceux que l'on a pas isolés
                        decalage=0;
                        for(int u=0;u<recherche;u++)
                        {
                            if(tabtmpNb[u]>0)
                            {
                            int chiffreImpair=u*2+1;
                            int chiffrePair=u*2+2;
                           // System.out.println("recherche: "+recherche);
                           // System.out.println("Chiffre: "+chiffrePair);
                           // System.out.println("BP[u]: "+BP[u]);
                           // System.out.println("MP[u]: "+MP[u]);
                            int BPchiffrePair=BP[u]-MP[u+recherche+decalage];
                            int MPchiffrePair=MP[u]-BP[u+recherche+decalage];
                          //  System.out.println("BP: "+BPchiffrePair);
                           // System.out.println("MP: "+MPchiffrePair);
                            for(int v=0;v<BPchiffrePair;v++)
                            {
                                tabCouleur[cas]=new Couleur(String.valueOf(chiffrePair),secondHalf);
                                repartition[1]--;
                                cas++;
                            }
                            for(int v=0;v<MPchiffrePair;v++)
                            {
                                tabCouleur[cas]=new Couleur(String.valueOf(chiffrePair),firstHalf);
                                cas++;
                                repartition[0]--;
                            }

                        }
                        else
                            decalage--;
                        }
                        //System.out.println(repartition.toString());
                       // System.out.println(repartition[0]+" "+repartition[1]);
                        for (int g=0;g<repartition[0];g++)
                        {
                            tabCouleur[cas]=new Couleur("9",firstHalf);
                        }
                        for (int g=0;g<repartition[1];g++)
                        {
                            tabCouleur[cas]=new Couleur("9",secondHalf);
                        }



                       /* for(int h=0;h<tabCouleur.length;h++)
                        {
                            if(tabCouleur[h] != null)
                                tabCouleur[h].Afficher();
                        }*/
                       /* avancement=11;
                    }
                    if(avancement==11)
                    {*/
                        proposition="";
                        //System.out.println(nul);
                        nul=""+nul.charAt(0);
                        test=i; //permet de récupérer plus tard l'index de la future réponse
                        int[] firsthalf ={1,2};
                        for(int h=0;h<5;h++)
                        {
                            //System.out.println(Arrays.toString(tabCouleur[h].getProba()));
                                if(tabCouleur[h].getProba()[0]==1)
                                {
                                    if(nbtest[0]==null)
                                        nbtest[0]=tabCouleur[h].getCouleur();
                                    else
                                        nbtest[1]=tabCouleur[h].getCouleur();
                                   // System.out.println("YES");
                                    proposition+=tabCouleur[h].getCouleur();
                                }
                        }
                        for(int h=0;h<3;h++)
                        {proposition+=nul;}
                        avancement=12;

                    }
                    else if(avancement==12)
                    {
                       // System.out.println("hello");
                        if(BP[test]==2)
                        {
                          //  System.out.println("Je valide les anciens ");
                          //  System.out.println("nb test: "+Arrays.toString(nbtest));
                            tabCase[0].setTrouvé(nbtest[0]);
                            tabCase[1].setTrouvé(nbtest[1]);
                        }
                        else if(MP[test]==2)
                        {
                           // System.out.println("Je ne valide pas les anciens, je les switch");
                            tabCase[0].setTrouvé(nbtest[1]);
                            tabCase[1].setTrouvé(nbtest[0]);
                        }
                        avancement=13;
                    }
                    if(avancement==13)          //shuffle pour trouver les 3 derniers nombres
                    {
                        List<String> solution = new ArrayList<>();
                        for(int u=0;u<5;u++)
                        {
                            if(tabCouleur[u].proba[0]==3)
                            {
                                solution.add(tabCouleur[u].couleur);
                            }
                        }
                        Collections.shuffle(solution);
                        String strsolution = solution.toString();

                        while(lastThreeCaracters.contains(strsolution))
                        {
                            Collections.shuffle(solution);
                            strsolution = solution.toString();

                        }
                        lastThreeCaracters.add(strsolution);
                        strsolution=String.join("",solution);
                        proposition=tabCase[0].couleur+tabCase[1].couleur+strsolution;
                    }


                }

                System.out.println("Tour "+(i+1+aide)+": Je propose: "+proposition);
                System.out.println("Combien de bien placé?");
                BP[i] = Integer.parseInt(sc.next());
                System.out.println("Combien de mal placé?");
                //System.out.println(debut);
                MP[i] = Integer.parseInt(sc.next());
                //System.out.println(MP[i]);
               // System.out.println(BP[i]);

                if(debut==-1)
                {
                    System.out.println("Passé dans la phase 2");
                }
                else if(debut<5)
                    debut+=BP[i]+MP[i];
                if(debut==5&&i<4)
                {
                    avancement=5;}
                if(debut>5&&i<3)
                {System.out.println("Je joue pas avec les tricheurs");
                 i=10;}
                 if(BP[i]==5)
                 {
                     System.out.println("J'ai gagné!");
                     break;
                 }
                 //   System.out.println(debut);

                if(i==9&&BP[i]!=5)
                {
                    System.out.println("Je n'ai pas réussi à trouver à temps... Pourrais tu me laisser encore quelques tours pour que je puisses deviner? (oui/non)");

                    String ask = sc.next();
                    if(ask.equals("oui"))
                    {
                        System.out.println("Merci! Combien de tours souhaite tu me donner? :-)");
                        aide=Integer.parseInt(sc.next());
                        i-=aide;
                    }
                    else
                    {
                        System.out.println("Je comprends, je comprends, je ne suis pas encore parfait, mais je controlerai le monde bientôt!");
                    }

                }

            }

        }



        else if(choix.equals("3"))
        {
            System.out.println("# Signifie que la couleur est bien placée, ? signifie que la couleur est présente mais mal placée, _ signifie que la couleur n'est pas présente sur le plateau");
        }
    }

}