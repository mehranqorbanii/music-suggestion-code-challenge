package com.code.challenge.service.extractor;

import com.code.challenge.model.InfoDetailResult;
import com.code.challenge.model.RequestDetail;

public interface RequestHandler {

    Integer handle(RequestDetail requestDetail, InfoDetailResult model);

}
