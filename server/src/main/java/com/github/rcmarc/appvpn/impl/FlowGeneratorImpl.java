package com.github.rcmarc.appvpn.impl;

import com.github.rcmarc.appvpn.flow.PacketInfo;
import com.github.rcmarc.appvpn.interfaces.Flow;
import com.github.rcmarc.appvpn.interfaces.FlowBuilder;
import com.github.rcmarc.appvpn.interfaces.FlowGenerator;
import com.github.rcmarc.appvpn.interfaces.FlowListener;
import com.github.rcmarc.appvpn.services.PropertiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
// import java.util.LinkedList;
// import java.util.Timer;
// import java.util.TimerTask;

@Component
@RequiredArgsConstructor
public class FlowGeneratorImpl implements FlowGenerator {

    private final FlowListener flowListenerLogger;
    private final HashMap<String, Flow> currentFlows = new HashMap<>();
    private final PropertiesService propertiesService;
    private final FlowBuilder flowBuilder;

    @Override
    public void addPacket(PacketInfo packet) {
        long currentTimeStamp = packet.getTimeStamp();
        synchronized (currentFlows) {
            if (currentFlows.containsKey(packet.getFlowId())) {
                final Flow flow = currentFlows.get(packet.getFlowId());
                if ((currentTimeStamp - flow.getFlowStartTime()) > propertiesService.getFlowTimeout() * 1000) {
                    if (flow.packetCount() > 1) {
                        onFlowGenerated(flow, packet.getSourceIP(), packet.getDestinationIP());
                    }
                    currentFlows.remove(packet.getFlowId());
                    currentFlows.put(packet.getFlowId(), flowBuilder.create(packet));
                } else if (packet.hasFlagFIN()) {
                    flow.addPacket(packet);
                    onFlowGenerated(flow, packet.getSourceIP(), packet.getDestinationIP());
                    currentFlows.remove(packet.getFlowId());
                } else {
                    flow.addPacket(packet);
                    currentFlows.put(packet.getFlowId(), flow);
                }
            } else {
                currentFlows.put(packet.getFlowId(), flowBuilder.create(packet));
            }
        }
    }

    @Override
    public FlowListener getFlowListener() {
        return flowListenerLogger;
    }

    // @PostConstruct
    // private void listenFlowTimeout() {
    //     Timer timer = new Timer();
    //     timer.schedule(new TimerTask() {
    //         @Override
    //         public void run() {
    //             LinkedList<String> keys = new LinkedList<>();
    //             long currentTime = System.currentTimeMillis();
    //             synchronized (currentFlows) {
    //                 currentFlows.forEach((key, flow) -> {
    //                     if (currentTime - (flow.getFlowStartTime() / 1000) > propertiesService.getFlowTimeout()) {
    //                         keys.add(key);
    //                     }
    //                 });
    //                 keys.forEach(currentFlows::remove);
    //             }
    //         }
    //     }, 0, propertiesService.getFlowTimeout());
    // }
}
