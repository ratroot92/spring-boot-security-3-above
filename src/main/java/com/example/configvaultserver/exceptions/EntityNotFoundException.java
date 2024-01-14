package com.example.configvaultserver.exceptions;

import com.example.configvaultserver.enums.EntityEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityNotFoundException extends RuntimeException {
    String entity;

    public EntityNotFoundException(EntityEnum entity) {
        super(entity.name().toLowerCase() + " not found.");
        this.entity = entity.name();
    }
}
