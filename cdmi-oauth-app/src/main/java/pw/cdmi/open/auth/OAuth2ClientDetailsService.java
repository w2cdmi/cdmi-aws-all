package pw.cdmi.open.auth;

import java.util.List;

import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;

public class OAuth2ClientDetailsService implements ClientDetailsService, ClientRegistrationService {

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeClientDetails(String clientId) throws NoSuchClientException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ClientDetails> listClientDetails() {
		// TODO Auto-generated method stub
		return null;
	}

}
