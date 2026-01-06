package com.globalnest.backend.domain.property.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class OptionList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "option_seq_gen")
    @SequenceGenerator(
            name = "option_seq_gen",
            sequenceName = "option_seq",
            allocationSize = 1
    )
    private long optionId;

    private String optionName;

}
