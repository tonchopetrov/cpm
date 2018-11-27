package com.polestar.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Ticket {

    @Id
    private Integer id = 1;
    private Integer ticketNum;
}
