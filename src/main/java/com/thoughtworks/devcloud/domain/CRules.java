package com.thoughtworks.devcloud.domain;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "C_RULES")
public class CRules implements Serializable {

    private static final long serialVersionUID = 8226152518633514990L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PLUGIN_NAME", nullable = false)
    private String pluginName;
    @Column(name = "PLUGIN_RULE_KEY", nullable = false)
    private String pluginRuleKey;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "NAME_CHINESE", nullable = false)
    private String nameChinese;

    @Column(name="DESCRIPTION", columnDefinition="LONGTEXT")
    private String description;

    @Column(name="DESCRIPTION_CHINESE", columnDefinition="LONGTEXT")
    private String descriptionChinese;

    @Column(name="EXAMPLE_BAD", columnDefinition="LONGTEXT")
    private String exampleBad;

    @Column(name="EXAMPLE_BAD_CHINESE", columnDefinition="LONGTEXT")
    private String exampleBadChinese;

    @Column(name="EXAMPLE_GOOD", columnDefinition="LONGTEXT")
    private String exampleGood;

    @Column(name="EXAMPLE_GOOD_CHINESE", columnDefinition="LONGTEXT")
    private String exampleGoodChinese;

    @Column(name="RECOMMENDATION", columnDefinition="LONGTEXT")
    private String recommendation;

    @Column(name="RECOMMENDATION_CHINESE", columnDefinition="LONGTEXT")
    private String recommendationChinese;

    @Column(name = "DESCRIPTION_FORMAT")
    private String descriptionFormat;

    @NotEmpty
    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "SYSTEM_TAGS")
    private String systemTags;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "CREATED_AT", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "UPDATED_AT", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "STATUS")
    private String status;
    @Column(name = "CATEGORY_BIG")
    private String categoryBig;
    @Column(name = "CATEGORY_SMALL")
    private String categorySmall;
    @Column(name = "RULE_SET")
    private String ruleSet;
    @Column(name = "REMARK1")
    private String remark1;

    @Column(name = "REMARK2")
    private String remark2;
    @Column(name = "JENKINS_PLUGIN_NAME")
    private String jenkinsPluginName;
}
