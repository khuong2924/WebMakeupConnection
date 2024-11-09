package khuong.com.webmakeupconnection.service;
import khuong.com.webmakeupconnection.dto.MessageDTO;
import khuong.com.webmakeupconnection.entity.Message;
import khuong.com.webmakeupconnection.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<MessageDTO> mapToDTO(List<Message> messages) {
        List<MessageDTO> dtos = new ArrayList<>();
        for (Message message : messages) {
            dtos.add(new MessageDTO(
               message.getId(),
               message.getSender().getId(),
               message.getReceiver().getId(),
               message.getContent(),
               message.getMessageDate(),
               message.getIsRead()
            ));
        }
        return dtos;
    }

    public List<MessageDTO> getAll() {
        List<Message> messages = messageRepository.findAll();
        return mapToDTO(messages);
    }

    public void create(MessageDTO messageDTO) {
        Message message = new Message();
        message.setId(messageDTO.getId());
        message.setSender(message.getSender());
        message.setReceiver(message.getReceiver());
        message.setContent(messageDTO.getContent());
        message.setMessageDate(messageDTO.getMessageDate());
        message.setIsRead(messageDTO.getIsRead());
        messageRepository.save(message);
    }

    public void update(Long id, MessageDTO messageDTO) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        message.setId(messageDTO.getId());
        message.setSender(message.getSender());
        message.setReceiver(message.getReceiver());
        message.setContent(messageDTO.getContent());
        message.setMessageDate(messageDTO.getMessageDate());
        message.setIsRead(messageDTO.getIsRead());
        messageRepository.save(message);
    }

    public MessageDTO getById(Long id) {
        Message mess = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        if (mess == null) {
            throw new RuntimeException("Message not found");
        }
        return new MessageDTO(
                mess.getId(),
                mess.getSender().getId(),
                mess.getReceiver().getId(),
                mess.getContent(),
                mess.getMessageDate(),
                mess.getIsRead()
        );
    }

    public void delete(Long id) {
        messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        messageRepository.deleteById(id);
    }


}












