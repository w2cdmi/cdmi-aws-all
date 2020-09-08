package pw.cdmi.core.encryption;


import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import pw.cdmi.core.encryption.AesCBCEncryptor;

@SpringBootApplication
@EnableCaching
public class Application {
	//自定义配置项解密类
	
	@Bean(name = "jasyptStringEncryptor")
	public StringEncryptor stringEncryptor() {
		return new AesCBCEncryptor();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
