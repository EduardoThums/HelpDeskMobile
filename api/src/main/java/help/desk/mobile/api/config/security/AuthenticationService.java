package help.desk.mobile.api.config.security;

import help.desk.mobile.api.controller.authentication.request.AuthenticationRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

	private AuthenticationManager authenticationManager;

	private JwtTokenProvider jwtTokenProvider;

	public AuthenticationService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public String authenticate(AuthenticationRequest request) {
		final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return jwtTokenProvider.generateToken(authentication);
	}
}
