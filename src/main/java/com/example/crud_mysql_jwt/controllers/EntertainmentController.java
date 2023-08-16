package com.example.crud_mysql_jwt.controllers;

import com.example.crud_mysql_jwt.dto.EntertainmentDTO;
import com.example.crud_mysql_jwt.dto.EntertainmentResponse;
import com.example.crud_mysql_jwt.service.EntertainmentService;
import com.example.crud_mysql_jwt.utils.AppConst;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/entertainments")
public class EntertainmentController {

    private final EntertainmentService entertainmentService;

    public EntertainmentController(EntertainmentService entertainmentService) {
        this.entertainmentService = entertainmentService;
    }

    // http://localhost:8080/api/entertainments?pageNumber=1&pageSize=10&sortBy=id&sortDir=asc
    @GetMapping
    public ResponseEntity<EntertainmentResponse> getAllEntertainments(
            @RequestParam(value = "pageNo", defaultValue = AppConst.NUMBER_OF_PAGE_DEFAULT, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConst.PAGE_SIZE_DEFAULT, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConst.SORT_BY_DEFAULT_VALUE, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConst.SORT_DIR_DEFAULT_VALUE, required = false)String sortDir
    ) {
        System.out.println(pageSize);
        return new ResponseEntity<>(entertainmentService.getAllEntertainments(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntertainmentDTO> getEntertainmentById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(entertainmentService.getEntertainmentByID(id));
    }

    //@Valid validar los datos antes de ejecutar el metodo

    @PostMapping("/{user_id}")
    public ResponseEntity<EntertainmentDTO> saveEntertainment(@Valid @RequestBody EntertainmentDTO entertainmentDTO, @PathVariable(name = "user_id") long user_id) {
        return new ResponseEntity<>(entertainmentService.createEntertainment(entertainmentDTO, user_id), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntertainmentDTO> updateEntertainment(@Valid @RequestBody EntertainmentDTO entertainmentDTO, @PathVariable(name = "id") long id) {
        EntertainmentDTO entertainmentResponse = entertainmentService.updateEntertainment(entertainmentDTO, id);
        return new ResponseEntity<>(entertainmentResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntertainment(@PathVariable(name = "id") long id) {
        entertainmentService.deleteEntertainment(id);
        return new ResponseEntity<>("Entretenimiento eliminado con exito.", HttpStatus.OK);
    }
}
