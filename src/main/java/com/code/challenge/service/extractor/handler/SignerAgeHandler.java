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

public class SignerAgeHandler extends AbstractRequestHandler {

    private static SignerAgeHandler instance;

    private SignerAgeHandler() {
    }

    public static RequestHandler getInstance() {
        if (instance == null) {
            synchronized (HandlerServiceLocator.class) {
                if (instance == null)
                    instance = new SignerAgeHandler();
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
                Integer.parseInt(request.getFirstParam()), model.getCustomers()
        ).flatMap(customer -> customer.getAlbums().stream()).distinct().collect(Collectors.toList());

        return this.getSingerAlbums(request.getSecondParam(), model.getAlbums())
                .filter(album -> targetAlbums.contains(album.getName()))
                .mapToInt(AlbumDetail::getTracks).sum();
    }

    private Stream<CustomerDetail> filterCustomersByAge(Integer age, List<CustomerDetail> customers) {
        return CollectionUtils.emptyIfNull(customers).stream().filter(customer -> customer.getAge().equals(age));
    }

    private Stream<AlbumDetail> getSingerAlbums(String singer, List<AlbumDetail> albums) {
        return CollectionUtils.emptyIfNull(albums).stream().filter(album -> album.getSinger().equals(singer));
    }

}
