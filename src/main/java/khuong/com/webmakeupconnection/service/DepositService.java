package khuong.com.webmakeupconnection.service;
import khuong.com.webmakeupconnection.dto.DepositDTO;
import khuong.com.webmakeupconnection.entity.Deposit;
import khuong.com.webmakeupconnection.repository.DepositRepository;
import khuong.com.webmakeupconnection.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DepositService {
    @Autowired
    private DepositRepository depositRepository;
    @Autowired
    private UserRepository userRepository;

    private List<DepositDTO> mapToDTO(List<Deposit> deposits) {
        List<DepositDTO> dtos = new ArrayList<>();
        for (Deposit deposit : deposits) {
            DepositDTO dto = new DepositDTO(deposit.getId(), deposit.getPayment().getId(), deposit.getAmount(), deposit.getDepositDate(), deposit.getStatus());
            dtos.add(dto);
        }
        return dtos;
    }
    public List<DepositDTO> getAll() {
        List<Deposit> deposits = depositRepository.findAll();
        return mapToDTO(deposits);
    }

    public void create(DepositDTO depositDTO) {
        Deposit deposit = new Deposit();
        deposit.setId(depositDTO.getId());
        deposit.setAmount(depositDTO.getAmount());
        deposit.setDepositDate(depositDTO.getDepositDate());
        deposit.setStatus(depositDTO.getStatus());
        depositRepository.save(deposit);
    }

    public void update(Long id, DepositDTO depositDTO) {
        Deposit deposit = depositRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deposit not found"));
        deposit.setAmount(depositDTO.getAmount());
        deposit.setDepositDate(depositDTO.getDepositDate());
        deposit.setStatus(depositDTO.getStatus());
        depositRepository.save(deposit);
    }

    public DepositDTO findById(Long id) {
        Deposit deposit = depositRepository.findById(id).orElseThrow(() -> new RuntimeException("Deposit not found"));

        return new DepositDTO(deposit.getId(), deposit.getPayment().getId(), deposit.getAmount(), deposit.getDepositDate(), deposit.getStatus());
    }

    public void delete(Long id) {
        depositRepository.findById(id).orElseThrow(() -> new RuntimeException("Deposit not found"));
        userRepository.deleteById(id);
    }



}
