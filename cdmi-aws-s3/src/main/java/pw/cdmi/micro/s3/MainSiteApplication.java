package pw.cdmi.micro.s3;

//import com.alibaba.nacos.api.annotation.NacosInjected;
//import com.alibaba.nacos.api.annotation.NacosProperties;
//import com.alibaba.nacos.api.config.ConfigService;
//import com.alibaba.nacos.api.exception.NacosException;
//import com.alibaba.nacos.api.naming.NamingService;
//import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
//import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
//import net.unicon.cas.client.configuration.EnableCasClient;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@NacosPropertySource(dataId = "UAP.MICRO.S3", autoRefreshed = true) /**自动更新与S3微服务有关的配置 **/
//@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = "127.0.0.1:8848"))
public class MainSiteApplication {

//	@NacosInjected
//	private NamingService namingService;
//
//	@NacosInjected
//	private ConfigService configService;
//
//	@Value("${server.port}")
//	private int serverPort;
//
//	@Value("${spring.application.name}")
//	private String applicationName;
//
//	@PostConstruct
//	public void registerInstance() throws NacosException {
//		namingService.registerInstance(applicationName,"127.0.0.1",serverPort);
//		configService.publishConfig("casd", "DEFAULT_GROUP", "{\"id\":1,\"name\":\"mercyblitz\"}");
//	}

	public static void main(String[] args) {
		SpringApplication.run(MainSiteApplication.class, args);
	}
}
