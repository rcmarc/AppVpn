package com.github.rcmarc.appvpn.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VpnEvent {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Ip src;

    @ManyToOne(fetch = LAZY)
    private Ip dst;

    private LocalDate createDate;

    private double flowPacketsPerSecond;

    private double flowIATMin;
}
