package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "Error")
public class ExceptionResponseDto<T> {

    private String status;
    private T description;

}
