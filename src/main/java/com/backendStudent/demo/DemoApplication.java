package com.backendStudent.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        FrontOffice fr = new FrontOffice();
        fr.setVisible(true);
        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
        if (run.isRunning()) {
            fr.refreshTable();
        }


    }

}
