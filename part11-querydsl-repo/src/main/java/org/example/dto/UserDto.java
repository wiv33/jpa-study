package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pilseong.ko
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;

    private int age;
}
