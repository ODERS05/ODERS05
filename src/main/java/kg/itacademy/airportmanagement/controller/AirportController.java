package kg.itacademy.airportmanagement.controller;

import kg.itacademy.airportmanagement.model.AirportModel;
import kg.itacademy.airportmanagement.service.AirportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/airport")
@Slf4j
public class AirportController {

    @Autowired
    AirportService airportService;

    @PostMapping(path = "/create")
    public ResponseEntity<AirportModel> createNewAirport(@RequestBody AirportModel airportModel) {
        AirportModel result = airportService.create(airportModel);
        //Если при создании поле id что то есть, значит мы создали успешно и можем вернуть 201
        if (result.getId() != null) {
            //вернется со статус 201
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            //вернется со статус 500 если поле id пустое
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Boolean> updateAirport(@RequestBody AirportModel airportModel) {
        try {
            airportService.update(airportModel);
            return ResponseEntity.ok(true);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<AirportModel> getAirportById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(airportService.getById(id));
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping(path = "/getByAirportName")
    public ResponseEntity<List<AirportModel>> getByAirportName(@RequestParam("airportName") String airportName) {
        try {
            return ResponseEntity.ok(airportService.getAllByAirportName(airportName));
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(airportService.deleteById(id));
        } catch (RuntimeException ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}

