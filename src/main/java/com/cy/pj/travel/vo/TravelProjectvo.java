package com.cy.pj.travel.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TravelProjectvo implements Serializable {
    private Integer id;
    private String name;
    private Integer price;
    private String text;
    private String img;
    private Date createdTime;
    private Integer didianId;
    private Didianvo didian;
}
