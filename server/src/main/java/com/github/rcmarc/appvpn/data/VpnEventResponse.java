package com.github.rcmarc.appvpn.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VpnEventResponse {
    private Long Id;
    private String ipSrc;
    private String domainSrc;
    private String ipDst;
    private String domainDst;
    private LocalDate createdDate;
}
