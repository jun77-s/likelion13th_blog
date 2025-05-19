package likelion13th.blog.service;

import likelion13th.blog.domain.Article;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final List<Article> articleDB = new ArrayList<>();
    private Long nextId = 1L;

    public Article addArticle(Article article){

        if(article.getAuthor() == null
                || article.getContent() == null
                || article.getTitle() == null
                || article.getPassword() == null){
            throw new IllegalArgumentException("제목, 내용, 작성자, 비밀번호는 필수 입력 항목입니다.");
        }

        Article newArticle = new Article(
                nextId++,
                article.getContent(),
                article.getTitle(),
                article.getAuthor(),
                article.getPassword()
        );

        articleDB.add(newArticle);

        return newArticle;
    }

    public List<Article> findAll(){

        return articleDB;
    }

}
