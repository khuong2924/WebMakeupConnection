package khuong.com.webmakeupconnection.service;
import khuong.com.webmakeupconnection.dto.StatisticDTO;
import khuong.com.webmakeupconnection.entity.Statistic;
import khuong.com.webmakeupconnection.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticService {

    @Autowired
    private StatisticRepository statisticRepository;

    private List<StatisticDTO> mapToDto(List<Statistic> statistics) {
        List<StatisticDTO> dtos = new ArrayList<StatisticDTO>();
        for (Statistic statistic : statistics) {
            StatisticDTO dto = new StatisticDTO();
            dto.setId(statistic.getId());
            dto.setIncome(statistic.getIncome());
            dto.setExpenses(statistic.getExpenses());
            dto.setServiceType(statistic.getServiceType());
            dto.setLocation(statistic.getLocation());
            dto.setPeriod(statistic.getPeriod());
            dto.setCreatedAt(statistic.getCreatedAt());
            dtos.add(dto);
        }
        return dtos;
    }

    public List<StatisticDTO> getAll() {
        List<Statistic> statistics = statisticRepository.findAll();
        return mapToDto(statistics);
    }

    public void create(StatisticDTO statisticDTO) {
        Statistic statistic = new Statistic();
        statistic.setIncome(statisticDTO.getIncome());
        statistic.setExpenses(statisticDTO.getExpenses());
        statistic.setServiceType(statisticDTO.getServiceType());
        statistic.setLocation(statisticDTO.getLocation());
        statistic.setPeriod(statisticDTO.getPeriod());
        statistic.setCreatedAt(statisticDTO.getCreatedAt());
        statisticRepository.save(statistic);
    }

    public void update(Long id, StatisticDTO statisticDTO) {
        Statistic statistic = statisticRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Statistic not found"));
        statistic.setIncome(statisticDTO.getIncome());
        statistic.setExpenses(statisticDTO.getExpenses());
        statistic.setServiceType(statisticDTO.getServiceType());
        statistic.setLocation(statisticDTO.getLocation());
        statistic.setPeriod(statisticDTO.getPeriod());
        statistic.setCreatedAt(statisticDTO.getCreatedAt());
        statisticRepository.save(statistic);
    }

    public StatisticDTO getById(Long id) {
        Statistic statistic = statisticRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Statistic not found"));
        if (statistic == null) {
            throw new RuntimeException("Statistic not found");
        }

        return new StatisticDTO(
                statistic.getId(),
                statistic.getUser().getId(),
                statistic.getIncome(),
                statistic.getExpenses(),
                statistic.getServiceType(),
                statistic.getLocation(),
                statistic.getPeriod(),
                statistic.getCreatedAt()
        );

    }

    public void delete(Long id) {
        statisticRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Statistic not found"));
    }
}









