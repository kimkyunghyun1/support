package com.example.support.controller;

import com.example.support.dto.ArticleForm;
import com.example.support.dto.CommentDto;
import com.example.support.entity.Article;
import com.example.support.repository.ArticleRepository;
import com.example.support.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String ArticleForm() {

        return "articles/new";
    }
    @PostMapping("/articles/create")
    public String crateArticle(ArticleForm form) {

        Article article = form.toEntity();
        log.info(article.toString());

        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        return "redirect:/articles/" + saved.getId();
    }
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentsDtos = commentService.comments(id);

        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentsDtos);

        return "articles/show";
    }
    @GetMapping("/articles")
    public String index(Model model) {
        List<Article> articleEntityList = articleRepository.findAll();  // ArrayList 상위타입이라 List로 써도됨

        model.addAttribute("articleList", articleEntityList);

        return "articles/index";
    }
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Article articleEntity = articleRepository.findById(id).orElse(null);

        model.addAttribute("article", articleEntity);

        return "articles/edit";
    }
    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        Article articleEntity = form.toEntity();

        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        if (target != null) {
            articleRepository.save(articleEntity);
        }

        return "redirect:/articles/" + articleEntity.getId();
    }
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        Article target = articleRepository.findById(id).orElse(null);

        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }

        return "redirect:/articles";
    }
}
