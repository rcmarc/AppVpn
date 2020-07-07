package com.github.rcmarc.appvpn.impl;

import com.github.rcmarc.appvpn.interfaces.AIModel;
import com.github.rcmarc.appvpn.interfaces.Flow;
import com.github.rcmarc.appvpn.interfaces.FlowListener;
import com.github.rcmarc.appvpn.models.Ip;
import com.github.rcmarc.appvpn.models.VpnEvent;
import com.github.rcmarc.appvpn.repositories.IpRepository;
import com.github.rcmarc.appvpn.repositories.VpnEventRepository;
import com.github.rcmarc.appvpn.services.DNSService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class FlowListenerImpl implements FlowListener {

    private final AIModel classifier;
    private final DNSService dnsService;
    private final IpRepository ipRepository;
    private final VpnEventRepository vpnEventRepository;
    private int count = 0;

    @Override
    @Transactional
    public void accept(Flow flow, String srcIp, String dstIp) {
        double[] features = flow.timeBasedFeatures();
        int n = classifier.classify(features);
        if (n == 0) return;
        if (count < Integer.MAX_VALUE) System.out.println("\rvpn total: " + ++count);
        Ip src = Ip.builder().src(srcIp).domain(dnsService.resolve(srcIp)).build();
        Ip dst = Ip.builder().src(dstIp).domain(dnsService.resolve(dstIp)).build();
        ipRepository.save(src);
        ipRepository.save(dst);
        VpnEvent event = VpnEvent.builder()
                .createDate(LocalDate.now())
                .src(src).dst(dst)
                .flowPacketsPerSecond(features[0])
                .flowIATMin(features[1])
                .build();
        vpnEventRepository.save(event);
    }
}
