package article.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import article.adapter.input.web.request.ArticleSaveRequest;
import article.adapter.input.web.response.ArticleInfoResponse;
import article.adapter.output.persistence.ArticlePersistenceAdapter;
import article.adapter.output.persistence.enums.ArticleCategory;
import article.adapter.output.persistence.enums.ArticleSaleStatus;
import article.adapter.output.persistence.enums.ArticleVisibilityStatus;
import article.adapter.output.persistence.repository.Article;
import article.adapter.output.persistence.repository.ArticleMongoRepository;
import article.domain.command.ArticleSaveCommand;
import article.domain.command.ArticleSearchCommand;
import article.domain.command.ArticleUpdateCommand;
import article.domain.dto.ArticleImage;
import config.EnableMongoTestServer;
import file.domain.ImageKind;
import java.util.List;
import java.util.Optional;
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
        ArticleSaveRequest request = new ArticleSaveRequest();
        request.setTitle("Test Article");
        request.setContent("Test Content");
        request.setCategory(ArticleCategory.CLOTHING);
        request.setPrice(10000);

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

        List<Article> articleList = articlePersistenceAdapter.getArticleList(command, ArticleVisibilityStatus.VISIBILITY);

        assertFalse(articleList.isEmpty());

        Article article = articleList.getFirst();
        assertEquals("Test Article", article.getTitle());
        assertEquals("Test Content", article.getContent());
        assertEquals(ArticleCategory.CLOTHING, article.getCategory());
        assertEquals(10000, article.getPrice());
        assertEquals(ArticleSaleStatus.ON_SALE, article.getSaleStatus());
        assertEquals(ArticleVisibilityStatus.VISIBILITY, article.getVisibilityStatus());
        assertEquals("userId", article.getUserId());
    }

    @Test
    void 게시글_수정() {
        articleMongoRepository.findAll().forEach(article -> {

            ArticleImage imageA = ArticleImage.of("A-imageId", "A-imageUrl", ImageKind.ARTICLE);
            ArticleImage imageB = ArticleImage.of("B-imageId", "B-imageUrl", ImageKind.ARTICLE);
            article.setImageList(List.of(imageA, imageB));

            articleMongoRepository.save(article);

            ArticleUpdateCommand command = ArticleUpdateCommand.builder()
                .articleId(article.getId())
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
            assertEquals("Updated Article", updatedArticle.getTitle());
            assertEquals("Updated Content", updatedArticle.getContent());
            assertEquals(20000, updatedArticle.getPrice());
            assertEquals(ArticleSaleStatus.RESERVED, updatedArticle.getSaleStatus());
            assertEquals(ArticleVisibilityStatus.VISIBILITY, updatedArticle.getVisibilityStatus());
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

    @Test
    void 게시글_북마크() {
        articleMongoRepository.findAll().forEach(article -> {
            articleService.bookmarkArticle(article.getId(), "userId");
        });

        articleMongoRepository.findAll().forEach(article -> {
            assertTrue(article.getBookmarkUserIdList().contains("userId"));
        });
    }

    @Test
    void 게시글_북마크_조회() {
        ArticleSaveRequest request = new ArticleSaveRequest();
        request.setTitle("Test Article");
        request.setContent("Test Content");
        request.setCategory(ArticleCategory.CLOTHING);
        request.setPrice(10000);

        ArticleSaveCommand command = ArticleSaveCommand.of(
            request, imageList, "userId");

        articleService.saveArticle(command);

        articleMongoRepository.findAll().forEach(article -> {
            articleService.bookmarkArticle(article.getId(), "userId");
        });

        articleMongoRepository.findAll().forEach(article -> {
            assertTrue(article.getBookmarkUserIdList().contains("userId"));
        });

        List<Article> bookedArticle = articlePersistenceAdapter.getBookmarkedArticles("userId");
        assertFalse(bookedArticle.isEmpty());
    }

    @Test
    void 게시글_북마크_취소() {
        articleMongoRepository.findAll().forEach(article -> {
            articleService.unbookmarkArticle(article.getId(), "userId");
        });

        articleMongoRepository.findAll().forEach(article -> {
            assertFalse(article.getBookmarkUserIdList().contains("userId"));
        });
    }

}