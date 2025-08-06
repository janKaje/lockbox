package com.ssblur.lockbox;

import com.ssblur.lockbox.platform.Services;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.utils.value.IntValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class LockboxEvents implements BlockEvent.Break, BlockEvent.Place, InteractionEvent.RightClickBlock {
  @Override
  public EventResult breakBlock(Level level, BlockPos pos, BlockState state, ServerPlayer player, @Nullable IntValue xp) {
    if(level instanceof ServerLevel server) {
      var data = LockboxData.computeIfAbsent(server);
      if (data.check(pos, player)) {
        return EventResult.pass();
      } else {
        if (!Services.CONFIG.allowFrameBreak()) {
          player.displayClientMessage(Component.translatable("message.lockbox.break"), true);
          return EventResult.interruptFalse();
        }
        var start = data.lookup(pos);
        if (start != null && LockboxUtil.isOnBorder(pos, start, data.data.get(start))) {
          data.remove(start);
          return EventResult.pass();
        }

        player.displayClientMessage(Component.translatable("message.lockbox.break"), true);
        return EventResult.interruptFalse();
      }
    }
    return EventResult.pass();
  }

  @Override
  public EventResult click(Player player, InteractionHand hand, BlockPos pos, Direction face) {
    var level = player.level();

    if(level instanceof ServerLevel server)
      if(LockboxData.computeIfAbsent(server).check(pos, player)) {
        return EventResult.pass();
      } else {
        player.displayClientMessage(Component.translatable("message.lockbox.use"), true);
        return EventResult.interruptFalse();
      }

    return EventResult.pass();
  }

  @Override
  public EventResult placeBlock(Level level, BlockPos pos, BlockState state, @Nullable Entity placer) {
    if(level instanceof ServerLevel server && placer instanceof Player player) {
      var data = LockboxData.computeIfAbsent(server);
      if (!data.check(pos, player)) {
        player.displayClientMessage(Component.translatable("message.lockbox.place"), true);
        return EventResult.interruptFalse();
      }
    }
    return EventResult.pass();
  }
}
