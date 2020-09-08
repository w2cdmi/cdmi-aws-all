package pw.cdmi.open.security;

import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import pw.cdmi.core.db.GenericDao;
import pw.cdmi.open.service.UserService;

/**
 * 认证成功后，转到该类进行额外事项的处理，比如记录用户的登录时间、来源IP地址
 * 该选项可能导致配置文件中http default-target-url失效
 * 不同的业务所要做的事情不同，在新的应用可以继承该类，但需要在onAuthenticationSuccess
 * 方法中需要调用super.onAuthenticationSuccess()以实现对用户访问信息的记录。
 * 另外，该方法中必须进行页面的跳转。
 * @author wuwei
 *
 */
public class SimpleLoginSuccessHandler implements AuthenticationSuccessHandler {

	private static Logger logger = LoggerFactory
			.getLogger(SimpleLoginSuccessHandler.class);
	
	/**
	 * 以下两个变量含义见配置文件
	 */
	private String defaultTargetUrl;
	private boolean forwardToDestination = false;
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	private GenericDao daoImpl;

	@Inject
	private UserService userAccountService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		logger.info("认证：认证成功，保存用户的访问信息");
		//1.登录成功后，记录用户的访问时间，访问IP信息
		//2.用户登录成功后，根据账号状态跳转到特定的页面，比如状态为密码需修改，则跳转到重新修改密码页面
//		String as = listToString(authentication.getAuthorities());
//		
//		HttpSession session = request.getSession();
//		if (StringUtils.isNotEmpty(as)) {
//			// 获取用户的菜单
//			UserMenu um = new UserMenu();// 菜单
//			String username = authentication.getName();
//			String ql = "select r.enumValue from SiteRole r,SiteMenu m,UserAccount a,SiteMenuAcl acl where a.userName='"
//					+ username
//					+ "' and  r.id=acl.roleId and a.id=up.userId and r.id=rp.roleId and p.id=rp.privilegeId order by p.menuSeq ";
//			List<Object[]> objList = jpaImpl.find(ql);
//			for (Object[] objects : objList) {
//				String pid = objects[3] + "";
//				String pty = objects[5] + "";
//				if (!"0".equals(pid)) {
//					if ("1".equals(pty)) {
//						um.createSblist(objects[0] + "", objects[1] + "",
//								objects[2] + "", pid);
//					}
//				} else {
//					um.setTplist(objects[0] + "", objects[1] + "", objects[4]
//							+ "", objects[6] + "");
//				}
//			}
//			um.setSblist(um.getSblist());
//			session.setAttribute("usermenu", um);
//			// 用户权限下所有的 操作url
//			List<String> urllist = new ArrayList<String>();
//			String ql2 = "select p.url,r.EnglishName from Role r,RolePrivilege rp,Privilege p  where r.EnglishName  in ( "
//					+ as
//					+ " ) and p.priType='2' and r.id=rp.roleId and p.id=rp.privilegeId order by p.menuSeq";
//			List<Object[]> objList2 = jpaImpl.find(ql2);
//			for (Object[] objects : objList2) {
//				urllist.add("" + objects[0]);
//			}
//			session.setAttribute("AuthsOfUrl", urllist);
//			UserAccount userAccount = userAccountService
//					.getUserAccountByUserName(username);
//			UserPagerEdit user = new UserPagerEdit();
//			if (userAccount != null) {
//				user = userAccountService.getUserAccount(String
//						.valueOf(userAccount.getId()));
//			}
//			String name = "";
//			if (user != null && user.getName() != null) {
//				name = user.getName();
//			} else {
//				name = username;
//			}
//			HorizontalMsg hm = new HorizontalMsg(username, new Date(),
//					as.replace("'", ""), name);
//			session.setAttribute("HorizontalMsg", hm);
//			session.removeAttribute("permissiondenied");
//		} else {
//			// this.defaultTargetUrl="/index/permissiondenied";
//			session.setAttribute("permissiondenied", "该用户没有被授权，请联系管理员授权！");
//		}
//		session.removeAttribute("errorMsg");
//		redirectStrategy.sendRedirect(request, response, this.defaultTargetUrl);
		/*
		 * response.sendRedirect(request.getContextPath()+this.defaultTargetUrl);
		 */
		if(this.forwardToDestination){
            request.getRequestDispatcher(this.defaultTargetUrl).forward(request, response);  
        }else{ 
            this.redirectStrategy.sendRedirect(request, response, this.defaultTargetUrl);  
        }
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	public void setForwardToDestination(boolean forwardToDestination) {  
        this.forwardToDestination = forwardToDestination;  
    } 
	
	private String listToString(Collection<? extends GrantedAuthority> ga) {
		StringBuilder sb = new StringBuilder();
		if (ga.size() > 0) {
			sb.append("'");
			for (GrantedAuthority grantedAuthority : ga) {
				sb.append(grantedAuthority.getAuthority() + "','");
			}
			String ret = sb.toString();
			int lt = ret.lastIndexOf(",");
			if (lt > 0) {
				ret = ret.substring(0, lt);
			}
			return ret;
		}
		return "";
	}

}