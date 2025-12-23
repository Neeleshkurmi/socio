package com.genz.socio.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String messge){
        super(messge);
    }
}
