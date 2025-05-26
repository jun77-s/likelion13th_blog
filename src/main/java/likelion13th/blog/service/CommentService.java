package likelion13th.blog.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import likelion13th.blog.domain.Article;
import likelion13th.blog.domain.Comment;
import likelion13th.blog.dto.AddCommentRequest;
import likelion13th.blog.dto.CommentResponse;
import likelion13th.blog.dto.DeleteRequest;
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
    /*댓글 삭제*/
    public void deleteComment(long commentId, DeleteRequest request) {
        /* 1. 요청이 들어온 게시글 ID로 데이터베이스에서 댓글 찾기. 해당하는 댓글이 없으면 에러 */
        Comment comment=commentRepository.findById(commentId)
                .orElseThrow(()->new EntityNotFoundException("해당 ID의 게시글을 찾을 수 없습니다."));

        /* 2. 비밀번호 일치하는지 확인 : 요청을 보낸 사람이 이 댓글의 삭제 권한을 가지고 있는지
            request.getPassword() : 게시글 수정 요청을 보낸 사람이 입력한 비밀번호
            article.getPassword() : 데이터베이스에 저장된 비밀번호 (작성자가 댓글 쓸때 등록한)
         */
        if(!request.getPassword().equals(comment.getPassword())){
            throw new RuntimeException("해당 댓글에 대한 삭제 권한이 없습니다.");
        }
        /* 3. 댓글 삭제 */
        commentRepository.deleteById(commentId);
        /* 4. 게시글의 commentCount -1 */
        comment.getArticle().decreaseCommentCount();
    }

}
