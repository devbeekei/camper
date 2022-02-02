package com.ss.camper.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

    @Enumerated(EnumType.STRING)
    @Column(name = "terms_type", length = 30, nullable = false)
    private TermsType termsType;

    @Column(name = "agree", columnDefinition = "TINYINT DEFAULT 0", nullable = false)
    private boolean agree;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, insertable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date created;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
