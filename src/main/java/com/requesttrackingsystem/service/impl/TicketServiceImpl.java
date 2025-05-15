package com.requesttrackingsystem.service.impl;

import com.requesttrackingsystem.dto.request.TicketCreateRequestDto;
import com.requesttrackingsystem.dto.response.TicketResponseDto;
import com.requesttrackingsystem.entity.Ticket;
import com.requesttrackingsystem.entity.User;
import com.requesttrackingsystem.entity.enums.TicketStatus;
import com.requesttrackingsystem.repository.TicketRepository;
import com.requesttrackingsystem.service.TicketService;
import com.requesttrackingsystem.service.UserService;
import com.requesttrackingsystem.utils.TicketConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketConverter ticketConverter;
    private final TicketRepository ticketRepository;
    private final UserService userService;

    public TicketServiceImpl(TicketConverter ticketConverter, TicketRepository ticketRepository, UserService userService) {
        this.ticketConverter = ticketConverter;
        this.ticketRepository = ticketRepository;
        this.userService = userService;
    }

    @Override
    public TicketResponseDto createTicket(TicketCreateRequestDto ticketCreateRequestDto, String username) {
        User user = userService.findByUsername(username);

        Ticket requestedTicket = ticketConverter.toTicketEntity(ticketCreateRequestDto, user);
        Ticket savedTicket = ticketRepository.save(requestedTicket);

        return ticketConverter.toTicketResponseDto(savedTicket);
    }

    @Override
    public List<TicketResponseDto> getUserTickets(String username) {
        return ticketRepository.findByUserUsername(username)
                .stream()
                .map(ticketConverter::toTicketResponseDto)
                .toList();
    }

    @Override
    public List<TicketResponseDto> getAllTicketsByStatus(String ticketStatus) {
        List<Ticket> tickets ;
        if (ticketStatus == null || ticketStatus.trim().isEmpty()) {
            tickets = ticketRepository.findAll();
        } else {
            try {
                TicketStatus enumStatus = TicketStatus.valueOf(ticketStatus.toUpperCase());
                tickets = ticketRepository.findByStatus(enumStatus);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status value: " + ticketStatus);
            }
        }
        return tickets.stream()
                .map(ticketConverter::toTicketResponseDto)
                .toList();
    }

    @Override
    public TicketResponseDto respondToTicket(Long ticketId, String responseText) {
        Ticket request = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setResponse(responseText);
        return ticketConverter.toTicketResponseDto(ticketRepository.save(request));
    }

    @Override
    public TicketResponseDto updateStatus(Long ticketId, String ticketStatus) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(TicketStatus.valueOf(ticketStatus));
        return ticketConverter.toTicketResponseDto(ticketRepository.save(ticket));
    }

}
