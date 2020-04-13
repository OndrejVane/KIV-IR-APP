package cz.vaneo.kiv.ir.InformationRetrieval.controller;


import cz.vaneo.kiv.ir.InformationRetrieval.model.service.QueryRequest;
import cz.vaneo.kiv.ir.InformationRetrieval.model.service.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Controller {

    Logger LOGGER = LoggerFactory.getLogger(Controller.class);


    @PostMapping("/")
    public QueryResponse index(@RequestBody QueryRequest queryRequest) {

        LOGGER.info("INFO");
        LOGGER.warn("WARN");
        LOGGER.error("ERROR");
        return new QueryResponse("Hello here is spring query: " + queryRequest.getQuery());
    }
}

