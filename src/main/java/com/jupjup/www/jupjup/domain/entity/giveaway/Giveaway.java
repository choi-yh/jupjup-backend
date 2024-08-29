package com.jupjup.www.jupjup.domain.entity.giveaway;

import com.jupjup.www.jupjup.domain.entity.User;
import com.jupjup.www.jupjup.chat.entity.Room;
import com.jupjup.www.jupjup.domain.enums.GiveawayStatus;
import com.jupjup.www.jupjup.image.entity.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "giveaway")
public class Giveaway {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private GiveawayStatus status = GiveawayStatus.PENDING;

    @OneToMany(mappedBy = "giveaway")
    private List<Room> chatRooms = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JoinColumn(name = "giver_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User giver;

    @Column(name = "giver_id")
    private Long giverId;

    // TODO: cascade & orphanRemoval 에 대한 이해 필요
    // cascade
    // orphanRemoval: 부모와 자식간의 연관관계를 제거하면 자식 엔티티가 고아가 되어 DB 에서 삭제됨
    @OneToMany(mappedBy = "giveaway", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;

    // TODO: 거래 장소

    // TODO: 댓글 수 -> 관련 채팅 수인가?

    // TODO: 조회 수


    @Builder
    public Giveaway(String title, String description, Long giverId, List<Image> images) {
        this.title = title;
        this.description = description;
        this.giverId = giverId;
        this.images = images;
    }

    public void update(String title, String description, GiveawayStatus status, List<Image> images) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.images = images;
    }

}
