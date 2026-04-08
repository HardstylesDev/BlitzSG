async function postJson(url, body) {
    const res = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body || {})
    });

    const data = await res.json().catch(() => ({}));
    if (!res.ok) {
        throw new Error(data.error || `Request failed: ${url}`);
    }
    return data;
}

async function loadDashboard() {
    const res = await fetch('/network');
    if (!res.ok) {
        throw new Error('Failed loading /network');
    }

    const data = await res.json();
    document.getElementById('proxy').textContent = JSON.stringify(data.proxy, null, 2);
    document.getElementById('ports').textContent = JSON.stringify(data.portRange, null, 2);

    const tbody = document.querySelector('#servers tbody');
    tbody.innerHTML = '';
    for (const server of data.servers || []) {
        const tr = document.createElement('tr');
        tr.innerHTML = `<td>${server.id || ''}</td><td>${server.type || ''}</td><td>${server.status || ''}</td><td>${server.port || ''}</td><td>${server.pid || ''}</td><td><button class="stop-btn" data-id="${server.id}">Stop</button></td>`;
        tbody.appendChild(tr);
    }

    document.querySelectorAll('.stop-btn').forEach((button) => {
        button.addEventListener('click', async () => {
            const id = button.getAttribute('data-id');
            try {
                await postJson(`/servers/stop/${encodeURIComponent(id)}`, {});
                await loadDashboard();
            } catch (err) {
                alert(err.message);
            }
        });
    });
}

document.getElementById('refresh').addEventListener('click', () => {
    loadDashboard().catch((err) => {
        alert(err.message);
    });
});

document.getElementById('networkStart').addEventListener('click', async () => {
    try {
        await postJson('/network/start', {
            "startProxy": true,
            "hubCount": 1
        });
        await loadDashboard();
    } catch (err) {
        alert(err.message);
    }
});

document.getElementById('networkStop').addEventListener('click', async () => {
    try {
        await postJson('/network/stop', {});
        await loadDashboard();
    } catch (err) {
        alert(err.message);
    }
});

document.getElementById('proxyStart').addEventListener('click', async () => {
    try {
        await postJson('/proxy/start', {});
        await loadDashboard();
    } catch (err) {
        alert(err.message);
    }
});

document.getElementById('proxyStop').addEventListener('click', async () => {
    try {
        await postJson('/proxy/stop', {});
        await loadDashboard();
    } catch (err) {
        alert(err.message);
    }
});

document.getElementById('startServerForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    const id = document.getElementById('serverId').value.trim();
    const type = document.getElementById('serverType').value;
    const mapName = document.getElementById('mapName').value.trim();

    if (!id) {
        alert('Server id is required');
        return;
    }

    const payload = {
        id,
        serverName: id,
        type
    };

    if (type === 'game' && mapName) {
        payload.mapName = mapName;
    }

    try {
        await postJson('/servers/start', payload);
        await loadDashboard();
    } catch (err) {
        alert(err.message);
    }
});

loadDashboard().catch((err) => {
    alert(err.message);
});
