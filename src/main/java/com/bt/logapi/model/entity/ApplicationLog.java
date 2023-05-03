package com.bt.logapi.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity(name = "log")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApplicationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "log_id", nullable = false)
    @JsonProperty("id")
    private String id;

    @JsonProperty("timestamp")
    private Date timestamp;

    @JsonProperty("description")
    private String description;

    @JsonProperty("applicationId")
//    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "application_id")
    private String applicationId;

}
