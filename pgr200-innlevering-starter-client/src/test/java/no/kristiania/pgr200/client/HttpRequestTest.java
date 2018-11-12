package no.kristiania.pgr200.client;

import org.junit.Test;

import no.kristiania.pgr200.client.HttpRequest;
import no.kristiania.pgr200.client.HttpResponse;
import no.kristiania.pgr200.server.HttpServer;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

public class HttpRequestTest {

	
	@Test
	public void ShouldExecuteRequest() throws IOException {
		HttpServer server = new HttpServer(0);
		int port = server.getActualPort();
		HttpRequest request = new HttpRequest("GET", "/list", port);
		HttpResponse response = request.execute();
		
		assertThat(response.getStatusCode()).isEqualTo(200);
	}
	@Test
	   public void shouldParseStatusLine() throws IOException {
	       HttpRequest request = new HttpRequest("urlecho.appspot.com", "/echo?status=404", 80);
	       HttpResponse response = request.execute();
	       assertThat(response.getStatusCode()).isEqualTo(404);
	       assertThat(response.getStatusText()).isEqualTo("Not Found");
	   }
}
