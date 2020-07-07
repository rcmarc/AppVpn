package com.github.rcmarc.appvpn.mapper;

import com.github.rcmarc.appvpn.data.VpnEventResponse;
import com.github.rcmarc.appvpn.models.VpnEvent;

public interface VpnMapper {

    VpnEvent map(VpnEventResponse vpnEventResponse);

    VpnEventResponse map(VpnEvent vpnEvent);

}
