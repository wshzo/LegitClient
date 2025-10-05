package legitclient.command.commands;

import legitclient.LegitClient;
import legitclient.command.Command;
import legitclient.module.Module;
import legitclient.util.ChatUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super(new ArrayList<>(Arrays.asList("toggle", "t")));
    }

    @Override
    public void runCommand(ArrayList<String> args) {
        if (args.size() < 2) {
            ChatUtil.sendFormatted(
                    String.format("%sUsage: .%s <&omodule&r>&r", LegitClient.clientName, args.get(0).toLowerCase(Locale.ROOT))
            );
        } else {
            Module module = LegitClient.moduleManager.getModule(args.get(1));
            if (module == null) {
                ChatUtil.sendFormatted(String.format("%sModule not found (&o%s&r)&r", LegitClient.clientName, args.get(1)));
            } else {
                boolean changed = true;
                if (args.size() >= 3) {
                    if (args.get(2).equalsIgnoreCase("true")
                            || args.get(2).equalsIgnoreCase("on")
                            || args.get(2).equalsIgnoreCase("1")) {
                        changed = !module.isEnabled();
                    } else if (args.get(2).equalsIgnoreCase("false")
                            || args.get(2).equalsIgnoreCase("off")
                            || args.get(2).equalsIgnoreCase("0")) {
                        changed = module.isEnabled();
                    }
                }
                if (changed && module.toggle()) {
                    ChatUtil.sendFormatted(String.format("%s%s: %s&r", LegitClient.clientName, module.getName(), module.isEnabled() ? "&a&lON" : "&c&lOFF"));
                }
            }
        }
    }
}
