package likelion13th.blog.repository;

import likelion13th.blog.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    // JpaRepository 클래스 상속받음
    // 엔티티 Article과 기본키 타입을 인수로
}