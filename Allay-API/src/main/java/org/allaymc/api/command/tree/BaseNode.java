package org.allaymc.api.command.tree;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.allaymc.api.command.CommandResult;
import org.allaymc.api.command.CommandSender;
import org.allaymc.api.command.SenderType;
import org.allaymc.api.command.exception.CommandParseException;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamData;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamOption;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Allay Project 2023/12/29
 *
 * @author daoge_cmd
 */
public abstract class BaseNode implements CommandNode {
    protected int depth;
    protected CommandNode parent;
    protected List<CommandNode> leaves;
    @Getter
    @Setter
    protected CommandNode optionalLeaf;
    protected boolean optional = false;
    protected String name;
    protected Function<CommandContext, CommandResult> executor;
    @Getter
    protected Object defaultValue;
    @Getter
    @Setter
    protected int minArgCostBranch;
    @Getter
    @Setter
    protected int maxArgCostBranch;
    protected Set<CommandParamOption> paramOptions = EnumSet.noneOf(CommandParamOption.class);

    public BaseNode(String name, CommandNode parent) {
        this(name, parent, null);
    }

    public BaseNode(String name, CommandNode parent, Object defaultValue) {
        this.name = name;
        this.parent = parent;
        this.defaultValue = defaultValue;
    }

    @Override
    public CommandNode defaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean isOptional() {
        return optional;
    }

    @Override
    public CommandNode optional() {
        // No need to set again if already optional
        if (optional) return this;
        // A node can only have one optional leaf
        if (parent.getOptionalLeaf() != null) {
            throw new IllegalArgumentException("A node can only have one optional leaf node");
        }
        this.optional = true;
        parent.setOptionalLeaf(this);
        // If the node is optional, minimum argument cost is 0, otherwise it's 1
        setMinArgCostBranch(optional ? 0 : 1);
        // Update minimum argument cost for branches (optional does not affect maximum argument cost)
        updateMinArgCostBranch(this);
        return this;
    }

    @Override
    public int depth() {
        return depth;
    }

    @Override
    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public CommandNode parent() {
        return parent;
    }

    @Override
    public CommandNode up(int count) {
        Preconditions.checkArgument(count >= 1);
        if (count > depth) {
            throw new CommandParseException("ParamNode.up(count): count is bigger than depth");
        }
        CommandNode node = this;
        for (int i = 0; i < count; i++) {
            node = node.parent();
        }
        return node;
    }


    @Override
    public CommandNode nextNode(CommandContext context) {
        if (isLeaf()) {
            return null;
        }
        if (context.haveUnhandledArg()) {
            var leftArgCount = context.getLeftArgCount();
            for (var leaf : leaves) {
                if (leftArgCount >= leaf.getMinArgCostBranch() && leftArgCount <= leaf.getMaxArgCostBranch() && leaf.match(context)) {
                    return leaf;
                }
            }
        } else {
            var optionalLeaf = getOptionalLeaf();
            if (optionalLeaf != null) {
                // Ignore RootNode, so the index is optionalLeaf.depth() - 1
                context.putResult(optionalLeaf.getDefaultValue());
                return optionalLeaf;
            }
        }
        return null;
    }

    @Override
    public boolean isLeaf() {
        return leaves == null;
    }

    @Override
    public List<CommandNode> getLeaves() {
        return leaves;
    }

    @Override
    public CommandNode addLeaf(CommandNode leaf) {
        if (leaves == null) {
            leaves = new ArrayList<>();
        }
        leaf.setDepth(depth + 1);
        leaf.setMaxArgCostBranch(leaf.getMaxArgCost());
        updateMaxArgCostBranch(leaf);
        // Optional parameters cannot be followed by non-optional parameters
        // Minimum argument cost for branches is updated in optional()
        if (optional) {
            leaf.optional();
        }
        leaves.add(leaf);
        return leaf;
    }

    protected void updateMinArgCostBranch(CommandNode leaf) {
        var p = leaf.parent();
        while (!p.isRoot()) {
            var min = p.getMinArgCostBranch() + leaf.getMinArgCostBranch();
            if (min < p.getMinArgCostBranch()) {
                p.setMinArgCostBranch(min);
            }
            leaf = p;
            p = leaf.parent();
        }
    }

    protected void updateMaxArgCostBranch(CommandNode leaf) {
        var p = leaf.parent();
        while (!p.isRoot()) {
            var max = p.getMaxArgCostBranch() + leaf.getMaxArgCostBranch();
            if (max > p.getMaxArgCostBranch()) {
                p.setMaxArgCostBranch(max);
            }
            leaf = p;
            p = leaf.parent();
        }
    }

    @Override
    public <SENDER_TYPE extends CommandSender> CommandNode exec(BiFunction<CommandContext, SENDER_TYPE, CommandResult> biExecutor, SenderType<SENDER_TYPE> senderType) {
        this.executor = context -> {
            var sender = context.getSender();
            if (senderType.validate(sender)) {
                return biExecutor.apply(context, senderType.caster().apply(context.getSender()));
            } else {
                context.addInvalidExecutorError(senderType);
                return context.fail();
            }
        };
        return this;
    }

    @Override
    public CommandResult applyExecutor(CommandContext context) {
        return executor.apply(context);
    }

    @Override
    public CommandNode addParamOption(CommandParamOption option) {
        paramOptions.add(option);
        return this;
    }

    @Override
    public CommandParamData toNetworkData() {
        var data = new CommandParamData();
        data.setName(name);
        data.setOptional(optional);
        data.getOptions().addAll(paramOptions);
        return data;
    }
}
