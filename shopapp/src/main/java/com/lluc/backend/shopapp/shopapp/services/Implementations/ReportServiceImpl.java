package com.lluc.backend.shopapp.shopapp.services.Implementations;

import com.lluc.backend.shopapp.shopapp.models.entities.Report;
import com.lluc.backend.shopapp.shopapp.repositories.ReportRepository;
import com.lluc.backend.shopapp.shopapp.services.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public Report addReport(Report report) {
        return reportRepository.save(report);
    }
}