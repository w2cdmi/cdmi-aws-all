package pw.cdmi.open.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//@Configuration
//@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	
	@Value("${resource.id:spring-boot-application}") // 默认值spring-boot-application
	private String resourceId;
	
	@Value("${access_token.validity_period:3600}") // 默认值3600
	private int accessTokenValiditySeconds = 3600;
	
	private TokenStore tokenStore = new InMemoryTokenStore();
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private OAuth2UserDetailsService userDetailsService;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.inMemory()
//        .withClient("browser")//客户端ID,通过浏览器方式访问
//        .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//        .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//        .scopes("read", "write", "trust")//授权用户的操作权限
//        .secret("secret")//密码
//        .accessTokenValiditySeconds(120).//token有效期为120秒
//        refreshTokenValiditySeconds(600);//刷新token有效期为600秒
//		clients.inMemory()
//        .withClient("aws-client")//客户端ID,通过第三方系统调用AK/SK方式
//        .authorizedGrantTypes("client_credentials", "authorization_code", "refresh_token")
//        .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//        .scopes("read", "write", "trust")//授权用户的操作权限
//        .secret("secret")				 //密码
//        .accessTokenValiditySeconds(120).//token有效期为120秒
//        refreshTokenValiditySeconds(600);//刷新token有效期为600秒
		super.configure(clients);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
//		.authenticationManager(authenticationManager);
//		endpoints.tokenStore(tokenStore).authenticationManager(authenticationManager)
//				.userDetailsService(userDetailsService);
//		endpoints.authenticationManager(this.authenticationManager);
//		endpoints.accessTokenConverter(accessTokenConverter());
//		endpoints.tokenStore(tokenStore());
//		
//		// 配置TokenServices参数
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(endpoints.getTokenStore());
//        tokenServices.setSupportRefreshToken(false);
//        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
//        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
//        tokenServices.setAccessTokenValiditySeconds( (int) TimeUnit.DAYS.toSeconds(30)); // 30天
//        endpoints.tokenServices(tokenServices);
		super.configure(endpoints);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		super.configure(oauthServer);
//		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}
	
	/**
	 * token store
	 * 
	 * @param accessTokenConverter
	 * @return
	 */
//	@Bean
//	public TokenStore tokenStore() {
//		TokenStore tokenStore = new JwtTokenStore(accessTokenConverter());
//		return tokenStore;
//	}
	
	/**
	 * token converter
	 * 
	 * @return
	 */
//	@Bean
//	public JwtAccessTokenConverter accessTokenConverter() {
//		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {
//			/***
//			 * 重写增强token方法,用于自定义一些token返回的信息
//			 */
//			@Override
//			public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//				String userName = authentication.getUserAuthentication().getName();
//				User user = (User) authentication.getUserAuthentication().getPrincipal();// 与登录时候放进去的UserDetail实现类一直查看link{SecurityConfiguration}
//				/** 自定义一些token属性 ***/
//				final Map<String, Object> additionalInformation = new HashMap<>();
//				additionalInformation.put("userName", userName);
//				additionalInformation.put("roles", user.getAuthorities());
//				((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
//				OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);
//				return enhancedToken;
//			}
//
//		};
//		accessTokenConverter.setSigningKey("123");// 测试用,资源服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
//		return accessTokenConverter;
//	}
}
