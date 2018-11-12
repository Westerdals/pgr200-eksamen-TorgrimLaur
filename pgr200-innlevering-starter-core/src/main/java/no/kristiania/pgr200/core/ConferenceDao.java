package no.kristiania.pgr200.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


public class ConferenceDao {
    
    private DataSource dataSource;
    
    public ConferenceDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public List<ConferenceTalk> listTalks() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM talks";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                try (ResultSet rs = statement.executeQuery()) {
                    List<ConferenceTalk> result = new ArrayList<>();
                    while (rs.next()) {
                        ConferenceTalk talk = new ConferenceTalk();
                        talk.setTitle(rs.getString("title"));
                        talk.setDescription(rs.getString("description"));
                        talk.setTopic(rs.getString("topic"));
                        talk.setDay(rs.getString("day"));
                        talk.setStarts(rs.getString("starts"));
                        talk.setID(rs.getString("id"));
                        result.add(talk);
                    }
                    return result;
                }
            }
        }
    }
    

    public void insertTalk(String title, String description, String topic, String day, String starts) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO talks (TITLE, DESCRIPTION, TOPIC, DAY, STARTS) values (? ,? ,? ,? ,?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, title);
                statement.setString(2, description);
                statement.setString(3, topic);
                statement.setString(4, day);
                statement.setString(5, starts);
                
                statement.executeUpdate();
            }
        }
    }
    /*public void updateTalk(String table, String collumn, String change, String cond1, String cond2) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE " + table + " set " + collumn + " = " + "'"+change+"'" + " where " + cond1 + " = " + "'"+cond2+"'";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.executeUpdate();
            }
        }
    }*/
    
    public void updateTalk(String table, String change1, String change2, String change3, String change4, String change5, String id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE " + table + " set title = " + "'"+change1+"', description = " + "'"+change2+"', topic = " + "'"+change3+"', day = "
                    +"'"+change4+"', starts = " + "'"+change5+"' where ID = " + id;
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.executeUpdate();
            }
        }
    }
    
    public void deleteTalk(String table, String collumn, String change) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM " + table + " WHERE " + collumn + " = " + "'" + change + "'";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.executeUpdate();
            }
        }
    }

}
