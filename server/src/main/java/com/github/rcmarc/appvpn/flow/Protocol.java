package com.github.rcmarc.appvpn.flow;

import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.network.Ip6;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;
import org.jnetpcap.protocol.vpn.L2TP;

public class Protocol {
    private Tcp tcp;
    private Udp udp;
    private Ip4 ipv4;
    private Ip6 ipv6;
    private L2TP l2tp;

    public Protocol() {
        super();
        tcp = new Tcp();
        udp = new Udp();
        ipv4 = new Ip4();
        ipv6 = new Ip6();
        l2tp = new L2TP();
    }

    public Tcp getTcp() {
        return tcp;
    }

    public void setTcp(Tcp tcp) {
        this.tcp = tcp;
    }

    public Udp getUdp() {
        return udp;
    }

    public void setUdp(Udp udp) {
        this.udp = udp;
    }

    public Ip4 getIpv4() {
        return ipv4;
    }

    public void setIpv4(Ip4 ipv4) {
        this.ipv4 = ipv4;
    }

    public Ip6 getIpv6() {
        return ipv6;
    }

    public void setIpv6(Ip6 ipv6) {
        this.ipv6 = ipv6;
    }

    public L2TP getL2tp() {
        return l2tp;
    }

    public void setL2tp(L2TP l2tp) {
        this.l2tp = l2tp;
    }
}
