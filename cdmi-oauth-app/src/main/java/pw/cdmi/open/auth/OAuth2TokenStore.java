//package pw.cdmi.open.auth;
//
//import java.io.UnsupportedEncodingException;
//import java.math.BigInteger;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Types;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.support.SqlLobValue;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.OAuth2RefreshToken;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
//import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore.SafeAccessTokenRowMapper;
//
//public class OAuth2TokenStore implements TokenStore {
//
//	private static final Logger logger = LoggerFactory.getLogger(OAuth2TokenStore.class);
//	
//	private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();
//	
//	public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
//		this.authenticationKeyGenerator = authenticationKeyGenerator;
//	}
//	
//	@Override
//	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
//		return readAuthentication(token.getValue());
//	}
//
//	@Override
//	public OAuth2Authentication readAuthentication(String token) {
//		OAuth2Authentication authentication = null;
//
//		try {
//			authentication = jdbcTemplate.queryForObject(selectAccessTokenAuthenticationSql,
//					new RowMapper<OAuth2Authentication>() {
//						public OAuth2Authentication mapRow(ResultSet rs, int rowNum) throws SQLException {
//							return deserializeAuthentication(rs.getBytes(2));
//						}
//					}, extractTokenKey(token));
//		}
//		catch (EmptyResultDataAccessException e) {
//			if (logger.isInfoEnabled()) {
//				logger.info("Failed to find access token for token " + token);
//			}
//		}
//		catch (IllegalArgumentException e) {
//			logger.warn("Failed to deserialize authentication for " + token, e);
//			removeAccessToken(token);
//		}
//
//		return authentication;
//	}
//
//	@Override
//	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
//		String refreshToken = null;
//		if (token.getRefreshToken() != null) {
//			refreshToken = token.getRefreshToken().getValue();
//		}
//		
//		if (readAccessToken(token.getValue())!=null) {
//			removeAccessToken(token.getValue());
//		}
//
//		jdbcTemplate.update(insertAccessTokenSql, new Object[] { extractTokenKey(token.getValue()),
//				new SqlLobValue(serializeAccessToken(token)), authenticationKeyGenerator.extractKey(authentication),
//				authentication.isClientOnly() ? null : authentication.getName(),
//				authentication.getOAuth2Request().getClientId(),
//				new SqlLobValue(serializeAuthentication(authentication)), extractTokenKey(refreshToken) }, new int[] {
//				Types.VARCHAR, Types.BLOB, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BLOB, Types.VARCHAR });
//	}
//
//	@Override
//	public OAuth2AccessToken readAccessToken(String tokenValue) {
//		OAuth2AccessToken accessToken = null;
//
//		try {
//			accessToken = jdbcTemplate.queryForObject(selectAccessTokenSql, new RowMapper<OAuth2AccessToken>() {
//				public OAuth2AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
//					return deserializeAccessToken(rs.getBytes(2));
//				}
//			}, extractTokenKey(tokenValue));
//		}
//		catch (EmptyResultDataAccessException e) {
//			if (logger.isInfoEnabled()) {
//				logger.info("Failed to find access token for token " + tokenValue);
//			}
//		}
//		catch (IllegalArgumentException e) {
//			logger.warn("Failed to deserialize access token for " + tokenValue, e);
//			removeAccessToken(tokenValue);
//		}
//
//		return accessToken;
//	}
//
//	@Override
//	public void removeAccessToken(OAuth2AccessToken token) {
//		removeAccessToken(token.getValue());
//		
//	}
//
//	public void removeAccessToken(String tokenValue) {
//		jdbcTemplate.update(deleteAccessTokenSql, extractTokenKey(tokenValue));
//	}
//	
//	@Override
//	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
//		OAuth2RefreshToken refreshToken = null;
//
//		try {
//			refreshToken = jdbcTemplate.queryForObject(selectRefreshTokenSql, new RowMapper<OAuth2RefreshToken>() {
//				public OAuth2RefreshToken mapRow(ResultSet rs, int rowNum) throws SQLException {
//					return deserializeRefreshToken(rs.getBytes(2));
//				}
//			}, extractTokenKey(token));
//		}
//		catch (EmptyResultDataAccessException e) {
//			if (logger.isInfoEnabled()) {
//				logger.info("Failed to find refresh token for token " + token);
//			}
//		}
//		catch (IllegalArgumentException e) {
//			logger.warn("Failed to deserialize refresh token for token " + token, e);
//			removeRefreshToken(token);
//		}
//
//		return refreshToken;
//	}
//
//	@Override
//	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
//		return readAuthenticationForRefreshToken(token.getValue());
//	}
//
//	@Override
//	public void removeRefreshToken(OAuth2RefreshToken token) {
//		removeRefreshToken(token.getValue());
//		
//	}
//
//	private void removeRefreshToken(String token) {
//		jdbcTemplate.update(deleteRefreshTokenSql, extractTokenKey(token));
//	}
//	
//	@Override
//	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
//		removeAccessTokenUsingRefreshToken(refreshToken.getValue());
//		
//	}
//
//	@Override
//	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
//		OAuth2AccessToken accessToken = null;
//
//		String key = authenticationKeyGenerator.extractKey(authentication);
//		try {
//			accessToken = jdbcTemplate.queryForObject(selectAccessTokenFromAuthenticationSql,
//					new RowMapper<OAuth2AccessToken>() {
//						public OAuth2AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
//							return deserializeAccessToken(rs.getBytes(2));
//						}
//					}, key);
//		}
//		catch (EmptyResultDataAccessException e) {
//			if (logger.isDebugEnabled()) {
//				logger.debug("Failed to find access token for authentication " + authentication);
//			}
//		}
//		catch (IllegalArgumentException e) {
//			logger.error("Could not extract access token for authentication " + authentication, e);
//		}
//
//		if (accessToken != null
//				&& !key.equals(authenticationKeyGenerator.extractKey(readAuthentication(accessToken.getValue())))) {
//			removeAccessToken(accessToken.getValue());
//			// Keep the store consistent (maybe the same user is represented by this authentication but the details have
//			// changed)
//			storeAccessToken(accessToken, authentication);
//		}
//		return accessToken;
//	}
//
//	@Override
//	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
//		List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();
//
//		try {
//			accessTokens = jdbcTemplate.query(selectAccessTokensFromUserNameAndClientIdSql, new SafeAccessTokenRowMapper(),
//					userName, clientId);
//		}
//		catch (EmptyResultDataAccessException e) {
//			if (logger.isInfoEnabled()) {
//				logger.info("Failed to find access token for clientId " + clientId + " and userName " + userName);
//			}
//		}
//		accessTokens = removeNulls(accessTokens);
//
//		return accessTokens;
//	}
//
//	@Override
//	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
//		List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();
//
//		try {
//			accessTokens = jdbcTemplate.query(selectAccessTokensFromClientIdSql, new SafeAccessTokenRowMapper(),
//					clientId);
//		}
//		catch (EmptyResultDataAccessException e) {
//			if (logger.isInfoEnabled()) {
//				logger.info("Failed to find access token for clientId " + clientId);
//			}
//		}
//		accessTokens = removeNulls(accessTokens);
//
//		return accessTokens;
//	}
//
//	private void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
//		String refreshToken = null;
//		if (token.getRefreshToken() != null) {
//			refreshToken = token.getRefreshToken().getValue();
//		}
//		
//		if (readAccessToken(token.getValue())!=null) {
//			removeAccessToken(token.getValue());
//		}
//
//		jdbcTemplate.update(insertAccessTokenSql, new Object[] { extractTokenKey(token.getValue()),
//				new SqlLobValue(serializeAccessToken(token)), authenticationKeyGenerator.extractKey(authentication),
//				authentication.isClientOnly() ? null : authentication.getName(),
//				authentication.getOAuth2Request().getClientId(),
//				new SqlLobValue(serializeAuthentication(authentication)), extractTokenKey(refreshToken) }, new int[] {
//				Types.VARCHAR, Types.BLOB, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BLOB, Types.VARCHAR });
//	}
//	
//	protected String extractTokenKey(String value) {
//		if (value == null) {
//			return null;
//		}
//		MessageDigest digest;
//		try {
//			digest = MessageDigest.getInstance("MD5");
//		}
//		catch (NoSuchAlgorithmException e) {
//			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
//		}
//
//		try {
//			byte[] bytes = digest.digest(value.getBytes("UTF-8"));
//			return String.format("%032x", new BigInteger(1, bytes));
//		}
//		catch (UnsupportedEncodingException e) {
//			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
//		}
//	}
//	
//	private List<OAuth2AccessToken> removeNulls(List<OAuth2AccessToken> accessTokens) {
//		List<OAuth2AccessToken> tokens = new ArrayList<OAuth2AccessToken>();
//		for (OAuth2AccessToken token : accessTokens) {
//			if (token != null) {
//				tokens.add(token);
//			}
//		}
//		return tokens;
//	}
//}
