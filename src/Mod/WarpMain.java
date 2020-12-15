package Mod;

import Mod.HUD.client.GUIeventhandler;
import Mod.HUD.client.SkyboxEventHandler;
import Mod.server.WarpCheckLoop;
import Mod.server.WarpJumpListener;
import api.DebugFile;
import api.listener.events.controller.ClientInitializeEvent;
import api.listener.events.controller.ServerInitializeEvent;
import api.mod.StarMod;
import api.network.packets.PacketUtil;

/**
 * STARMADE MOD
 * CREATOR: Max1M
 * DATE: 14.10.2020
 * TIME: 19:06
 */

/**
 * the main class where the mod is run from by starloader.
 */
public class WarpMain extends StarMod {
    /**
     * empty method required for jar to build correctly. prints "hello space" if run.
     */
    public static void main(String[] args) {
        System.out.println("hello space!");
    }
    public static StarMod instance;
    /**
     *  sets mod information like author, name and version.
     */
    @Override
    public void onGameStart() {
        super.onGameStart();
        this.setModVersion("0.6");
        this.setModName("WarpSpace");
        this.setModAuthor("IR0NSIGHT");
        this.setModDescription("an alternative FTL system");
        DebugFile.log("WarpSpace info set.",this);
        this.setSMDResourceId(8166);
    }
    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        DebugFile.log("enabled.",this);
        PacketUtil.registerPacket(PacketSCUpdateWarp.class);
    }

    @Override
    public void onServerCreated(ServerInitializeEvent event) {
        super.onServerCreated(event);
        DebugFile.log("WarpSpace creating listeners at server creation",this);
    //    JumpListener.createListener();
        WarpJumpListener.createListener();
        WarpCheckLoop.loop(25); //TODO use a frequency from a config
    //    CheeseCatchLoop.createLoop();
    }

    @Override
    public void onClientCreated(ClientInitializeEvent event) {
        super.onClientCreated(event);
        DebugFile.log("calling static method to register to GUI draw listener", WarpMain.instance);
        GUIeventhandler.addHUDDrawListener();
    //    SkyboxEventHandler.CreateListener();
    }
}
