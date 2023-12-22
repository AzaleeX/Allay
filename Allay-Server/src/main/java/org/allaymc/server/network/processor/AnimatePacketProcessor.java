package org.allaymc.server.network.processor;

import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.server.network.DataPacketProcessor;
import org.cloudburstmc.protocol.bedrock.packet.AnimatePacket;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacketType;

/**
 * Allay Project 11/22/2023
 *
 * @author Cool_Loong
 */
public class AnimatePacketProcessor extends DataPacketProcessor<AnimatePacket> {
    @Override
    public void handle(EntityPlayer player, AnimatePacket pk) {
        if (pk.getAction() == AnimatePacket.Action.SWING_ARM) {
            player.getCurrentChunk().addChunkPacket(pk, chunkLoader -> chunkLoader != player);
        }
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ANIMATE;
    }
}