package com.scm.dto;


import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class ResponseDTO {

    boolean status = false;
    String msg = null;
    Object data = null;

}
