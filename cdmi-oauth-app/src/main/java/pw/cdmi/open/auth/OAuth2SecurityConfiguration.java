package pw.cdmi.open.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private OAuth2UserDetailsService userDetailsService;

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new Md5PasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.POST).antMatchers("/users/v3/user");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				// .antMatchers("/orders/**").hasRole("USER") //用户权限
				// .antMatchers("/users/**").hasRole("ADMIN") //管理员权限
				.antMatchers("/**/**").authenticated().and().formLogin().loginPage("/login") // 跳转登录页面的控制器，该地址要保证和表单提交的地址一致！
				// 登陆成功后的处理
				.successHandler(new AuthenticationSuccessHandler() {
					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {
						Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
						if (principal != null && principal instanceof UserDetails) {
							UserDetails user = (UserDetails) principal;
							System.out.println("loginUser:" + user.getUsername());
							// 维护在session中
							request.getSession().setAttribute("userDetail", user);
							response.sendRedirect("/");
						}
					}
				})
				// 登陆失败后的处理
				.failureHandler(new AuthenticationFailureHandler() {
					@Override
					public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
							AuthenticationException authenticationException) throws IOException, ServletException {
						System.out.println("error" + authenticationException.getMessage());
						response.sendRedirect("/login");
					}
				}).permitAll().and().logout().permitAll().and().csrf().disable();//// 暂时禁用CSRF，否则无法提交表单
		// http
		// .anonymous().disable()
		// .authorizeRequests()
		// .antMatchers("/oauth/token").permitAll();
		// http
		// .anonymous().disable()
		// .authorizeRequests()
		// .antMatchers("/users/v3/user").permitAll();
		// http
		// .anonymous().disable()
		// .authorizeRequests()
		// .antMatchers("/").permitAll();
	}
}
