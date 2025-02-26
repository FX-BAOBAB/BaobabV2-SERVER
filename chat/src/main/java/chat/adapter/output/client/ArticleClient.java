package chat.adapter.output.client;

import chat.adapter.output.client.response.ArticleFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "baobab-article")
public interface ArticleClient {

    @GetMapping("/articleId")
    ArticleFeignResponse getArticleBy(@RequestParam String articleId);

}
