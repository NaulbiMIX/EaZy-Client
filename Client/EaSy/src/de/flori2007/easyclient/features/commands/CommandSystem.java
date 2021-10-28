/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.net.InetAddress
 *  java.util.Arrays
 *  java.util.List
 *  java.util.function.Consumer
 */
package de.flori2007.easyclient.features.commands;

import de.flori2007.easyclient.EaSyClient;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.IChatComponent;

public class CommandSystem {
    private List<String> EXPLOITS = Arrays.asList((Object[])new String[]{"ArmAnimation", "BeinAnimation", "AuthSmasher", "CustomPayload", "CustomSellload", "AntiCheatCrash1", "TabComplete", "CustomByteException", "CommandBlock", "Test", "FAWE new", "PlayerLook", "SkyCrasher", "BlazeBypass2", "BrutalBypass", "crash-1", "crash-2", "crash-3", "crash-4", "crash-5", "ExploitFixerBypass", "SpigotGuardBypass", "NzxterIstKackeBypass", "LevenProxyBypass", "TCPShieldBypass", "VentryShieldBypass"});

    public void onCommand(String[] input) {
        String command = input[0].substring(1);
        String[] args = (String[])Arrays.copyOfRange((Object[])input, (int)1, (int)input.length);
        if (command.equalsIgnoreCase("help")) {
            this.sendChatMessageWithPrefix("\u00a77---------------------------");
            this.sendChatMessageWithPrefix("\u00a76\u00a7l-authors \u00a77- List of Client Authors");
            this.sendChatMessageWithPrefix("\u00a76\u00a7l-copyip \u00a77- Copies Domain and IP");
            this.sendChatMessageWithPrefix("\u00a76\u00a7l-clearchat \u00a77- Clears your chat");
            this.sendChatMessageWithPrefix("\u00a76\u00a7l-exploits \u00a77- Show the list of available exploits");
            this.sendChatMessageWithPrefix("\u00a77---------------------------");
        } else if (command.equalsIgnoreCase("copyip")) {
            try {
                GuiScreen.setClipboardString(InetAddress.getLocalHost().getHostAddress());
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        } else if (command.equalsIgnoreCase("clearchat")) {
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
            this.sendChatMessage("");
        } else if (command.startsWith("authors")) {
            this.sendChatMessageWithPrefix("Client Coder: EnZaXD aka Flori2007");
            this.sendChatMessageWithPrefix("Helper: mori0, Emii, GodEcho, Wal Edits");
        } else if (command.startsWith("exploits")) {
            this.EXPLOITS.forEach(e -> this.sendChatMessageWithPrefix("-" + e));
        } else {
            this.EXPLOITS.forEach(e -> {
                if (command.startsWith(e)) {
                    this.sendChatMessageWithPrefix("Kr\u00e4sching...");
                    EaSyClient.INSTANCE.clientTimeOut = true;
                    EaSyClient.INSTANCE.lag = System.currentTimeMillis();
                }
            });
        }
    }

    public void sendChatMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(IChatComponent.Serializer.jsonToComponent("{text:\"" + message + "\"}"));
    }

    public void sendChatMessageWithPrefix(String message) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(IChatComponent.Serializer.jsonToComponent("{text:\"\u00a76[\u00a74\u00a7lE\u00a7c\u00a7la\u00a74\u00a7lS\u00a7c\u00a7ly\u00a76] " + message + "\"}"));
    }
}

