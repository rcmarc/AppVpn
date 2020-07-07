package com.github.rcmarc.appvpn.repositories;

import com.github.rcmarc.appvpn.models.VpnEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VpnEventRepository extends JpaRepository<VpnEvent, Long> {
    
    List<VpnEvent> findAllByCreateDate(LocalDate localDate);

    List<VpnEvent> findAllByCreateDateAfterAndCreateDateBefore(LocalDate start, LocalDate end);
}
