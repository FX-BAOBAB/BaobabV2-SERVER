package article.adapter.input.web;

import article.adapter.input.web.request.ArticleReportSaveRequest;
import article.application.port.input.SaveArticleReportUseCase;
import article.domain.command.ArticleReportSaveCommand;
import global.annotation.AuthenticatedUser;
import global.annotation.input.RestAdapter;
import global.api.Api;
import global.resolver.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestAdapter
@RequiredArgsConstructor
public class ArticleReportApiController {

    private final SaveArticleReportUseCase saveArticleReportUseCase;

    @PostMapping("/report")
    public Api<Boolean> saveReport(
        @RequestBody @Valid Api<ArticleReportSaveRequest> articleReportSaveRequest,
        @AuthenticatedUser AuthUser authUser
    ) {
        return Api.OK(saveArticleReportUseCase.saveArticleReport(
            ArticleReportSaveCommand.of(articleReportSaveRequest.getBody()), authUser.getUserId()));
    }

}
