package com.ss.camper.blog.application;

import com.ss.camper.blog.application.dto.BlogDTO;
import com.ss.camper.blog.application.exception.AlreadyBlogRegisteredException;
import com.ss.camper.blog.domain.Blog;
import com.ss.camper.blog.domain.BlogRepository;
import com.ss.camper.user.clientUser.domain.ClientUser;
import com.ss.camper.user.clientUser.domain.ClientUserRepository;
import com.ss.camper.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class BlogService {

    private final ModelMapper modelMapper;
    private final ClientUserRepository clientUserRepository;
    private final BlogRepository blogRepository;

    @Transactional
    public BlogDTO registerBlog(long userId, BlogDTO blogDTO) {
        ClientUser clientUser = clientUserRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        if (clientUser.getBlog() != null) throw new AlreadyBlogRegisteredException();

        Blog blog = blogRepository.save(Blog.builder()
                .user(clientUser)
                .title(blogDTO.getTitle())
                .introduction(blogDTO.getIntroduction())
                .build());

        return modelMapper.map(blog, BlogDTO.class);
    }

}
