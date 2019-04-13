package com.example.kamil.androidsoapclient2.serviceOperating.requestBuilder.markups;

public class Body{
    private ServiceMethod serviceMethod;
    public Body() {
        this.serviceMethod = new ServiceMethod();
    }
    public ServiceMethod getServiceMethod() {
        return serviceMethod;
    }
}
