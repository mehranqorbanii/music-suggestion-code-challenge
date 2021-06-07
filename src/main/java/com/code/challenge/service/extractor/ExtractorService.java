package com.code.challenge.service.extractor;

import com.code.challenge.model.InfoDetailResult;

import java.util.List;

public interface ExtractorService {

    List<Integer> extractRequestInfo(InfoDetailResult model);
}
