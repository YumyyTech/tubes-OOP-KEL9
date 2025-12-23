package backend;

public class Voter extends AbstractUser {

    private boolean hasVoted = false;

    public Voter(String username) {
        super(username);
    }

    @Override
    public boolean canVote() {
        return !hasVoted;
    }

    public void setVoted() {
        hasVoted = true;
    }
}
