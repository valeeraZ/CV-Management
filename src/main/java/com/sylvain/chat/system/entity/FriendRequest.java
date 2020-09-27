package com.sylvain.chat.system.entity;

import com.sylvain.chat.system.representation.FriendRequestRepresentation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Table(name = "friend_request")
public class FriendRequest extends AbstractAuditBase{
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean isAccepted = false;

    public FriendRequest(User sender, User recipient){
        this.sender = sender;
        this.recipient = recipient;
    }

    public FriendRequestRepresentation toFriendRequestRepresentation(){
        return FriendRequestRepresentation.builder()
                .id(id)
                .sender(sender.toUserPublicRepresentation())
                .recipient(recipient.toUserPublicRepresentation())
                .isAccepted(isAccepted)
                .createdAt(super.getCreatedAt())
                .build();
    }
}
