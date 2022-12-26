package com.example.support.api;

import com.example.support.dto.ArticleForm;
import com.example.support.entity.Article;
import com.example.support.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleApiController {
    @Autowired
        private ArticleRepository articleRepository;

    @GetMapping("/api/articles")
        public List<Article> index() {
            return articleRepository.findAll();
    }
    @GetMapping("/api/articles/{id}")
        public Article index(@PathVariable Long id) {
            return articleRepository.findById(id).orElse(null);
        }
   @PostMapping("/api/articles")
        public Article create(ArticleForm dto) {
            Article article = dto.toEntity();
            return articleRepository.save(article);
   }
}
