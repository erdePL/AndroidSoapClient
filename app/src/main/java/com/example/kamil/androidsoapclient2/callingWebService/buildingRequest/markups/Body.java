package com.example.kamil.androidsoapclient2.callingWebService.buildingRequest.markups;

public class Body{
    private ServiceMethod serviceMethod;
    public Body(){
        this.serviceMethod = new ServiceMethod();
    }
    public ServiceMethod getServiceMethod(){
        return serviceMethod;
    }
}