package com.lluc.backend.shopapp.shopapp.repositories;

import com.lluc.backend.shopapp.shopapp.models.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}