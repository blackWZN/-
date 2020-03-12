package com.wzn.ablog.common.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "echarts_source")
@ToString
public class EchartsSource {
    @Id
    private String id;
    @Column(name = "xaxisname")
    private String xaxisName;
    @Column(name = "flagname")
    private String flagName;
    private String data;
    private String status;
}
