/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */


/**
 *
 * @author rank
 */
public class Voter extends AbstractUser {

    private boolean hasVoted;

    public Voter(String username) {
        super(username);
        this.hasVoted = false;
    }

    @Override
    public boolean canVote() {
        return !hasVoted;
    }

    public void setVoted() {
        this.hasVoted = true;
    }
}