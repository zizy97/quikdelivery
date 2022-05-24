package com.quikdeliver.controller;

import com.quikdeliver.advice.exception.APIError;
import com.quikdeliver.entity.VehicleOwner;
import com.quikdeliver.service.VehicleOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vehicle-owner")
public class VehicleOwnerController {
    @Autowired
    private VehicleOwnerService vehicleOwnerService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllVOs() {
        return new ResponseEntity<>(vehicleOwnerService.getAllVO(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVOById(@PathVariable Long id) {
        if(vehicleOwnerService.isVOExists(id)) {
            return new ResponseEntity<>(vehicleOwnerService.getVOById(id), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new APIError().addCommonError("ID invalid or not found"),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVO(@RequestBody VehicleOwner vo) {
        if(!vehicleOwnerService.isVOExists(vo.getNic())) {
            return new ResponseEntity<>(vehicleOwnerService.addVO(vo), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new APIError().addCommonError("VehicleOwner Already registered to the system"),HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateVO(@RequestBody VehicleOwner vo, @PathVariable Long id) {
        if(vo.getId() == id) {
            if(vehicleOwnerService.isVOExists(id)) {
                return new ResponseEntity<>(vehicleOwnerService.updateVO(vo,id), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(new APIError().addCommonError("ID invalid or not found"),HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(new APIError().addCommonError("ID Cannot be change"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteVO(@PathVariable Long id) {
        if(vehicleOwnerService.isVOExists(id)) {
            vehicleOwnerService.deleteVO(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new APIError().addCommonError("ID invalid or not found"),HttpStatus.NOT_FOUND);
        }
    }
}
