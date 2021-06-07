package com.code.challenge.service.parser.impl;

import com.code.challenge.exception.GeneralException;
import com.code.challenge.model.*;
import com.code.challenge.service.parser.Parser;
import com.code.challenge.util.ObjectMapperUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class YamlFileParser implements Parser {

    private static final int DELIMITER_LINE_LENGTH = 1;
    private static final String LINE_BREAK = "\n";
    private static final int CUSTOMER_COUNT_INDEX = 1;
    private static final String REQUEST_LINE_SPLITTER = " ";
    private static final int REQUEST_PARAM_SIZE = 3;
    private static final int ALBUM_DETAIL_LINE_COUNT = 4;
    private static final int REQUEST_TYPE_INDEX = 0;
    private static final int FIRST_PARAM_INDEX = 1;
    private static final int SECOND_PARAM_INDEX = 2;

    private static Parser parser;

    private YamlFileParser() {
    }

    public static Parser getInstance() {
        if (parser == null) {
            synchronized (YamlFileParser.class) {
                if (parser == null)
                    parser = new YamlFileParser();
            }
        }
        return parser;
    }

    @Override
    public InfoDetailResult parse(String filePath) throws GeneralException {
        this.validateFilePath(filePath);
        try {
            return parsFileToDetailModel(Files.lines(Paths.get(filePath)).collect(Collectors.toList()));
        } catch (Exception e) {
            throw new GeneralException(e);
        }
    }

    private InfoDetailResult parsFileToDetailModel(List<String> lines) throws Exception {
        Optional<Integer> albumCountIndex = getUserAlbumDelimiterIndex(lines);
        if (albumCountIndex.isEmpty())
            throw new GeneralException("Invalid parameter: file format not valid");
        InfoDetailResult model = new InfoDetailResult();
        model.setCustomers(parsCustomerDetails(lines, albumCountIndex.get()));
        model.setAlbums(parsAlbumDetails(lines, albumCountIndex.get()));
        model.setRequests(parsRequests(lines, albumCountIndex.get()));
        return model;
    }

    private List<RequestDetail> parsRequests(List<String> lines, Integer albumCountIndex) {
        int requestCountIndex = getRequestCountIndex(lines, albumCountIndex);
        List<String> requestStrings = lines.subList(requestCountIndex + 1, lines.size());
        return CollectionUtils.emptyIfNull(requestStrings).stream().map(this::toRequestDetail)
                .collect(Collectors.toList());
    }

    private RequestDetail toRequestDetail(String requestString) {
        String[] requestParams = requestString.split(REQUEST_LINE_SPLITTER);
        if (!isRequestParamValid(requestParams)) {
            throw new GeneralException("Invalid parameter: requests are not in valid form : " + requestString);
        }
        return RequestDetail.of(
                RequestType.fromValue(Integer.parseInt(requestParams[REQUEST_TYPE_INDEX])),
                requestParams[FIRST_PARAM_INDEX],
                requestParams[SECOND_PARAM_INDEX]
        );
    }

    private List<CustomerDetail> parsCustomerDetails(List<String> lines, Integer albumCountIndex) throws Exception {
        String yamlString = String.join(LINE_BREAK, lines.subList(CUSTOMER_COUNT_INDEX, albumCountIndex));
        return Arrays.asList(ObjectMapperUtil.parsYaml(yamlString, CustomerDetail[].class));
    }

    private List<AlbumDetail> parsAlbumDetails(List<String> lines, Integer albumCountIndex) throws Exception {
        String yamlString = String.join(
                LINE_BREAK, lines.subList(albumCountIndex + 1, getRequestCountIndex(lines, albumCountIndex))
        );
        return Arrays.asList(ObjectMapperUtil.parsYaml(yamlString, AlbumDetail[].class));
    }

    private int getRequestCountIndex(List<String> lines, int albumCountIndex) {
        int albumsCount = Integer.parseInt(lines.get(albumCountIndex));
        return albumCountIndex + (albumsCount * ALBUM_DETAIL_LINE_COUNT) + 1;
    }

    private Optional<Integer> getUserAlbumDelimiterIndex(List<String> lines) {
        for (int index = 1; index <= lines.size(); index++) {
            if (isDelimiterLine(lines.get(index)))
                return Optional.of(index);
        }
        return Optional.empty();
    }

    private void validateFilePath(String filePath) throws GeneralException {
        Optional.ofNullable(filePath)
                .orElseThrow(() -> new GeneralException("Invalid parameter: File path required"));
        if (!fileExists(filePath)) {
            throw new GeneralException("Invalid parameter: file not exists");
        }
    }

    private boolean isRequestParamValid(String[] requestParams) {
        return requestParams.length == REQUEST_PARAM_SIZE;
    }

    private boolean isDelimiterLine(String line) {
        return line.length() == DELIMITER_LINE_LENGTH;
    }

    private boolean fileExists(String filePath) {
        return Paths.get(filePath).toFile().exists();
    }
}
