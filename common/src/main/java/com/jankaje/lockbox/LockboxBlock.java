package com.jankaje.lockbox;

import com.jankaje.lockbox.platform.Services;
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

      // If lockbox has no interior, throw error
      int minboxsize = LockboxUtil.getMinLockboxSize(start, end);
      if (minboxsize < 3) {
        player.displayClientMessage(
          Component.translatable("message.lockbox.toosmall"),
          true
        );
        return InteractionResult.FAIL;
      }

      // If lockbox is bigger than max size, throw error
      int boxsize = LockboxUtil.getMaxLockboxSize(start, end);
      if (boxsize > Services.CONFIG.maxSize()) {
        player.displayClientMessage(
          Component.translatable("message.lockbox.toobig", Services.CONFIG.maxSize()),
          true
        );
        return InteractionResult.FAIL;
      }

      // If lockbox doesn't have complete frame, throw error
      if (!LockboxUtil.ensureCompleteFrame(level, start, end)) {
        player.displayClientMessage(
                Component.translatable("message.lockbox.incomplete_frame"),
                true
        );
        return InteractionResult.FAIL;
      }

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
