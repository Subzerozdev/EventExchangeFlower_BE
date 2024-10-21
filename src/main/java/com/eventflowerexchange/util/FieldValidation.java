package com.eventflowerexchange.util;

import jakarta.persistence.EntityNotFoundException;

public class FieldValidation {
    public static void checkObjectExist(Object obj, String className) {
        if (obj == null) {
            throw new EntityNotFoundException(className + " is not existed");
        }
    }
}
