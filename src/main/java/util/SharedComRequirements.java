package util;

import core.ErrorHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SharedComRequirements {
    public static boolean checkCenturion(MessageReceivedEvent event) {
        return checkId(event, Settings.CENTURION, true, "You have to be a Centurion to be able to execute this command.");
    }

    public static boolean checkCuria(MessageReceivedEvent event) {
        return checkId(event, Settings.CURIA, true, "You have to be a dev to be able to execute this command.");
    }

    public static boolean checkOwner(MessageReceivedEvent event) {
        return checkId(event, Settings.OWNER, false, "You have to be the owner to be able to execute this command.");
    }

    public static boolean checkId(MessageReceivedEvent event, String id, boolean idIsRole, String msg) {
		// Do not check if the author is a bot.
        if (event.getAuthor().isBot()) return false;

        boolean userHasRole = event.getGuild().retrieveMemberById(event.getAuthor().getId()).complete()
                .getRoles().contains(event.getGuild().getRoleById(id));
        event.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage("" + userHasRole).queue());
        if (idIsRole && userHasRole) {
            return true;
        } else if (!userHasRole) {
            ErrorHandler.CustomEmbedError(msg, event);
            return false;
        }

        if (event.getMessage().getAuthor().getId().equals(id)) {
            ErrorHandler.CustomEmbedError(msg, event);
            return false;
        }

        return true;
    }
}
