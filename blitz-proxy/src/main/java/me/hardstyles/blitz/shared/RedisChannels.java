package me.hardstyles.blitz.shared;

public final class RedisChannels {
    private RedisChannels() {
    }

    public static final String MATCH_JOIN_REQUEST = "blitz:match:join-request";
    public static final String CORE_SPAWN_REQUEST = "blitz:core:spawn-request";
    public static final String CORE_STOP_REQUEST = "blitz:core:stop-request";
    public static final String SERVER_HEARTBEAT = "blitz:server:heartbeat";
    public static final String SERVER_RUNTIME_CONFIG = "blitz:server:runtime-config";
}
