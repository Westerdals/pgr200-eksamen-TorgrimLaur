package no.kristiania.pgr200.core;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class ConferenceTalkTest {

	@Test
	public void shouldSetTitle() {
		ConferenceTalk talk = new ConferenceTalk();
		talk.setTitle("title");
		assertThat(talk.getTitle()).isEqualTo("title");
	}
	@Test
	public void shouldSetDescription() {
		ConferenceTalk talk = new ConferenceTalk();
		talk.setDescription("description");
		assertThat(talk.getDescription()).isEqualTo("description");
	}
	@Test
	public void shouldSetTopic() {
		ConferenceTalk talk = new ConferenceTalk();
		talk.setTopic("topic");
		assertThat(talk.getTopic()).isEqualTo("topic");
	}
	@Test
	public void shouldSetDay() {
		ConferenceTalk talk = new ConferenceTalk();
		talk.setDay("day");
		assertThat(talk.getDay()).isEqualTo("day");
	}
	@Test
	public void shouldSetTime() {
		ConferenceTalk talk = new ConferenceTalk();
		talk.setStarts("starts");
		assertThat(talk.getStarts()).isEqualTo("starts");
	}
}
