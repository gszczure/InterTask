package com.charity.intertask.controller;

import com.charity.intertask.dto.CollectionBoxDto;
import com.charity.intertask.dto.MoneyDto;
import com.charity.intertask.model.CollectionBox;
import com.charity.intertask.service.ICollectionBoxService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boxes")
public class CollectionBoxController {

    private final ICollectionBoxService boxService;

    public CollectionBoxController(ICollectionBoxService boxService) {
        this.boxService = boxService;
    }

    /**
     * Dodałem zwwracanie DTO przy rejestracji boxa oraz w endpoincie createEvent oraz w assignBoxToEvent gdzie
     * nie koniecznie trzeba je dawać, ponieważ w treści zadania
     * nie było sprecyzowane co dokładnie powinny zwracać te endpoity.
     * Alternatywnie można zwracać komunikat tekstowy String o pomyślnym zakończeniu operacji.
     */
    @PostMapping("/register")
    public ResponseEntity<CollectionBoxDto> registerBox() {
        CollectionBox box = boxService.registerBox();

        CollectionBoxDto responseDto = new CollectionBoxDto.Builder()
                .id(box.getId())
                .assigned(false)
                .empty(true)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CollectionBoxDto>> getAllBoxes() {
        List<CollectionBoxDto> boxes = boxService.getAllBoxes();
        return ResponseEntity.ok(boxes);
    }

    @PutMapping("/{id}/assign/{eventId}")
    public ResponseEntity<CollectionBoxDto> assignBoxToEvent(
            @PathVariable Long id,
            @PathVariable Long eventId
    ) {
        CollectionBox box = boxService.assignBoxToEvent(id, eventId);

        CollectionBoxDto responseDto = new CollectionBoxDto.Builder()
                .id(box.getId())
                .assigned(true)
                .empty(box.isEmpty())
                .build();

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{id}/money")
    public ResponseEntity<String> addMoneyToBox(
            @PathVariable Long id,
            @Valid @RequestBody MoneyDto moneyDto
    ) {
        boxService.addMoneyToBox(id, moneyDto);
        return ResponseEntity.ok().body(moneyDto.getAmount() + " " +  moneyDto.getCurrency() + " added to box.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBox(@PathVariable Long id) {
        boxService.deleteBox(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<String> withdrawFromBox(@PathVariable Long id) {
        boxService.withdrawFromBox(id);
        return ResponseEntity.ok().body("Transaction successful");
    }
}