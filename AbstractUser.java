/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.ram.qwfdgdnfnhv;

/**
 *
 * @author rank
 */
public abstract class AbstractUser {
    protected String username;

    public AbstractUser(String username) {
        this.username = username;
    }

    public abstract boolean canVote();
}