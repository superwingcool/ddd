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
@Table(name = "T_JENKINS_JOB_INFO")
public class TJenkinsJobInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotEmpty
    @Column(name = "PROJECT_NAME", nullable = false)
    private String projectName;

    @NotEmpty
    @Column(name = "GIT_URL", nullable = false)
    private String gitUrl;

    @Column(name = "PROJECT_ID")
    private String projectId;

    @Column(name = "JOB_NAME")
    private String jobName;

    @Column(name = "VIEW_NAME")
    private String viewName;

    @Column(name = "JOB_TYPE")
    private String jobType;

    @Column(name = "JOB_TRIGGER_TIME", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date jobTriggerTime;

    @Column(name = "TASK_MISSION")
    private String taskMission;

    @Column(name = "REMARK1")
    private String remark1;

    @Column(name = "REMARK2")
    private String remark2;

    @Column(name = "SUB_DIR")
    private String subDir;

    @Column(name = "CREDENTIAL_TITLE")
    private String credentialTitle;

    @Column(name = "BUILD_LANCHER")
    private String buildLancher;

    @Column(name = "TEMPLATE_ID")
    private String templateId;

    @Column(name = "RULE_INFO", columnDefinition = "LONGTEXT")
    private String ruleInfo;

    @Column(name = "JOB_BUILD_NODE")
    private String jobBuildNode;

}
