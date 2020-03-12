package com.wzn.ablog.common.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "echarts_dimensions")
public class EchartsDimensions {
    @Id
    private String id;
    private String name;
    private String status;
}
