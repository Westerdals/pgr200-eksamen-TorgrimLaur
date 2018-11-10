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

}
