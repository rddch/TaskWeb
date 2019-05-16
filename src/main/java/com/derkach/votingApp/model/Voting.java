package com.derkach.votingApp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "voting")
public class Voting {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "THEME")
    private String theme;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Vote> options = new ArrayList<>();

    @Column(name = "STATUS")
    @NotNull
    private Boolean status;

    public Voting() {
    }

    public Voting(String theme, Boolean status, List<Vote> options) {
        this.theme = theme;
        this.options = options;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<Vote> getOptions() {
        return options;
    }

    public void setOptions(List<Vote> options) {
        this.options = options;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


}
