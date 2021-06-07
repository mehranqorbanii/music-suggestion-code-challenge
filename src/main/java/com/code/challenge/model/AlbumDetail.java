package com.code.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDetail {

    private String name;

    private String singer;

    private String genre;

    private Integer tracks;

}
