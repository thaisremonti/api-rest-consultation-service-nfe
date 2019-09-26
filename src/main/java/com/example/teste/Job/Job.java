package com.example.teste.Job;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.teste.Controller.EstadoController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Job {

    @Autowired
    private EstadoController estado;

    private static final Logger log = LoggerFactory.getLogger(Job.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 300000)
    public void reportCurrentTime() {
        estado.teste();
        log.info("Job  Historico", dateFormat.format(new Date()));
    }
}