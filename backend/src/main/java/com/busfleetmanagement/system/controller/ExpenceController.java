package com.busfleetmanagement.system.controller;

import com.busfleetmanagement.system.entity.ExpenceLog;
import com.busfleetmanagement.system.service.ExpenceLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin
public class  ExpenceController {

    private final ExpenceLogService expenceLogService;

    public ExpenceController(ExpenceLogService expenceLogService) {
        this.expenceLogService = expenceLogService;
    }

    // Get all expenses
    @GetMapping
    public ResponseEntity<List<ExpenceLog>> getAllExpenses() {

        return ResponseEntity.ok(
                expenceLogService.getAllExpences()
        );
    }

    // Get expense by ID
    @GetMapping("/{expenceID}")
    public ResponseEntity<ExpenceLog> getExpenseById(
            @PathVariable int expenceID) {

        return ResponseEntity.ok(expenceLogService.getExpenceById(expenceID)
        );
    }

    // Create expense
    @PostMapping
    public ResponseEntity<ExpenceLog> createExpense(
            @RequestBody ExpenceLog expenceLog) {

        ExpenceLog createdExpense = expenceLogService.createExpence(expenceLog);

        return new ResponseEntity<>(
                createdExpense,
                HttpStatus.CREATED
        );
    }

    // Update expense
    @PutMapping("/{expenceID}")
    public ResponseEntity<ExpenceLog> updateExpence(
            @PathVariable int expenceID,
            @RequestBody ExpenceLog expenceLog) {

        ExpenceLog updatedExpence = expenceLogService.updateExpence(
                        expenceID,
                        expenceLog
                );

        return ResponseEntity.ok(updatedExpence);
    }

    // Delete expense
    @DeleteMapping("/{expenceID}")
    public ResponseEntity<Void> deleteExpence(
            @PathVariable int expenceID) {

        expenceLogService.deleteExpence(expenceID);

        return ResponseEntity.noContent().build();
    }
}
