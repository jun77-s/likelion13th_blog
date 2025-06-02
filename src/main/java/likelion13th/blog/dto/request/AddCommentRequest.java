package likelion13th.blog.dto.request;

import likelion13th.blog.domain.Article;
import likelion13th.blog.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AddCommentRequest {
    private String content;
    private String author;
    private String password;

    public Comment toEntity(Article article){
        return Comment.builder()
                .content(content)
                .author(author)
                .password(password)
                .article(article)
                .createdAt(LocalDateTime.now())
                .build();
    }
}