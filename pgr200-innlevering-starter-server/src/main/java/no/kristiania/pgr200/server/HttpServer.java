package no.kristiania.pgr200.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	private String requestBody = "none";
	private String responseBody = "none";
    
    
    
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
                    line = readNextLine(input);
                    
                }
                line = readNextLine(input);
                if(!line.isEmpty()) {
                	requestBody = line;
                }
                handleRequest(requestLines);
                int contentLength = responseBody.length();
                if(responseBody.equals("none")) {
                	contentLength = 0;
                }
                output.write((protocol + " " + statusCode + " " + statusMessages.get(statusCode) + "\r\n").getBytes());
                output.write("X-Server-Name: Kristiania Web Server\r\n".getBytes());
                output.write(("Connection: close\r\n").getBytes());
                
                
               	output.write(("Content-Length: " + Integer.toString(contentLength) + "\r\n").getBytes());
               	output.write(("\r\n").getBytes());
               	output.write((responseBody + "\r\n").getBytes());
                
                
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
    	statusCode = "404";
    	
    	ArrayList<String> lines = requestLines;
    	for(int i = 0; i < lines.size(); i++) {
    		System.out.println(lines.get(i));
    		if(lines.get(i).contains("HTTP")) {
    		requestLine = lines.get(i);
    		String[] parts = requestLine.split(" ");
    		method = parts[0];
    		path = new HttpPath(parts[1]);
    		protocol = parts[2];
    		statusCode = "200";
    		}
    		
    	}
    	
    	
    	if(method.equals("GET")) {
    		List<ConferenceTalk> list;
    		
    		String[] arguments = {"list"};
    		//DatabaseMain.main(arguments);
    		//list = DatabaseMain.getList();
    		DatabaseMain db = new DatabaseMain();
    		db.run(arguments);
    		list = db.getList();
    		StringBuilder sb = new StringBuilder();
    		for(int i = 0; i < list.size(); i ++) {
    			sb.append(list.get(i));
    		}
    		responseBody = sb.toString();
    		
    		
    		//maa motta list fra database og sende som strings i body
    	}else if(method.equals("POST")) {
    		if(path.getFullPath().contains("add")) {
    			
    			String[] arguments = {"add"};
    			DatabaseMain.main(arguments);
    			statusCode = "200";
    			responseBody = "inserted default talk";
    			
    		}else if(path.getFullPath().contains("insert")) {
    			String[] array = requestBody.split("&");
    			HashMap<String, String> parts = new HashMap<>();
    			for(String s : array) {
    				int equalPos = s.indexOf("=");
    				parts.put(s.substring(0, equalPos), s.substring(equalPos+1));
    			}
    			String arg2 = parts.get("title");
    		    String arg3 = parts.get("description");
    		    String arg4 = parts.get("topic");
    		    String arg5 = parts.get("day");
    		    String arg6 = parts.get("starts");
    			String[] arguments = {"insert", arg2, arg3, arg4, arg5, arg6};
    			//maa ha fields fra client
    			
    			
    			
    			DatabaseMain.main(arguments);
    			statusCode = "200";
    			responseBody = "inserted talk";
    		}else if(path.getFullPath().contains("update")) {
    			
    			String[] arguments = {"update"};
    			DatabaseMain.main(arguments);
    			//maa ha fields fra client
    		}else if(path.getFullPath().contains("clear")) {
    			String[] arguments = {"clear"};
    			DatabaseMain.main(arguments);
    			
    		}
    	}else {
    		statusCode = "404";
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
