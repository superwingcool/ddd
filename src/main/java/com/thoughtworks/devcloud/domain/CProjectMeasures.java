package com.thoughtworks.devcloud.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "C_PROJECT_MEASURES")
public class CProjectMeasures implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "BIGINT")
    private Integer id;

    @Column(name = "VALUE")
    private BigDecimal value;

    @JoinColumn(name = "METRIC_ID", referencedColumnName = "ID")
    @ManyToOne
    private CMetrics cMetrics;

    @JoinColumn(name = "SNAPSHOT_ID", referencedColumnName = "ID")
    @ManyToOne
    private CSnapshots cSnapshots;

    @Column(name = "TENDENCY")
    private Integer tendency;

    @Column(name = "FILE_UUID")
    private String fileUuid;

    @Column(name = "SCOPE")
    private String scope;

    @Column(name = "LANGUAGE")
    private String language;

    @JoinColumn(name = "PROJECT_UUID", referencedColumnName = "PROJECT_UUID")
    @ManyToOne()
    private CProjects cProjects;

    @Column(name = "REMARK1")
    private String remark1;

    @Column(name = "REMARK2")
    private String remark2;

    @Column(name = "TEXT_VALUE")
    private String textValue;

    @Column(name = "MEASURE_DATA", columnDefinition = "LONGBLOB")
    private String measureData;

}
