package com.github.rcmarc.appvpn.repositories;

import com.github.rcmarc.appvpn.models.Ip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpRepository extends JpaRepository<Ip, Long> {
}
