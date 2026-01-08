package com.globalnest.backend.domain.property.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PropertyOptionList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "property_option_seq_gen")
    @SequenceGenerator(
            name = "property_option_seq_gen",
            sequenceName = "property_option_seq",
            allocationSize = 1
    )
    private Long propertyOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private OptionList optionList;
}
