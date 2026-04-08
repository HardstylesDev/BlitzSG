# blitz-core

Node orchestration service that manages Java server instances for Blitz.

## Features

- Start Java instances on specific ports
- Track active instances in memory
- Graceful stop (`stop` command), then hard kill timeout fallback
- Tail recent logs per instance
- Consume spawn requests from Redis
- Publish server heartbeat/status to Redis
- Stage map template + map config files for predetermined map startup
- Publish per-instance runtime role/map metadata to Redis for unified plugin boot mode

## Run

```bash
npm install
npm start
```

## API

- `GET /health`
- `GET /servers`
- `GET /servers/:id`
- `GET /network`
- `POST /servers/start`
- `POST /servers/stop/:id`
- `POST /network/start`
- `POST /network/stop`
- `GET /servers/:id/logs`
- `GET /ports`
- `GET /metrics`

## Redis Channels

- `blitz:core:spawn-request`
- `blitz:core:stop-request`
- `blitz:server:heartbeat`
- `blitz:server:runtime-config`

Runtime metadata key format:

- `blitz:server:runtime:<serverId>`

## Environment

See `.env.example` for all variables.
Important map-related settings:

- `MAP_WORLD_SOURCE_DIR`
- `MAP_CONFIG_SOURCE_DIR`
- `GAME_SERVER_JAR`
- `HUB_SERVER_JAR`
- `PROXY_SERVER_JAR`
- `PROXY_WORKDIR`
- `BACKEND_PLUGINS_SOURCE_DIR`
- `PROXY_PLUGINS_SOURCE_DIR`
- `AUTO_START_PROXY`
- `AUTO_START_HUBS`
- `AUTO_HUB_PREFIX`
- `PLUGIN_DATA_FOLDER`
- `RUNTIME_CONFIG_TTL_SECONDS`
- `DB_AUTO_GENERATE`
- `DB_TYPE`
- `DB_HOST`
- `DB_PORT`
- `DB_NAME`
- `DB_USERNAME`
- `DB_PASSWORD`

Port range configuration options:

- `PORT_RANGE_START` + `PORT_RANGE_END`
- or `PORT_RANGE` with shorthand format `start-end` (example: `25000-25100`)

Network bootstrap behavior:

- Core copies all `.jar` files from `BACKEND_PLUGINS_SOURCE_DIR` into each spawned backend server `plugins` folder.
- Core copies all `.jar` files from `PROXY_PLUGINS_SOURCE_DIR` into the Velocity `plugins` folder before startup.
- During `/network/start`, Velocity `lobby` target in `velocity.toml` is automatically wired to the first spawned hub port.
- Core generates `plugins/<PLUGIN_DATA_FOLDER>/database.yml` for each spawned backend instance from `DB_*` environment values.

## MySQL Docker

Use the included MySQL image scaffold under `../runtime/mysql`:

```bash
cd ../runtime/mysql
docker compose up -d --build
```

This runs MySQL with host mapping `27017 -> 3306` so backend instances can use:

- `DB_TYPE=mysql`
- `DB_HOST=localhost`
- `DB_PORT=27017`
- `DB_NAME=BlitzSG`
- `DB_USERNAME=root`
- `DB_PASSWORD=admin`
