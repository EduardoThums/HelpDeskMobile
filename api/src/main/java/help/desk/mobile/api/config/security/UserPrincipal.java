package help.desk.mobile.api.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import help.desk.mobile.api.domain.entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserPrincipal implements UserDetails {

	private Long id;

	private String username;

	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(Long id, String name, String password, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = name;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserPrincipal create(UserEntity userEntity) {
		final List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(userEntity.getProfile().getRole()));

		return new UserPrincipal(
				userEntity.getId(),
				userEntity.getUsername(),
				userEntity.getPassword(),
				authorities
		);
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}