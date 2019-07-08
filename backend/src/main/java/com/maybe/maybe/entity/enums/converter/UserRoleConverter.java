package com.maybe.maybe.entity.enums.converter;

import com.maybe.maybe.entity.enums.UserRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UserRoleConverter
        extends EnumDBConverter<UserRole>
        implements AttributeConverter<UserRole, Integer> {

    public UserRoleConverter() {
        super(UserRole.class);
    }

    public static UserRole getById(Integer id) {
        return getById(UserRole.class, id);
    }
}
