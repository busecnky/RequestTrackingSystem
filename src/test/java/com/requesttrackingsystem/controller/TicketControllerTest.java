package com.requesttrackingsystem.controller;

import com.requesttrackingsystem.dto.request.TicketCreateRequestDto;
import com.requesttrackingsystem.dto.response.TicketResponseDto;
import com.requesttrackingsystem.entity.enums.TicketCategory;
import com.requesttrackingsystem.service.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketControllerTest {

    @InjectMocks
    private TicketController ticketController;

    @Mock
    private TicketService ticketService;

    @Mock
    private UserDetails userDetails;

    @Test
    void testCreateTicket() {
        TicketCreateRequestDto requestDto = new TicketCreateRequestDto("Title", "Desc", TicketCategory.REQUEST);
        TicketResponseDto responseDto = new TicketResponseDto();

        when(userDetails.getUsername()).thenReturn("testuser");
        when(ticketService.createTicket(requestDto, "testuser")).thenReturn(responseDto);

        ResponseEntity<TicketResponseDto> response = ticketController.createTicket(requestDto, userDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        verify(ticketService).createTicket(requestDto, "testuser");
    }

    @Test
    void testGetMyTickets() {
        List<TicketResponseDto> tickets = List.of(new TicketResponseDto());
        when(userDetails.getUsername()).thenReturn("testuser");
        when(ticketService.getUserTickets("testuser")).thenReturn(tickets);

        ResponseEntity<List<TicketResponseDto>> response = ticketController.getMyTickets(userDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tickets, response.getBody());
    }

    @Test
    void testGetAllTicketsByStatus() {
        String status = "OPEN";
        List<TicketResponseDto> tickets = List.of(new TicketResponseDto());
        when(ticketService.getAllTicketsByStatus(status)).thenReturn(tickets);

        ResponseEntity<List<TicketResponseDto>> response = ticketController.getAllTicketsByStatus(status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tickets, response.getBody());
    }

    @Test
    void testRespondToTicket() {
        Long ticketId = 1L;
        String responseText = "Thanks for reaching out.";
        TicketResponseDto responseDto = new TicketResponseDto();

        when(ticketService.respondToTicket(ticketId, responseText)).thenReturn(responseDto);

        ResponseEntity<TicketResponseDto> response = ticketController.respondToTicket(ticketId, responseText);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testUpdateStatus() {
        Long ticketId = 1L;
        String status = "CLOSED";
        TicketResponseDto responseDto = new TicketResponseDto();

        when(ticketService.updateStatus(ticketId, status)).thenReturn(responseDto);

        ResponseEntity<TicketResponseDto> response = ticketController.updateStatus(ticketId, status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }
}

