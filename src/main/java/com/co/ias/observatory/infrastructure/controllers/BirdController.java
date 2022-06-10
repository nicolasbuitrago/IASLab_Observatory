package com.co.ias.observatory.infrastructure.controllers;

import com.co.ias.observatory.birds.application.ports.input.CreateBirdUseCase;
import com.co.ias.observatory.birds.application.ports.input.DeleteBirdUseCase;
import com.co.ias.observatory.birds.application.ports.input.QueryBirdByIdUseCase;
import com.co.ias.observatory.birds.application.ports.input.UpdateBirdUseCase;
import com.co.ias.observatory.infrastructure.logSystem.LogStatus;
import com.co.ias.observatory.infrastructure.logSystem.Logger;
import com.co.ias.observatory.infrastructure.models.ApplicationError;
import com.co.ias.observatory.infrastructure.models.BirdDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class BirdController {

    private final CreateBirdUseCase createBirdUseCase;
    private final QueryBirdByIdUseCase queryBirdByIdUseCase;
    private final UpdateBirdUseCase updateBirdUseCase;
    private final DeleteBirdUseCase deleteBirdUseCase;

    public BirdController(CreateBirdUseCase createBirdUseCase, QueryBirdByIdUseCase queryBirdByIdUseCase, UpdateBirdUseCase updateBirdUseCase, DeleteBirdUseCase deleteBirdUseCase) {
        this.createBirdUseCase = createBirdUseCase;
        this.queryBirdByIdUseCase = queryBirdByIdUseCase;
        this.updateBirdUseCase = updateBirdUseCase;
        this.deleteBirdUseCase = deleteBirdUseCase;
    }

    @RequestMapping(value = "birds", method = RequestMethod.POST)
    public ResponseEntity<?> createBird(@RequestBody BirdDTO birdDTO) {
        try {
            System.out.println(birdDTO);
            BirdDTO birdDTOOutput = createBirdUseCase.execute(birdDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(birdDTOOutput);
        } catch (NullPointerException | IllegalArgumentException e) {
            ApplicationError applicationError = new ApplicationError(
                    "InputDataValidationError",
                    "Bad Input data.",
                    Map.of("error", e.getMessage())
            );
            Logger.log(LogStatus.ERROR, "Error Create Bird: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(applicationError);
        } catch (Exception e) {
            ApplicationError applicationError = new ApplicationError(
                    "SystemError",
                    "Try later.",
                    Map.of()
            );
            Logger.log(LogStatus.ERROR, "Error Create Bird: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(applicationError);
        }
    }

    @RequestMapping(value = "/birds/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getBirdById(@PathVariable("id") Long id) {
        try {
            Optional<BirdDTO> birdDTO = queryBirdByIdUseCase.execute(id);
            if (birdDTO.isPresent()) {
                return ResponseEntity.ok(birdDTO.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found bird with this id");
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            ApplicationError applicationError = new ApplicationError(
                    "InputDataValidationError",
                    "Bad input data",
                    Map.of("error", e.getMessage())
            );
            Logger.log(LogStatus.ERROR, "Error get Bird by id: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(applicationError);
        } catch (Exception e) {
            ApplicationError applicationError = new ApplicationError(
                    "SystemError",
                    "Try later",
                    Map.of()
            );
            Logger.log(LogStatus.ERROR, "Error get Bird by id: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(applicationError);
        }
    }

    @RequestMapping(value = "/birds", method = RequestMethod.PUT)
    public ResponseEntity<?> updateBird(@RequestBody BirdDTO birdDTO) {
        try {
            BirdDTO birdDTOOutput = updateBirdUseCase.execute(birdDTO);
            return ResponseEntity.ok(birdDTOOutput);
        } catch (NullPointerException | IllegalArgumentException e) {
            ApplicationError applicationError = new ApplicationError(
                    "InputDataValidationError",
                    "Bad input data",
                    Map.of("error", e.getMessage())
            );
            Logger.log(LogStatus.ERROR, "Error update Bird: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(applicationError);
        } catch (Exception e) {
            ApplicationError applicationError = new ApplicationError(
                    "SystemError",
                    "Try later",
                    Map.of()
            );
            Logger.log(LogStatus.ERROR, "Error update Bird: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(applicationError);
        }
    }

    @RequestMapping(value="/birds/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBird(@PathVariable("id") Long id) {
        try {
            Boolean result = deleteBirdUseCase.execute(id);
            if (result) {
                return ResponseEntity.ok("Deleted bird");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bird can not be deleted");
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            ApplicationError applicationError = new ApplicationError(
                    "InputDataValidationError",
                    "Bad input data",
                    Map.of("error", e.getMessage())
            );
            Logger.log(LogStatus.ERROR, "Error delete Bird: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(applicationError);
        } catch (Exception e) {
            ApplicationError applicationError = new ApplicationError(
                    "SystemError",
                    "Try later",
                    Map.of()
            );
            Logger.log(LogStatus.ERROR, "Error delete Bird: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(applicationError);
        }
    }
}
