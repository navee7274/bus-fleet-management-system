package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.entity.Driver;
import com.busfleetmanagement.system.repository.BusRepository;
import com.busfleetmanagement.system.repository.DriverRepository;

import java.util.List;
import java.util.Optional;

public class BusService {
    private final BusRepository BusRepository;

    public BusService(BusRepository BusRepository) {
        this.BusRepository = BusRepository;
    }

    //CREAT BUS RECORD
    public Bus creatBusRecord(Bus bus) {
        return BusRepository.save(bus);
    }

    // READ ALL Driver RECORDS
    public List<Bus> getAllBusRecords() {
        return BusRepository.findAll();
    }

    // READ ONLY ONE Bus RECORD
    public Optional<Bus> getBusRecordById(String bRegistrationNo) {
        return BusRepository.findById(bRegistrationNo);
    }

    public Bus updateBus(String bRegistrationNo, Bus bus) {
        Bus existingRecord = BusRepository.findById(bRegistrationNo)
                .orElseThrow(() -> new RuntimeException("Bus record not found"));

        existingRecord.setBusRegistrationNo(bus.getBusRegistrationNo());
        existingRecord.setPurchaseDate(bus.getPurchaseDate());
        existingRecord.setPurchasePrice(bus.getPurchasePrice());
        existingRecord.setCapacity(bus.getCapacity());
        existingRecord.setNotes(bus.getNotes());
        existingRecord.setActive(bus.isActive());

        return BusRepository.save(existingRecord);

    }
}