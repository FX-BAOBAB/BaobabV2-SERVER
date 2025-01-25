package article.adapter.input.web;

import article.adapter.input.web.request.ArticleSaveRequest;
import article.adapter.input.web.request.ArticleSearchCondition;
import article.adapter.input.web.request.ArticleUpdateRequest;
import article.adapter.input.web.response.ArticleListResponse;
import article.adapter.output.persistence.repository.Article;
import article.application.port.input.DeleteArticleUseCase;
import article.application.port.input.GetArticleUseCase;
import article.application.port.input.SaveArticleUseCase;
import article.application.port.input.UpdateArticleUseCase;
import article.core.common.converter.ArticleConverter;
import article.domain.command.ArticleSaveCommand;
import article.domain.command.ArticleSearchCommand;
import article.domain.command.ArticleUpdateCommand;
import global.annotation.AuthenticatedUser;
import global.api.Api;
import global.resolver.AuthUser;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ArticleApiController {

    private final ArticleConverter articleConverter;

    private final SaveArticleUseCase saveArticleUseCase;

    private final GetArticleUseCase getArticleUseCase;

    private final UpdateArticleUseCase updateArticleUseCase;

    private final DeleteArticleUseCase deleteArticleUseCase;

    @PostMapping("/save")
    public Api<Boolean> save(@Valid ArticleSaveRequest articleSaveRequest,
        @AuthenticatedUser AuthUser authUser) {

        ArticleSaveCommand articleSaveCommand = articleConverter.toSaveCommand(
            articleSaveRequest, authUser.getUserId());

        return Api.OK(saveArticleUseCase.saveArticle(articleSaveCommand));
    }

    @GetMapping("/my-articles")
    public Api<ArticleListResponse> getMyArticles(@AuthenticatedUser AuthUser authUser,
        Pageable pageable) {

        List<Article> articleList = getArticleUseCase.getMyArticles(
            authUser.getUserId(), pageable);

        return Api.OK(articleConverter.toResponse(articleList));
    }

    // TODO Article List Algorithm 적용 필요
    @GetMapping("/list")
    public Api<List<Article>> getAllArticles(@ModelAttribute ArticleSearchCondition condition) {

        ArticleSearchCommand articleSearchCommand = articleConverter.toSearchCommand(condition);

        return Api.OK(getArticleUseCase.getArticleList(articleSearchCommand));
    }

    @GetMapping("/article/{articleId}")
    public Api<Article> getArticleById(@PathVariable String articleId) {
        return Api.OK(getArticleUseCase.getArticleBy(articleId));
    }

    @PutMapping("/article")
    public Api<Boolean> updateArticle(
        @Valid @ModelAttribute ArticleUpdateRequest articleUpdateRequest,
        @AuthenticatedUser AuthUser authUser) {
        ArticleUpdateCommand articleUpdateCommand = articleConverter.toUpdateCommand(
            articleUpdateRequest, authUser.getUserId());
        return Api.OK(updateArticleUseCase.updateArticle(articleUpdateCommand));
    }

    @DeleteMapping("/article/{articleId}")
    public Api<Boolean> deleteArticle(@PathVariable String articleId,
        @AuthenticatedUser AuthUser authUser) {
        return Api.OK(deleteArticleUseCase.deleteArticle(articleId, authUser.getUserId()));
    }

}
