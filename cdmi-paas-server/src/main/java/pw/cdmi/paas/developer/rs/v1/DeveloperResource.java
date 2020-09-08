package pw.cdmi.paas.developer.rs.v1;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pw.cdmi.paas.developer.model.DeveloperResponse;
import pw.cdmi.paas.developer.model.DeveloperType;
import pw.cdmi.paas.developer.model.NewDeveloper;
import pw.cdmi.paas.developer.model.entities.AuthCertificate;
import pw.cdmi.paas.developer.model.entities.Developer;
import pw.cdmi.paas.developer.service.AuthCertificateService;
import pw.cdmi.paas.developer.service.DeveloperService;


@RestController
@RequestMapping("/pass/v3")
public class DeveloperResource  {
	@Autowired
	private DeveloperService developerService;
	
	@Autowired
	private AuthCertificateService authCertificateService;
	
	/**
	 * 注册用户添加开发者信息
	 * @param developer
	 * @return
	 */
	@RequestMapping(value="/developers/developer", method = RequestMethod.POST)
	public @ResponseBody String createDeveloper(@RequestParam NewDeveloper developer){
	//	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String accountId = "";
		if(developer==null||developer.getManager()==null
				||StringUtils.isBlank(developer.getName())
				||StringUtils.isBlank(developer.getType())
				||StringUtils.isBlank(developer.getManager().getEmail())
				||StringUtils.isBlank(developer.getManager().getMobile())
				){
			throw new SecurityException("参数传入错误");
		}
		DeveloperType fromName = DeveloperType.fromName(developer.getType());
		if(fromName==null){
			throw new SecurityException("类型传入错误");
		}
		
		Developer newDeveloper = new Developer();
		switch (fromName) {
		case Persion:
			newDeveloper.setName(developer.getName());
			newDeveloper.setType(fromName);
			newDeveloper.setManagerEmail(developer.getManager().getEmail());
			newDeveloper.setManagerMobile(developer.getManager().getMobile());
			newDeveloper.setManagerName(developer.getName());
			newDeveloper.setDirector_id("123");
			
			break;
		case Enterprise:
			if(StringUtils.isBlank(developer.getManager().getName())){
				throw new SecurityException("manage名字不能为空");
			}
			newDeveloper.setName(developer.getName());
			newDeveloper.setType(fromName);
			newDeveloper.setManagerEmail(developer.getManager().getEmail());
			newDeveloper.setManagerMobile(developer.getManager().getMobile());
			newDeveloper.setManagerName(developer.getName());
			newDeveloper.setDirector_id(" ");

			break;

		default:
			break;
		}
		
		String newid =  developerService.createDeveloper(newDeveloper);
		return newid;		
	}
	
	@RequestMapping(value = "/{developer_id}",method = RequestMethod.GET)
	public @ResponseBody DeveloperResponse getDeveloperById(@PathVariable("developer_id")String id){
		if(StringUtils.isBlank(id)){
			throw new SecurityException("参数传入错误");
		}
		Developer findById = developerService.findDeveloperById(id);
		if(findById==null){
			throw new SecurityException("未找到该开发者");
		}
		DeveloperResponse developerResponse = new DeveloperResponse();
		developerResponse.setId(findById.getId());
		developerResponse.setName(findById.getName());
		developerResponse.setCreatetime(findById.getCreateTime().toString());
		developerResponse.setType(findById.getType().toString());
		
		developerResponse.getManager().setEmail(findById.getManagerEmail());
		developerResponse.getManager().setMobile(findById.getManagerMobile());
		developerResponse.getManager().setName(findById.getManagerName());		
		return developerResponse;	
	}
	
	@RequestMapping(value="/certificates/certificate", method = RequestMethod.POST)
	public @ResponseBody AuthCertificate createCertificate(){
		String developerId = "";
		return authCertificateService.createCertificate(developerId);
	}
}
