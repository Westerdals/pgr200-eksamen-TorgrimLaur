package no.kristiania.pgr200.server;

import org.junit.Test;



import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

public class HttpRequestTest {

	
	@Test
	public void ShouldExecuteRequest() throws IOException {
		HttpServer server = new HttpServer(0);
		int port = server.getActualPort();
		HttpRequest request = new HttpRequest("localhost", port, "echo?status=200");
		HttpResponse response = request.execute();
		
		assertThat(response.getStatusCode()).isEqualTo(200);
	}
}
