package com.thoughtworks.devcloud.domain;

import lombok.*;
import org.hibernate.annotations.Type;
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
@Table(name = "T_JENKINS_JOB_BUILD_INFO")
public class TJenkinsJobBuildInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotEmpty
    @Column(name = "VIEW_NAME", nullable = false)
    private String viewName;

    @NotEmpty
    @Column(name = "GIT_URL", nullable = false)
    private String gitUrl;

    @Column(name = "JOB_NAME")
    private String jobName;

    @Column(name = "BUILD_NUM")
    private String buildNum;

    @Column(name = "BUILD_LANCHER")
    private String buildLancher;

    @Column(name = "IS_SUCCESS", nullable = false, columnDefinition = "BIT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isSuccess;

    @Column(name = "CHECK_START_TIME")
    private String checkStartTime;

    @Column(name = "CHECK_END_TIME")
    private String checkEndTime;

    @Column(name = "CHECK_ELAPSE_TIME")
    private String checkElapseTime;

    @Column(name = "BUILD_URL")
    private String buildUrl;

    @Column(name = "FAILD_RESON", columnDefinition = "TEXT")
    private String faildReson;
    @Column(name = "REMARK1")
    private String remark1;

    @Column(name = "REMARK2")
    private String remark2;

    @Column(name = "SNAPSHOT_ID")
    private Integer snapshotId;

    @Column(name = "IAM_TOKEN", columnDefinition = "TEXT")
    private String iamToken;

    @Column(name = "BIND_ID")
    private String bindId;

    @Column(name = "RULE_INFO", columnDefinition = "LONGTEXT")
    private String ruleInfo;

    @Column(name = "TEMPLATE_ID")
    private String templateId;

    @Column(name = "DOMAIN_UUID")
    private String domainUuid;
}
