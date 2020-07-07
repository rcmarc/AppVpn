package com.github.rcmarc.appvpn.controllers;

import com.github.rcmarc.appvpn.data.VpnEventRequest;
import com.github.rcmarc.appvpn.data.VpnEventResponse;
import com.github.rcmarc.appvpn.services.VpnEventService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
public class EventController {

    private final VpnEventService vpnEventService;

    @PostMapping
    public ResponseEntity<List<VpnEventResponse>> getAll(@RequestBody VpnEventRequest request){
        return ResponseEntity.ok(vpnEventService.getAll(request));
    }

    @PostMapping("{id}")
    public ResponseEntity<Void> train(@PathVariable Long id){
        vpnEventService.train(id);
        return ResponseEntity.ok().build();
    }

}
