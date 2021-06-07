package com.code.challenge.service.extractor;

import com.code.challenge.model.RequestType;
import com.code.challenge.service.extractor.handler.*;

import java.util.HashMap;
import java.util.Map;

public class HandlerServiceLocator {

    private static HandlerServiceLocator instance;

    private Map<RequestType, RequestHandler> handlers;

    private HandlerServiceLocator() {
        initHandlers();
    }

    public static HandlerServiceLocator getInstance() {
        if (instance == null) {
            synchronized (HandlerServiceLocator.class) {
                if (instance == null)
                    instance = new HandlerServiceLocator();
            }
        }
        return instance;
    }

    private void initHandlers() {
        if (handlers != null) {
            return;
        }
        handlers = new HashMap<>();
        handlers.put(RequestType.USER_SINGERS, UserSingerHandler.getInstance());
        handlers.put(RequestType.SINGER_CITY, SingerCityHandler.getInstance());
        handlers.put(RequestType.GENRE_AGE, GenreAgeHandler.getInstance());
        handlers.put(RequestType.GENRE_CITY, GenreCityHandler.getInstance());
        handlers.put(RequestType.SINGER_AGE, SignerAgeHandler.getInstance());
        handlers.put(RequestType.USER_GENRES, UserGenreHandler.getInstance());
    }

    public RequestHandler getHandler(RequestType type) {
        if (handlers.containsKey(type))
            return handlers.get(type);
        else
            throw new IllegalArgumentException("no handler found for corresponding type");
    }

}
