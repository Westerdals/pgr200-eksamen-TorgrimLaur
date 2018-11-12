package no.kristiania.pgr200.client;


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
    private String body = "none";
    private LinkedHashMap<String, String> requestHeaders;

    public HttpRequest(String method, String path, int port) throws IOException {
        
        this.method = method;
        this.port = port;
        this.path = path;
        requestHeaders = new LinkedHashMap<>();
        addRequestHeaders();
     
    }
    
    public HttpResponse execute() throws UnknownHostException, IOException {
    	Socket clientSocket = new Socket(host, port);
    	outputStream = clientSocket.getOutputStream();
    	
    	writeLine((method + " " + path + " " + protocol));
    	for(String key : requestHeaders.keySet()) {
    		writeLine((key + requestHeaders.get(key)));
    	}
    	writeLine("");
    	if(body != null) {
    		writeLine(body);
    	}
    	outputStream.flush();
    	
    	
    	return new HttpResponse(clientSocket.getInputStream());
    }
    
    public static void main(String[] args) throws IOException {
    	new HttpRequest("POST", "/add", 80);
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
    public String getBody() {
    	return body;
    }
    
    public void setHost(String host) {
    	this.host = host;
    }
    public String getHost() {
    	return host;
    }
}
