package com.requesttrackingsystem.service;

import com.requesttrackingsystem.dto.request.TicketCreateRequestDto;
import com.requesttrackingsystem.dto.response.TicketResponseDto;

import java.util.List;

public interface TicketService {
    TicketResponseDto createTicket(TicketCreateRequestDto dto, String username);
    List<TicketResponseDto> getUserTickets(String username);
    List<TicketResponseDto> getAllTicketsByStatus(String status);
    TicketResponseDto respondToTicket(Long requestId, String responseText);
    TicketResponseDto updateStatus(Long requestId, String status);
}
