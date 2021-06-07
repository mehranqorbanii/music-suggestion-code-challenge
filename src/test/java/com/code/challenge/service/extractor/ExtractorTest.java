package com.code.challenge.service.extractor;

import com.code.challenge.model.InfoDetailResult;
import com.code.challenge.service.extractor.impl.SimpleExtractorService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static com.code.challenge.service.TestUtils.createInfoDetailResult;

public class ExtractorTest {

    @Test
    void extractRequestInfo_allDataExits_returnOk() {
        List<Integer> results = SimpleExtractorService.getInstance().extractRequestInfo(createInfoDetailResult());
        assertNotNull(results);
        assertEquals(results.size(), 7);
        assertEquals(results.get(0), 5);
        assertEquals(results.get(1), 36);
        assertEquals(results.get(2), 0);
        assertEquals(results.get(3), 22);
        assertEquals(results.get(4), 0);
        assertEquals(results.get(5), 0);
        assertEquals(results.get(6), 5);
    }

    @Test
    void extractRequestInfo_noRequestExits_returnZeroAnswer() {
        InfoDetailResult input = createInfoDetailResult();
        input.setRequests(null);
        List<Integer> results = SimpleExtractorService.getInstance().extractRequestInfo(input);
        assertNotNull(results);
        assertEquals(results.size(), 0);
    }

}
