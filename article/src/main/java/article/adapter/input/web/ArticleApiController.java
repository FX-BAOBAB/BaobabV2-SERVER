package article.adapter.input.web;

import article.adapter.input.web.request.ArticleSaveRequest;
import article.adapter.input.web.request.ArticleSearchCondition;
import article.adapter.input.web.request.ArticleUpdateRequest;
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
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public Api<Boolean> save(
        @Valid @ModelAttribute ArticleSaveRequest request,
        @RequestPart("imageList") List<MultipartFile> imageList,
        @AuthenticatedUser AuthUser authUser) {

        ArticleSaveCommand command = ArticleSaveCommand.of(request, imageList, authUser.getUserId());

        return Api.OK(saveArticleUseCase.saveArticle(command));
    }

    // TODO Article List Algorithm 적용 필요
    // TODO My-articles : Auth Filter 적용 필요
    @GetMapping({"/list","/my-articles","/article/{articleId}"})
    public Api<List<Article>> getAllArticles(@PathVariable(required=false) String articleId,@ModelAttribute ArticleSearchCondition condition,Pageable pageable) {

        condition.setPageable(pageable);

        if(!StringUtils.isEmpty(articleId)) {
            condition.setArticleId(articleId);
        }

        return Api.OK(getArticleUseCase.getArticleList(ArticleSearchCommand.of(condition)));
    }

    // TODO Yang ji ung 과제
    @PostMapping("/article")
    public Api<Boolean> updateArticle(@Valid @ModelAttribute ArticleUpdateRequest articleUpdateRequest, @AuthenticatedUser AuthUser authUser) {
        ArticleUpdateCommand articleUpdateCommand = articleConverter.toUpdateCommand(articleUpdateRequest, authUser.getUserId());
        updateArticleUseCase.updateArticle(articleUpdateCommand); // pass > throw Exception Handling
        //return "redirect:"; //
        return null;
    }

    @DeleteMapping("/article/{articleId}")
    public Api<Boolean> deleteArticle(@PathVariable String articleId, @AuthenticatedUser AuthUser authUser) {
        return Api.OK(deleteArticleUseCase.deleteArticle(articleId, authUser.getUserId()));
    }

}
