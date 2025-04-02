package com.ainapapy.aigle.configs;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import java.lang.reflect.InvocationTargetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
    
    @Autowired
    private HikariDataSource dataSource;

    @PreDestroy
    public void cleanUp() {
        // Fermeture propre du pool HikariCP
        if (dataSource != null) {
            System.out.println(">>> Fermeture propre du pool de connexions DataSources...");
            dataSource.close();
        }

        // Fermeture conditionnelle spécifique MySQL
        try {
            // Vérifie si la classe MySQL Cleanup existe dans le classpath
            Class<?> cleanupThreadClass = Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread");
            System.out.println(">>> Fermeture propre du AbandonedConnectionCleanupThread MySQL...");
            cleanupThreadClass.getMethod("uncheckedShutdown").invoke(null);
        } catch (ClassNotFoundException e) {
            System.out.println(">>> Aucun AbandonedConnectionCleanupThread à fermer (non-MySQL)");
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            System.err.println("Erreur lors du nettoyage du AbandonedConnectionCleanupThread : " + e.getMessage());
        }
    }
}
