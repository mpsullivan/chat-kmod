
package ws.salient.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;


public class ResponseTest {
    
    
    ObjectMapper json = new ObjectMapper();
    
    @Test
    public void writeValuAsString() throws JsonProcessingException {
        Response response = new Response().withText("Line chart").withAttachable(new LineChart().withTitle("title", "subtitle"));
        String result = json.writeValueAsString(response);
        System.out.println(result);
    }
}
