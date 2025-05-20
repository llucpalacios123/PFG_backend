package com.lluc.backend.shopapp.shopapp.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressRequest {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}