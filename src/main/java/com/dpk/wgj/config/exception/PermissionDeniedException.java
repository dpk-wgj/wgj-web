package com.dpk.wgj.config.exception;

public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(String message){
        super(message);
    }
}
