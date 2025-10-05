package legitclient.management;

import legitclient.enums.ChatColors;

import java.awt.*;
import java.io.File;

public class FriendManager extends PlayerFileManager {
    public FriendManager() {
        super(new File("./config/LegitClient/", "friends.txt"), new Color(ChatColors.DARK_GREEN.toAwtColor()));
    }
}
