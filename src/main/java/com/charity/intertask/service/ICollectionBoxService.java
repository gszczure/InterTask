package com.charity.intertask.service;

import com.charity.intertask.dto.CollectionBoxDto;
import com.charity.intertask.dto.MoneyDto;
import com.charity.intertask.model.CollectionBox;

import java.util.List;

public interface ICollectionBoxService {

    /**
     * Registers a new collection box.
     *
     * @return Registered collection box
     */
    CollectionBox registerBox();

    /**
     * Lists all collection boxes with basic information.
     *
     * @return List of collection box DTOs
     */
    List<CollectionBoxDto> getAllBoxes();

    /**
     * Retrieves a collection box by its ID.
     *
     * @param id Box ID
     * @return Collection box
     * @throws jakarta.persistence.EntityNotFoundException if box not found
     */
    CollectionBox getBoxById(Long id);

    /**
     * Delete (unregister) a box.
     *
     * @param id Box ID
     */
    void deleteBox(Long id);

    /**
     * Assigns a collection box to a fundraising event.
     *
     * @param boxId Box ID
     * @param eventId Event ID
     * @return Updated collection box
     * @throws IllegalStateException if box is not empty
     * @throws jakarta.persistence.EntityNotFoundException if box or event not found
     */
    CollectionBox assignBoxToEvent(Long boxId, Long eventId);

    /**
     * Adds money to a  box.
     *
     * @param boxId Box ID
     * @param moneyDto Money data to add
     * @throws IllegalStateException if box is not assigned to an event
     * @throws jakarta.persistence.EntityNotFoundException if box not found
     */
    void addMoneyToBox(Long boxId, MoneyDto moneyDto);

    /**
     * Transfers money to the fundraising event's account.
     *
     * @param boxId Box ID
     * @throws IllegalStateException if box is not assigned to an event
     * @throws jakarta.persistence.EntityNotFoundException if box not found
     */
    void withdrawFromBox(Long boxId);
}