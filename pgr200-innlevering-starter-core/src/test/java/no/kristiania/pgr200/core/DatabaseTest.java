package no.kristiania.pgr200.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.h2.jdbc.JdbcSQLException;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.BeforeClass;
import org.junit.Test;



public class DatabaseTest {

	
	public DataSource createDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
		dataSource.setUser("sa");
		
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.migrate();
		
		return dataSource;
	}
	
	@Test
	public void shouldClearDB() throws SQLException {
		DataSource dataSource = createDataSource();
		ConferenceDao dao = new ConferenceDao(dataSource);
		dao.insertTalk("test", "tester", "topictest", "daytest", "startstest");
		
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.clean();
		flyway.migrate();
		
		assertThat(dao.listTalks().isEmpty()).isTrue();
		
	}
	
	
	@Test
	public void shouldReturnTalks() throws SQLException {
		DataSource dataSource = createDataSource();
		ConferenceDao dao = new ConferenceDao(dataSource);
		dao.insertTalk("java", "morejava", "3", "4", "5");
		ConferenceTalk thisTalk = null;
		List<ConferenceTalk> list = dao.listTalks();
		for(ConferenceTalk talk : list) {
			if(talk.getTitle().equals("java")) {
				thisTalk = talk;
			}
		}
		assertThat(thisTalk.getTitle()).isEqualTo("java");
		
	}
	
	@Test
	public void shouldInsertTalk() throws SQLException {
		DataSource dataSource = createDataSource();
		ConferenceDao dao = new ConferenceDao(dataSource);
		dao.insertTalk("Title", "Description", "Topic","Day", "Starts");
		ConferenceTalk thisTalk = null;
		List<ConferenceTalk> list = dao.listTalks();
		for(ConferenceTalk talk : list) {
			if(talk.getTitle().equals("Title")) {
				thisTalk = talk;
			}
		}
		assertThat(thisTalk.getTitle()).isEqualTo("Title");
		
	}
	
	@Test
	public void shouldUpdateTalk() throws SQLException{
		DataSource dataSource = createDataSource();
		ConferenceDao dao = new ConferenceDao(dataSource);
		dao.insertTalk("new", "test", "testtopic", "testday", "teststart");
		ConferenceTalk insertedTalk = null;
		List<ConferenceTalk> list = dao.listTalks();
		for(ConferenceTalk t : list) {
			if(t.getTitle().equals("new")) {
				insertedTalk = t;
			}
		}
		String id = insertedTalk.getID();
		dao.updateTalk("talks", "title", "description", "topic", "day", "starts", id);
		List<ConferenceTalk> newList = dao.listTalks();
		for(ConferenceTalk talk : newList) {
			insertedTalk = talk;
		}
		assertThat(insertedTalk.getTitle()).isEqualTo("title");
	}
	
	@Test
	public void shouldDeleteTalk() throws SQLException{
		DataSource dataSource = createDataSource();
		ConferenceDao dao = new ConferenceDao(dataSource);
		dao.insertTalk("this", "talk", "should", "be", "deleted");
		ConferenceTalk insertedTalk = null;
		List<ConferenceTalk> list = dao.listTalks();
		for(ConferenceTalk t : list) {
			if(t.getTitle().equals("this")) {
				insertedTalk = t;
			}
		}
		assertThat(insertedTalk.getTitle()).isEqualTo("this");
		dao.deleteTalk("talks", insertedTalk.getID());
		boolean checked = false;
		List<ConferenceTalk> newList = dao.listTalks();
		for(ConferenceTalk t : newList) {
			if(t.getTitle().equals("this")) {
				checked = true;
			}
		}
		assertThat(checked).isEqualTo(false);
	}
	
}
