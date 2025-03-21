package chat.adapter.output.client;

import chat.adapter.output.client.dto.ArticleFeignInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "baobab-article", url = "https://baobab.run")
public interface ArticleClient {

    @GetMapping("/userId")
    ArticleFeignInfo getUserIdBy(@RequestParam String articleId);

}
