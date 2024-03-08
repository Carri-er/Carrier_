package com.ex.springboot.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PayDTO {
	private String Member_Id;
	private int Course_num;
    private String orderId;
    String Pay_date;
}
