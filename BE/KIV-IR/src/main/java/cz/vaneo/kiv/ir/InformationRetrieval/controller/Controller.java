package cz.vaneo.kiv.ir.InformationRetrieval.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Controller {

    private static final String FILE_NAME = "articles.json";
    Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    /**
     * Load all articles from file and store to article manager
     *
     * @return message with number of successfully added articles
     */
    @GetMapping("/init")
    public String init() {
        return "init";
    }


}

