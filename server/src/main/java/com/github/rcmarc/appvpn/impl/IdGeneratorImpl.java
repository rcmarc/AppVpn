package com.github.rcmarc.appvpn.impl;

import com.github.rcmarc.appvpn.interfaces.IdGenerator;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class IdGeneratorImpl implements IdGenerator {

    private Long id = 0L;

    public synchronized Long nextId() {
        return ++id;
    }
}
