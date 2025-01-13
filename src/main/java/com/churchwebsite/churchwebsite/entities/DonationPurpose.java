package com.churchwebsite.churchwebsite.entities;

import com.churchwebsite.churchwebsite.utils.MiscUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "donation_purpose")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationPurpose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "You must provide donation purpose.")
    @Column(name = "donation_purpose_name", nullable = false)
    private String donationPurposeName;

    @NotEmpty(message = "You must provide some donation purpose description.")
    @Column(name = "donation_purpose_description")
    private String donationPurposeDescription;

    @ToString.Exclude
    @OneToMany(mappedBy = "donationPurpose")
    private List<Donation> donations;

    @Transient
    private String excerpt;

    public String getExcerpt() {
        return MiscUtils.generateExcerpt(donationPurposeDescription, 100);
    }
}