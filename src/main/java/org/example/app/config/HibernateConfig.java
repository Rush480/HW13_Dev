package org.example.app.config;

import lombok.Getter;
import org.example.app.model.Planet;
import org.example.app.model.Client;
import org.example.app.model.Ticket;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Getter
public class HibernateConfig {
    private static final HibernateConfig INSTANCE;
    private static final String DB_URL = "jdbc:postgresql://localhost:5438/postgres";
    private static final String DB_USER = "Rush";
    private static final String DB_PASSWORD = "rush";
    private final SessionFactory sessionFactory;


    static {
        Flyway flyway = Flyway.configure()
                .dataSource(DB_URL, DB_USER, DB_PASSWORD)
                .locations("db/migrations")
                .load();
        flyway.migrate();
        INSTANCE = new HibernateConfig();
    }

    private HibernateConfig() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(Planet.class)
                .addAnnotatedClass(Ticket.class)
                .buildSessionFactory();
    }

    public static HibernateConfig getInstance() {
        return INSTANCE;
    }

    public void close() {
        sessionFactory.close();
    }
}
