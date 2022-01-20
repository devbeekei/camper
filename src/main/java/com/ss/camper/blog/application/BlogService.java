package com.ss.camper.blog.application;

import com.ss.camper.blog.application.dto.BlogDTO;
import com.ss.camper.blog.application.dto.BlogInfoDTO;
import com.ss.camper.blog.application.exception.AlreadyBlogRegisteredException;
import com.ss.camper.blog.application.exception.NotFoundBlogException;
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
    public BlogDTO registerBlog(final long userId, final BlogDTO blogDTO) {
        ClientUser clientUser = clientUserRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        if (clientUser.getBlog() != null) throw new AlreadyBlogRegisteredException();

        Blog blog = blogRepository.save(Blog.builder()
                .user(clientUser)
                .title(blogDTO.getTitle())
                .introduction(blogDTO.getIntroduction())
                .build());

        return modelMapper.map(blog, BlogDTO.class);
    }

    @Transactional
    public BlogDTO modifyBlog(final long userId, final BlogDTO blogDTO) {
        ClientUser clientUser = clientUserRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        Blog blog = clientUser.getBlog();
        if (blog == null) throw new NotFoundBlogException();

        blog.updateInfo(blogDTO.getTitle(), blogDTO.getIntroduction());

        return modelMapper.map(blog, BlogDTO.class);
    }

    @Transactional
    public BlogInfoDTO getBlogInfo(final long blogId) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(NotFoundBlogException::new);
        return modelMapper.map(blog, BlogInfoDTO.class);
    }

}
