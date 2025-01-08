package article.adapter.input.web;

import article.adapter.input.web.request.ArticleSaveRequest;
import article.application.port.input.SaveArticleUseCase;
import article.core.common.converter.ArticleConverter;
import article.domain.command.ArticleSaveCommand;
import global.api.Api;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleApiController {

    private final ArticleConverter articleConverter;

    private final SaveArticleUseCase saveArticleUseCase;

    @PostMapping()
    public Api<Boolean> save(@Valid ArticleSaveRequest articleSaveRequest) {
        ArticleSaveCommand articleSaveCommand = articleConverter.toSaveCommand(articleSaveRequest);
        boolean isSaved = saveArticleUseCase.saveArticle(articleSaveCommand);
        return Api.OK(isSaved);
    }

}


