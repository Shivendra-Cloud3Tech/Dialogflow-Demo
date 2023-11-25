package com.bot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RegisterComplain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String IssueType;
    String Address;
    String Location;
    String Details;
    String RequestType;
}
