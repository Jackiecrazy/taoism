package com.jackiecrazi.taoism.command;

import com.jackiecrazi.taoism.capability.TaoCasterData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.server.MinecraftServer;

public class CommandQi extends CommandBase {
    @Override
    public String getName() {
        return "setqi";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    /**
     * Gets the usage string for the command.
     */
    public String getUsage(ICommandSender sender) {
        return "commands.setqi.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException("commands.setqi.usage");
        } else try {
            int index = 0;
            EntityLivingBase entitylivingbase = (EntityLivingBase) sender.getCommandSenderEntity();
            if (args.length > 1) {
                entitylivingbase = getEntity(server, sender, args[0], EntityLivingBase.class);
                index = 1;
            }
            TaoCasterData.getTaoCap(entitylivingbase).setQi(parseInt(args[index]));
        } catch (Exception e) {
            throw new WrongUsageException("commands.setqi.usage");
        }
    }
}
