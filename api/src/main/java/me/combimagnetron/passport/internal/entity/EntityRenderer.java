package me.combimagnetron.passport.internal.entity;

import me.combimagnetron.passport.event.EventBus;
import me.combimagnetron.passport.event.impl.connection.UserJoinEvent;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;

import java.util.HashMap;

public class EntityRenderer {
    //private final ScheduledThreadPoolExecutor threadPoolExecutor = (ScheduledThreadPoolExecutor) Executors.newVirtualThreadPerTaskExecutor();
    private final HashMap<Entity, Vector3d> entityPositionMap = new HashMap<>();

    private EntityRenderer() {
        EventBus.subscribe(UserJoinEvent.class, null, userJoinEvent -> {
            
        });
    }

}
