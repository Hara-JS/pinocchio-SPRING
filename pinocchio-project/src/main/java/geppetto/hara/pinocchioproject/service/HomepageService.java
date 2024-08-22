package geppetto.hara.pinocchioproject.service;

import geppetto.hara.pinocchioproject.domain.Article;
import geppetto.hara.pinocchioproject.dto.AddArticleRequest;
import geppetto.hara.pinocchioproject.dto.UpdateArticleRequest;
import geppetto.hara.pinocchioproject.repository.HomepageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HomepageService {

    private final HomepageRepository homepageRepository;

    public Article save(AddArticleRequest request, String userName) {
        return homepageRepository.save(request.toEntity(userName));
    }

    public List<Article> findAll() {
        return homepageRepository.findAll();
    }

    public Article findById(long id) {
        return homepageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    public void delete(long id) {
        Article article = homepageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        homepageRepository.delete(article);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = homepageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    // 게시글을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

}