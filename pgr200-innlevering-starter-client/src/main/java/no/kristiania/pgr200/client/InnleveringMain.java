package no.kristiania.pgr200.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import no.kristiania.pgr200.server.HttpRequest;

public class InnleveringMain {
	private String command;
	private Scanner input = new Scanner(System.in);
	private ArrayList<String> acceptedCommands;
	private int port;
	
	public static void main(String[] args) {
		new InnleveringMain();
	}
	
	public InnleveringMain() {
		acceptedCommands = new ArrayList<>();
		addAcceptedCommands();
		System.out.println("Write command");
		command = input.nextLine();
	}
	
	public void handleInput() throws IOException {
		if(command.equals("list")) {
			String method = "GET";
			String path = "/list";
			
			HttpRequest request = new HttpRequest(method, path, port);
			request.execute();
		}else if(command.equals("add")) {
			String method = "POST";
			String path = "/add";
			String body;
			System.out.println("add title: ");
			String title = input.nextLine();
			System.out.println("add description: ");
			String description = input.nextLine();
			System.out.println("add topic");
			String topic = input.nextLine();
			
			body = "title=" + title + "&description=" + description + "&topic=" + topic;
			
			
			HttpRequest request = new HttpRequest(method, path, port);
			request.setBody(body);
			request.execute();
		}else if(command.equals("update")) {
			String method = "POST";
			String path = "/update";
			String body;
			
			System.out.println("write title of talk you want to update: ");
			String updateTalk = input.nextLine();
			System.out.println("add new title: ");
			String title = input.nextLine();
			System.out.println("add new description: ");
			String description = input.nextLine();
			System.out.println("add new topic");
			String topic = input.nextLine();
			
			body = "title=" + title + "&description=" + description + "&topic=" + topic;
			
			HttpRequest request = new HttpRequest(method, path, port);
			request.setBody(body);
			request.execute();
		}else if(command.equalsIgnoreCase("clear")) {
			String method = "POST";
			String path = "/clear";
			
			HttpRequest request = new HttpRequest(method, path, port);
			request.execute();
		}else {
			String error = "Unknown command. Try ";
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < acceptedCommands.size(); i++) {
				sb.append(acceptedCommands.get(i) + ", ");
			}
			System.out.println(error + sb.toString() + "...");
		}
	}
	
	public void addAcceptedCommands() {
		acceptedCommands.add("list");
		acceptedCommands.add("add");
		acceptedCommands.add("update");
		acceptedCommands.add("clear");
	}
}
