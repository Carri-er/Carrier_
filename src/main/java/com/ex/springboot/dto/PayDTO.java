package com.ex.springboot.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PayDTO {
	private String Member_Id;
	private int Course_num;
    private String orderId;
    private String Pay_date;
    private String course_name;
    private int amount;
    private String course_img;
}
