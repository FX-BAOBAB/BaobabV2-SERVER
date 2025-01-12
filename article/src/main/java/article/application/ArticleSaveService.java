package article.application;

import article.adapter.output.persistence.enums.ArticleStatus;
import article.application.port.input.SaveArticleUseCase;
import article.application.port.output.ArticlePersistencePort;
import article.domain.command.ArticleSaveCommand;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleSaveService implements SaveArticleUseCase {

    private final ArticlePersistencePort articlePersistencePort;

    @Override
    public boolean saveArticle(ArticleSaveCommand articleSaveCommand) {

        articleSaveCommand.setStatus(ArticleStatus.ON_SALE);
        articleSaveCommand.setRegisteredAt(LocalDateTime.now());

        return articlePersistencePort.saveArticle(articleSaveCommand);
    }
}
