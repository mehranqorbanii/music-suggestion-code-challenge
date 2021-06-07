package com.code.challenge.service.extractor.handler;

import com.code.challenge.model.AlbumDetail;
import com.code.challenge.model.CustomerDetail;
import com.code.challenge.model.InfoDetailResult;
import com.code.challenge.model.RequestDetail;
import com.code.challenge.service.extractor.RequestHandler;
import com.code.challenge.service.extractor.HandlerServiceLocator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class UserSingerHandler extends AbstractRequestHandler {

    private static UserSingerHandler instance;

    private UserSingerHandler() {
    }

    public static RequestHandler getInstance() {
        if (instance == null) {
            synchronized (HandlerServiceLocator.class) {
                if (instance == null)
                    instance = new UserSingerHandler();
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
        if (customer.isEmpty() || customer.get().getAlbums().isEmpty()) {
            return ZERO_TRACKS;
        }
        return this.filterAlbumsBySinger(request.getSecondParam(), model.getAlbums())
                .filter(album -> this.isAlbumInCustomerList(album, customer.get()))
                .mapToInt(AlbumDetail::getTracks).sum();
    }

    private boolean isAlbumInCustomerList(AlbumDetail album, CustomerDetail customerDetail) {
        return customerDetail.getAlbums().contains(album.getName());
    }

    private Stream<AlbumDetail> filterAlbumsBySinger(String singer, List<AlbumDetail> albums) {
        return albums.stream().filter(album -> album.getSinger().equals(singer));
    }

    private Optional<CustomerDetail> getCustomerDetail(String customerName, List<CustomerDetail> customers) {
        return customers.stream()
                .filter(customerDetail -> customerDetail.getName().equals(customerName))
                .findFirst();
    }

}
