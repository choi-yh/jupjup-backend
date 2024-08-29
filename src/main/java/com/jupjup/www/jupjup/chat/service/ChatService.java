package com.jupjup.www.jupjup.chat.service;

import com.jupjup.www.jupjup.chat.dto.ChatList;
import com.jupjup.www.jupjup.chat.entity.Chat;
import com.jupjup.www.jupjup.chat.repository.ChatRepository;
import com.jupjup.www.jupjup.chat.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;

    public Chat add(Long roomId, String content, Long userId) {
        // TODO: 소켓으로 채팅 전송

        // TODO: 해당 채팅방 마지막 메시지 업데이트

        Chat chat = Chat.builder()
                .roomId(roomId)
                .userId(userId)
                .content(content)
                .build();

        return chatRepository.save(chat);
    }

    public List<ChatList> chatList(Pageable pageable, Long roomId, Long userId) {
        // 해당 room 에 참여중인 user 인지 확인
        roomRepository.findByIdAndUserId(roomId, userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 채팅방의 유저가 아닙니다."));

        // 해당 룸의 채팅 호출. TODO: 시간 순 정렬 필요
        List<Chat> chatList = chatRepository.findAllByRoomId(pageable, roomId);

        return chatList.stream()
                .map(ChatList::toDTO)
                .toList();
    }

}
