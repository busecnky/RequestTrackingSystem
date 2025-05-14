package com.requesttrackingsystem.controller;

import com.requesttrackingsystem.dto.request.TicketCreateRequestDto;
import com.requesttrackingsystem.dto.response.TicketResponseDto;
import com.requesttrackingsystem.entity.enums.TicketStatus;
import com.requesttrackingsystem.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TicketResponseDto> createTicket(@Valid @RequestBody TicketCreateRequestDto dto,
                                                           @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ticketService.createTicket(dto, userDetails.getUsername()));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<TicketResponseDto>> getMyTickets(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ticketService.getUserTickets(userDetails.getUsername()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TicketResponseDto>> getAllTickets(@RequestParam(required = false) TicketStatus ticketStatus) {
        return ResponseEntity.ok(ticketService.getAllTickets(ticketStatus));
    }

    @PostMapping("/{id}/respond")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TicketResponseDto> respondToTicket(@PathVariable Long id,
                                                       @RequestBody String response) {
        return ResponseEntity.ok(ticketService.respondToTicket(id, response));
    }

    @PostMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TicketResponseDto> updateStatus(@PathVariable Long id,
                                                   @RequestParam TicketStatus ticketStatus) {
        return ResponseEntity.ok(ticketService.updateStatus(id, ticketStatus));
    }
}
