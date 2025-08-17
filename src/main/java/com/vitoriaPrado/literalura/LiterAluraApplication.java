package com.vitoriaPrado.literalura;

import com.vitoriaPrado.literalura.menu.Menu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

    private final Menu menu;

    public LiterAluraApplication(Menu menu) {
        this.menu = menu;
    }

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Override
    public void run(String... args) {
        menu.iniciar();
    }
}
