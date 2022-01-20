package com.ss.camper.blog.infra;

import com.ss.camper.blog.domain.Blog;
import com.ss.camper.blog.domain.BlogRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogJpaRepository extends JpaRepository<Blog, Long>, BlogRepository {

}
