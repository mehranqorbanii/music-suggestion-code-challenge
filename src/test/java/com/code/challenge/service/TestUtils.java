package com.code.challenge.service;

import com.code.challenge.model.*;

import java.util.Arrays;
import java.util.List;

public class TestUtils {


    public static InfoDetailResult createInfoDetailResult() {
        InfoDetailResult result = new InfoDetailResult();
        result.setCustomers(createCustomersList());
        result.setAlbums(createAlbumList());
        result.setRequests(createRequestList());
        return result;
    }

    private static List<RequestDetail> createRequestList() {
        RequestDetail detail = new RequestDetail(RequestType.USER_SINGERS, "gholi", "hoyad");
        RequestDetail detail2 = new RequestDetail(RequestType.USER_SINGERS, "gholi", "xy");
        RequestDetail detail3 = new RequestDetail(RequestType.USER_GENRES, "gholi", "rock");
        RequestDetail detail4 = new RequestDetail(RequestType.USER_GENRES, "mehdi", "pop");
        RequestDetail detail5 = new RequestDetail(RequestType.SINGER_AGE, "20", "xy");
        RequestDetail detail6 = new RequestDetail(RequestType.GENRE_AGE, "18", "malmsteen");
        RequestDetail detail7 = new RequestDetail(RequestType.GENRE_AGE, "19", "persian");
        return Arrays.asList(detail, detail2, detail3, detail4, detail5, detail6, detail7);
    }

    private static List<AlbumDetail> createAlbumList() {
        AlbumDetail albumDetail = new AlbumDetail("eclipse", "malmsteen", "classic", 10);
        AlbumDetail albumDetail2 = new AlbumDetail("barf", "xy", "pop", 22);
        AlbumDetail albumDetail3 = new AlbumDetail("tekunbede", "xy", "pop", 14);
        AlbumDetail albumDetail4 = new AlbumDetail("hoyad", "hoyad", "persian", 5);
        return Arrays.asList(albumDetail, albumDetail3, albumDetail2, albumDetail4);
    }

    private static List<CustomerDetail> createCustomersList() {
        CustomerDetail detail = new CustomerDetail(
                "gholi", 8, "tehran", Arrays.asList("tekunbede", "barf", "hoyad")
        );
        CustomerDetail detail2 = new CustomerDetail(
                "mehdi", 19, "mashhad", Arrays.asList("eclipse", "barf", "hoyad")
        );

        return Arrays.asList(detail, detail2);
    }


}
