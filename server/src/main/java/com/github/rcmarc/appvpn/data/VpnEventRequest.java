package com.github.rcmarc.appvpn.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class VpnEventRequest {
    private final LocalDate start;
    private final LocalDate end;
}
