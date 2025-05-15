package com.requesttrackingsystem.service.impl;

import com.requesttrackingsystem.dto.request.TicketCreateRequestDto;
import com.requesttrackingsystem.dto.response.TicketResponseDto;
import com.requesttrackingsystem.entity.Ticket;
import com.requesttrackingsystem.entity.User;
import com.requesttrackingsystem.entity.enums.TicketCategory;
import com.requesttrackingsystem.entity.enums.TicketStatus;
import com.requesttrackingsystem.repository.TicketRepository;
import com.requesttrackingsystem.service.UserService;
import com.requesttrackingsystem.utils.TicketConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketConverter ticketConverter;

    @Mock
    private UserService userService;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    void testCreateTicket() {
        TicketCreateRequestDto requestDto = new TicketCreateRequestDto("title", "desc", TicketCategory.SERVICE);
        User user = new User();
        Ticket ticket = new Ticket();
        Ticket savedTicket = new Ticket();
        TicketResponseDto responseDto = new TicketResponseDto();

        when(userService.findByUsername("john")).thenReturn(user);
        when(ticketConverter.toTicketEntity(requestDto, user)).thenReturn(ticket);
        when(ticketRepository.save(ticket)).thenReturn(savedTicket);
        when(ticketConverter.toTicketResponseDto(savedTicket)).thenReturn(responseDto);

        TicketResponseDto result = ticketService.createTicket(requestDto, "john");

        assertEquals(responseDto, result);
        verify(ticketRepository).save(ticket);
    }

    @Test
    void testGetUserTickets() {
        String username = "john";
        List<Ticket> tickets = List.of(new Ticket());
        TicketResponseDto dto = new TicketResponseDto();

        when(ticketRepository.findByUserUsername(username)).thenReturn(tickets);
        when(ticketConverter.toTicketResponseDto(any())).thenReturn(dto);

        List<TicketResponseDto> result = ticketService.getUserTickets(username);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void testGetAllTicketsByStatus_WithNull() {
        List<Ticket> tickets = List.of(new Ticket());
        when(ticketRepository.findAll()).thenReturn(tickets);
        when(ticketConverter.toTicketResponseDto(any())).thenReturn(new TicketResponseDto());

        List<TicketResponseDto> result = ticketService.getAllTicketsByStatus(null);

        assertEquals(1, result.size());
    }

    @Test
    void testGetAllTicketsByStatus_InvalidStatus() {
        assertThrows(IllegalArgumentException.class, () ->
                ticketService.getAllTicketsByStatus("INVALID")
        );
    }

    @Test
    void testRespondToTicket() {
        Long id = 1L;
        String responseText = "Here is your response";
        Ticket ticket = new Ticket();
        ticket.setId(id);
        Ticket saved = new Ticket();
        TicketResponseDto dto = new TicketResponseDto();

        when(ticketRepository.findById(id)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(ticket)).thenReturn(saved);
        when(ticketConverter.toTicketResponseDto(saved)).thenReturn(dto);

        TicketResponseDto result = ticketService.respondToTicket(id, responseText);

        assertEquals(dto, result);
        verify(ticketRepository).save(ticket);
        assertEquals(responseText, ticket.getResponse());
    }

    @Test
    void testUpdateStatus() {
        Long id = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(id);
        Ticket saved = new Ticket();
        TicketResponseDto dto = new TicketResponseDto();

        when(ticketRepository.findById(id)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(ticket)).thenReturn(saved);
        when(ticketConverter.toTicketResponseDto(saved)).thenReturn(dto);

        TicketResponseDto result = ticketService.updateStatus(id, "OPEN");

        assertEquals(dto, result);
        assertEquals(TicketStatus.OPEN, ticket.getStatus());
    }

    @Test
    void testUpdateStatus_InvalidTicket_ThrowsException() {
        when(ticketRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                ticketService.updateStatus(99L, "OPEN")
        );
    }
}
