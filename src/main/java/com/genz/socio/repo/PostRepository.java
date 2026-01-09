package com.genz.socio.repo;

import com.genz.socio.dto.entity.Post;
import com.genz.socio.dto.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;


public interface PostRepository extends JpaRepository<Post, Long> {
    // In PostRepository.java
    @Query("SELECT p FROM Post p WHERE p.author.profile IN :following ORDER BY p.createdAt DESC")
    Page<Post> findFeedByFollowing(@Param("following") Set<Profile> following, Pageable pageable);
}
