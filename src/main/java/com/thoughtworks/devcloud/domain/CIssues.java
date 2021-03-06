package com.thoughtworks.devcloud.domain;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "C_ISSUES")
public class CIssues implements Serializable {

    private static final long serialVersionUID = 5273260735557867762L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "BIGINT")
    private Integer id;

    @JoinColumn(name = "RULE_ID", referencedColumnName = "id")
    @ManyToOne
    private CRules cRules;

    @Column(name = "SEVERITY", columnDefinition = "BIT")
    @Type(type = "org.hibernate.type.IntegerType")
    private Integer severity;

    @Column(name = "ISSUE_KEY", nullable = false)
    private String issueKey;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "MESSAGE_HASH")
    private String messageHash;

    @Column(name = "LINE")
    private Integer line;

    @Column(name = "LINE_HASH")
    private String lineHash;

    @Column(name = "STATUS", columnDefinition = "BIT")
    @Type(type = "org.hibernate.type.IntegerType")
    private Integer status;

    @Column(name = "MANUAL_STATUS", columnDefinition = "BIT")
    @Type(type = "org.hibernate.type.IntegerType")
    private Integer manualStatus;

    @Column(name = "TAGS")
    private String tags;

    @Column(name = "ISSUE_UUID")
    private String issueUuid;

    @Column(name = "FILE_UUID")
    private String fileUuid;

    @JoinColumn(name = "SNAPSHOT_ID", referencedColumnName = "id")
    @ManyToOne()
    private CSnapshots cSnapshots;

    @JoinColumn(name = "PROJECT_UUID", referencedColumnName = "PROJECT_UUID")
    @ManyToOne()
    private CProjects cProjects;

    @Column(name = "CREATED_AT", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "FIXED_AT", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fixedAt;

    @Column(name = "REMARK1")
    private String remark1;

    @Column(name = "REMARK2")
    private String remark2;
}
