package biggis.api;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseFeature;

@Path("events")
public class PushServletSSEExample {

	@GET
	@Path("messages")
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput getServerSentEvents() {
		final EventOutput eventOutput = new EventOutput();
		try {
			new SimpleKafkaConsumer(eventOutput, "test").start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return eventOutput;
	}
	
	
}
