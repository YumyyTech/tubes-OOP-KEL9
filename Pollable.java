/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



/**
 *
 * @author rank
 */
public interface Pollable {
    void addVote(int optionIndex) throws Exception;
    int getVoteCount(int optionIndex);
    String getQuestion();
    String[] getOptions();
}