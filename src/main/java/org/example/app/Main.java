package org.example.app;


import org.example.app.config.HibernateConfig;
import org.example.app.model.Ticket;
import org.example.app.service.ClientCrudService;
import org.example.app.service.PlanetCrudService;
import org.example.app.service.TicketCrudService;
import org.hibernate.PropertyValueException;


public class Main {
    public static void main(String[] args) {

        PlanetCrudService planetCrudService = new PlanetCrudService();
        ClientCrudService clientCrudService = new ClientCrudService();
        TicketCrudService ticketCrudService = new TicketCrudService();
        Ticket ticketFromMarsToVen = Ticket.builder()
                .client(clientCrudService.findClientById(1L))
                .planetFrom(planetCrudService.findPlanetById("MARS"))
                .planetTo(planetCrudService.findPlanetById("VEN"))
                .build();
        ticketCrudService.saveTicket(ticketFromMarsToVen);
        System.out.println("ticketFromMarsToVen = " + ticketFromMarsToVen);
        Ticket ticketWithoutClient = Ticket.builder()
                .planetFrom(planetCrudService.findPlanetById("MARS"))
                .planetTo(planetCrudService.findPlanetById("SAT"))
                .build();
        try {
            ticketCrudService.saveTicket(ticketWithoutClient);
        } catch (PropertyValueException exception) {
            System.err.println(exception.getMessage());
        }
        Ticket ticketWithNotExistedPlanet = Ticket.builder()
                .client(clientCrudService.findClientById(2L))
                .planetFrom(planetCrudService.findPlanetById("NEP"))
                .planetTo(planetCrudService.findPlanetById("SOME_PLANET"))
                .build();
        try {
            ticketCrudService.saveTicket(ticketWithNotExistedPlanet);
        } catch (PropertyValueException exception) {
            System.err.println(exception.getMessage());
        }
        HibernateConfig.getInstance().close();
    }
}

