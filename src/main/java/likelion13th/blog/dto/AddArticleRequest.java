package likelion13th.blog.dto;

import likelion13th.blog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddArticleRequest {
    private String title;
    private String content;
    private String author;
    private String password;

    public Article toEntity(){
        return Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .password(password)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
