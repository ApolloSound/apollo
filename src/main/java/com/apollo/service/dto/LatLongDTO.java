package com.apollo.service.dto;

import com.apollo.service.dto.constraints.Latitude;
import com.apollo.service.dto.constraints.Longitude;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel(value = "LatLong", description = "A DTO to representing a coordinate")
public class LatLongDTO implements Serializable {

    @Latitude
    private Double latitude;

    @Longitude
    private Double longitude;

    public LatLongDTO() {
    }

    public LatLongDTO(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "LatLongDTO{" +
            "latitude=" + latitude +
            ", longitude=" + longitude +
            '}';
    }
}
