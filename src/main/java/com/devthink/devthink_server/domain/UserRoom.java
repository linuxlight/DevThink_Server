package com.devthink.devthink_server.domain;

import com.devthink.devthink_server.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private Long roomId;

    private String status;
}