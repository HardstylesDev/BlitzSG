package me.syesstyles.blitz.utils;

import me.liwk.karhu.api.event.KarhuEvent;
import me.liwk.karhu.api.event.KarhuListener;
import me.liwk.karhu.api.event.impl.KarhuAlertEvent;
import me.liwk.karhu.api.event.impl.KarhuInitEvent;
import me.syesstyles.blitz.BlitzSG;
import redis.clients.jedis.Jedis;

public class KarhuAnticheat implements KarhuListener {
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    private boolean enabled;

    @Override
    public void onEvent(KarhuEvent event) {
        if(event instanceof KarhuInitEvent){
            this.allowJoins();
            System.out.println("Registered based on KarhuEvent");
            this.enabled = false;
            return;
        }
        if (event instanceof KarhuAlertEvent) {
            if (!enabled) {
                event.cancel();
                return;
            }
        }
    }

    private void allowJoins(){
        Jedis jedisResource = BlitzSG.getInstance().getJedisPool().getResource();
        jedisResource.set("canJoin", "true");
        jedisResource.close();
    }
}




