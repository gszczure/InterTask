package com.charity.intertask.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionBoxDto {
    private Long id;
    private boolean assigned;
    private boolean empty;
}