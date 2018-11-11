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
    
    public HttpResponse execute() {
    	
    }
    
    public static void main (String[] args) throws IOException {
        System.out.println("Insert Command!");
        new HttpRequest("localhost", 80);
    }
    
    

    
    
    
    
}
