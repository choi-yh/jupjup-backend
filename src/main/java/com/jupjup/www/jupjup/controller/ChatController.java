package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.model.dto.chat.ChatList;
import com.jupjup.www.jupjup.model.dto.chat.CreateChatRequest;
import com.jupjup.www.jupjup.service.chat.ChatService;
import com.jupjup.www.jupjup.service.oauth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
// TODO: 전체 서버 관점에서 /api/v1 prefix 를 사용한다면 application.yml 에서 설정하는 것이 좋아보임
@RequestMapping("/api/v1/chat-rooms/{roomId}/chats") // 메인은 채팅인데 채팅 방을 기준으로 가는 URL 이 괜찮을지..
public class ChatController {

    private final ChatService chatService;

    @GetMapping("")
    public ResponseEntity<List<ChatList>> getChatList(
            @PathVariable Long roomId,
            @PageableDefault(sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable,
            Authentication authentication
    ) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        List<ChatList> list = chatService.chatList(pageable, roomId, customUserDetails.getUserEmail());

        return ResponseEntity
                .ok()
                .body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChat(@PathVariable Long roomId, @PathVariable Long id) {

        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("")
    public ResponseEntity<?> createChat(
            @PathVariable Long roomId,
            @RequestBody CreateChatRequest request
    ) {
//        return ResponseEntity
//                // TODO : 보람=> 이 부분 수정해야할 것 같아요 URL 만드는게 아마도 {} 내부 변수 값 인식 못할 것 같아용
//                .created(URI.create("/rooms/{roomId}/chats/{chatId}"))
//                .build();
        Long chatId = null;
        URI location = URI.create(String.format("/chat-rooms/%d/chats/%d", roomId, chatId));

        return ResponseEntity
                .created(location)
                .build();
    }

}
