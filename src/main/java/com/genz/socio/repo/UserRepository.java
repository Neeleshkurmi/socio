package com.genz.socio.repo;

import com.genz.socio.dto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.emailOrPhone = ?1 OR u.userName = ?1 AND u.password = ?2")
    User getUserByEmailOrUserNameAndPassword(String emailOrUserName, String password);
}