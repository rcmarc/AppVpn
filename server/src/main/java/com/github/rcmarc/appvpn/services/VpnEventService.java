package com.github.rcmarc.appvpn.services;

import com.github.rcmarc.appvpn.data.VpnEventRequest;
import com.github.rcmarc.appvpn.data.VpnEventResponse;
import com.github.rcmarc.appvpn.error.VpnException;
import com.github.rcmarc.appvpn.interfaces.AIModel;
import com.github.rcmarc.appvpn.mapper.VpnMapper;
import com.github.rcmarc.appvpn.models.VpnEvent;
import com.github.rcmarc.appvpn.repositories.VpnEventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VpnEventService {

    private final VpnEventRepository vpnEventRepository;
    private final VpnMapper mapper;
    private final AIModel trainer;

    @Transactional(readOnly = true)
    public List<VpnEventResponse> getAll(VpnEventRequest request) {
        return vpnEventRepository.findAllByCreateDateAfterAndCreateDateBefore(request.getStart(), request.getEnd())
                .stream().map(mapper::map)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public void train(Long id) {
        VpnEvent event = vpnEventRepository.findById(id)
                .orElseThrow(() -> new VpnException(String.format("Event with id %d not found", id)));
        double[] data = {event.getFlowPacketsPerSecond(), event.getFlowIATMin(), 1.0};
        trainer.train(data);
    }

}
