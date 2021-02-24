package com.amaistra.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.amaistra.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // @Query("SELECT Post FROM Post LIMIT 5 OFFSET 5")
    @Transactional
    @Query(value = "SELECT * FROM post ORDER BY id DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Post> findByLimitAndOffset(@Param("limit") Integer limit ,@Param("offset") Integer offset);

}
