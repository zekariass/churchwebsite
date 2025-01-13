package com.churchwebsite.churchwebsite.entities;

import com.churchwebsite.churchwebsite.enums.DonationPaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "donation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "You must select or enter a donation amount.")
    @Column(name = "donation_amount", nullable = false)
    private Double donationAmount;

    @CreatedDate
    @Column(name = "donation_time", nullable = false, updatable = false)
    private LocalDateTime donationTime;

    @NotNull(message = "You must select payment method.")
    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private DonationPaymentMethod paymentMethod;

    @NotNull(message = "You should select purpose of this donations.")
    @ManyToOne
    @JoinColumn(name = "donation_purpose_id", nullable = false)
    private DonationPurpose donationPurpose;

    @Column(name = "donor_full_name")
    private String donorFullName;

    @Column(name = "donor_email")
    private String donorEmail;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "direct_debit_sort_code")
    private String directDebitSortCode;

    @Column(name = "direct_debit_account")
    private String directDebitAccount;
}