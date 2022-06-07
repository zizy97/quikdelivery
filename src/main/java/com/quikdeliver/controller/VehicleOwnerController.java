package com.quikdeliver.controller;

import com.quikdeliver.advice.exception.APIError;
import com.quikdeliver.entity.Vehicle;
import com.quikdeliver.entity.VehicleOwner;
import com.quikdeliver.service.VOService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;

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
            return new ResponseEntity<>(voService.getVO(id), HttpStatus.OK);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVO(@PathVariable Long id,Principal principal) {
        VehicleOwner vo = voService.getVO(principal.getName());
        if(voService.isVOExist(id)) {
            if(id == vo.getId()){
                voService.deleteVO(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(
                        new APIError().addCommonError("No Permission for delete"),
                        HttpStatus.FORBIDDEN);
            }
        }else{
            return new ResponseEntity<>(
                    new APIError().addCommonError("ID invalid or not found"),
                    HttpStatus.NOT_FOUND);
        }
    }

    //Add Vehicle
    @PostMapping("/vehicle")
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehicle, Principal principal){
        Vehicle addedVehicle = voService.addVehicleToVO(principal.getName(), vehicle);
        return new ResponseEntity<>(addedVehicle,HttpStatus.CREATED);
    }

    //Get Vehicle
    @GetMapping("/vehicles")
    public ResponseEntity<?> getVehicles(Principal principal){
        List<Vehicle> vehiclesByVO = voService.getVehiclesByVO(principal.getName());
        return new ResponseEntity<>(vehiclesByVO, HttpStatus.OK);
    }

    @PostMapping("/allocation")
    public ResponseEntity<?> addAllocation(@RequestBody AllocationRequest allocationRequest){
        voService.assignAllocation(allocationRequest.getPdrId(),allocationRequest.getVehicleId(),allocationRequest.getDriverId());
        return new ResponseEntity<>("SuccessFully Created",HttpStatus.CREATED);
    }
}

@Getter
class AllocationRequest {
    private Long pdrId;
    private Long driverId;
    private Long vehicleId;
}