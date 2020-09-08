package pw.cdmi.core.encryption;

import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import pw.cdmi.msm.comment.rs.CommentTarget;
import pw.cdmi.msm.comment.rs.Owner;
import pw.cdmi.msm.comment.rs.TestCommentRequest;


public class CommentTest {

	@Test
	public void createComment(){
		TestCommentRequest testCommentRequest = new TestCommentRequest();
		
		Owner owner = new Owner();
		CommentTarget commentTarget = new CommentTarget();
		owner.setHeadImage("头像");
		owner.setId("评论人id");
		owner.setName("评论人name");
		commentTarget.setId("目标id");
		commentTarget.setOwnerId("目标持有者id");
		commentTarget.setType("Tenancy_File");
		testCommentRequest.setContent("我是评论");
		testCommentRequest.setOwner(owner);
		testCommentRequest.setTarget(commentTarget);
		
		
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());	
		HttpEntity<TestCommentRequest> request = new HttpEntity<TestCommentRequest>(testCommentRequest, headers);

		String url = "http://localhost:8080/comments/v1/test";
		ResponseEntity<String> result = new RestTemplate().postForEntity(url, request, String.class);
		System.out.println("newid :" + result.getBody());
	}
	@Test
	public void listComment(){
		String url = "http://localhost:8080/comments/v1/目标id/comment?type=Tenancy_File&cursor=0&maxcount=10";
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity(url, String.class);
		System.out.println(forEntity.getBody());
	}
	@Test
	public void  commentNumber(){
		String url = "http://localhost:8080/comments/v1/目标id/amount?type=Tenancy_File";
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity(url, String.class);
		System.out.println(forEntity.getBody());
	}
	
	@Test
	public void deleteComment(){
		
	}
}
