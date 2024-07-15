package fr.diginamic.entities;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @GeneratedValue
    private long id;

    private String username;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<GrantedAuthority> authorities;

    public UserAccount() {
    	
    }
 // Constructeur pour créer un utilisateur avec un rôle
    public UserAccount(String username, String password, String... authorities) {
//        GrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_" + role);
//        authorities = new ArrayList<>();
//        authorities.add(roleAuthority);
        this.username = username;
        this.password = password;
        this.authorities =Arrays.stream(authorities).map(SimpleGrantedAuthority::new).map(GrantedAuthority.class::cast).toList();
    }
    
//    // Constructeur pour créer un utilisateur avec plusieurs rôles
//    public UserAccount(String username, String password, List<String> roles) {
//        authorities = new ArrayList<>();
//        for (String role : roles) {
//            GrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_" + role);
//            authorities.add(roleAuthority);
//        }
//        this.username = username;
//        this.password = password;
//    }

	// Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}


}
