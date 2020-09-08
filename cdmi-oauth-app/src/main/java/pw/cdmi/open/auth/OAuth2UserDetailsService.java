package pw.cdmi.open.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import pw.cdmi.paas.account.model.entities.UserAccount;
import pw.cdmi.paas.account.service.UserService;

@Component
public class OAuth2UserDetailsService implements UserDetailsService {

	@Autowired
    private UserService service;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount user = service.getUserAccountByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return null;
	}

}
