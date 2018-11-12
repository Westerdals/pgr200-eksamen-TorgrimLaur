package no.kristiania.pgr200.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGPoolingDataSource;

import no.kristiania.pgr200.core.ConferenceDao;

public class DatabaseMain {
    
    private DataSource dataSource;
    private ConferenceDao dao;
    
    private String dbuser;
    private String dbpassword;
    private String database;
    
        public DatabaseMain() throws SQLException {
            readProperties();
            this.dataSource = createDataSource();
            dao = new ConferenceDao(dataSource);
        }
    
        public DataSource createDataSource() {
            PGPoolingDataSource dataSource = new PGPoolingDataSource();
            dataSource.setUrl(database);
            dataSource.setUser(dbuser);
            dataSource.setPassword(dbpassword);
            
            Flyway flyway = new Flyway();
            flyway.setDataSource(dataSource);
            flyway.migrate();
            
            return dataSource;
        }
        
        private void readProperties() {
            Properties prop = new Properties();
            InputStream input = null;
            
            try {
                input = new FileInputStream("innlevering.properties");
                prop.load(input);
                dbuser = prop.getProperty("dbuser");
                dbpassword = prop.getProperty("dbpassword");
                database = prop.getProperty("database");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        public static void main (String[] args) throws SQLException {
            new DatabaseMain().run(args);
        }
        


        public void run(String[] args) throws SQLException {
            if (args.length == 0) {
                System.out.println("Run class with argument");
                System.exit(1);
                
            }
            
            String command = args[0];
            
            if (command.equals("list")) {
                dao.listTalks();
            }else if (command.equals("add")) {
                dao.insertTalk("Hello", "World", "Java", "Today", "Right Now");
            }else if (command.equals("insert")) {
                String title = args[1];
                String description = args[2];
                String topic = args[3];
                String day = args[4];
                String starts = args[5];
                dao.insertTalk(title, description, topic, day, starts);
            }else if (command.equals("update")) {
                String table = args[1];
                String title = args[2];
                String description = args[3];
                String topic = args[4];
                String day = args[5];
                String starts = args[6];
                String id = args[7];
                dao.updateTalk(table, title, description, topic, day, starts, id);
            }else if(command.equals("clear")){
            	clearDB();
            } else if (command.equals("delete")) {
                String table = args[1];
                String collumn = args[2];
                String change = args[3];
                dao.deleteTalk(table, collumn, change);
            }else {
                System.err.println("Unknown command");
            }
        }
        
        public static String userInput() {
            String userInput;
            Scanner input = new Scanner(System.in);
            System.out.println("write command");
            userInput = input.nextLine();
            
            if (!userInput.equals("add")) {
                input.close();
            }
            return userInput;
        }
        
        public List<ConferenceTalk> getList() throws SQLException {
            List<ConferenceTalk> list = dao.listTalks();
              
            return list;
        }
        
        public void clearDB() {
        	Flyway flyway = new Flyway();
            flyway.setDataSource(dataSource);
            flyway.clean();
        }
    
}
