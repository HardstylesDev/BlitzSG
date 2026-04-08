package me.hardstyles.blitz.shared;

public class JoinRequest {
    private String requestId;
    private String playerUuid;
    private String mapName;
    private String source;
    private long timestamp;

    public JoinRequest() {
    }

    public JoinRequest(String requestId, String playerUuid, String mapName, String source, long timestamp) {
        this.requestId = requestId;
        this.playerUuid = playerUuid;
        this.mapName = mapName;
        this.source = source;
        this.timestamp = timestamp;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getPlayerUuid() {
        return playerUuid;
    }

    public String getMapName() {
        return mapName;
    }

    public String getSource() {
        return source;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
