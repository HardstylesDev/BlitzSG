package me.hardstyles.blitz.shared;

public class SpawnRequest {
    private String requestId;
    private String mapName;
    private String serverType;
    private String preferredServerId;
    private Integer preferredPort;
    private Integer minInstances;
    private int maxPlayers;
    private long timestamp;

    public SpawnRequest() {
    }

    public SpawnRequest(String requestId,
                        String mapName,
                        String serverType,
                        String preferredServerId,
                        Integer preferredPort,
                        Integer minInstances,
                        int maxPlayers,
                        long timestamp) {
        this.requestId = requestId;
        this.mapName = mapName;
        this.serverType = serverType;
        this.preferredServerId = preferredServerId;
        this.preferredPort = preferredPort;
        this.minInstances = minInstances;
        this.maxPlayers = maxPlayers;
        this.timestamp = timestamp;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getMapName() {
        return mapName;
    }

    public String getServerType() {
        return serverType;
    }

    public String getPreferredServerId() {
        return preferredServerId;
    }

    public Integer getPreferredPort() {
        return preferredPort;
    }

    public Integer getMinInstances() {
        return minInstances;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
