package com.agrocode.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "meeting_participants")
public class Participant {
    //DEPRECATED
    //DEPRECATED
    //DEPRECATED
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int meetingId;
    private String userName;

    public Participant() {}

    public Participant(int meetingId, String userName) {
        this.meetingId = meetingId;
        this.userName = userName;
    }

    // Getters and Setters
}
