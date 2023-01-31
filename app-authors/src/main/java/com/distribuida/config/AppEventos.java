package com.distribuida.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.flywaydb.core.Flyway;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class AppEventos {

    @Inject
    @ConfigProperty(name="quarkus.datasource.jdbc.url")
    String dbUrl;

    @Inject
    @ConfigProperty(name="quarkus.datasource.username")
    String dbUser;

    @Inject
    @ConfigProperty(name="quarkus.datasource.password")
    String dbPassword;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object event){
        System.out.println("***++migrando base de datos");
        var flyway = Flyway.configure()
                .dataSource(dbUrl, dbUser, dbPassword)
                .load();
        flyway.migrate();
    }

}