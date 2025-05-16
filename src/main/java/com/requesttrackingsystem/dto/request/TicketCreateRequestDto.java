package com.requesttrackingsystem.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TicketCreateRequestDto(@NotBlank(message = "Title is required")
                                     String title,
                                     @NotBlank(message = "Category is required")
                                     String ticketCategory,
                                     @NotBlank(message = "Description is required")
                                     String description ){
}
