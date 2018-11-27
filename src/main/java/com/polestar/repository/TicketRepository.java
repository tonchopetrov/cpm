package com.polestar.repository;

import com.polestar.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {
}
