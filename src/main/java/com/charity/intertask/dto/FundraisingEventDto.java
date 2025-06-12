package com.charity.intertask.dto;

import com.charity.intertask.model.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundraisingEventDto {
    private Long id;

    @NotBlank(message = "Event name is required")
    private String name;

    @NotNull(message = "Currency is required")
    private Currency currency;
}