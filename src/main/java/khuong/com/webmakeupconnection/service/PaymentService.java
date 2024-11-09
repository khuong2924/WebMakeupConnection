//package khuong.com.webmakeupconnection.service;
//
//import khuong.com.webmakeupconnection.dto.PaymentDTO;
//import khuong.com.webmakeupconnection.entity.Payment;
//import khuong.com.webmakeupconnection.entity.User;
//import khuong.com.webmakeupconnection.repository.PaymentRepository;
//import khuong.com.webmakeupconnection.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class PaymentService {
//
//    @Autowired
//    private PaymentRepository paymentRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private List<PaymentDTO> mapToDto(List<Payment> payments) {
//        List<PaymentDTO> paymentDTOList = new ArrayList<>();
//        for (Payment payment : payments) {
//            paymentDTOList.add(new PaymentDTO(
//                    payment.getId(),
//
//                    payment.getAmount(),
//                    payment.getStatus(),
//                    payment.getCreatedAt()
//            ));
//        }
//        return paymentDTOList;
//    }
//
//    public List<PaymentDTO> getAll() {
//        List<Payment> payments = paymentRepository.findAll();
//        return mapToDto(payments);
//    }
//
//    public void create(PaymentDTO paymentDTO) {
//        Payment payment = new Payment();
//        User user = userRepository.findById(paymentDTO.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        payment.setUser(user);
//        payment.setAmount(paymentDTO.getAmount());
//        payment.setPaymentDate(paymentDTO.getPaymentDate());
//        payment.setMethod(Payment.Method.valueOf(paymentDTO.getMethod()));
//        paymentRepository.save(payment);
//    }
//
//    public void update(Long id, PaymentDTO paymentDTO) {
//        Payment payment = paymentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Payment not found"));
//        payment.setAmount(paymentDTO.getAmount());
//        payment.setPaymentDate(paymentDTO.getPaymentDate());
//        payment.setMethod(Payment.Method.valueOf(paymentDTO.getMethod()));
//        paymentRepository.save(payment);
//    }
//
//    public PaymentDTO getById(Long id) {
//        Payment payment = paymentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Payment not found"));
//        return new PaymentDTO(
//                payment.getId(),
//                payment.getUser().getId(),
//                payment.getAmount(),
//                payment.getPaymentDate(),
//                payment.getMethod().toString()
//        );
//    }
//
//    public void delete(Long id) {
//        paymentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Payment not found"));
//        paymentRepository.deleteById(id);
//    }
//}
