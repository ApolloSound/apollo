package com.apollo.service.dto;

import com.apollo.domain.enumeration.AgentType;
import io.swagger.annotations.ApiModel;

import java.time.LocalDateTime;

@ApiModel(value = "Playback", description = "A DTO representing a playback of a song")
public class PlaybackDTO {
    private Double latitude;
    private Double longitude;
    private LocalDateTime time;
    private AgentType client;
    private UserSimplifiedDTO user;
    private TrackDTO track;

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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public AgentType getClient() {
        return client;
    }

    public void setClient(AgentType client) {
        this.client = client;
    }

    public UserSimplifiedDTO getUser() {
        return user;
    }

    public void setUser(UserSimplifiedDTO user) {
        this.user = user;
    }

    public TrackDTO getTrack() {
        return track;
    }

    public void setTrack(TrackDTO track) {
        this.track = track;
    }
}
