package com.github.murillocg.webcrawler.core;

import com.github.murillocg.webcrawler.model.CrawlResult;
import com.github.murillocg.webcrawler.model.CrawlStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class CrawlResultRepositoryTest {

    private CrawlResultRepository crawlResultRepository;

    @BeforeEach
    public void setUp() {
        crawlResultRepository = new CrawlResultRepository();
    }

    @Test
    public void saveAsActiveShouldReturnAValidEntityWithActiveStatusWhenCalled() {
        String keyword = "myKeyword";
        CrawlResult crawlResult = crawlResultRepository.saveAsActive(keyword);

        assertNotNull(crawlResult.getId());
        assertEquals(CrawlStatus.ACTIVE, crawlResult.getStatus());
        assertNotNull(crawlResult.getUrls());
    }
    @Test
    public void saveAsActiveShouldThrowExceptionWhenKeywordIsNull() {
        assertThrows(NullPointerException.class, () -> crawlResultRepository.saveAsActive(null));
    }

    @Test
    public void saveAsActiveShouldReturnTenEntitiesWhenCalledConcurrentlyByTenThreads() throws ExecutionException, InterruptedException {
        int threads = 100;
        ExecutorService service = Executors.newFixedThreadPool(threads);
        Collection<Future<CrawlResult>> futures = new ArrayList<>(threads);

        for (int i = 0; i < threads; ++i) {
            String keyword = String.format("keyword%d", i);
            futures.add(service.submit(() -> crawlResultRepository.saveAsActive(keyword)));
        }

        Set<CrawlResult> crawlResults = new HashSet<>();
        for (Future<CrawlResult> f : futures) {
            crawlResults.add(f.get());
        }

        assertThat(crawlResults.size(), equalTo(threads));
    }

    @Test
    public void addUrlToCrawlResultShouldReturnUrlsAddedWhenTheEntityIsValid() {
        String keyword = "myKeyword";
        CrawlResult crawlResult = crawlResultRepository.saveAsActive(keyword);

        crawlResultRepository.addUrlToCrawlResult(crawlResult.getId(), "http://www.myurl1.com");
        crawlResultRepository.addUrlToCrawlResult(crawlResult.getId(), "http://www.myurl2.com");

        Optional<CrawlResult> crawlResultOptional = crawlResultRepository.findById(crawlResult.getId());
        Assertions.assertFalse(crawlResultOptional.isEmpty());

        crawlResult = crawlResultOptional.get();

        assertEquals(2, crawlResult.getUrls().size());
        assertTrue(crawlResult.getUrls().contains("http://www.myurl1.com"));
    }

    @Test
    public void addUrlToCrawlResultShouldThrowExceptionWhenIdDoesNotExist() {
        assertThrows(IllegalArgumentException.class,
                () -> crawlResultRepository.addUrlToCrawlResult("notExist", "http://www.myurl1.com"));
    }

    @Test
    public void addUrlToCrawlResultShouldThrowExceptionWhenUrlIsNull() {
        String keyword = "myKeyword";
        CrawlResult crawlResult = crawlResultRepository.saveAsActive(keyword);
        assertThrows(NullPointerException.class,
                () -> crawlResultRepository.addUrlToCrawlResult(crawlResult.getId(), null));
    }

    @Test
    public void saveAsDoneShouldChangeTheStatusToDoneWhenIsValidEntity() {
        String keyword = "myKeyword";
        CrawlResult crawlResult = crawlResultRepository.saveAsActive(keyword);
        crawlResultRepository.saveAsDone(crawlResult.getId());

        Optional<CrawlResult> crawlResultOptional = crawlResultRepository.findById(crawlResult.getId());

        Assertions.assertTrue(crawlResultOptional.isPresent());
        crawlResult = crawlResultOptional.get();
        assertEquals(CrawlStatus.DONE, crawlResult.getStatus());
    }

    @Test
    public void saveAsDoneShouldThrowExceptionWhenIdDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> crawlResultRepository.saveAsDone("notExist"));
    }

    @Test
    public void findByIdShouldReturnEmptyWhenIdDoesNotExist() {
        Optional<CrawlResult> crawlResultOptional = crawlResultRepository.findById("notExist");
        Assertions.assertTrue(crawlResultOptional.isEmpty());
    }

    @Test
    public void findByIdShouldReturnValidEntityWhenIdExists() {
        String keyword = "myKeyword";
        CrawlResult crawlResult = crawlResultRepository.saveAsActive(keyword);

        Optional<CrawlResult> crawlResultOptional = crawlResultRepository.findById(crawlResult.getId());
        assertFalse(crawlResultOptional.isEmpty());
    }

    @Test
    public void countUrlsByIdShouldReturnZeroWhenIdDoesNotExist() {
        int count = crawlResultRepository.countUrlsById("notExist");
        assertEquals(0, count);
    }

    @Test
    public void countUrlsByIdShouldReturnOneWhenOneUrlWasAlreadyAdded() {
        String keyword = "myKeyword";
        CrawlResult crawlResult = crawlResultRepository.saveAsActive(keyword);

        crawlResultRepository.addUrlToCrawlResult(crawlResult.getId(), "http://www.myurl.com");
        int count = crawlResultRepository.countUrlsById(crawlResult.getId());
        assertEquals(1, count);
    }

}