package legitclient.command.commands;

import legitclient.LegitClient;
import legitclient.command.Command;
import legitclient.module.Module;
import legitclient.util.ChatUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class ListCommand extends Command {
    public ListCommand() {
        super(new ArrayList<>(Arrays.asList("list", "l", "modules", "legitclient")));
    }

    @Override
    public void runCommand(ArrayList<String> args) {
        if (!LegitClient.moduleManager.modules.isEmpty()) {
            ChatUtil.sendFormatted(String.format("%sModules:&r", LegitClient.clientName));
            for (Module module : LegitClient.moduleManager.modules.values()) {
                ChatUtil.sendFormatted(String.format("%s»&r %s&r", module.isHidden() ? "&8" : "&7", module.formatModule()));
            }
        }
    }
}
