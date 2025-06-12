package com.charity.intertask.controller;

import com.charity.intertask.dto.CollectionBoxDto;
import com.charity.intertask.model.CollectionBox;
import com.charity.intertask.service.CollectionBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boxes")
@RequiredArgsConstructor
public class CollectionBoxController {

    private final CollectionBoxService boxService;

    @PostMapping("/register")
    public ResponseEntity<CollectionBoxDto> registerBox() {
        CollectionBox box = boxService.registerBox();

        CollectionBoxDto responseDto = CollectionBoxDto.builder()
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
}