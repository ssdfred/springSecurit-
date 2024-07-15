package fr.diginamic.mappers;

import java.util.stream.Collectors;

import org.springframework.security.authorization.AuthorityAuthorizationDecision;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import fr.diginamic.dto.UserAccountDto;
import fr.diginamic.entities.UserAccount;

public class UserAccountMapper {

	public static UserAccountDto toDto(UserAccount userAccount) {
		UserAccountDto dto = new UserAccountDto();
		dto.setUsername(userAccount.getUsername());
		dto.setPassword(userAccount.getPassword());
		dto.setAuthorites(userAccount.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toList()));
		return dto;
	}
	public static UserAccount toEntity(UserAccountDto dto) {
		return new UserAccount(dto.getUsername(),dto.getPassword(),dto.getAuthorites().stream().map(SimpleGrantedAuthority::new).toArray(String[]::new));
	}
	public static UserDetails toUserDetails(UserAccount userAccount) {
		return User.builder().username(userAccount.getUsername()).password(userAccount.getPassword()).authorities(userAccount.getAuthorities()).build();
	}
}
