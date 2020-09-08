package pw.cdmi.paas.rs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import pw.cdmi.paas.auth.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class) // 指定spring-boot的启动类
@WebAppConfiguration
public class AccountTest {

	private TestRestTemplate restTemplate;

	private String wxid;

	private String mobile;

	private String qywxCordId;

	private String staffId;

	@Before
	public void before() {
		// 初始化数据
		wxid = "wx2343424242";
		mobile = "18615703283";
		qywxCordId = "qw32322323232";
		staffId = "wgssgs";
		restTemplate = new TestRestTemplate();
	}

	@Test
	public void registerUserByWX() {
		// 通过微信ID获取用户的信息，如名称，头像
	}

	@Test
	public void registerUserByMobile() {
		// 通过手机注册一个账号
//		NewUser user = new NewUser();
//		user.setChannel(RegisterChannel.MOBILE);
//		NewUser.Info info = user.new Info();
//		info.setAccount(mobile);
//		user.setInfo(info);
//		HttpHeaders headers = new HttpHeaders();
//        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//        headers.setContentType(type);
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//        headers.set("token", "xxx");
////		JSONObject jsonObj = JSONObject.fromObject(user);
////		String json_string = jsonObj.toString();
////		System.out.println("json_user :" + json_string);
//		
//		HttpEntity<NewUser> request = new HttpEntity<NewUser>(user, headers);
//
//		String url = "http://localhost:8888/users/v3/user";
//		ResponseEntity<String> result = restTemplate.postForEntity(url, request, String.class);
//		System.out.println("newid :" + result.getBody());
	}

	@Test
	public void registerUserByQYWX() {
		// 通过企业微信注册一个用户账号
	}
}
