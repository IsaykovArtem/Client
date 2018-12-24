package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientForServer {

	private static BufferedReader clava = new BufferedReader(new InputStreamReader(System.in));
	private static boolean isConnect=true;

	public static void main(String[] args) throws IOException{
		socketClient();
	}

	public static void menu() {
		System.out.println("Тут есть меню действий");
		System.out.println("1 для SET");
		System.out.println("0 для выхода");
	}

	private static void socketClient() throws IOException {
		Socket socket = new Socket("localhost", 8080);
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

		String line = reader.readLine();
		System.out.println(line);
		writer.println("I understand - " + line);
		while(isConnect){
			menu();
			line = clava.readLine();
			writer.println(line);
			switch (line) {
				case "0": {
					isConnect=false;
					reader.close();
					writer.close();
				}break;
				case "1": {
					line =reader.readLine();
					while (!line.equals("End")){
						System.out.println(line);
						line =reader.readLine();
					}
				}break;
			}
		}
	}
}