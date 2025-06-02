package likelion13th.blog.dto.response;

import likelion13th.blog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SimpleArticleResponse {

    private final Long id;
    private final String title;
    private final String author;

    public static SimpleArticleResponse of(Article article) {
        return SimpleArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .author(article.getAuthor())
                .build();
    }
}
