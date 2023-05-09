package com.textract.api.view.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CleanedIDResponse {

    private String identityNumber;
    private String fullName;
    private String birthDate;

    public CleanedIDResponse(){
        this.identityNumber = "";
        this.fullName = "";
        this.birthDate = "";
    }
}
