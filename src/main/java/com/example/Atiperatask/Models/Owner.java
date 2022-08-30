package com.example.Atiperatask.Models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Owner implements Serializable {
    private String login;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return Objects.equals(login, owner.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
