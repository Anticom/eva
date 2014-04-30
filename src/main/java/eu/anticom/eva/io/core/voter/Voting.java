package eu.anticom.eva.io.core.voter;

public class Voting {
    public enum Vote {
        POSITIVE, NEGATIVE, NEUTRAL, FORCE_POSITIVE, FORCE_NEGATIVE
    }

    protected Vote vote;
    /**
     * the higher, the more prioritized
     * default is 0
     */
    protected Integer priority;

    public Voting(Vote vote, Integer priority) {
        this.vote = vote;
        this.priority = priority;
    }

    public Voting(Vote vote) {
        this(vote, 0);
    }

    public Voting() {
        this(Vote.NEUTRAL);
    }

    //region getters
    public Vote getVote() {
        return vote;
    }

    public Integer getPriority() {
        return priority;
    }
    //endregion
}
