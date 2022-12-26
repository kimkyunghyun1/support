package com.example.support.api;

import com.example.support.dto.CommentDto;
import com.example.support.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {
        // 서비스에게 위임
        List<CommentDto> dtos = commentService.comments(articleId);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto) {
        CommentDto createdDto = commentService.create(articleId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto) {
        CommentDto updatedDto = commentService.update(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id) {
        CommentDto updatedDto = commentService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

}
