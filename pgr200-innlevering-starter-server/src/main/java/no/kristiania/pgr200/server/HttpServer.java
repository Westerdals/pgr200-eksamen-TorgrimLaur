package no.kristiania.pgr200.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.sql.SQLException;
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
    
    
    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        start();
    }    
    
    
    public static void main (String[] args) throws IOException {
        new HttpServer(80);
        
        }
    
    public void start() throws IOException {
        new Thread(() -> {
            try {
                serverThread(serverSocket);
            } catch (SQLException e) {
                
                e.printStackTrace();
            }
        }).start();
    }
    
    public void serverThread(ServerSocket serverSocket) throws SQLException {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                
                InputStream input = clientSocket.getInputStream();
                
                String line = readNextLine(input);
                while(!line.isEmpty()) {
                    System.out.println(line);
                    if (line.contains("list")) {
                        String[] arguments = new String[] {"list"};
                        DatabaseMain.main(arguments);
                    }else if(line.contains("add")) {
                    	String[] arguments = new String[] {"add"};
                    	DatabaseMain.main(arguments);
                    }
                    line = readNextLine(input);
                    
                }
                
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
    
    
    
    
}
