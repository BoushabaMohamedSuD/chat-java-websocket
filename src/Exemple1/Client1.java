package Exemple1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
	private int index=0;
	private static  String name;

	public static void main(String[] args) {
		
		try {
			
			System.out.println("Etablir une connexion vers le serveur");
			Socket socket = new Socket("localhost" , 1234);
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			PrintWriter pw = new PrintWriter(os , true);
			Scanner scanner = new Scanner(System.in);
		
		
			System.out.println("connection begun");
			String req = br.readLine();
			System.out.println( req );
			new Observer(br).start();
			
			
			
			while(true) {
				if(br!=null) {
					name =scanner.nextLine();
					pw.println(name);
					
				}else {
					break;
				}
			}
			System.out.println("client deconnected");
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
}


