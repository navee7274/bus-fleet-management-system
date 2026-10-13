
package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.dto.DashboardSummaryResponse;
import com.busfleetmanagement.system.entity.Booking;
import com.busfleetmanagement.system.entity.Payment;
import com.busfleetmanagement.system.enums.PaymentStatus;
import com.busfleetmanagement.system.repository.BusRepository;
import com.busfleetmanagement.system.repository.DriverRepository;
import com.busfleetmanagement.system.repository.BookingRepository;
import com.busfleetmanagement.system.repository.JourneyRepository;
import com.busfleetmanagement.system.repository.PaymentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
public class DashboardService {

    private final BusRepository busRepository;
    private final DriverRepository driverRepository;
    private final BookingRepository bookingRepository;
    private final JourneyRepository journeyRepository;
    private final PaymentRepository paymentRepository;

    public DashboardService(
            BusRepository busRepository,
            DriverRepository driverRepository,
            BookingRepository bookingRepository,
            JourneyRepository journeyRepository,
            PaymentRepository paymentRepository) {

        this.busRepository = busRepository;
        this.driverRepository = driverRepository;
        this.bookingRepository = bookingRepository;
        this.journeyRepository = journeyRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional(readOnly = true)
    public DashboardSummaryResponse getSummary() {

        // Current date in Sri Lanka
        LocalDate today = LocalDate.now(
                ZoneId.of("Asia/Colombo")
        );

        LocalDateTime monthStart = today
                .withDayOfMonth(1)
                .atStartOfDay();

        LocalDateTime nextMonth = monthStart.plusMonths(1);

        // Fetch existing records
        var buses = busRepository.findAll();
        var drivers = driverRepository.findAll();
        var bookings = bookingRepository.findAll();
        var journeys = journeyRepository.findAll();
        var payments = paymentRepository.findAll();

        // Bus statistics
        long totalBuses = buses.size();

        long activeBuses = buses.stream()
                .filter(bus -> bus.isActive())
                .count();

        // Driver statistics
        long totalDrivers = drivers.size();

        long activeDrivers = drivers.stream()
                .filter(driver -> driver.isDActive())
                .count();

        // Booking statistics
        long totalBookings = bookings.size();

        long activeBookings = bookings.stream()
                .filter(booking ->
                        isActiveBooking(booking))
                .count();

        // Today's journeys
        long todaysJourneys = journeys.stream()
                .filter(journey ->
                        today.equals(journey.getJourneyDate()))
                .count();

        // Payment statistics
        long totalPayments = payments.size();

        long successfulPayments = payments.stream()
                .filter(this::isSuccessful)
                .count();

        long pendingPayments = payments.stream()
                .filter(payment ->
                        hasPaymentStatus(payment, "PENDING"))
                .count();

        long failedPayments = payments.stream()
                .filter(payment ->
                        hasPaymentStatus(payment, "FAIL"))
                .count();

        // Monthly revenue from successful payments
        BigDecimal monthlyRevenue = journeys.stream()
                .filter(journey -> journey.getJourneyDate() != null)
                .filter(journey -> {
                    LocalDate journeyDate = journey.getJourneyDate();

                    return !journeyDate.isBefore(
                            monthStart.toLocalDate())
                            && journeyDate.isBefore(
                            nextMonth.toLocalDate());
                })
                .map(journey -> journey.getIncomeAmount())
                .filter(income -> income != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Five most recent bookings
        List<DashboardSummaryResponse.RecentBooking>
                recentBookings = bookings.stream()
                .sorted(
                        Comparator.comparing(
                                Booking::getCreatedAt,
                                Comparator.nullsLast(
                                        Comparator.reverseOrder()
                                )
                        )
                )
                .limit(5)
                .map(this::toRecentBooking)
                .toList();

        // Build response using getters and setters
        DashboardSummaryResponse response =
                new DashboardSummaryResponse();

        response.setTotalBuses(totalBuses);
        response.setActiveBuses(activeBuses);

        response.setTotalDrivers(totalDrivers);
        response.setActiveDrivers(activeDrivers);

        response.setTotalBookings(totalBookings);
        response.setActiveBookings(activeBookings);

        response.setTodaysJourneys(todaysJourneys);

        response.setTotalPayments(totalPayments);
        response.setSuccessfulPayments(successfulPayments);
        response.setPendingPayments(pendingPayments);
        response.setFailedPayments(failedPayments);

        response.setMonthlyRevenue(monthlyRevenue);
        response.setRecentBookings(recentBookings);

        return response;
    }

    private boolean isActiveBooking(Booking booking) {

        if (booking.getStatus() == null) {
            return false;
        }

        return switch (booking.getStatus()) {
            case PENDING, PAYMENT_PENDING, CONFIRMED -> true;
            case COMPLETED, REJECTED, CANCELLED -> false;
        };
    }

    private boolean isSuccessful(Payment payment) {

        return payment.getPaymentStatus() == PaymentStatus.SUCCESS;
    }

    private boolean hasPaymentStatus(
            Payment payment, String keyword) {

        return payment.getPaymentStatus() != null
                && payment.getPaymentStatus()
                .name()
                .toUpperCase(Locale.ROOT)
                .contains(keyword);
    }

    private DashboardSummaryResponse.RecentBooking
    toRecentBooking(Booking booking) {

        DashboardSummaryResponse.RecentBooking recent =
                new DashboardSummaryResponse.RecentBooking();

        recent.setBookingID(booking.getBookingID());

        recent.setCustomerName(
                booking.getCustomerFirstName() + " "
                        + booking.getCustomerLastName()
        );

        recent.setStartLocation(booking.getStartLocation());
        recent.setDestination(booking.getDestination());

        recent.setStatus(
                booking.getStatus() != null
                        ? booking.getStatus().name()
                        : "UNKNOWN"
        );

        recent.setBusRegistrationNo(
                booking.getBus() != null
                        ? booking.getBus().getBusRegistrationNo()
                        : null
        );

        return recent;
    }
}
