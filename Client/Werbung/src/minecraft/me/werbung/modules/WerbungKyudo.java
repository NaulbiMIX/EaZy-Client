/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 */
package me.werbung.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.werbung.Werbung;
import me.werbung.events.ChatEvent;
import me.werbung.events.EventUpdate;
import me.werbung.modules.Category;
import me.werbung.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class WerbungKyudo
extends Module {
    Long time = System.currentTimeMillis() + 10010L;
    List<String> players = new ArrayList<String>();
    List<String> accepted = new ArrayList<String>();
    int i = 0;

    public WerbungKyudo() {
        super("WerbungKyudo", "\u00a77TeamKyudo Werbung (\u00a79I\u00a77)", 23, Category.CHAT);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (System.currentTimeMillis() > this.time) {
            if (this.players.size() != 0) {
                if (this.i > this.players.size() - 1) {
                    this.mc.thePlayer.addChatMessage(new ChatComponentText("\u00a79\u00a7lWERBUNG \u00a77> Warte auf neue Spieler..."));
                    this.updateList();
                    this.time = System.currentTimeMillis() + 2002L;
                    return;
                }
                if (this.accepted.size() != 0) {
                    String name = this.accepted.get(0);
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage("/msg " + name + " " + Werbung.instance.msg));
                    this.accepted.remove(name);
                    this.time = System.currentTimeMillis() + 2002L;
                    return;
                }
                if (this.i >= 16 && this.i % 16 == 0) {
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage("/friend removeall"));
                } else {
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage("/friend add " + this.players.get(this.i)));
                }
                this.time = System.currentTimeMillis() + 2002L;
                ++this.i;
                if (this.i >= 16 && this.i % 16 == 0) {
                    this.time = System.currentTimeMillis() + 20020L;
                }
            } else {
                this.mc.thePlayer.addChatMessage(new ChatComponentText("\u00a79\u00a7lWERBUNG \u00a77> Spieler liste leer!"));
                this.time = System.currentTimeMillis() + 2002L;
            }
            this.updateList();
        }
    }

    @EventTarget
    public void onChat(ChatEvent ec) {
        if (ec.getMsg().contains("hat deine Freundschaftsanfrage angenommen.")) {
            String name = ec.getMsg().replace("[Freunde] ", "").replace(" hat deine Freundschaftsanfrage angenommen.", "");
            this.accepted.add(name);
        }
    }

    public void updateList() {
        int count = 0;
        Collection playersC = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
        for (NetworkPlayerInfo i : playersC) {
            String name = i.getGameProfile().getName();
            if (this.players.contains(name)) continue;
            this.players.add(name);
            ++count;
        }
        this.mc.thePlayer.addChatMessage(new ChatComponentText("\u00a79\u00a7lWERBUNG \u00a77> Spieler Liste aktualisiert! (\u00a7a+" + count + "\u00a77)"));
    }

    @Override
    public void onEnable() {
        this.time = System.currentTimeMillis() + 3003L;
        Collection playersC = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
        for (NetworkPlayerInfo i : playersC) {
            String name = i.getGameProfile().getName();
            this.players.add(name);
        }
        this.mc.thePlayer.addChatMessage(new ChatComponentText("\u00a79\u00a7lWERBUNG \u00a77> TeamKyudo-Werbungs Modul aktiviert! (\u00a79" + this.players.size() + "\u00a77)"));
        EventManager.register(this);
    }

    @Override
    public void onDisable() {
        this.players.clear();
        this.accepted.clear();
        this.i = 0;
        this.mc.thePlayer.addChatMessage(new ChatComponentText("\u00a79\u00a7lWERBUNG \u00a77> TeamKyudo-Werbungs Modul deaktiviert!"));
        EventManager.unregister(this);
    }
}

