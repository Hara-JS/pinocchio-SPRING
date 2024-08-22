package geppetto.hara.pinocchioproject.repository;

import geppetto.hara.pinocchioproject.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomepageRepository extends JpaRepository<Article, Long> {
}
