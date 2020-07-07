package com.github.rcmarc.appvpn.repositories;

import com.github.rcmarc.appvpn.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
