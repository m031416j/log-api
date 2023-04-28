package com.bt.logapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "application")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Application implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "application_id", nullable = false)
    private String applicationId;


}
