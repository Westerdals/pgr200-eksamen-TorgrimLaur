package no.kristiania.pgr200.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class InnleveringMain {
	private String command;
	private Scanner input = new Scanner(System.in);
	private ArrayList<String> acceptedCommands;
	private int port = 80;
	
	public static void main(String[] args) throws IOException {
		new InnleveringMain();
		
	}
	
	public InnleveringMain() throws IOException {
		acceptedCommands = new ArrayList<>();
		addAcceptedCommands();
		System.out.println("Write command");
		command = input.nextLine();
		handleInput();
	}
	
	public void handleInput() throws IOException {
		if(command.equals("list")) {
			String method = "GET";
			String path = "/list";
			
			HttpRequest request = new HttpRequest(method, path, port);
			request.execute();
			
		}
		else if(command.equals("insert")) {
			String method = "POST";
			String path = "/insert";
			String body;
			System.out.println("add title: ");
			String title = input.nextLine();
			System.out.println("add description: ");
			String description = input.nextLine();
			System.out.println("add topic");
			String topic = input.nextLine();
			System.out.println("add conference day");
			String day = input.nextLine();
			System.out.println("add starttime");
			String starts = input.nextLine();
			
			body = "title=" + title + "&description=" + description + "&topic=" + topic + "&day=" + day + "&starts=" + starts;
			
			HttpRequest request = new HttpRequest(method, path, port);
			request.setBody(body);
			request.execute();
			
		}
		else if(command.equals("update")) {
			String method = "POST";
			String path = "/update";
			String body;
			
			/*
			 * sette table som skal endres
			 * sette hvilke felter som skal endres
			 * sette condition for hvilket felt som skal endres
			 */
			
			//String table, String change1, String change2, String change3, String change4, String change5, String id
			
			System.out.println("what table do you want to update?");
			String table = input.nextLine();
			System.out.println("write new title");
			String title = input.nextLine();
			System.out.println("Write new description: ");
			String description = input.nextLine();
			System.out.println("Write new topic: ");
			String topic = input.nextLine();
			System.out.println("Write what conference day the talk will be held");
			String day = input.nextLine();
			System.out.println("Write new starttime");
			String starts = input.nextLine();
			System.out.println("write id of talk you want to update: ");
			String id = input.nextLine();
			
			body = "table=" + table + "&title=" + title + "&description=" + description + "&topic=" + topic + "&day=" + day + "&starts=" + starts + "&id=" + id;
			
			HttpRequest request = new HttpRequest(method, path, port);
			request.setBody(body);
			request.execute();
			
		}
		else if(command.equalsIgnoreCase("clear")) {
			String method = "POST";
			String path = "/clear";
			
			HttpRequest request = new HttpRequest(method, path, port);
			request.execute();
			
		}
		else if(command.equals("add")) {
			String method = "POST";
			String path = "/add";
			
			HttpRequest request = new HttpRequest(method, path, port);
			request.execute();
		}
		else if(command.equals("delete")) {
			String method = "POST";
			String path = "/delete";
			
			System.out.println("What table do you want to delete from?");
			String table = input.nextLine();
			System.out.println("Write id of talk you want to delete");
			String id = input.nextLine();
			
			String body = "table=" + table + "&id=" +id;
			
			HttpRequest request = new HttpRequest(method, path, port);
			request.setBody(body);
			request.execute();
		}
		else {
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
		acceptedCommands.add("insert");
		acceptedCommands.add("update");
		acceptedCommands.add("clear");
		acceptedCommands.add("delete");
	}
}
