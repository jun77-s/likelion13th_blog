package likelion13th.blog.service;

import likelion13th.blog.domain.Article;
import likelion13th.blog.dto.request.AddArticleRequest;
import likelion13th.blog.dto.response.ArticleResponse;
import likelion13th.blog.repository.ArticleRepository;
import likelion13th.blog.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CommentRepository commentRepository;



    @DisplayName("addArticle() : 게시글 생성 성공")
    @Test
    void addArticle() {
        //given
        AddArticleRequest addArticleRequest = new AddArticleRequest(
                "제목입니다",
                "내용입니다",
                "글쓴이입니다",
                "비번입니다1234"
        );

        Article article = Article.builder()
                .id(1L)
                .title("제목입니다")
                .content("내용입니다")
                .author("글쓴이입니다")
                .password("비번입니다1234")
                .createdAt(LocalDateTime.now())
                .commentCount(0)
                .build();

        given(articleRepository.save(any(Article.class))).willReturn(article);

        //when
        ArticleResponse response=articleService.addArticle(addArticleRequest);

        //then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("제목입니다");
        assertThat(response.getContent()).isEqualTo("내용내용");


    }

}