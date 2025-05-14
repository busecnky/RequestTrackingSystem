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

        Ticket requestedTicket = ticketConverter.toEntity(ticketCreateRequestDto, user);
        Ticket savedTicket = ticketRepository.save(requestedTicket);

        return ticketConverter.toDto(savedTicket);
    }

    @Override
    public List<TicketResponseDto> getUserTickets(String username) {
        return ticketRepository.findByUserUsername(username)
                .stream()
                .map(ticketConverter::toDto)
                .toList();
    }

    @Override
    public List<TicketResponseDto> getAllTickets(TicketStatus ticketStatus) {
        List<Ticket> requests = (ticketStatus != null)
                ? ticketRepository.findByStatus(ticketStatus)
                : ticketRepository.findAll();

        return requests.stream()
                .map(ticketConverter::toDto)
                .toList();    }

    @Override
    public TicketResponseDto respondToTicket(Long ticketId, String response) {
        Ticket request = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setResponse(response);
        request.setStatus(TicketStatus.RESPONDED);
        return ticketConverter.toDto(ticketRepository.save(request));
    }

    @Override
    public TicketResponseDto updateStatus(Long ticketId, TicketStatus ticketStatus) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(ticketStatus);
        return ticketConverter.toDto(ticketRepository.save(ticket));
    }

}
