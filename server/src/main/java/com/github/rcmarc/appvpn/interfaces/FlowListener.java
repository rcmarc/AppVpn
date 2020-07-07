package com.github.rcmarc.appvpn.interfaces;

@FunctionalInterface
public interface FlowListener {

    void accept(Flow flow, String srcIp, String dstIp);

}
