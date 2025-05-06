package com.backendStudent.demo;

import com.backendStudent.demo.Service.EtudiantService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        EtudiantTable fr = new EtudiantTable();
        fr.setVisible(true);
        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
        if (run.isRunning()) {
            fr.refreshTable();
        }


    }

}
