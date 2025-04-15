package com.w3itexperts.ombe.apimodals;

public class userVoteDTO {
    private String userId;
    private String sessionId;
    private String eateryId;
    private boolean liked;

    public userVoteDTO(String userId, String sessionId, String eateryId, boolean liked) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.eateryId = eateryId;
        this.liked = liked;
    }

    public String getUserId() { return userId; }
    public String getSessionId() { return sessionId; }
    public String getEateryId() { return eateryId; }
    public boolean isLiked() { return liked; }

    public void setUserId(String userId) { this.userId = userId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    public void setEateryId(String eateryId) { this.eateryId = eateryId; }
    public void setLiked(boolean liked) { this.liked = liked; }
}