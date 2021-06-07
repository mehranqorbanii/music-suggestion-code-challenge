package com.code.challenge.service.parser;

import com.code.challenge.exception.GeneralException;
import com.code.challenge.model.InfoDetailResult;
import com.code.challenge.service.parser.impl.YamlFileParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


public class ParserTest {

    static File validFile;

    static File emptyRequestFile;

    static File invalid_params_file;

    static File album_count_mismatch_file;


    @BeforeAll
    static void initialize() {
        validFile = new File(
                Objects.requireNonNull(ParserTest.class
                        .getClassLoader()
                        .getResource("items.txt"))
                        .getFile());
        emptyRequestFile = new File(
                Objects.requireNonNull(ParserTest.class
                        .getClassLoader()
                        .getResource("items_no_request.txt"))
                        .getFile());
        invalid_params_file = new File(
                Objects.requireNonNull(ParserTest.class
                        .getClassLoader()
                        .getResource("items_invalid_request_params.txt"))
                        .getFile());

        album_count_mismatch_file = new File(
                Objects.requireNonNull(ParserTest.class
                        .getClassLoader()
                        .getResource("items_album_count_mismatch.txt"))
                        .getFile());
    }

    @Test
    void getInstance_objectIsSingleton_returnsSame() {
        Parser parserFirstInstance = YamlFileParser.getInstance();
        Parser parserSecondInstance = YamlFileParser.getInstance();
        assertEquals(parserFirstInstance, parserSecondInstance);
    }

    @Test
    void getInstance_fileCorrect_returnsOk() {

        InfoDetailResult result = YamlFileParser.getInstance().parse(validFile.getAbsolutePath());
        assertNotNull(result);
        assertNotNull(result.getAlbums());
        assertNotNull(result.getCustomers());
        assertNotNull(result.getRequests());
        assertEquals(result.getCustomers().size(), 3);
        assertEquals(result.getAlbums().size(), 6);
        assertEquals(result.getRequests().size(), 7);

    }

    @Test
    void getInstance_emptyRequest_returnsOk() {

        InfoDetailResult result = YamlFileParser.getInstance().parse(emptyRequestFile.getAbsolutePath());
        assertNotNull(result);
        assertNotNull(result.getAlbums());
        assertNotNull(result.getCustomers());
        assertNotNull(result.getRequests());
        assertEquals(result.getCustomers().size(), 3);
        assertEquals(result.getAlbums().size(), 6);
        assertEquals(result.getRequests().size(), 0);

    }

    @Test
    void parse_pathIsNotValid_returnError() {
        assertThrows(GeneralException.class, () -> YamlFileParser.getInstance().parse("/file/not/exists"));
    }

    @Test
    void parse_requestParamsInvalid_returnError() {
        assertThrows(
                GeneralException.class, () -> YamlFileParser.getInstance().parse(invalid_params_file.getAbsolutePath())
        );
    }

    @Test
    void parse_albumCountMismatchAlbumDetail_returnError() {
        assertThrows(
                GeneralException.class,
                () -> YamlFileParser.getInstance().parse(album_count_mismatch_file.getAbsolutePath())
        );
    }


}
