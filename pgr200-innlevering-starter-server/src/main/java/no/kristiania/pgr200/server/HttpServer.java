package no.kristiania.pgr200.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import no.kristiania.pgr200.core.ConferenceDao;
import no.kristiania.pgr200.core.ConferenceTalk;
import no.kristiania.pgr200.core.DatabaseMain;

    public class HttpServer {
    
    private ServerSocket serverSocket;
    private int port;
    private ConferenceDao dao;
    private DatabaseMain dataMain;
    private HashMap<String, String> statusMessages = new HashMap<>();
    private String statusCode;
	private String method;
	private HttpPath path;
	private String protocol;
    
    
    
    public HttpServer(int port) throws IOException {
    	addStatusMessages();
    	serverSocket = new ServerSocket(port);
        start();
    }    
    
    
    public static void main (String[] args) throws IOException {
        new HttpServer(80);
        
        }
    
    public void start() throws IOException {
        new Thread(() -> {
            try {
                serverThread();
            } catch (SQLException e) {
                
                e.printStackTrace();
            }
        }).start();
    }
    
    public void serverThread() throws SQLException {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                
                InputStream input = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream();
                ArrayList<String> requestLines = new ArrayList<>();
                
                String line = readNextLine(input);
                while(!line.isEmpty()) {
                    System.out.println(line);
                    requestLines.add(line);
                    /*if (line.contains("list")) {
                        String[] arguments = new String[] {"list"};
                        DatabaseMain.main(arguments);
                    }else if(line.contains("add")) {
                    	String[] arguments = new String[] {"add"};
                    	DatabaseMain.main(arguments);
                    }*/
                    line = readNextLine(input);
                    
                }
                handleRequest(requestLines);
                
                output.write((protocol + " " + statusCode + " " + statusMessages.get(statusCode) + "\r\n").getBytes());
                output.write(("Connection: close\r\n").getBytes());
                
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();    
            }
        }
    }
    
    public static String readNextLine(InputStream input) throws IOException {
        StringBuilder nextLine = new StringBuilder();
        int c;
        while ((c = input.read()) != -1) {
            if (c == '\r') {
                input.read();
                break;
            }
            nextLine.append((char )c );
        }
        return nextLine.toString();
        
    }
    
    public void handleRequest(ArrayList<String> requestLines) throws SQLException {
    	String requestLine;
    	statusCode = "200";
    	
    	ArrayList<String> lines = requestLines;
    	for(int i = 0; i < lines.size(); i++) {
    		if(lines.get(i).contains("/")) {
    		requestLine = lines.get(i);
    		String[] parts = requestLine.split(" ");
    		method = parts[0];
    		path = new HttpPath(parts[1]);
    		protocol = parts[2];
    		}else {
    			statusCode = "404";
    		}
    	}
    	
    	if(method.equals("GET")) {
    		//kjoer list fra database
    		DatabaseMain db = new DatabaseMain();
    		String[] args = {"list"};
    		db.main(args);
    		//list maa returnere en liste som kan skrives i body i response
    	}else if(method.equals("POST")) {
    		if(path.getFullPath().contains("add")) {
    			DatabaseMain db = new DatabaseMain();
    			String[] args = {};
    			db.main(args);
    			//add fields maa ligge i path
    		}else if(path.getFullPath().contains("update")) {
    			DatabaseMain db = new DatabaseMain();
    			String[] args = {};
    			db.main(args);
    			//update fields maa ligge i path
    		}
    	}
    	
    }
    
    public void addStatusMessages() {
    	statusMessages.put("200", "OK");
    	statusMessages.put("404", "Not Found");
    	statusMessages.put("500", "Internal Server Error");
    }
    
    public int getActualPort() {
    	return serverSocket.getLocalPort();
    }
    
}
