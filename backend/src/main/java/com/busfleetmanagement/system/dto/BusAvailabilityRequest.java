package com.busfleetmanagement.system.dto;

import java.time.LocalDateTime;

public class BusAvailabilityRequest {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int passengerCount;

    public BusAvailabilityRequest() {
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }
}