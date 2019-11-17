/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tatou_numerique;

import java.sql.*;
import java.sql.DriverManager;
import java.io.*;
import java.io.IOException;
/*
 *
 * @author pc
 */
public class connexion {
       
        String url = "jdbc:mysql://localhost:3306/gestion_etude?verifyServerCertificate=false&useSSL=true";
        String driver = "com.mysql.jdbc.Driver";
        String nomUtilisateur = "root";
        String motDePasse = "tatouage";
        Connection cCon;
    public connexion() {
    	
    }
    
    
    
    public boolean openConnection(){
	   boolean go=true,retour=false;
	   int nbrEssai=0;
	    	while(go&&nbrEssai<3){
	           try{
                     Class.forName("com.mysql.jdbc.Driver").newInstance();
                     try { Thread.sleep(20); } catch (Exception e) {e.printStackTrace();}
                     cCon = DriverManager.getConnection( url, nomUtilisateur, motDePasse );
                     retour=true;
                     go=false;
                  }catch(Exception e1){e1.printStackTrace();}
                  nbrEssai++;
            }
            return retour;        	
	}
	
	public boolean closeConnection(){
		 boolean retour=false;
		 try{
		 	cCon.close();
		 	retour=true;
		 
		 }catch(Exception e2){e2.printStackTrace();} 
		 return retour;
	}
	

}

