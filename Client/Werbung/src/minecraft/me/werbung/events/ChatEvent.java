/*
 * Decompiled with CFR 0.145.
 */
package me.werbung.events;

import com.darkmagician6.eventapi.events.Event;

public class ChatEvent
implements Event {
    private String msg;

    public ChatEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

