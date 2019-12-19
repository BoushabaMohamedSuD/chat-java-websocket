package Exemple1;

import java.io.BufferedReader;
import java.io.IOException;

public class Observer extends Thread {
	private BufferedReader br;
	public Observer(BufferedReader br) {
		this.br=br;
	}

	@Override
	public void run() {
		System.out.println("Observer activited");
		String req;
		while(true) {
			
			try {
				req = this.br.readLine();
				System.out.println(req);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
