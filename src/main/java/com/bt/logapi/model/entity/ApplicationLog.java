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
import java.time.LocalDateTime;

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
    private Integer id;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("description")
    private String description;

    @JsonProperty("applicationId")
    @Column(name = "application_id")
    private String applicationId;

}
