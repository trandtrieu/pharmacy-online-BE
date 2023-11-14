package com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

	private int category_id;
	private String category_name;
	private String category_image;

	public CategoryDTO(int category_id, String category_name) {
		super();
		this.category_id = category_id;
		this.category_name = category_name;
	}

}
