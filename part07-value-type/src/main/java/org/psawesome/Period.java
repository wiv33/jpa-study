package org.psawesome;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

/**
 * @author pilseong.ko
 */
@Embeddable
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Period {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
