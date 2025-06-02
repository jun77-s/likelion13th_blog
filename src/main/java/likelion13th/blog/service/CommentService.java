package likelion13th.blog.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import likelion13th.blog.domain.Article;
import likelion13th.blog.domain.Comment;
import likelion13th.blog.dto.request.AddCommentRequest;
import likelion13th.blog.dto.response.CommentResponse;
import likelion13th.blog.dto.request.DeleteRequest;
import likelion13th.blog.exception.CommentNotFoundException;
import likelion13th.blog.exception.PermissionDeniedException;
import likelion13th.blog.repository.ArticleRepository;
import likelion13th.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;


    /*댓글 작성*/
    @Transactional
    public CommentResponse addComment(long articleId, AddCommentRequest request) {

        /* 1. 요청이 들어온 게시글 ID로 데이터베이스에서 게시글 찾기. 해당하는 게시글이 없으면 에러*/
        Article article=articleRepository.findById(articleId)
                .orElseThrow(()->new EntityNotFoundException("해당 ID의 게시글을 찾을 수 없습니다."));

        /* 2. 위 게시글에 대한 댓글 생성하여 저장 */
        Comment comment=request.toEntity(article);
        comment=commentRepository.save(comment);

        /* 3. 게시글의 commentCount 필드 +1 */
        article.increaseCommentCount();

        /* 4. 방금 생성한 댓글을 DTO로 변환하여 반환 */
        return CommentResponse.of(comment);
    }
    //댓글 삭제
    @Transactional
    public void deleteComment(long commentId, DeleteRequest request) {

        Comment comment=commentRepository.findById(commentId)
                .orElseThrow(()->new CommentNotFoundException("해당 ID의 댓글을 찾을 수 없습니다."));

        if(!request.getPassword().equals(comment.getPassword())){
            throw new PermissionDeniedException("해당 글에 대한 삭제 권한이 없습니다.");
        }

        commentRepository.deleteById(commentId);
        comment.getArticle().decreaseCommentCount();
    }

}
