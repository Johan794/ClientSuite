package com.seek.TalentSuite.application.service;

import com.seek.TalentSuite.application.dto.response.ClientsMetrics;
import com.seek.TalentSuite.persistence.entity.Client;

import java.util.List;

public interface MetricsService {

    ClientsMetrics calculateMetrics(List<Client> currentClients);
}
