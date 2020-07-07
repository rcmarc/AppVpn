package com.github.rcmarc.appvpn.data;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ExpiredJwtResponse {

    private String message;
    @Builder.Default
    private Long code = 101L;

}
