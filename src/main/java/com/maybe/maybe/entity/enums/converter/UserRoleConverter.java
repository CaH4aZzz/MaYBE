package com.maybe.maybe.entity.enums.converter;

import com.maybe.maybe.entity.enums.UserRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UserRoleConverter implements AttributeConverter<UserRole, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserRole userRole) {
        if (userRole == null) {
            return null;
        }
        return userRole.getId();
    }

    @Override
    public UserRole convertToEntityAttribute(Integer id) {
        return UserRole.getById(id);
    }
}

