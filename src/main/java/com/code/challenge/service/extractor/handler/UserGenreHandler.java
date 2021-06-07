package com.code.challenge.service.extractor.handler;

import com.code.challenge.model.AlbumDetail;
import com.code.challenge.model.CustomerDetail;
import com.code.challenge.model.InfoDetailResult;
import com.code.challenge.model.RequestDetail;
import com.code.challenge.service.extractor.RequestHandler;
import com.code.challenge.service.extractor.HandlerServiceLocator;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class UserGenreHandler extends AbstractRequestHandler {

    private static UserGenreHandler instance;

    private UserGenreHandler() {
    }

    public static RequestHandler getInstance() {
        if (instance == null) {
            synchronized (HandlerServiceLocator.class) {
                if (instance == null)
                    instance = new UserGenreHandler();
            }
        }
        return instance;
    }

    @Override
    public Integer handle(RequestDetail request, InfoDetailResult model) {
        if (super.isDataProcessable(model)) {
            return ZERO_TRACKS;
        }
        Optional<CustomerDetail> customer = this.getCustomerDetail(request.getFirstParam(), model.getCustomers());
        if (customer.isEmpty() || CollectionUtils.isEmpty(customer.get().getAlbums())) {
            return ZERO_TRACKS;
        }

        return this.getAlbumsByGenre(request.getSecondParam(), model.getAlbums())
                .filter(album -> isAlbumInCustomerList(album, customer.get()))
                .mapToInt(AlbumDetail::getTracks).sum();
    }

    private boolean isAlbumInCustomerList(AlbumDetail album, CustomerDetail customerDetail) {
        return CollectionUtils.emptyIfNull(customerDetail.getAlbums()).contains(album.getName());
    }

    private Optional<CustomerDetail> getCustomerDetail(String customerName, List<CustomerDetail> customers) {
        return CollectionUtils.emptyIfNull(customers).stream()
                .filter(customerDetail -> customerDetail.getName().equals(customerName))
                .findFirst();
    }

    private Stream<AlbumDetail> getAlbumsByGenre(String genre, List<AlbumDetail> albums) {
        return CollectionUtils.emptyIfNull(albums).stream().filter(album -> album.getGenre().equals(genre));
    }
}
