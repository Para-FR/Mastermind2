import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * <h1>Class for the Database</h1>
 *
 *
 *
 *
 */


public class BDD {

    public String password;
    public String username;

    Date theDate = new Date();
    Scanner sc = new Scanner(System.in);
    SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
    java.sql.Date theBirthdate = new java.sql.Date(2000,01,01);
    int counter = 0;
    /**
     * <h3>Constructor of the class</h3>
     * @param _username -the username to log into the database
     * @param _password -the password to log into the database
     */

    public BDD(String _username,String _password)
    {
        username=_username;
        password=_password;
    }

    /**
     * <h3>Used to change the password if the default password is wrong</h3>
     * @param _password - the new password
     */
    public void SetPassword(String _password)
    {
        password=_password;
    }

    /**
     * <h3>Used to change the username if the default username is wrong</h3>
     * @param _username -the new username
     */
    public void SetUsername(String _username) {
        username = _username;
    }


    /**
     * <h3>Method to test the connection to the database</h3>
     * @return boolean -true=success | false=can't connect
     */

    public boolean TestConnect()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/?", username, password);

            return true;
        }
        catch(Exception e){

            //System.out.println(e);
            return false;

        }

    }

    /**
     * <h3>Used to create the database when it doesn't exist</h3>
     */

    public void CreateBDD() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/?", username, password);
            Statement stmt=con.createStatement();
            int Result=stmt.executeUpdate("CREATE DATABASE mastermind");

            System.out.print("Base de donnee mastermind creee avec succès ! \n");

        }
        catch(Exception e){

            //System.out.println("La base de donnee mastermind existe dejà.");

        }
    }

    /**
     *<h3>Used to create the user table</h3>
     */

    public void CreateUsersTable() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind", username, password);
            Statement stmt=con.createStatement();
            String SQL_table_to_play = "CREATE TABLE users(" +
                    "id_user INT PRIMARY KEY AUTO_INCREMENT," +
                    "nom VARCHAR(20)," +
                    "prenom VARCHAR(20), " +
                    "nickname VARCHAR(10), " +
                    "password VARCHAR(10)," +
                    "email VARCHAR(30)," +
                    "birthdate DATE)";
            int table = stmt.executeUpdate(SQL_table_to_play);
            System.out.print("Table users creee avec succès ! \n");
        }
        catch(Exception e){
            //System.out.println("La table 'users' existe dejà. ");
        }
    }

    /**
     *<h3>Used to create the stat table</h3>
     */

    public void CreateStatsTable() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind", username, password);
            Statement stmt=con.createStatement();
            String SQL_table_to_play = "CREATE TABLE stats(" +
                    "id_stats INT PRIMARY KEY AUTO_INCREMENT," +
                    "nickname VARCHAR(10)," +
                    "won INT," +
                    "lost INT," +
                    "ratio INT," +
                    "record INT," +
                    "ranking INT)";
            int table = stmt.executeUpdate(SQL_table_to_play);
            System.out.print("Table statistique creee avec succès ! \n");
        }
        catch(Exception e){
            //System.out.println("La table 'stats' existe dejà.");
        }
    }

    /**
     *<h3>Used to try the connection to this specific database</h3>
     */

    public void ConnectBDD() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind",username,password);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     *<h3>Used to try to Disconnect from this specific database</h3>
     */
    public void Disconnect() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind",username,password);
            con.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * <h3>Used to display the user's profile</h3>
     * @param pseudo -the unique pseudo of the user
     */

    public void ShowProfile(String pseudo){
        String data_Name = "";
        String data_Prenom = "";
        String data_Nick = "";
        String data_Email = "";
        String data_Birthdate = "";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // Connection
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind",username,password);
            // Instruction
            Statement stmt=con.createStatement();
            // Request
            String SQL_query = "SELECT * FROM users WHERE nickname = '" + pseudo + "'";
            ResultSet rs=stmt.executeQuery(SQL_query);

            while(rs.next()) {
                data_Name = rs.getString("nom");
                data_Prenom = rs.getString("prenom");
                data_Nick = rs.getString("nickname");
                data_Email = rs.getString("email");
                data_Birthdate = rs.getString("birthdate");
                System.out.println("Voici vos informations personnelles: ");
                System.out.println("Votre nom: " + data_Name);
                System.out.println("Votre prenom: " + data_Prenom);
                System.out.println("Votre pseudo: " + data_Nick);
                System.out.println("Votre email: " + data_Email);
                System.out.println("Votre date de naissance: " + data_Birthdate);
                System.out.println("Votre mot de passe reste protege ...");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * <h3>Used to display the current user's stats</h3>
     * @param pseudo -the unique pseudo of the user
     */
    public void ShowStats(String pseudo){
        String data_Win = "";
        String data_Lose = "";
        String data_Ratio = "";
        String data_Ranking = "";
        String data_Best = "";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // Connection
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind",username,password);
            // Instruction
            Statement stmt=con.createStatement();
            // Request
            String SQL_query = "SELECT * FROM stats WHERE nickname = '" + pseudo + "'";
            ResultSet rs=stmt.executeQuery(SQL_query);

            while(rs.next()) {
                data_Win = rs.getString("won");
                data_Lose = rs.getString("lost");
                data_Ratio = rs.getString("ratio");
                data_Ranking = rs.getString("ranking");
                data_Best = rs.getString("record");
                System.out.println("Voici vos informations personnelles: ");
                System.out.println("Nombre de fois vous avez gagne: " + data_Win);
                System.out.println("Nombre de fois vous avez perdu : " + data_Lose);
                System.out.println("Votre ratio (victoires / defaites): " + data_Ratio);
                System.out.println("Votre reccord personnel (nombre de coup minimal): " + data_Best);
                System.out.println("Votre rang au classement: " + data_Ranking);
            }
            con.close();
        }
        catch(Exception e){
            //System.out.println(e);
        }
    }


    /**
     * <h3>Used to check if a data is present in a certain column</h3>
     * @param field -which field to Select
     * @param table -which table to Select
     * @param theField -with which condition?
     * @return  boolean true = the data exists | false = the data is not in the database or something went wrong
     */

    public boolean Select(String field, String table, String theField) {
        String Data = "";
        String userData = "";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // Connection
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind",username,password);
            // Instruction
            Statement stmt=con.createStatement();
            // Request
            String SQL_query = "SELECT " + field + " FROM " + table + " WHERE " + field + " = '" + theField + "'";
            ResultSet rs=stmt.executeQuery(SQL_query);

            ResultSetMetaData metaData = rs.getMetaData();
            userData = metaData.getColumnLabel(1);
            while(rs.next()) {
                Data = rs.getString(userData);
               // System.out.print(Data + "\n");
                if (theField.equals(Data)) {
                    System.out.println("C'est correct !");
                    return true;
                } else if (Data == "") {
                    return false;
                }
                else {
                    System.out.print("Mauvaise information ! \n");
                }
            }
            con.close();
        }
        catch(Exception e){
            //System.out.println(e);
        }
        return false;
    }

    /**
     * <h3>Used to check if table stats is empty</h3>
     * @param pseudo -the unique pseudo of the user
     * @return boolean -true = table stats is empty | false = it is not empty
     */
    public boolean CheckEmptyStats(String pseudo) {
        int empty;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind", username, password);
            Statement stmt = con.createStatement();
            String SQL_Empty = "SELECT count(*) FROM stats WHERE nickname = '" + pseudo + "'";
            ResultSet rs = stmt.executeQuery(SQL_Empty);

            while(rs.next()) {
                empty = rs.getInt(1);
                if(empty == 0) {
                    return true;
                }
            }
        }
        catch (Exception e) {
            //System.out.println("");
        }
        return false;
    }


    /**
     * <h3>Used to insert stats</h3>
     * @param w -Number of win to add
     * @param l -number of loss to add
     * @param r -number of round to add
     * @param pseudo -the unique pseudo of the user
     * @return boolean -true = data inserted | false = something went wrong
     */

    public boolean InsertStats(int w, int l, int r, String pseudo) {
        double ratio = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind", username, password);
            Statement stmt = con.createStatement();
            String SQL_Get_Lign_Number = "SELECT count(*) FROM stats";
            ResultSet rs = stmt.executeQuery(SQL_Get_Lign_Number);


            boolean rectif = false;
            if(l==0) {
                l = 1;
                rectif=true;
            }
            ratio=(double)w/(double)l;

            if(rectif)
                l=0;
            if(rs.next()) {
                int rank = rs.getInt(1);
                if (rank == 0) {
                    rank = 1;
                } else {
                    rank += 1;
                }
                String SQL_query_In = "INSERT INTO stats(nickname, won, lost, ratio, record, ranking) VALUES ('" + pseudo + "', '" + w + "', '" + l + "', '" + ratio + "', '" + r + "', '" + rank + "')";
                int secres = stmt.executeUpdate(SQL_query_In);
                System.out.println("Statistiques sauvegardees !");
                return true;
            }
            con.close();
        }
        catch (Exception e) {
            //System.out.println(e);
            System.out.println("Une erreur est survenue dans la sauvegarde de votre partie");
        }
        return false;
    }


    /**
     * <h3>Used to update stats</h3>
     * @param w -Number of win to add
     * @param l -number of loss to add
     * @param r -number of round to add
     * @param pseudo -the unique pseudo of the user
     * @return boolean -true = update is done | false = something went wrong
     */

    public boolean UpdateStats(int w, int l, int r, String pseudo) {
        double ratio=0;
        String userdata="";
        String getData ="";

        int rounds = r;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind", username, password);
            Statement stmt = con.createStatement();
            String SQL_get_Name = "SELECT * FROM stats WHERE nickname = '" + pseudo + "' ORDER BY ratio DESC";
            ResultSet rs = stmt.executeQuery(SQL_get_Name);

            while(rs.next()) {
                int win = w + rs.getInt(3);
                int loose = l + rs.getInt(4);
                int rank = (rs.getInt(7));

                boolean rectif = false;
                if(loose==0) {
                    loose = 1;
                    rectif=true;
                }
                ratio=(double)win/(double)(loose);

                if(rectif)
                    loose=0;

                /*int win=0;
                int loose=0;*/
                /*System.out.println(getData);
                System.out.println(pseudo);*/
                String DBuser = rs.getString(2);
                if (pseudo.equals(DBuser)) {
                    /*ratio = ratio / (counter+1);
                    rounds = rounds / (counter+1);*/
                    String SQL_query_Update = "UPDATE stats SET nickname = '" + pseudo + "', won=" + win + ", lost=" + loose + ", ratio = " + ratio + ", record = " + rounds + ", ranking = " + rank +
                            " WHERE nickname = '" + pseudo + "'";
                    int secres = stmt.executeUpdate(SQL_query_Update);
                    System.out.println("Les donnees ont ete mises à jour !");
                    return true;
                }
            }

        } catch (Exception e) {
            //System.out.println(e);
            System.out.println("Une erreur est survenue dans la mise à jour de vos resultats");
        }
        return false;
    }

    /**
     * <h3>Used to finish the update of the stats</h3>
     * @return boolean -true = successful | false = something went wrong
     */

    public boolean FinishUpdate() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind", username, password);
            Statement stmt1 = con1.createStatement();
            String SQL_order = "SELECT * FROM stats ORDER BY ratio DESC";
            ResultSet rs1 = stmt1.executeQuery(SQL_order);


            int i = 1;
            while (rs1.next()) {
                int id = rs1.getInt(1);
                String SQL_update = "UPDATE stats SET ranking = " + i + " WHERE id_stats = " + id;
                int new_up = stmt1.executeUpdate(SQL_update);
                return true;
            }

        } catch (Exception e) {
            //System.out.println(e);
            System.out.println("Une erreur est survenue dans la mise à jour de vos resultats");
        }
        return false;
    }

    /**
     * <h3>Used to update the ranking</h3>
     * @param pseudo -the unique pseudo of the user
     * @return boolean -true = successful | false = something went wrong
     */
    public boolean RefreshRanking(String pseudo) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind", username, password);
            Statement stmt1 = con1.createStatement();
            Statement stmt2 = con1.createStatement();
            String SQL_Get_rank = "SELECT ranking FROM stats";
            String SQL_Get_size = "SELECT count(ranking) FROM stats";
            ResultSet rs = stmt1.executeQuery(SQL_Get_rank);
            ResultSet rs1 = stmt2.executeQuery(SQL_Get_size);

            int the_First = rs.getInt("ranking");
            int size = rs1.getInt(1);

            if (the_First != 1) {
                while (rs.next()) {
                    java.sql.Array rank = rs.getArray("ranking");

                    for (int k = 0; k <= size; ++k) {
                        PreparedStatement pstmt = con1.prepareStatement( "UPDATE stats set ranking = " + -1);
                        pstmt.executeUpdate();
                        System.out.println("Le classement a ete mis à jour !");
                        return true;
                    }
                }
            }
        } catch (Exception e) {
           // System.out.println(e);
            System.out.println("Une erreur est survenue dans la mise à jour de vos resultats");
        }

        return false;
    }

    /**
     * <h3>Used to add an account</h3>
     * @return boolean true = data inserted | false = something went wrong
     */

    public boolean InsertAccount() {
        System.out.println("Les champs ne doivent pas être trop long (<10 caractères sauf l'email 30).");
        System.out.println("Et tous non vide.");
        System.out.println("Entrer votre nom: ");
        String theName = sc.next();
        System.out.println("Entrer votre prenom: ");
        String theFirstName = sc.next();
        System.out.println("Entrer un pseudo: ");
        String theNick = sc.next();
        System.out.println("Entrer un mot de passe: ");
        String thePass = sc.next();
        System.out.println("Entrer une adresse mail: ");
        String theEmail = sc.next();
        System.out.println("Entrer votre date de naissance au format JJ.MM.YYYY: ");
        String dateDB = sc.next();

        try {
            theDate = formater.parse(dateDB);
            theBirthdate = new java.sql.Date(theDate.getTime());
            Class.forName("com.mysql.jdbc.Driver");
            // Connection
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind", username, password);
            // Instruction
            Statement stmt = con.createStatement();
            // Request
            if (theNick.length() > 10 || thePass.length() > 10 || theEmail.length() > 30 || dateDB.length() > 10 || theBirthdate == null) {
                System.out.println("Erreur - Nombre de caractère dans certains champs invalides - Recommencez");
                InsertAccount();
            }
            else {
                String SQL_query_In = "INSERT INTO users(nom, prenom, nickname, password, email, birthdate) VALUES ('" + theName + "','" + theFirstName + "','" + theNick + "', '" + thePass + "', '" + theEmail + "', '" + theBirthdate + "')";
                int rs = stmt.executeUpdate(SQL_query_In);
                System.out.println("Le compte a ete cree !");
                return true;
            }
            con.close();
        } catch (Exception e) {
            //System.out.println(e);
        }
        return false;
    }

    /**
     * <h3>used to update an account</h3>
     * @param BDD_user -username of the user to change
     * @return boolean -true = account added | false = something went wrong
     */

    public boolean UpdateAccount(String BDD_user) {
        System.out.println("Choisissez quel champ vous voulez modifier: ");
        System.out.println("1. Le nom");
        System.out.println("2. Le prenom");
        System.out.println("3. Le pseudo");
        System.out.println("4. Le mot de passe");
        System.out.println("5. L'email");
        System.out.println("6. La date de naissance");
        String theAnswer = sc.next();

        try{
            Class.forName("com.mysql.jdbc.Driver");
            // Connection
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind",username,password);
            // Instruction
            Statement stmt=con.createStatement();
            // Request
            if (theAnswer == null) {
                System.out.print("Veuillez entrer un numero valide comme indiquer ... ");
                UpdateAccount(BDD_user);
            }
            else {
                if (theAnswer.equals("1")) {
                    System.out.println("Entrer le nouveau nom: ");
                    String theName = sc.next();
                    if (theName.length() > 20){
                        System.out.println("Votre Nom est incorrect. Veuillez ressaisir.");
                        UpdateAccount(BDD_user);
                    }
                    String SQL_query_In = "UPDATE users SET nom = '" + theName + "' WHERE nickname = '" + BDD_user + "'";
                    int rs = stmt.executeUpdate(SQL_query_In);
                    System.out.println("Votre nom a ete modifie avec succès !");
                    System.out.println("Voulez-vous modifier quelque chose d'autre ? o/n: ");
                    String modAgain = sc.next();
                    if (modAgain.equals("o")) {
                        UpdateAccount(BDD_user);
                    } else {
                        return true;
                    }
                } else if (theAnswer.equals("2")) {
                    System.out.println("Entrer le nouveau prenom: ");
                    String theFirstName = sc.next();
                    if (theFirstName.length() > 20){
                        System.out.println("Votre prenom est trop long. Veuillez ressaisir.");
                        UpdateAccount(BDD_user);
                    }
                    String SQL_query_In = "UPDATE users SET prenom = '" + theFirstName + "' WHERE nickname = '" + BDD_user + "'";
                    int rs = stmt.executeUpdate(SQL_query_In);
                    System.out.println("Votre prenom a ete modifie avec succès !");
                    System.out.println("Voulez-vous modifier quelque chose d'autre ? o/n: ");
                    String modAgain = sc.next();
                    if (modAgain.equals("o")) {
                        UpdateAccount(BDD_user);
                    } else {
                        return true;
                    }
                } else if (theAnswer.equals("3")) {
                    System.out.println("Entrer le nouveau pseudo: ");
                    String theNick = sc.next();
                    if (theNick.length() > 10){
                        System.out.println("Votre pseudo est trop long. Veuillez ressaisir.");
                        UpdateAccount(BDD_user);
                    }
                    String SQL_query_In = "UPDATE users SET nickname = '" + theNick + "' WHERE nickname = '" + BDD_user + "'";
                    int rs = stmt.executeUpdate(SQL_query_In);
                    System.out.println("Votre pseudo a ete modifie avec succès !");
                    System.out.println("Voulez-vous modifier quelque chose d'autre ? o/n: ");
                    String modAgain = sc.next();
                    if (modAgain.equals("o")) {
                        UpdateAccount(BDD_user);
                    } else {
                        return true;
                    }
                } else if (theAnswer.equals("4")) {
                    System.out.println("Entrer le nouveau mot de passe: ");
                    String thePass = sc.next();
                    if (thePass.length() > 10){
                        System.out.println("Votre mot de passe est trop long. Veuillez ressaisir.");
                        UpdateAccount(BDD_user);
                    }
                    String SQL_query_In = "UPDATE users SET password = '" + thePass + "' WHERE nickname = '" + BDD_user + "'";
                    int rs = stmt.executeUpdate(SQL_query_In);
                    System.out.println("Votre mot de passe a ete modifie avec succès !");
                    System.out.println("Voulez-vous modifier quelque chose d'autre ? o/n: ");
                    String modAgain = sc.next();
                    if (modAgain.equals("o")) {
                        UpdateAccount(BDD_user);
                    } else {
                        return true;
                    }
                } else if (theAnswer.equals("5")) {
                    System.out.println("Entrer le nouvel email: ");
                    String theEmail = sc.next();
                    if (theEmail.length() > 30){
                        System.out.println("Votre email est trop long. Veuillez ressaisir.");
                        UpdateAccount(BDD_user);
                    }
                    String SQL_query_In = "UPDATE users SET mail = '" + theEmail + "' WHERE nickname = '" + BDD_user + "'";
                    int rs = stmt.executeUpdate(SQL_query_In);
                    System.out.println("Votre email a ete modifie avec succès !");
                    System.out.println("Voulez-vous modifier quelque chose d'autre ? o/n: ");
                    String modAgain = sc.next();
                    if (modAgain.equals("o")) {
                        UpdateAccount(BDD_user);
                    } else {
                        return true;
                    }
                } else if (theAnswer.equals("6")) {
                    System.out.println("Entrer la nouvelle date de naissance au format JJ.MM.YYYY: ");
                    String dateDB = sc.next();
                    if (dateDB.length() > 10){
                        System.out.println("Votre Nom est inccorect entrer le format indique : JJ.MM.YYYY. Veuillez ressaisir.");
                        UpdateAccount(BDD_user);
                    }
                    theDate = formater.parse(dateDB);
                    theBirthdate = new java.sql.Date(theDate.getTime());
                    String SQL_query_In = "UPDATE users SET birthdate = '" + theBirthdate + "' WHERE nickname = '" + BDD_user + "'";
                    int rs = stmt.executeUpdate(SQL_query_In);
                    System.out.println("Votre date de naissance a ete modifie avec succès !");
                    System.out.println("Voulez-vous modifier quelque chose d'autre ? o/n: ");
                    String modAgain = sc.next();
                    if (modAgain.equals("o")) {
                        UpdateAccount(BDD_user);
                    } else {
                        return true;
                    }
                }
            }
            con.close();
        }
        catch(Exception e){
            //System.out.println(e);
        }
        return false;
    }
}


