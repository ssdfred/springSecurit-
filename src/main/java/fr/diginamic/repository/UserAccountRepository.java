package fr.diginamic.repository;

import java.util.List;

import fr.diginamic.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  UserAccountRepository extends JpaRepository<UserAccount, Long> {



	UserAccount findByUsername(String username);
    public List<UserAccount> findAll();
}
