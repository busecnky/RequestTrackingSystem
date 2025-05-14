package com.requesttrackingsystem.dto.request;

import com.requesttrackingsystem.entity.enums.TicketCategory;
import jakarta.validation.constraints.NotBlank;

public record TicketCreateRequestDto(@NotBlank(message = "Title is required")
                                     String title,
                                     @NotBlank(message = "Description is required")
                                     String description,
                                     @NotBlank(message = "Category is required")
                                     TicketCategory ticketCategory) {
}
