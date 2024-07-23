package com.example.messenger.chatRealtime;
import com.example.messenger.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User senderUser;
    @ManyToOne
    private Conversation conversation;
    private String message;
    private LocalDateTime createTime;
    private String status;
}
