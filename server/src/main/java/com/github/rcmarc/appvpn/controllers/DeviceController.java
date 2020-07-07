package com.github.rcmarc.appvpn.controllers;

import com.github.rcmarc.appvpn.data.Device;
import com.github.rcmarc.appvpn.mapper.DevicePcapIfMapper;
import com.github.rcmarc.appvpn.services.DeviceService;
import com.github.rcmarc.appvpn.services.VpnService;
import lombok.AllArgsConstructor;
import org.jnetpcap.PcapIf;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
@AllArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;
    private final VpnService vpnService;
    private final DevicePcapIfMapper mapper;

    @GetMapping
    public ResponseEntity<List<Device>> getAll() {
       return ResponseEntity.ok(deviceService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Device> getDevice(@PathVariable Long id){
        PcapIf pcapIf = deviceService.select(id);
        vpnService.loop(pcapIf);
        return ResponseEntity.ok(mapper.map(pcapIf, id));
    }

}
