package no.kristiania.pgr200.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponse {

    private int statusCode;
    private String statusText;
    private InputStream inputStream;
    private Map<String, String> headers = new LinkedHashMap<>();


    public HttpResponse(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        String statusLine = readNextLine();

        String[] parts = statusLine.split(" ");
        this.statusCode = Integer.parseInt(parts[1]);
        this.statusText = parts[2];

        String headerLine;
        while((headerLine = readNextLine()) != null){
            if(headerLine.isEmpty()) break;

            int colonPos = headerLine.indexOf(":");
            String headerName = headerLine.substring(0, colonPos);
            String headerValue = headerLine.substring(colonPos+1).trim();

            headers.put(headerName, headerValue);
        }

        String body = readNextLine();


        System.out.println("HTTP/1.1 " + statusCode + " " + statusText);
        for(String key : headers.keySet()){
            System.out.println(key + ": " + headers.get(key));
        }
        System.out.println(body);
    }

    private String readNextLine() throws IOException {
        StringBuilder result = new StringBuilder();
        int c;
        while((c = inputStream.read()) != -1){
            if(c == '\r'){
                c = inputStream.read();
                if(c != '\n'){
                    inputStream.reset();
                }
                break;
            }
            result.append((char)c);
        }
        return result.toString();
    }
    
    public int getStatusCode() {
    	return statusCode;
    }
}

