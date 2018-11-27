package com.polestar.service;

import com.polestar.model.Ticket;

public interface TicketService {

    Ticket getTicket();
    Ticket incrementTicketNumber();
    Ticket updateTicketNumber(Ticket ticket);
}
