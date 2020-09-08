package pw.cdmi.aws.geo;

import org.apache.servicecomb.springboot.starter.provider.EnableServiceComb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author No.11
 *
 */

 @SpringBootApplication
// @ComponentScan(excludeFilters = {
// @Filter(type = FilterType.ANNOTATION, classes = MongoRepositoryBean.class) })
// @ComponentScan(basePackages = { "pw.cdmi.aws" })
// @EnableJpaRepositories(basePackages = { "pw.cdmi.aws.geo.repositories.mongo"
// }, excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value
// = MongoRepositoryBean.class))
// @EnableJpaRepositories(basePackages = { "pw.cdmi.aws.geo.repositories.mongo"
// })
// @EntityScan(basePackages = { "pw.cdmi.aws" })
// @EnableTransactionManagement
// @EnableDiscoveryClient
@EnableServiceComb // 新增注解
public class Application {

	public static void main(String[] args) throws Exception {
//		Log4jUtils.init(); // # 日志初始化
//		BeanUtils.init(); // # Spring bean初始化
		SpringApplication.run(Application.class, args);
	}
}
