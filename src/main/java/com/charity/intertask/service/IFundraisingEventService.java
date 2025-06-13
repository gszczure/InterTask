package com.charity.intertask.service;

import com.charity.intertask.dto.FinancialReportDto;
import com.charity.intertask.dto.FundraisingEventDto;
import com.charity.intertask.model.FundraisingEvent;

public interface IFundraisingEventService {

    /**
     * Creates a new fundraising event.
     *
     * @param eventDto Data for the new event
     * @return Created fundraising event
     */
    FundraisingEvent createEvent(FundraisingEventDto eventDto);

    /**
     * Retrieves a fundraising event by its ID.
     *
     * @param id Event ID
     * @return Fundraising event
     * @throws jakarta.persistence.EntityNotFoundException if event not found
     */
    FundraisingEvent getEventById(Long id);

    /**
     * Generates a financial report with all fundraising events and their account balances.
     *
     * @return Financial report DTO
     */
    FinancialReportDto generateFinancialReport();
}