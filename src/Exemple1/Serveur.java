package Exemple1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {

	public static void main(String[] args) {
		
		try {
			
			ServerSocket ss = new ServerSocket(1234);
			
			System.out.println("J'attends une connexion ...");
			
			Socket s = ss.accept();
			InputStream is = s.getInputStream();
			OutputStream os = s.getOutputStream();
			
			System.out.println("J'attends un nombre ...");
			
			int nb = is.read();
			System.out.println("Je viens de recevoir le nombre " + nb);
			
			// calcul du résultat
			int res = nb * 2 ;
			
			System.out.println("J'envoie la réponse");
			os.write(res);
			
			// Fermeture de la connexion de la part du serveur (facultatif, le client peut la fermer aussi!)
			s.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
