package com.genz.socio.repo;

import com.genz.socio.dto.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.emailOrPhone = ?1 OR u.userName = ?1")
    Optional<User> findUserByEmailOrUserName(String emailOrUserName);

    @Query("SELECT u FROM User u WHERE u.userName LIKE %:userName%")
    Page<User> findByUserNameContaining(@Param("userName") String userName, Pageable pageable);

    Optional<User> findByUserName(String username);
}