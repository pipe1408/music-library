package com.felipe.musiclibraryback.entities.dto;

import jakarta.validation.constraints.NotBlank;

public record GroupDTO(
        @NotBlank
        String name,

        @NotBlank
        String shortName
) {
}
