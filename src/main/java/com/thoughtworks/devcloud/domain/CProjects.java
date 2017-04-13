package com.thoughtworks.devcloud.domain;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "C_PROJECTS")
public class CProjects implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotEmpty
    @Column(name = "PROJECT_NAME", nullable = false)
    private String projectName;

    @NotEmpty
    @Column(name = "CURRENT_SNAPSHOT_ID", nullable = false)
    private Integer currentSnapshotId;

    @NotEmpty
    @Column(name = "PROJECT_UUID", nullable = false)
    private String projectUuid;

//    @Column(name = "CREATED_AT", columnDefinition = "DATETIME")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdAt;

    @Column(name = "PROJECT_NAME_KEY")
    private String projectNameKey;

    @Column(name = "SCM_ADDR")
    private String scmAddr;

    @Column(name = "BRANCH")
    private String branch;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "FILEPATH")
    private String filePath;

    @Column(name = "DEVCLOUD_PROJECT_UUID")
    private String devcloudProjectUuid;

    @Column(name = "REMARK1")
    private String remark1;

    @Column(name = "REMARK2")
    private String remark2;
}
