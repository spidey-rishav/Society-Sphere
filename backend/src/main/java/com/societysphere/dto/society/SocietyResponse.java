package com.societysphere.dto.society;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocietyResponse {

    private Long id;

    private String name;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private Boolean isActive;

    private String subscriptionPlanName;

    private Integer totalFlats;

    private Integer totalResidents;

}