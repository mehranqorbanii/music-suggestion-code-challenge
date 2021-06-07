package com.code.challenge;

import com.code.challenge.model.InfoDetailResult;
import com.code.challenge.service.extractor.ExtractorService;
import com.code.challenge.service.extractor.impl.SimpleExtractorService;
import com.code.challenge.service.parser.impl.YamlFileParser;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        InfoDetailResult detail = YamlFileParser.getInstance().parse(new File(
                Objects.requireNonNull(Main.class
                        .getClassLoader()
                        .getResource("items.txt"))
                        .getFile()).getAbsolutePath());
        ExtractorService extractorService = SimpleExtractorService.getInstance();
        List<Integer> results = extractorService.extractRequestInfo(detail);
    }

}
