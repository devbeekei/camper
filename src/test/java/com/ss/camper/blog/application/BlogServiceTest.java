package com.ss.camper.blog.application;

import com.ss.camper.blog.application.dto.BlogDTO;
import com.ss.camper.blog.application.exception.AlreadyBlogRegisteredException;
import com.ss.camper.blog.domain.Blog;
import com.ss.camper.blog.domain.BlogRepository;
import com.ss.camper.user.clientUser.domain.ClientUser;
import com.ss.camper.user.clientUser.domain.ClientUserRepository;
import com.ss.camper.user.exception.NotFoundUserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static com.ss.camper.blog.BlogMock.initBlog;
import static com.ss.camper.blog.BlogMock.initBlogDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private BlogRepository blogRepository;

    @Mock
    private ClientUserRepository clientUserRepository;

    @InjectMocks
    private BlogService blogService;

    @Test
    void 블로그_개설() {
        final long userId = 1;
        final ClientUser clientUser = ClientUser.builder().id(userId).build();
        given(clientUserRepository.findById(anyLong())).willReturn(Optional.of(clientUser));
        final Blog blog = initBlog(userId, 1L);
        given(blogRepository.save(any(Blog.class))).willReturn(blog);

        final BlogDTO blogDTO = initBlogDTO(null);
        final BlogDTO result =  blogService.registerBlog(userId, blogDTO);

        assertThat(result.getId()).isEqualTo(blog.getId());
        assertThat(result.getTitle()).isEqualTo(blog.getTitle());
        assertThat(result.getIntroduction()).isEqualTo(blog.getIntroduction());
    }

    @Test
    void 존재하지_않는_회원이_블로그_개설() {
        final long userId = 1;
        given(clientUserRepository.findById(anyLong())).willReturn(Optional.empty());

        final BlogDTO blogDTO = initBlogDTO(null);
        assertThrows(NotFoundUserException.class, () -> blogService.registerBlog(userId, blogDTO));
    }

    @Test
    void 블로그를_이미_개설한_회원이_블로그_개설() {
        final long userId = 1;
        final ClientUser clientUser = ClientUser.builder().id(userId).blog(initBlog(userId, 1L)).build();
        given(clientUserRepository.findById(anyLong())).willReturn(Optional.of(clientUser));

        final BlogDTO blogDTO = initBlogDTO(null);
        assertThrows(AlreadyBlogRegisteredException.class, () -> blogService.registerBlog(userId, blogDTO));
    }

}