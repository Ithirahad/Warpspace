package me.iron.WarpSpace.Mod.client;


import api.DebugFile;
import api.mod.StarMod;
import api.utils.game.PlayerUtils;
import api.utils.game.chat.CommandInterface;
import me.iron.WarpSpace.Mod.WarpMain;
import org.schema.common.util.linAlg.Vector3i;
import org.schema.game.common.data.player.PlayerState;
import org.schema.game.server.data.GameServerState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;


/**
 * STARMADE MOD
 * CREATOR: Max1M
 * DATE: 21.12.2020
 * TIME: 14:27
 */

/**
 * runs clientside for HUD arranging on runtime
 */
public class DebugUI implements CommandInterface {

    public static Integer[] parseText(String text, String keyword, String separator) {
        if (!text.contains(keyword)) {
            DebugFile.log("text does not contain keyword.");
            return null;
        }

        String s = text;
        s = s.replace(keyword,""); //remove keyword
        s = s.replace(" ",""); //remove space
        DebugFile.log("pasreText: after removing keyword " + keyword + ", string is: " + s);
        String[] parts = s.split(separator);
        Integer[] arr = new Integer[parts.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }
        DebugFile.log("integer[] is: " + arr.toString());
        return arr;
    }

    @Override
    public String getCommand() {
        return "WS";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"Warpspace","wsp","ws"};
    }

    @Override
    public String getDescription() {
        return "Debug command for warpspace";
    }

    @Override
    public boolean isAdminOnly() {
        return true;
    }

    @Override
    public boolean onCommand(PlayerState playerState, String[] strings) {
        if (strings[0].equalsIgnoreCase("beacons")) {
            StringBuilder b = new StringBuilder("All sectors with active beacons:");
            Collection<Vector3i> ss = WarpMain.instance.beaconManagerServer.getBeaconSectors();
            for (Vector3i s: ss) {
                b.append("Sector ").append(s).append("[");
                ArrayList<String> beacons = WarpMain.instance.beaconManagerServer.getBeacons(s);
                for (String UID: beacons) {
                    b.append(UID).append(", ");
                }
                b.append("]\n");
            }
            echo(b.toString(),playerState);
            return true;
        }
        return false;
    }

    @Override
    public void serverAction(@Nullable PlayerState playerState, String[] strings) {

    }

    @Override
    public StarMod getMod() {
        return null;
    }

    public static void echo(String mssg, @Nullable PlayerState p) {
        DebugFile.log("[WARPSPACE-DEBUG] "+mssg);
        if (p == null) {
            if (GameServerState.instance!=null) {
                for (PlayerState pl: GameServerState.instance.getPlayerStatesByName().values()) {
                //    PlayerUtils.sendMessage(pl,mssg);
                }
            }
        } else {
            PlayerUtils.sendMessage(p,mssg);
        }
    }
}
