package com.requesttrackingsystem.utils;

import com.requesttrackingsystem.dto.request.TicketCreateRequestDto;
import com.requesttrackingsystem.dto.response.TicketResponseDto;
import com.requesttrackingsystem.entity.Ticket;
import com.requesttrackingsystem.entity.User;
import com.requesttrackingsystem.entity.enums.TicketCategory;
import com.requesttrackingsystem.entity.enums.TicketStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TicketConverter {

    public TicketResponseDto toTicketResponseDto(Ticket ticket) {
        TicketResponseDto ticketResponseDto = new TicketResponseDto();
        ticketResponseDto.setId(ticket.getId());
        ticketResponseDto.setTitle(ticket.getTitle());
        ticketResponseDto.setDescription(ticket.getDescription());
        ticketResponseDto.setCategory(ticket.getCategory());
        ticketResponseDto.setStatus(ticket.getStatus());
        ticketResponseDto.setCreatedAt(ticket.getCreatedAt());
        ticketResponseDto.setResponse(ticket.getResponse());
        ticketResponseDto.setUsername(ticket.getUser().getUsername());
        return ticketResponseDto;
    }

    public Ticket toTicketEntity(TicketCreateRequestDto ticketCreateRequestDto, User user) {
        Ticket ticket = new Ticket();
        ticket.setTitle(ticketCreateRequestDto.title());
        ticket.setDescription(ticketCreateRequestDto.description());
        ticket.setCategory(TicketCategory.valueOf(ticketCreateRequestDto.ticketCategory()));
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUser(user);
        return ticket;
    }
}
