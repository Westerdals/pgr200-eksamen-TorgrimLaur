package no.kristiania.pgr200.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.h2.jdbc.JdbcSQLException;
import org.h2.jdbcx.JdbcDataSource;
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
	public void shouldReturnTalks() throws SQLException {
		DataSource dataSource = createDataSource();
		ConferenceDao dao = new ConferenceDao(dataSource);
		dao.insertTalk("java", "morejava", "3", "4", "5");
		List<ConferenceTalk> list = dao.listTalks();
		ConferenceTalk talk = list.get(0);
		assertThat(list).contains(talk);
		
	}
	/*
	@Test
	public void shouldInsertTalk() throws SQLException {
		DataSource dataSource = createDataSource();
		ConferenceDao dao = new ConferenceDao(dataSource);
		dao.insertTalk("Test", "Tester");
		List<ConferenceTalk> list = dao.listTalks();
		ConferenceTalk talk = list.get(0);
		assertThat(list).contains(talk);
		
	}
	
	@Test
	public void shouldClearDB() throws SQLException {
		DataSource dataSource = createDataSource();
		ConferenceDao dao = new ConferenceDao(dataSource);
		dao.insertTalk("test", "tester");

		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.migrate();
		
		assertThat(dao.listTalks().isEmpty()).isTrue();
		
	}*/
	
}
