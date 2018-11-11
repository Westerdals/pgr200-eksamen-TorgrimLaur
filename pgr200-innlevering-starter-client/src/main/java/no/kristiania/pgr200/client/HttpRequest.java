package no.kristiania.pgr200.client;


import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import no.kristiania.pgr200.server.HttpResponse;



public class HttpRequest {
    
    
    private String host;
    private int port;
    private OutputStream outputStream;
    private String method;
    private String protocol;
    private String path;

    public HttpRequest(String host, int port) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        this.host = host;
        this.port = port;
        Socket clientSocket = new Socket(host, port);
        
        
        clientSocket.getOutputStream().write((input + "\r\n").getBytes());
        
        clientSocket.close();
        scanner.close();

    }
    
    public HttpResponse execute() throws UnknownHostException, IOException {
    	Socket clientSocket = new Socket(host, port);
    	outputStream = clientSocket.getOutputStream();
    	
    	writeLine((method + " " + path + " " + protocol));
    	writeLine("Connection: close");
    	writeLine("");
    	outputStream.flush();
    	
    	
    	return new HttpResponse(clientSocket.getInputStream());
    }
    
    public static void main (String[] args) throws IOException {
        System.out.println("Insert Command!");
        new HttpRequest("localhost", 80);
    }
    
    public void writeLine(String line) throws IOException {
    	outputStream.write((line + "\r\n").getBytes());
    }

    
    
    
    
}
