package org.example.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;


import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "planet")
public class Planet {
    @Id
    private String id;
    @Column(name = "name")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Invalid planet name")
    private String name;
    @OneToMany(mappedBy = "planetFrom")
    @ToString.Exclude
    private Set<Ticket> ticketsFrom;
    @OneToMany(mappedBy = "planetTo")
    @ToString.Exclude
    private Set<Ticket> ticketsTo;
}
