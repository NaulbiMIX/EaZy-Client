/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.Agent
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.UserAuthentication
 *  com.mojang.authlib.exceptions.AuthenticationException
 *  com.mojang.authlib.exceptions.AuthenticationUnavailableException
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication
 *  org.lwjgl.opengl.Display
 */
package me.werbung;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.io.File;
import java.net.Proxy;
import java.util.UUID;
import me.werbung.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Session;
import org.lwjgl.opengl.Display;

public class Werbung {
    public static Werbung instance;
    public String Client_Name = "Werbung";
    public String Client_Version = "1337";
    public ModuleManager modulemanager;
    public String msg = "Komm mal auf SkySlayer.de. Ich liebe den Server!";
    public File directory;

    public void startClient() {
        instance = this;
        Display.setTitle((String)(String.valueOf(this.Client_Name) + " v" + this.Client_Version));
        this.modulemanager = new ModuleManager();
        this.directory = new File(Minecraft.getMinecraft().mcDataDir, this.Client_Name);
        if (!this.directory.exists()) {
            this.directory.mkdir();
        }
    }

    public void ClientCommand(String msg) {
        if (msg.split(" ")[0].equalsIgnoreCase(".help")) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a79\u00a7lWERBUNG HILFE"));
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(" "));
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a77Keybind: \u00a79U \u00a77- Werbung Bizzi.tv"));
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a77Keybind: \u00a79L \u00a77- Werbung Durchrasten.de"));
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a77Keybind: \u00a79I \u00a77- Werbung TeamKyudo.de"));
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a77Keybind: \u00a79O \u00a77- Werbung MineSucht.de"));
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a77Keybind: \u00a79X \u00a77- Werbung Rewinside.tv"));
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a77Keybind: \u00a79K \u00a77- Werbung Timolia.de"));
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a77Command: \u00a79.msg <Nachricht> \u00a77- Werbungs Nachricht \u00e4ndern."));
        }
        if (msg.split(" ")[0].equalsIgnoreCase(".msg")) {
            if (msg.split(" ").length < 2) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a79\u00a7lWERBUNG \u00a77> Bitte gebe eine Nachricht ein."));
                return;
            }
            String[] args = msg.split(" ");
            String message = args[1];
            int i = 1;
            while (++i < args.length) {
                message = String.valueOf(String.valueOf(message)) + " " + args[i];
            }
            Werbung.instance.msg = message;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a79\u00a7lWERBUNG \u00a77> Werbungs Nachricht erfolgreich gesetzt!"));
            return;
        }
    }

    public static String login(String email, String password) {
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(email);
        auth.setPassword(password);
        try {
            auth.logIn();
            Minecraft.session = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
            return "\u00a7a\u00a7lLogin successful: \u00a76" + Minecraft.session.getUsername();
        }
        catch (AuthenticationUnavailableException e) {
            return "\u00a74\u00a7lCannot contact authentication server!";
        }
        catch (AuthenticationException e) {
            e.printStackTrace();
            if (e.getMessage().contains("Invalid username or password.") || e.getMessage().toLowerCase().contains("account migrated")) {
                return "\u00a74\u00a7lWrong password!";
            }
            return "\u00a74\u00a7lCannot contact authentication server!";
        }
        catch (NullPointerException e) {
            return "\u00a74\u00a7lWrong password!";
        }
    }
}

