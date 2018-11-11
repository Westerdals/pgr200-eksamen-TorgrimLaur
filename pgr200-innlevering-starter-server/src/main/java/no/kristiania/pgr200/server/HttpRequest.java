package no.kristiania.pgr200.server;


import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Scanner;



public class HttpRequest {
    
    
    private String host;
    private int port;
    private OutputStream outputStream;
    private String method = "GET";
    private String protocol = "HTTP/1.1";
    private String path;
    private LinkedHashMap<String, String> requestHeaders;

    public HttpRequest(String host, int port, String path) throws IOException {
        //Scanner scanner = new Scanner(System.in);
        //String input = scanner.nextLine();
        this.host = host;
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
    	outputStream.flush();
    	
    	
    	return new HttpResponse(clientSocket.getInputStream());
    }
    
    public static void main (String[] args) throws IOException {
        System.out.println("Insert Command!");
        new HttpRequest("localhost", 80, "");
    }
    
    public void writeLine(String line) throws IOException {
    	outputStream.write((line + "\r\n").getBytes());
    }
    
    public void addRequestHeaders() {
    	requestHeaders.put("Host: ", host);
    	requestHeaders.put("Connection: ", "close");
    }
}
