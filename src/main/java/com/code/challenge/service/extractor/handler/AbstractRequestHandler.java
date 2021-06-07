package com.code.challenge.service.extractor.handler;

import com.code.challenge.model.InfoDetailResult;
import com.code.challenge.service.extractor.RequestHandler;
import org.apache.commons.collections4.CollectionUtils;

public abstract class AbstractRequestHandler implements RequestHandler {

    public static final Integer ZERO_TRACKS = 0;

    protected boolean isDataProcessable(InfoDetailResult model) {
        return CollectionUtils.isEmpty(model.getCustomers()) || CollectionUtils.isEmpty(model.getAlbums());
    }
}
