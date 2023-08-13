package com.example.support.service;

import com.example.support.dto.CommentDto;
import com.example.support.entity.Article;
import com.example.support.entity.Comment;
import com.example.support.repository.ArticleRepository;
import com.example.support.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;


    public List<CommentDto> comments(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);

        List<CommentDto> dtos = new ArrayList<CommentDto>();
        for(int i = 0; i < comments.size(); i++){
            Comment c = comments.get(i);
            CommentDto dto = CommentDto.createCommentDto(c);
            dtos.add(dto);
        }

        return dtos;
    }

    public CommentDto create(Long articleId, CommentDto dto) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(()-> new IllegalArgumentException("댓글실패 대상 게시글이 없습니다."));

        Comment comment = Comment.createComent(dto, article);

        Comment created = commentRepository.save(comment);
        return CommentDto.createCommentDto(created);
    }
    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 업습니다."));

        target.patch(dto);

        Comment updated = commentRepository.save(target);

        return CommentDto.createCommentDto(updated);
    }
    @Transactional
    public CommentDto delete(Long id) {
        Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 업습니다."));
        commentRepository.delete(target);

        return CommentDto.createCommentDto(target);
    }
}
