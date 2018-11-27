package com.polestar.service.impl;

import com.polestar.model.Ticket;
import com.polestar.repository.TicketRepository;
import com.polestar.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService {

    private final Integer TICKET_ID = 1;

    private TicketRepository repository;

    @Autowired
    public TicketServiceImpl(TicketRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    private void init(){
        Ticket ticket = new Ticket();
        ticket.setId(TICKET_ID);
        ticket.setTicketNum(4999);

        repository.save(ticket);
    }

    @Override
    public Ticket getTicket() {
        return repository.findOne(TICKET_ID);
    }

    @Override
    public Ticket incrementTicketNumber() {
        Ticket ticket = repository.findOne(TICKET_ID);
        ticket.setTicketNum(ticket.getTicketNum() + 1);
        return ticket;
    }

    @Override
    public Ticket updateTicketNumber(Ticket ticket) {
        Ticket theTicket = repository.findOne(TICKET_ID);
        theTicket.setTicketNum(ticket.getTicketNum());

        return repository.save(theTicket);
    }
}
