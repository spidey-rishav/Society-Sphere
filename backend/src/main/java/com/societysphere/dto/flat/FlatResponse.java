package com.societysphere.dto.flat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlatResponse {

    private Long id;

    private Long societyId;

    private String societyName;

    private String flatNumber;

    private Integer floorNumber;

    private String blockName;

    private Boolean isOccupied;

}