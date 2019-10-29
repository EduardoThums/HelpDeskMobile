package help.desk.mobile.api.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

import static java.lang.Long.parseLong;
import static java.util.Optional.empty;

@Component
public class JwtTokenProvider {

	private String jwtSecret;

	private int jwtExpiration;

	public JwtTokenProvider(@Value("${security.jwt.secret}") String jwtSecret,
	                        @Value("${security.jwt.expiration}") int jwtExpiration) {
		this.jwtSecret = jwtSecret;
		this.jwtExpiration = jwtExpiration;
	}

	public String generateToken(Authentication authentication) {
		final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		final Date now = new Date();
		final Date expiryDate = new Date(new Date().getTime() + jwtExpiration);

		return Jwts.builder()
				.setSubject(Long.toString(userPrincipal.getId()))
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public Optional<Long> getUserId(String jwt) {
		try {
			Claims claims = parse(jwt).getBody();

			return Optional.of(parseLong(claims.getSubject()));
		} catch (Exception ex) {
			return empty();
		}
	}

	private Jws<Claims> parse(String jwt) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
	}
}
