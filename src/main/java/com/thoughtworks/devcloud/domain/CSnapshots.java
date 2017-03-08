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
@Table(name = "C_SNAPSHOTS")
public class CSnapshots implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotEmpty
    @Column(name = "PROJECT_ID", nullable = false)
    private Integer projectId;

    @Column(name = "COMMIT_START_TIME", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commitStartTime;

    @Column(name = "COMMIT_END_TIME", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commitEndTime;

    @Column(name = "PROGRESS", nullable = false)
    private String progress;

    @Column(name = "SCM_ADDR")
    private String scmAddr;

    @Column(name = "SCM_BRANCH")
    private String scmBranch;

    @Column(name = "SCM_REVISION")
    private String scmRevision;

    @Column(name = "SCM_KEY")
    private String scmKey;

    @Column(name = "REMARK1")
    private String remark1;

    @Column(name = "REMARK2")
    private String remark2;
}
