package no.kristiania.pgr200.server;

import java.util.LinkedHashMap;

public class HttpPath {
    
    private String fullPath;
    private String path;
    private LinkedHashMap<String, String> data;
    
    public HttpPath(String fullPath) {
        this.fullPath = fullPath;
        data = new LinkedHashMap<>();
        handlePath();
    }
    
    public void handlePath() {
        if(fullPath.contains("?")) {
        	int questionPos = fullPath.indexOf('?');
            //String[] parts = fullPath.split("\\?");
            path = fullPath.substring(questionPos+1);
            if(path.contains("&")) {
                String[] dataParts = path.split("&");
                for(String s : dataParts) {
                    int equalPos = s.indexOf("=");
                    data.put(s.substring(0, equalPos), s.substring(equalPos+1));
                }
            }else {
            	int equalPos = path.indexOf("=");
            	data.put(path.substring(0, equalPos), path.substring(equalPos+1));
            }
        }
    }
    
    public String getFullPath() {
        return fullPath;
    }
    
    public String getPath() {
        return path;
    }
    
    public LinkedHashMap<String, String> getMap(){
        return data;
    }

}