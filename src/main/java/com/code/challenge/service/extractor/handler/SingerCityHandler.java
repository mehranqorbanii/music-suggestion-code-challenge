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

public class SingerCityHandler extends AbstractRequestHandler {


    private static SingerCityHandler instance;

    private SingerCityHandler() {
    }

    public static RequestHandler getInstance() {
        if (instance == null) {
            synchronized (HandlerServiceLocator.class) {
                if (instance == null)
                    instance = new SingerCityHandler();
            }
        }
        return instance;
    }

    @Override
    public Integer handle(RequestDetail request, InfoDetailResult model) {
        if (super.isDataProcessable(model)) {
            return ZERO_TRACKS;
        }
        List<String> targetAlbums = this.filterCustomersByCity(request.getFirstParam(), model.getCustomers())
                .flatMap(customer -> customer.getAlbums().stream()).distinct().collect(Collectors.toList());

        return this.filterAlbumsByGenre(request.getSecondParam(), model.getAlbums())
                .filter(album -> targetAlbums.contains(album.getName()))
                .mapToInt(AlbumDetail::getTracks).sum();

    }

    private Stream<CustomerDetail> filterCustomersByCity(String city, List<CustomerDetail> customers) {
        return CollectionUtils.emptyIfNull(customers).stream().filter(customer -> customer.getCity().equals(city));
    }


    private Stream<AlbumDetail> filterAlbumsByGenre(String singer, List<AlbumDetail> albums) {
        return CollectionUtils.emptyIfNull(albums).stream().filter(album -> album.getName().equals(singer));
    }

}
