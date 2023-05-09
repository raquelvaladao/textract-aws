package com.textract.api.domain.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tb_identity")
public class Identity {

    @Id
    @Column(name = "identity_number")
    private String identityNumber;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birth_date")
    private String birthDate;
}
