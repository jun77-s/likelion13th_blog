package likelion13th.blog.repository;


import likelion13th.blog.domain.Article;
import likelion13th.blog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByArticle(Article article);
}