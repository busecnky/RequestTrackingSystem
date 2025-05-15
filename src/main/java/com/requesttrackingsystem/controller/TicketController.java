package com.requesttrackingsystem.controller;

import com.requesttrackingsystem.dto.request.TicketCreateRequestDto;
import com.requesttrackingsystem.dto.response.TicketResponseDto;
import com.requesttrackingsystem.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@CrossOrigin("*")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TicketResponseDto> createTicket(@Valid @RequestBody
                                                              TicketCreateRequestDto ticketCreateRequestDto,
                                                           @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ticketService.createTicket(ticketCreateRequestDto, userDetails.getUsername()));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<TicketResponseDto>> getMyTickets(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ticketService.getUserTickets(userDetails.getUsername()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TicketResponseDto>> getAllTicketsByStatus(@RequestParam(required = false)
                                                                     String ticketStatus) {
        return ResponseEntity.ok(ticketService.getAllTicketsByStatus(ticketStatus));
    }

    @PostMapping("/{id}/respond")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TicketResponseDto> respondToTicket(@PathVariable Long id,
                                                       @RequestParam String responseText) {
        return ResponseEntity.ok(ticketService.respondToTicket(id, responseText));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TicketResponseDto> updateStatus(@PathVariable Long id,
                                                   @RequestParam String ticketStatus) {
        return ResponseEntity.ok(ticketService.updateStatus(id, ticketStatus));
    }
}
