package legitclient.command.commands;

import legitclient.LegitClient;
import legitclient.command.Command;
import legitclient.module.Module;
import legitclient.util.ChatUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class HideCommand extends Command {
    public HideCommand() {
        super(new ArrayList<>(Arrays.asList("hide", "h")));
    }

    @Override
    public void runCommand(ArrayList<String> args) {
        if (args.size() < 2) {
            ChatUtil.sendFormatted(
                    String.format("%sUsage: .%s <&omodule&r>&r", LegitClient.clientName, args.get(0).toLowerCase(Locale.ROOT))
            );
        } else if (!args.get(1).equals("*")) {
            Module module = LegitClient.moduleManager.getModule(args.get(1));
            if (module == null) {
                ChatUtil.sendFormatted(String.format("%sModule &o%s&r not found&r", LegitClient.clientName, args.get(1)));
            } else if (module.isHidden()) {
                ChatUtil.sendFormatted(String.format("%s&o%s&r is already hidden in HUD&r", LegitClient.clientName, module.getName()));
            } else {
                module.setHidden(true);
                ChatUtil.sendFormatted(String.format("%s&o%s&r has been hidden in HUD&r", LegitClient.clientName, module.getName()));
            }
        } else {
            for (Module module : LegitClient.moduleManager.modules.values()) {
                module.setHidden(true);
            }
            ChatUtil.sendFormatted(String.format("%sAll modules have been hidden in HUD&r", LegitClient.clientName));
        }
    }
}
