package com.ss.camper.user.domain;

import com.ss.camper.common.domain.DateRecord;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@SuperBuilder
@Entity
@Table(name = "terms_agree_history")
@AllArgsConstructor()
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgreeTermsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "AUTO_INCREMENT")
    @Column(name = "terms_agree_history_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private long user_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "terms_type", length = 30, nullable = false)
    private TermsType termsType;

    @Column(name = "agree", columnDefinition = "TINYINT DEFAULT 0", nullable = false)
    private boolean agree;

    @Embedded
    private DateRecord dateRecord;

}
