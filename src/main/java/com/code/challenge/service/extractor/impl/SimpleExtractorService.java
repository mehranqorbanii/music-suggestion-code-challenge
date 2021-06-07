package com.code.challenge.service.extractor.impl;

import com.code.challenge.model.InfoDetailResult;
import com.code.challenge.service.extractor.ExtractorService;
import com.code.challenge.service.extractor.HandlerServiceLocator;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimpleExtractorService implements ExtractorService {

    private static ExtractorService instance;

    private SimpleExtractorService() {
    }

    public static ExtractorService getInstance() {
        if (instance == null) {
            synchronized (ExtractorService.class) {
                if (instance == null)
                    instance = new SimpleExtractorService();
            }
        }
        return instance;
    }

    @Override
    public List<Integer> extractRequestInfo(InfoDetailResult model) {
        if (CollectionUtils.isEmpty(model.getRequests())) {
            return new ArrayList<>();
        }
        List<Integer> answers = new ArrayList<>();
        model.getRequests().forEach(request -> answers.add(
                HandlerServiceLocator.getInstance().getHandler(request.getType()).handle(request, model)
        ));
        return answers;
    }

}
