package org.example;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author pilseong.ko
 */
@MappedSuperclass
public class BaseEntity {

    private String createBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;
}
