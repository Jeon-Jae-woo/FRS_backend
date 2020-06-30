package com.recommendation.FRS.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query(value="SELECT p.id, p.title, p.email, p.created_at FROM Post as p WHERE p.title like %?1%",
            countQuery = "SELECT count(*) FROM Post as p WHERE p.title like %?1%", nativeQuery = true)
    Page<PostMapping> findByTitleContaining(String title, Pageable pageable);

    @Query(value="SELECT p.id, p.title, p.email, p.created_at FROM Post as p",
            countQuery = "SELECT count(*) FROM Post as p", nativeQuery = true)
    Page<PostMapping> findAllBy(Pageable pageable);
}
