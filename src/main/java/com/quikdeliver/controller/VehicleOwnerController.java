package com.quikdeliver.controller;

import com.quikdeliver.advice.exception.APIError;
import com.quikdeliver.entity.VehicleOwner;
import com.quikdeliver.service.VOService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("api/vehicle-owner")
@RequiredArgsConstructor
@RolesAllowed("ROLE_VO")
public class VehicleOwnerController {

    private final VOService voService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllVOs() {
        return new ResponseEntity<>(voService.getVOs(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVOById(@PathVariable Long id) {
        if(voService.isVOExist(id)) {
            return new ResponseEntity<>(voService.isVOExist(id), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(
                    new APIError().addCommonError("ID invalid or not found"),HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/add")
//    public ResponseEntity<?> addVO(@RequestBody VehicleOwner vo) {
//        if(!voService.isVOExist(vo.getEmail())) {
//            return new ResponseEntity<>(voService.saveVO(vo), HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>(
//                    new APIError()
//                            .addCommonError("VehicleOwner Already registered to the system"),
//                    HttpStatus.CONFLICT);
//        }
//    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateVO(@RequestBody VehicleOwner vo, @PathVariable Long id) {
        if(vo.getId() == id) {
            if(voService.isVOExist(id)) {
                voService.updateVO(vo,id);
                return new ResponseEntity<>("Success", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(
                        new APIError().addCommonError("ID invalid or not found"),
                        HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(
                    new APIError().addCommonError("ID Cannot be change"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteVO(@PathVariable Long id) {
        if(voService.isVOExist(id)) {
            voService.deleteVO(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(
                    new APIError().addCommonError("ID invalid or not found"),
                    HttpStatus.NOT_FOUND);
        }
    }
}
