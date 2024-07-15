package fr.diginamic.mappers;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import fr.diginamic.entities.UserAccount;

public class UserMapper {

	public static UserDetails toUserDetails(UserAccount userAccount) {
		return User.builder().username(userAccount.getUsername()).password(userAccount.getPassword()).authorities(userAccount.getAuthorities()).build();
	}
}
