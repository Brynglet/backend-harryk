package se.atg.service.harrykart.java.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/java/api")
public class HarryKartController {

    @Autowired
    private HarryKartService harryKartService;

    @PostMapping(path = "/play", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MappingJacksonValue> playHarryKart(@RequestBody String inputStr) {

        var harryResp = harryKartService.getInfo(inputStr);

        return ResponseEntity.ok(new MappingJacksonValue(harryResp));
    }

}
