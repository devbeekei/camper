package com.ss.camper.blog.domain;

import java.util.Optional;

public interface BlogRepository {
    Blog save(Blog build);
    Optional<Blog> findById(long blogId);
}
