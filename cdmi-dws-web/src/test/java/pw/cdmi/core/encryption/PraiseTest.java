package pw.cdmi.core.encryption;

import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import pw.cdmi.msm.praise.model.PraiseTarget;
import pw.cdmi.msm.praise.rs.Owner;
import pw.cdmi.msm.praise.rs.TestPraiseRequest;

public class PraiseTest {

	@Test
	public void createPraiseTest(){
		TestPraiseRequest testPraiseRequest = new TestPraiseRequest();
		PraiseTarget praiseTarget = new PraiseTarget();
		
		Owner owner = new Owner();
		
		praiseTarget.setId("目标id");
		praiseTarget.setOwnerId("目标的主人id");
		praiseTarget.setType("Tenancy_File");
		
		owner.setId("点赞人");
		owner.setName("点赞人name");
		owner.setHeadImage("头像");
		testPraiseRequest.setOwner(owner);
		testPraiseRequest.setTarget(praiseTarget);
		
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());	
		HttpEntity<TestPraiseRequest> request = new HttpEntity<TestPraiseRequest>(testPraiseRequest, headers);

		String url = "http://localhost:8080/praise/v1/test";
		ResponseEntity<String> result = new RestTemplate().postForEntity(url, request, String.class);
		System.out.println("newid :" + result.getBody());
		
	}
	
	@Test
	public void  praiseNumber(){
		String url = "http://localhost:8080/praise/v1/目标id/amount?type=Tenancy_File";
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity(url, String.class);
		System.out.println(forEntity.getBody());
		
	}
	
	@Test
	public void listPraise(){
		String url = "http://localhost:8080/praise/v1/目标id/praiser?type=Tenancy_File&cursor=0&maxcount=10";
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity(url, String.class);
		System.out.println(forEntity.getBody());
	}
	@Test
	public void IsEmpty(){
		String url = "http://localhost:8080/praise/v1/users/点赞人id/praised/目标id/?type=Tenancy_File";
		ResponseEntity<Map> forEntity = new RestTemplate().getForEntity(url, Map.class);
		System.out.println(forEntity.getBody());

	}
	
	
	
	@Test
	public void deletePraise(){	
		String url = "http://localhost:8080/praise/v1/users/点赞人id/praised/目标id/?type=Tenancy_File";
		ResponseEntity<Map> forEntity = new RestTemplate().getForEntity(url, Map.class);
		System.out.println(forEntity.getBody());
		JSONObject fromObject = JSONObject.fromObject(forEntity.getBody());
		Map bean = (Map)JSONObject.toBean(fromObject,Map.class);
		String id = (String)bean.get("id");
		
		String url2 = "http://localhost:8080/praise/v1/"+id+"?user_id=点赞人id";
		new RestTemplate().delete(url2,String.class);
		
	}
	
	
}
