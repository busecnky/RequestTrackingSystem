package com.requesttrackingsystem.utils;

import com.requesttrackingsystem.dto.request.TicketCreateRequestDto;
import com.requesttrackingsystem.dto.response.TicketResponseDto;
import com.requesttrackingsystem.entity.Ticket;
import com.requesttrackingsystem.entity.User;
import com.requesttrackingsystem.entity.enums.TicketCategory;
import com.requesttrackingsystem.entity.enums.TicketStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TicketConverterTest {

    private final TicketConverter ticketConverter = new TicketConverter();

    @Test
    void testToTicketEntity() {
        User user = new User();
        user.setUsername("john");

        TicketCreateRequestDto dto = new TicketCreateRequestDto(
                "Test Title",
                "Test Description",
                "PROBLEM"
        );

        Ticket ticket = ticketConverter.toTicketEntity(dto, user);

        assertEquals("Test Title", ticket.getTitle());
        assertEquals("Test Description", ticket.getDescription());
        assertEquals(TicketCategory.PROBLEM, ticket.getCategory());
        assertEquals(TicketStatus.OPEN, ticket.getStatus());
        assertEquals(user, ticket.getUser());
        assertNotNull(ticket.getCreatedAt());
    }

    @Test
    void testToTicketResponseDto() {
        User user = new User();
        user.setUsername("john");

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTitle("Title");
        ticket.setDescription("Desc");
        ticket.setCategory(TicketCategory.SERVICE);
        ticket.setStatus(TicketStatus.CLOSED);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setResponse("Resolved");
        ticket.setUser(user);

        TicketResponseDto dto = ticketConverter.toTicketResponseDto(ticket);

        assertEquals(1L, dto.getId());
        assertEquals("Title", dto.getTitle());
        assertEquals("Desc", dto.getDescription());
        assertEquals(TicketCategory.SERVICE, dto.getCategory());
        assertEquals(TicketStatus.CLOSED, dto.getStatus());
        assertEquals("Resolved", dto.getResponse());
        assertEquals("john", dto.getUsername());
        assertEquals(ticket.getCreatedAt(), dto.getCreatedAt());
    }
}
