package pw.cdmi.core.encryption;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class messageTest {
	@Test
	public void listMessage(){
		String url = "http://localhost:8080/messages/v1/messages?status=UNREAD&userId=目标持有者id&maxcount=1}";
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity(url, String.class);
		System.out.println(forEntity.getBody());
	}
	
}
