package com.github.rcmarc.appvpn.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Device {
    private Long id;
    private String name;
    private String description;
}
