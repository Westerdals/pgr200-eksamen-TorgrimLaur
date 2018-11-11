package no.kristiania.pgr200.server;


import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Scanner;



public class HttpRequest {
    
    
    private String host = "localhost";
    private int port;
    private OutputStream outputStream;
    private String method;
    private String protocol = "HTTP/1.1";
    private String path;
    private String body;
    private LinkedHashMap<String, String> requestHeaders;

    public HttpRequest(String method, String path, int port) throws IOException {
        //Scanner scanner = new Scanner(System.in);
        //String input = scanner.nextLine();
        this.method = method;
        this.port = port;
        this.path = path;
        requestHeaders = new LinkedHashMap<>();
        addRequestHeaders();
        //Socket clientSocket = new Socket(host, port);
        
        
        //clientSocket.getOutputStream().write((input + "\r\n").getBytes());
        
        //clientSocket.close();
       // scanner.close();

    }
    
    public HttpResponse execute() throws UnknownHostException, IOException {
    	Socket clientSocket = new Socket(host, port);
    	outputStream = clientSocket.getOutputStream();
    	
    	writeLine((method + " " + path + " " + protocol));
    	for(String key : requestHeaders.keySet()) {
    		writeLine((key + requestHeaders.get(key)));
    	}
    	writeLine("");
    	if(!body.isEmpty()) {
    		writeLine(body);
    	}
    	outputStream.flush();
    	
    	
    	return new HttpResponse(clientSocket.getInputStream());
    }
    
    
    public void writeLine(String line) throws IOException {
    	outputStream.write((line + "\r\n").getBytes());
    }
    
    public void addRequestHeaders() {
    	requestHeaders.put("Host: ", host);
    	requestHeaders.put("Connection: ", "close");
    }
    
    public void setBody(String body) {
    	this.body = body;
    }
}
