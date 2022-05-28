package com.quikdeliver.service;

import com.quikdeliver.entity.VehicleOwner;

import java.util.List;

public interface VOService {
    //VehicleOwner Read services
    public VehicleOwner getVO(Long id);
    public VehicleOwner getVO(String email);
    public List<VehicleOwner> getVOs();
    public boolean isVOExist(Long id);
    public boolean isVOExist(String email);

    //VehicleOwner create services
    public VehicleOwner saveVO(VehicleOwner driver);

    //VehicleOwner update services
    public void updateVO(VehicleOwner user,Long id);

    //customer delete services
    public void deleteVO(Long id);
}
