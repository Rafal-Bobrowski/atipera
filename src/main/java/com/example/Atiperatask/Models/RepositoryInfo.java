package com.example.Atiperatask.Models;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
public class RepositoryInfo implements Serializable {
    private String name;
    private Owner owner;
    private boolean fork;
    private List<BranchInfo> branchInfos;

    public RepositoryInfo setName(String name) {
        this.name = name;
        return this;
    }

    public RepositoryInfo setOwner(Owner owner) {
        this.owner = owner;
        return this;
    }

    public RepositoryInfo setFork(boolean fork) {
        this.fork = fork;
        return this;
    }

    public RepositoryInfo setBranchInfos(List<BranchInfo> branchInfos) {
        this.branchInfos = branchInfos;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepositoryInfo that = (RepositoryInfo) o;
        return fork == that.fork && Objects.equals(name, that.name) && Objects.equals(owner, that.owner) && Objects.equals(branchInfos, that.branchInfos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, fork, branchInfos);
    }
}
