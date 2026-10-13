
package com.busfleetmanagement.system.dto;

import java.math.BigDecimal;
import java.util.List;

public class DashboardSummaryResponse {

    private long totalBuses;
    private long activeBuses;

    private long totalDrivers;
    private long activeDrivers;

    private long totalBookings;
    private long activeBookings;

    private long todaysJourneys;

    private long totalPayments;
    private long successfulPayments;
    private long pendingPayments;
    private long failedPayments;

    private BigDecimal monthlyRevenue = BigDecimal.ZERO;

    private List<RecentBooking> recentBookings;

    public DashboardSummaryResponse() {
    }

    // Getters and setters

    public long getTotalBuses() {
        return totalBuses;
    }

    public void setTotalBuses(long totalBuses) {
        this.totalBuses = totalBuses;
    }

    public long getActiveBuses() {
        return activeBuses;
    }

    public void setActiveBuses(long activeBuses) {
        this.activeBuses = activeBuses;
    }

    public long getTotalDrivers() {
        return totalDrivers;
    }

    public void setTotalDrivers(long totalDrivers) {
        this.totalDrivers = totalDrivers;
    }

    public long getActiveDrivers() {
        return activeDrivers;
    }

    public void setActiveDrivers(long activeDrivers) {
        this.activeDrivers = activeDrivers;
    }

    public long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public long getActiveBookings() {
        return activeBookings;
    }

    public void setActiveBookings(long activeBookings) {
        this.activeBookings = activeBookings;
    }

    public long getTodaysJourneys() {
        return todaysJourneys;
    }

    public void setTodaysJourneys(long todaysJourneys) {
        this.todaysJourneys = todaysJourneys;
    }

    public long getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(long totalPayments) {
        this.totalPayments = totalPayments;
    }

    public long getSuccessfulPayments() {
        return successfulPayments;
    }

    public void setSuccessfulPayments(long successfulPayments) {
        this.successfulPayments = successfulPayments;
    }

    public long getPendingPayments() {
        return pendingPayments;
    }

    public void setPendingPayments(long pendingPayments) {
        this.pendingPayments = pendingPayments;
    }

    public long getFailedPayments() {
        return failedPayments;
    }

    public void setFailedPayments(long failedPayments) {
        this.failedPayments = failedPayments;
    }

    public BigDecimal getMonthlyRevenue() {
        return monthlyRevenue;
    }

    public void setMonthlyRevenue(BigDecimal monthlyRevenue) {
        this.monthlyRevenue = monthlyRevenue;
    }

    public List<RecentBooking> getRecentBookings() {
        return recentBookings;
    }

    public void setRecentBookings(
            List<RecentBooking> recentBookings) {
        this.recentBookings = recentBookings;
    }

    // Recent booking DTO

    public static class RecentBooking {

        private int bookingID;
        private String customerName;
        private String startLocation;
        private String destination;
        private String status;
        private String busRegistrationNo;

        public RecentBooking() {
        }

        public int getBookingID() {
            return bookingID;
        }

        public void setBookingID(int bookingID) {
            this.bookingID = bookingID;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getStartLocation() {
            return startLocation;
        }

        public void setStartLocation(String startLocation) {
            this.startLocation = startLocation;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBusRegistrationNo() {
            return busRegistrationNo;
        }

        public void setBusRegistrationNo(
                String busRegistrationNo) {
            this.busRegistrationNo = busRegistrationNo;
        }
    }
}
