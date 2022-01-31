package com.bruno.bdb.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class BranchDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotNull
    private String name;

    private LocalDateTime openingDate;

    private Integer status;

}
