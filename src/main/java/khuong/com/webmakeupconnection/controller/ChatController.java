package khuong.com.webmakeupconnection.controller;

import khuong.com.webmakeupconnection.dto.MessageDTO;
import khuong.com.webmakeupconnection.dto.ResponseDTO;
import khuong.com.webmakeupconnection.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatController {
    @Autowired
    private MessageService messageService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public MessageDTO sendMessage(MessageDTO messageDTO) throws Exception {
        messageService.create(messageDTO);
        return messageDTO;
    }

    @GetMapping("/messes")
    public ResponseDTO<List<MessageDTO>> getAll() {
        ResponseDTO<List<MessageDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setData(messageService.getAll());
        responseDTO.setStatus(200);
        return responseDTO;
    }
}
