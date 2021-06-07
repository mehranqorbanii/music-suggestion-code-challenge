package com.code.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoDetailResult {

    private List<CustomerDetail> customers;

    private List<AlbumDetail> albums;

    private List<RequestDetail> requests;

}
