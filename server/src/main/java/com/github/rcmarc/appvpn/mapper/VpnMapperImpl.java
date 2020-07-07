package com.github.rcmarc.appvpn.mapper;

import com.github.rcmarc.appvpn.data.VpnEventResponse;
import com.github.rcmarc.appvpn.error.VpnException;
import com.github.rcmarc.appvpn.models.VpnEvent;
import com.github.rcmarc.appvpn.repositories.VpnEventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class VpnMapperImpl implements VpnMapper {

    private final VpnEventRepository vpnEventRepository;

    @Override
    @Transactional(readOnly = true)
    public VpnEvent map(VpnEventResponse vpnEventResponse) {
        return vpnEventRepository.findById(vpnEventResponse.getId())
                .orElseThrow(() -> new VpnException("Event not found with id: " + vpnEventResponse.getId().toString()));
    }

    @Override
    public VpnEventResponse map(VpnEvent vpnEvent) {
        return VpnEventResponse.builder()
                .createdDate(vpnEvent.getCreateDate())
                .Id(vpnEvent.getId())
                .ipSrc(vpnEvent.getSrc().getSrc())
                .domainSrc(vpnEvent.getSrc().getDomain())
                .ipDst(vpnEvent.getDst().getSrc())
                .domainDst(vpnEvent.getDst().getDomain())
                .build();
    }
}
