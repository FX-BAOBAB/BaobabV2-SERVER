package article.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import article.adapter.input.web.request.ArticleSaveRequest;
import article.adapter.output.persistence.ArticlePersistenceAdapter;
import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleSaleStatus;
import article.adapter.output.persistence.repository.Article;
import article.adapter.output.persistence.repository.ArticleMongoRepository;
import article.domain.command.ArticleSaveCommand;
import article.domain.command.ArticleSearchCommand;
import article.domain.command.ArticleUpdateCommand;
import article.domain.dto.ArticleImage;
import config.EnableMongoTestServer;
import file.domain.ImageKind;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@EnableMongoTestServer
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMongoRepository articleMongoRepository;

    @Autowired
    private ArticlePersistenceAdapter articlePersistenceAdapter;

    @Mock
    private List<MultipartFile> imageList;

    @Test
    void 게시글_저장() {
        ArticleSaveRequest request = new ArticleSaveRequest(
            "Test Article", "Test Content", ArticleCategory.CLOTHING, 10000);

        ArticleSaveCommand command = ArticleSaveCommand.of(
            request, imageList, "userId");

        boolean isSaved = articleService.saveArticle(command);

        assertTrue(isSaved);
    }

    @Test
    void 게시글_조회() {
        ArticleSearchCommand command = ArticleSearchCommand.builder()
            .title("Test Article")
            .content("Test Content")
            .pageable(Pageable.ofSize(10))
            .build();

        List<Article> articleList = articlePersistenceAdapter.getArticleList(command);

        assertFalse(articleList.isEmpty());

        Article article = articleList.getFirst();
        assertEquals(article.getTitle(), "Test Article");
        assertEquals(article.getContent(), "Test Content");
        assertEquals(article.getCategory(), ArticleCategory.CLOTHING);
        assertEquals(article.getPrice(), 10000);
        assertEquals(article.getStatus(), ArticleSaleStatus.ON_SALE);
        assertEquals(article.getUserId(), "userId");
    }

    @Test
    void 게시글_수정() {
        articleMongoRepository.findAll().forEach(article -> {

            ArticleImage imageA = ArticleImage.of("A-imageId", "A-imageUrl", ImageKind.ARTICLE);
            ArticleImage imageB = ArticleImage.of("B-imageId", "B-imageUrl", ImageKind.ARTICLE);
            article.setImageList(List.of(imageA, imageB));

            articleMongoRepository.save(article);

            ArticleUpdateCommand command = ArticleUpdateCommand.builder()
                .id(article.getId())
                .title("Updated Article")
                .content("Updated Content")
                .category(ArticleCategory.CLOTHING)
                .price(20000)
                .deleteImageIdList(List.of(imageA.getImageId()))
                .status(ArticleSaleStatus.RESERVED)
                .userId("userId")
                .build();

            articleService.updateArticle(command);

            Article updatedArticle = articleMongoRepository.findById(article.getId()).get();
            assertEquals(updatedArticle.getTitle(), "Updated Article");
            assertEquals(updatedArticle.getContent(), "Updated Content");
            assertEquals(updatedArticle.getPrice(), 20000);
            assertEquals(updatedArticle.getStatus(), ArticleSaleStatus.RESERVED);
            assertFalse(updatedArticle.getImageList().contains(imageA));
        });
    }

//    @Test
//    void 게시물_삭제() {
//        articleMongoRepository.findAll().forEach(article -> {
//            articleService.deleteArticle(article.getId(), "userId");
//            assertFalse(articleMongoRepository.existsById(article.getId()));
//        });
//    }

}