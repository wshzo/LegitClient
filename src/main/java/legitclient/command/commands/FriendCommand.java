package legitclient.command.commands;

import legitclient.LegitClient;
import legitclient.command.Command;
import legitclient.enums.ChatColors;
import legitclient.util.ChatUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class FriendCommand extends Command {
    public FriendCommand() {
        super(new ArrayList<>(Arrays.asList("friend", "f")));
    }

    @Override
    public void runCommand(ArrayList<String> args) {
        if (args.size() >= 2) {
            String subCommand = args.get(1).toLowerCase(Locale.ROOT);
            switch (subCommand) {
                case "add":
                    if (args.size() < 3) {
                        ChatUtil.sendFormatted(
                                String.format("%sUsage: .%s add <&oname&r>&r", LegitClient.clientName, args.get(0).toLowerCase(Locale.ROOT))
                        );
                        return;
                    }
                    String added = LegitClient.friendManager.add(args.get(2));
                    if (added == null) {
                        ChatUtil.sendFormatted(String.format("%s&o%s&r is already in your friend list&r", LegitClient.clientName, args.get(2)));
                        return;
                    }
                    ChatUtil.sendFormatted(String.format("%sAdded &o%s&r to your friend list&r", LegitClient.clientName, added));
                    return;
                case "remove":
                    if (args.size() < 3) {
                        ChatUtil.sendFormatted(
                                String.format("%sUsage: .%s remove <&oname&r>&r", LegitClient.clientName, args.get(0).toLowerCase(Locale.ROOT))
                        );
                        return;
                    }
                    String removed = LegitClient.friendManager.remove(args.get(2));
                    if (removed == null) {
                        ChatUtil.sendFormatted(String.format("%s&o%s&r is not in your friend list&r", LegitClient.clientName, args.get(2)));
                        return;
                    }
                    ChatUtil.sendFormatted(String.format("%sRemoved &o%s&r from your friend list&r", LegitClient.clientName, removed));
                    return;
                case "list":
                    ArrayList<String> list = LegitClient.friendManager.getPlayers();
                    if (list.isEmpty()) {
                        ChatUtil.sendFormatted(String.format("%sNo friends&r", LegitClient.clientName));
                        return;
                    }
                    ChatUtil.sendFormatted(String.format("%sFriends:&r", LegitClient.clientName));
                    for (String friend : list) {
                        ChatUtil.sendRaw(String.format(ChatColors.formatColor("   &o%s&r"), friend));
                    }
                    return;
                case "clear":
                    LegitClient.friendManager.clear();
                    ChatUtil.sendFormatted(String.format("%sCleared your friend list&r", LegitClient.clientName));
                    return;
            }
        }
        ChatUtil.sendFormatted(
                String.format("%sUsage: .%s <&oadd&r/&oremove&r/&olist&r/&oclear&r>&r", LegitClient.clientName, args.get(0).toLowerCase(Locale.ROOT))
        );
    }
}
