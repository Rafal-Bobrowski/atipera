package com.example.Atiperatask.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchInfo implements Serializable {
    private String name;
    private Commit commit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BranchInfo that = (BranchInfo) o;
        return Objects.equals(name, that.name) && Objects.equals(commit, that.commit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, commit);
    }
}
