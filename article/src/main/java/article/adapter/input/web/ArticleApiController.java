package article.adapter.input.web;

import article.adapter.input.web.request.ArticleSaveRequest;
import article.adapter.input.web.request.ArticleSearchCondition;
import article.adapter.input.web.request.ArticleUpdateRequest;
import article.adapter.input.web.response.ArticleFeignResponse;
import article.adapter.input.web.response.ArticleInfoResponse;
import article.adapter.output.persistence.enums.ArticleSaleStatus;
import article.application.port.input.BookmarkArticleUseCase;
import article.application.port.input.DeleteArticleUseCase;
import article.application.port.input.GetArticleUseCase;
import article.application.port.input.SaveArticleUseCase;
import article.application.port.input.UpdateArticleUseCase;
import article.domain.command.ArticleSaleStatusUpdateCommand;
import article.domain.command.ArticleSaveCommand;
import article.domain.command.ArticleSearchCommand;
import article.domain.command.ArticleUpdateCommand;
import global.annotation.AuthenticatedUser;
import global.api.Api;
import global.resolver.AuthUser;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ArticleApiController {

    private final SaveArticleUseCase saveArticleUseCase;

    private final GetArticleUseCase getArticleUseCase;

    private final UpdateArticleUseCase updateArticleUseCase;

    private final DeleteArticleUseCase deleteArticleUseCase;

    private final BookmarkArticleUseCase bookmarkArticleUseCase;

    @PostMapping("/articles")
    public Api<Boolean> save(
        @Valid @ModelAttribute ArticleSaveRequest request,
        @RequestPart("imageList") List<MultipartFile> imageList,
        @AuthenticatedUser AuthUser authUser
    ) {
        ArticleSaveCommand command = ArticleSaveCommand.of(request, imageList, authUser.getUserId());
        return Api.OK(saveArticleUseCase.saveArticle(command));
    }

    @GetMapping({"/list", "/auth/list"})
    public Api<List<ArticleInfoResponse>> getAllArticles(
        @ModelAttribute ArticleSearchCondition condition,
        @AuthenticatedUser(required = false) AuthUser authUser,
        Pageable pageable
    ) {
        condition.setPageable(pageable);
        String userId = getUserId(authUser);
        return Api.OK(getArticleUseCase.getArticleList(ArticleSearchCommand.of(condition, userId)));
    }

    @GetMapping("/my-articles")
    public Api<List<ArticleInfoResponse>> getMyArticles(
        @ModelAttribute ArticleSearchCondition condition,
        @AuthenticatedUser AuthUser authUser,
        Pageable pageable
    ) {
        condition.setPageable(pageable);
        return Api.OK(getArticleUseCase.getArticleList(ArticleSearchCommand.of(condition,
            authUser.getUserId())));
    }

    @GetMapping({"/articles/{articleId}", "/auth/articles/{articleId}"})
    public Api<List<ArticleInfoResponse>> getArticle(
        @PathVariable String articleId,
        @AuthenticatedUser(required = false) AuthUser authUser
    ) {
        String userId = getUserId(authUser);
        return Api.OK(getArticleUseCase.getArticleList(ArticleSearchCommand.of(articleId, userId)));
    }

    @PostMapping("/articles/{articleId}/edit")
    public void update(
        @PathVariable String articleId,
        @Valid @ModelAttribute ArticleUpdateRequest articleUpdateRequest,
        @RequestPart(value = "addImages", required = false) List<MultipartFile> addImages,
        @AuthenticatedUser AuthUser authUser,
        HttpServletResponse response
    ) {
        ArticleUpdateCommand command = ArticleUpdateCommand.of(articleUpdateRequest, addImages,
            articleId, authUser.getUserId());
        updateArticleUseCase.updateArticle(command);

        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/articles/" + articleId)
            .toUriString();

        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            log.error("Error redirecting to article page: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/articles/{articleId}/sale-status")
    public void updateSaleStatus(
        @PathVariable String articleId,
        @RequestBody ArticleSaleStatus status,
        @AuthenticatedUser AuthUser authUser,
        HttpServletResponse response
    ) {
        ArticleSaleStatusUpdateCommand command = ArticleSaleStatusUpdateCommand.of(
            status, articleId, authUser.getUserId());
        updateArticleUseCase.updateArticleSaleStatus(command);

        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/articles/" + articleId)
            .toUriString();

        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            log.error("Error redirecting to article page: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{articleId}")
    public Api<Boolean> softDeleteArticle(
        @PathVariable String articleId,
        @AuthenticatedUser AuthUser authUser
    ) {
        return Api.OK(deleteArticleUseCase.softDeleteArticle(articleId, authUser.getUserId()));
    }

    @PostMapping("/articles/{articleId}/bookmarks")
    public Api<Boolean> bookmarkArticle(
        @PathVariable String articleId,
        @AuthenticatedUser AuthUser authUser
    ) {
        return Api.OK(bookmarkArticleUseCase.bookmarkArticle(articleId, authUser.getUserId()));
    }

    @DeleteMapping("/articles/{articleId}/bookmarks")
    public Api<Boolean> unbookmarkArticle(
        @PathVariable String articleId,
        @AuthenticatedUser AuthUser authUser
    ) {
        return Api.OK(bookmarkArticleUseCase.unbookmarkArticle(articleId, authUser.getUserId()));
    }

    @GetMapping("/bookmarks")
    public Api<List<ArticleInfoResponse>> getBookmarkArticle(@AuthenticatedUser AuthUser authUser) {
        return Api.OK(getArticleUseCase.getBookmarkedArticles(authUser.getUserId()));
    }

    // Chat-Service Feign Client 에서 사용
    @GetMapping("/simple-info")
    public ArticleFeignResponse getArticleSimpleInfo(@RequestParam String articleId) {
        return getArticleUseCase.getArticleBy(articleId);
    }

    private String getUserId(AuthUser authUser) {
        return authUser != null ? authUser.getUserId() : null;
    }

}
