package com.charity.intertask.controller;

import com.charity.intertask.dto.FinancialReportDto;
import com.charity.intertask.dto.FundraisingEventDto;
import com.charity.intertask.model.FundraisingEvent;
import com.charity.intertask.service.IFundraisingEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FundraisingEventController {

    private final IFundraisingEventService eventService;

    @PostMapping("/events")
    public ResponseEntity<FundraisingEventDto> createEvent(@Valid @RequestBody FundraisingEventDto eventDto) {
        FundraisingEvent createdEvent = eventService.createEvent(eventDto);

        FundraisingEventDto responseDto = FundraisingEventDto.builder()
                .id(createdEvent.getId())
                .name(createdEvent.getName())
                .currency(createdEvent.getCurrency())
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/financial-report")
    public ResponseEntity<FinancialReportDto> getFinancialReport() {
        FinancialReportDto report = eventService.generateFinancialReport();
        return ResponseEntity.ok(report);
    }
}