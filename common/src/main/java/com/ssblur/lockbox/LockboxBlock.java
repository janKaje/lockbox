package com.ssblur.lockbox;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class LockboxBlock extends Block {
  public LockboxBlock(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
    if(level instanceof ServerLevel server) {
      var start = LockboxUtil.exploreNegative(level, blockPos);
      var end = LockboxUtil.explorePositive(level, start);
      LockboxData.computeIfAbsent(server).add(start, end);
      if(player != null) {
        LockboxData.computeIfAbsent(server).add(start, player);
        player.displayClientMessage(
          Component.translatable("message.lockbox.created", start.getX(), start.getY(), start.getZ(), end.getX(), end.getY(), end.getZ()),
          true
        );
      }
    }
    return InteractionResult.SUCCESS;
  }
}
