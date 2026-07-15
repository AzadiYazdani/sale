package com.haraji.common.exception;

import com.haraji.common.constant.EntityType;
import com.haraji.common.constant.ErrorCode;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException(EntityType entityType) {

        super(
                ErrorCode.ENTITY_NOT_FOUND,
                HttpStatus.NOT_FOUND,
                "entity.nothing.found",
                entityType
        );
    }

    public EntityNotFoundException(EntityType entityType, Object id) {

        super(
                ErrorCode.ENTITY_NOT_FOUND,
                HttpStatus.NOT_FOUND,
                "entity.not.found",
                entityType,
                id
        );
    }

}
