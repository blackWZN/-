package com.wzn.ablog.common.entity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "echarts_source")
@ToString
@Accessors(chain = true)
public class EchartsSource {
    @Id
    private String id;
    private String xaxis;
    private String flag;
    private String data;
    private String status;
    @Column(name = "update_time")
    private String updateTime;
}
