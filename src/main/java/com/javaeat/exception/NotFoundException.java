package com.javaeat.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 248611599356373897L;
	private Integer code;
	private String message;

}
