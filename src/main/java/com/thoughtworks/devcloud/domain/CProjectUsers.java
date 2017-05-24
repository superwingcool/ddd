package com.thoughtworks.devcloud.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "C_PROJECT_USERS")
public class CProjectUsers implements Serializable{

    private static final long serialVersionUID = -53707770292815625L;

    @Id
    private Integer id;

    @Column(name = "DOMAIN_UUID")
    private String tenantId;

    @Column(name = "PROJECT_UUID")
    private String projectUuid;
}
