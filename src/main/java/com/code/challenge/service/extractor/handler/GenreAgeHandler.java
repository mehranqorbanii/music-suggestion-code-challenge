package com.code.challenge.service.extractor.handler;

import com.code.challenge.model.AlbumDetail;
import com.code.challenge.model.CustomerDetail;
import com.code.challenge.model.InfoDetailResult;
import com.code.challenge.model.RequestDetail;
import com.code.challenge.service.extractor.RequestHandler;
import com.code.challenge.service.extractor.HandlerServiceLocator;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenreAgeHandler extends AbstractRequestHandler {

    private static GenreAgeHandler instance;

    private GenreAgeHandler() {
    }

    public static RequestHandler getInstance() {
        if (instance == null) {
            synchronized (HandlerServiceLocator.class) {
                if (instance == null)
                    instance = new GenreAgeHandler();
            }
        }
        return instance;
    }

    @Override
    public Integer handle(RequestDetail request, InfoDetailResult model) {
        if (super.isDataProcessable(model)) {
            return ZERO_TRACKS;
        }
        List<String> targetAlbums = this.filterCustomersByAge(
                Integer.parseInt(request.getFirstParam()), model.getCustomers())
                .flatMap(customer -> CollectionUtils.emptyIfNull(customer.getAlbums())
                        .stream()).distinct().collect(Collectors.toList());

        return this.getAlbumsByGenre(request.getSecondParam(), model.getAlbums())
                .filter(album -> targetAlbums.contains(album.getName()))
                .mapToInt(AlbumDetail::getTracks).sum();
    }

    private Stream<CustomerDetail> filterCustomersByAge(Integer age, List<CustomerDetail> customers) {
        return CollectionUtils.emptyIfNull(customers).stream().filter(customer -> customer.getAge().equals(age));
    }

    private Stream<AlbumDetail> getAlbumsByGenre(String genre, List<AlbumDetail> albums) {
        return CollectionUtils.emptyIfNull(albums).stream().filter(album -> album.getGenre().equals(genre));
    }

}
