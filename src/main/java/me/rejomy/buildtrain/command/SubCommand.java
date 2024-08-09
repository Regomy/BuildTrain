package me.rejomy.buildtrain.command;

public abstract class SubCommand extends Command {
    boolean onlyPlayer;
    public SubCommand() {}
    public SubCommand(boolean onlyPlayer) {
        this.onlyPlayer = onlyPlayer;
    }
}
