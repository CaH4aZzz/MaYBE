package com.maybe.maybe.entity;
import com.maybe.maybe.entity.enums.DeskState;
import com.maybe.maybe.entity.enums.converter.DeskStateConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "desk")
public class Desk extends AbstractEntity {
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Convert(converter = DeskStateConverter.class)
    @Column(name = "desk_state_id", nullable = false)
    private DeskState deskState;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeskState getDeskState() {
        return deskState;
    }

    public void setDeskState(DeskState deskState) {
        this.deskState = deskState;
    }
}
