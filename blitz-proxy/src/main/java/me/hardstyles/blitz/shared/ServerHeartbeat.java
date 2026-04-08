package me.hardstyles.blitz.shared;

public class ServerHeartbeat {
    private String serverId;
    private String serverName;
    private ServerType serverType;
    private ServerState state;
    private String mapName;
    private String host;
    private int port;
    private int currentPlayers;
    private int maxPlayers;
    private long timestamp;

    public ServerHeartbeat() {
    }

    public String getServerId() {
        return serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public ServerState getState() {
        return state;
    }

    public String getMapName() {
        return mapName;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
