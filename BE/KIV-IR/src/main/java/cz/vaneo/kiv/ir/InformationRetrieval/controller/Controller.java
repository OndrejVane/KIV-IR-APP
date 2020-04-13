package cz.vaneo.kiv.ir.InformationRetrieval.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Controller {

    Logger LOGGER = LoggerFactory.getLogger(Controller.class);


    @PostMapping("/")
    public String index() {

        LOGGER.info("INFO");
        LOGGER.warn("WARN");
        LOGGER.error("ERROR");
        return "test backend";
    }
}

