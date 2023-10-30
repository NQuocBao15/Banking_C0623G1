package com.example.bankingc06.model;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    private Customer sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id", nullable = false)
    private Customer recipient;

    @Column(nullable = false)
    private BigDecimal transferAmount;

    @Column(nullable = false)
    private Long fees;

    @Column(nullable = false)
    private BigDecimal feesAmount;

    @Column(nullable = false)
    private BigDecimal transactionAmount;

    private Boolean deleted;

    private LocalDateTime createAt;

    public String getCreateAt() {
        String inputDateTime = String.valueOf(createAt);
        LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, DateTimeFormatter.ISO_DATE_TIME);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }

}
