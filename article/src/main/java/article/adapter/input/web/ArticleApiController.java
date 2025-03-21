package article.adapter.input.web;

import article.adapter.input.web.request.ArticleSaveRequest;
import article.adapter.input.web.request.ArticleSearchCondition;
import article.adapter.input.web.request.ArticleUpdateRequest;
import article.adapter.input.web.response.ArticleFeignResponse;
import article.adapter.input.web.response.ArticleInfoResponse;
import article.application.port.input.DeleteArticleUseCase;
import article.application.port.input.GetArticleUseCase;
import article.application.port.input.SaveArticleUseCase;
import article.application.port.input.UpdateArticleUseCase;
import article.domain.command.ArticleSaveCommand;
import article.domain.command.ArticleSearchCommand;
import article.domain.command.ArticleUpdateCommand;
import global.annotation.AuthenticatedUser;
import global.api.Api;
import global.resolver.AuthUser;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ArticleApiController {

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

    @GetMapping({"/list","/my-articles","/article/{articleId}"})
    public Api<List<ArticleInfoResponse>> getAllArticles(
        @PathVariable(required = false) String articleId,
        @ModelAttribute ArticleSearchCondition condition,
        @AuthenticatedUser(required = false) AuthUser authUser,
        Pageable pageable) {

        condition.setPageable(pageable);

        if(!StringUtils.isEmpty(articleId)) {
            condition.setArticleId(articleId);
        }

        if(authUser != null) {
            condition.setUserId(authUser.getUserId());
        }

        return Api.OK(getArticleUseCase.getArticleList(ArticleSearchCommand.of(condition)));
    }

    @PostMapping("/update")
    public ResponseEntity<Void> update(
        @Valid @ModelAttribute ArticleUpdateRequest articleUpdateRequest,
        @RequestPart(value = "addImages", required = false) List<MultipartFile> addImages,
        @AuthenticatedUser AuthUser authUser) {

        ArticleUpdateCommand command = ArticleUpdateCommand.of(articleUpdateRequest,
            addImages, authUser.getUserId());

        updateArticleUseCase.updateArticle(command);

        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/article/" + articleUpdateRequest.getId())
            .toUriString();

        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(redirectUrl))
            .build();
    }

    @DeleteMapping("/{articleId}")
    public Api<Boolean> deleteArticle(@PathVariable String articleId, @AuthenticatedUser AuthUser authUser) {
        return Api.OK(deleteArticleUseCase.deleteArticle(articleId, authUser.getUserId()));
    }

    // Chat-Service Feign Client 에서 사용
    @GetMapping("/userId")
    public ArticleFeignResponse getUserIdBy(@RequestParam String articleId) {
        return getArticleUseCase.getArticleBy(articleId);
    }

}
