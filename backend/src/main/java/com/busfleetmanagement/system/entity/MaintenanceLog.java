package com.busfleetmanagement.system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "MaintenanceLog")
@Access(AccessType.FIELD)
public class MaintenanceLog {
    @Id
    @Column()
}
