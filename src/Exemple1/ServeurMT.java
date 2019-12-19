package Exemple1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class ServeurMT extends Thread {

	private int nbClients = 0;
	private static ArrayList<containers> rooms = new ArrayList<containers>();

	public static void main(String[] args) {

		new ServeurMT().start();

	}

	@Override
	public void run() {
		try {

			ServerSocket ss = new ServerSocket(1234);
			System.out.println("Démarrage du serveur multithreads...");
			while (true) {
				Socket s = ss.accept();
				nbClients++;

				new Conversation(s, nbClients).start();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class Conversation extends Thread {

		private Socket socket;
		private int numero;
		private String name;
		private String target ;
		private int index = 0;

		public Conversation(Socket socket, int numero) {
			this.socket = socket;
			this.numero = numero;
		}

		public void RegistreClient(String Username, Socket socketClient) {
			ServeurMT.rooms.add(new containers(Username, socketClient));
		}

		public void sendMessage(String Username, String message,String sender) {
			try {

				// Socket s = ServeurMT.rooms.get(Username);
				Socket s = null;
				for (containers containers : rooms) {
					if(containers.getUsername().equals(Username)) {
						s=containers.getSocket();
					}
				}
				if (s == null) {
					System.out.println("this user is not existe");
					return;
				}

				InputStream is = s.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);

				OutputStream os = s.getOutputStream();
				PrintWriter pw = new PrintWriter(os, true);

				pw.println(this.name+" :: "+message);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void AfficheUsers(PrintWriter pw) {
			pw.println(":::: All USERS :::::::::");
			for (containers containers : rooms) {

				pw.println(containers.getUsername());
			}
			pw.println(":::: End :::::::::");
			pw.println("");
			pw.println("you are registred please enter the name of client");
			pw.println("press List@ to show all user connected");
		}

		public boolean Verification(String name, PrintWriter pw) {
			pw.println(":::: Begin Verification :::::::::");
			for (containers containers : rooms) {
				if (containers.getUsername().equals(name)) {
					pw.println(":::: You are connected :::::::::");
					return true;
				}
			}
			pw.println(":::: this name is not connected :::::::::");
			pw.println("");
			pw.println("you are registred please enter the name of client");
			pw.println("press List@ to show all user connected");
			return false;

		}

		@Override
		public void run() {
			try {
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);

				OutputStream os = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(os, true); // true pour envoyer le stream sans le stocker dans la
															// mémoire cache (false : par défaut)

				String IP = socket.getRemoteSocketAddress().toString();
				System.out.println("Connexion du client numéro : " + numero + ", IP : " + IP);

				pw.println("Bienvenue, vous êtes le client numéro : " + numero);
				System.out.println("une chaine de caractere a été envoyer");

				while (true) {
					if (br != null) {
						if (index == 0) {
							pw.println("give me your username");
							String req = br.readLine();
							System.out.println("Connect to user " + req);
							this.name=req;
							this.RegistreClient(req, socket);
							pw.println("you are registred please enter the name of client");
							pw.println("press List@ to show all user connected");
						} else if (index == 1) {
							String req = br.readLine();
							while (req.equals("List@")) {
								System.out.println("waiting ......");
								this.AfficheUsers(pw);
								req = br.readLine();
							}
							boolean key=this.Verification(req, pw);
							if(key) {
								this.target = req;
								pw.println("send your message too " + this.target);
							}else {
								index --;
							}

							

						} else {
							String req = br.readLine();
							this.sendMessage(this.target, req,this.name);
							pw.println("your message has been sended");
						}
						index++;

					} else {
						break;
					}

				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	class containers {
		private String username;
		private Socket socket;

		public String getUsername() {
			return username;
		}

		public Socket getSocket() {
			return socket;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public void setSocket(Socket socket) {
			this.socket = socket;
		}

		public containers(String username, Socket socket) {

			this.username = username;
			this.socket = socket;
		}

	}

}
