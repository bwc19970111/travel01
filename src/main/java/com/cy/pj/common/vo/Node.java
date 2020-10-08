package com.cy.pj.common.vo;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
/**VO*/
@Data
@ToString
public class Node implements Serializable{
	private static final long serialVersionUID = -8035928111049328300L;
	private Integer id;
	private String name;
	private Integer parentId;
}
