package com.code.challenge.service.parser;

import com.code.challenge.exception.GeneralException;
import com.code.challenge.model.InfoDetailResult;

public interface Parser {

    InfoDetailResult parse(String filePath) throws GeneralException;

}
