package com.seek.TalentSuite.application.service.impl;

import com.seek.TalentSuite.application.dto.response.ClientsMetrics;
import com.seek.TalentSuite.application.service.MetricsService;
import com.seek.TalentSuite.persistence.entity.Client;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class MetricsServiceImpl implements MetricsService {

    @Override
    public ClientsMetrics calculateMetrics(List<Client> currentClients) {

        long total = currentClients.size();
        List<Integer> ages = currentClients.stream().map(Client::getAge).filter(Objects::nonNull).sorted().toList();

        if (ages.isEmpty()){
            return ClientsMetrics.builder()
                    .totalClients(total)
                    .build();
        }

        int size = ages.size();
        int min = ages.get(0);
        int max = ages.get(size - 1);
        double avg = ages.stream().mapToInt(Integer::intValue).average().orElse(0);

        double median = size % 2 == 0 ?
                (ages.get(size / 2 - 1) + ages.get(size / 2)) / 2.0 :
                ages.get(size / 2);

        Map<Integer, Long> freq = ages.stream()
                .collect(Collectors.groupingBy(age -> age, Collectors.counting()));

        int mode = freq.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(0);

        double variance = ages.stream()
                .mapToDouble(age -> Math.pow(age - avg, 2))
                .average()
                .orElse(0);

        double stdDev = Math.sqrt(variance);

        return ClientsMetrics.builder()
                .totalClients(total)
                .averageAge(avg)
                .minAge(min)
                .maxAge(max)
                .medianAge(median)
                .modeAge(mode)
                .varianceAge(variance)
                .stdDevAge(stdDev)
                .build();
    }
}
