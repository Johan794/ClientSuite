package com.seek.TalentSuite.unit.application.service.impl;

import com.seek.TalentSuite.application.dto.response.ClientsMetrics;
import com.seek.TalentSuite.application.service.impl.MetricsServiceImpl;
import com.seek.TalentSuite.persistence.entity.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class MetricsServiceImplTest {
    @Spy
    private MetricsServiceImpl metricsService;


    @Test
    void calculateMetrics_shouldReturnCorrectMetricsForSingleClient() {
        Client client = new Client();
        client.setAge(30);
        List<Client> clients = Collections.singletonList(client);

        ClientsMetrics metrics = metricsService.calculateMetrics(clients);

        assertEquals(1, metrics.getTotalClients());
        assertEquals(30.0, metrics.getAverageAge());
        assertEquals(30, metrics.getMinAge());
        assertEquals(30, metrics.getMaxAge());
        assertEquals(30.0, metrics.getMedianAge());
        assertEquals(30, metrics.getModeAge());
        assertEquals(0.0, metrics.getVarianceAge());
        assertEquals(0.0, metrics.getStdDevAge());
    }

    @Test
    void calculateMetrics_shouldReturnCorrectMetricsForMultipleClients() {
        Client c1 = new Client(); c1.setAge(20);
        Client c2 = new Client(); c2.setAge(30);
        Client c3 = new Client(); c3.setAge(40);
        List<Client> clients = Arrays.asList(c1, c2, c3);

        ClientsMetrics metrics = metricsService.calculateMetrics(clients);

        assertEquals(3, metrics.getTotalClients());
        assertEquals(30.0, metrics.getAverageAge());
        assertEquals(20, metrics.getMinAge());
        assertEquals(40, metrics.getMaxAge());
        assertEquals(30.0, metrics.getMedianAge());
        assertEquals(20, metrics.getModeAge()); // All have same frequency, first max found
        assertEquals(66.66666666666667, metrics.getVarianceAge());
        assertEquals(Math.sqrt(66.66666666666667), metrics.getStdDevAge());
    }

    @Test
    void calculateMetrics_shouldIgnoreNullAges() {
        Client c1 = new Client(); c1.setAge(null);
        Client c2 = new Client(); c2.setAge(25);
        List<Client> clients = Arrays.asList(c1, c2);

        ClientsMetrics metrics = metricsService.calculateMetrics(clients);

        assertEquals(2, metrics.getTotalClients());
        assertEquals(25.0, metrics.getAverageAge());
        assertEquals(25, metrics.getMinAge());
        assertEquals(25, metrics.getMaxAge());
        assertEquals(25.0, metrics.getMedianAge());
        assertEquals(25, metrics.getModeAge());
        assertEquals(0.0, metrics.getVarianceAge());
        assertEquals(0.0, metrics.getStdDevAge());
    }

    @Test
    void calculateMetrics_shouldCalculateEvenMedian() {
        Client c1 = new Client(); c1.setAge(10);
        Client c2 = new Client(); c2.setAge(20);
        List<Client> clients = Arrays.asList(c1, c2);

        ClientsMetrics metrics = metricsService.calculateMetrics(clients);

        assertEquals(2, metrics.getTotalClients());
        assertEquals(15.0, metrics.getAverageAge());
        assertEquals(10, metrics.getMinAge());
        assertEquals(20, metrics.getMaxAge());
        assertEquals(15.0, metrics.getMedianAge());
        assertTrue(metrics.getModeAge() == 10 || metrics.getModeAge() == 20);
        assertEquals(25.0, metrics.getVarianceAge());
        assertEquals(5.0, metrics.getStdDevAge());
    }

}
