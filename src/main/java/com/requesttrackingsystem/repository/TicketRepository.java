package com.requesttrackingsystem.repository;

import com.requesttrackingsystem.entity.Ticket;
import com.requesttrackingsystem.entity.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUserUsername(String username);
    List<Ticket> findByStatus(TicketStatus ticketStatus);
    List<Ticket> findAllByStatus(TicketStatus ticketStatus);
}
