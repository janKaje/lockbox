package com.ssblur.lockbox;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class LockboxUtil {
  public static boolean isOnBorder(BlockPos pos, BlockPos start, BlockPos end) {
    boolean between = false;

    if(pos.getX() != start.getX() && pos.getX() != end.getX()) {
      if(pos.getX() >= start.getX() && pos.getX() >= start.getX())
        between = true;else
        return false;
    }
    if(pos.getY() != start.getY() && pos.getY() != end.getY()) {
      if(!between && pos.getY() >= start.getY() && pos.getY() >= start.getY())
        between = true;
      else
        return false;
    }
    if(pos.getZ() != start.getZ() && pos.getZ() != end.getZ()) {
      if(!between && pos.getZ() >= start.getZ() && pos.getZ() >= start.getZ())
        between = true;
      else
        return false;
    }

    return true;
  }

  @Nullable
  public static BlockPos exploreNegative(Level level, BlockPos pos) {
    while(
      level.getBlockState(pos.below()).getBlock() == Lockbox.LOCKBOX_FRAME.get()
        || level.getBlockState(pos.below()).getBlock() == Lockbox.LOCKBOX_CORE.get()
    ) pos = pos.below();

    while(
      level.getBlockState(pos.west()).getBlock() == Lockbox.LOCKBOX_FRAME.get()
        || level.getBlockState(pos.west()).getBlock() == Lockbox.LOCKBOX_CORE.get()
    ) pos = pos.west();

    while(
      level.getBlockState(pos.north()).getBlock() == Lockbox.LOCKBOX_FRAME.get()
        || level.getBlockState(pos.north()).getBlock() == Lockbox.LOCKBOX_CORE.get()
    ) pos = pos.north();

    // Repeat this to account for non-corner edges
    while(
      level.getBlockState(pos.below()).getBlock() == Lockbox.LOCKBOX_FRAME.get()
        || level.getBlockState(pos.below()).getBlock() == Lockbox.LOCKBOX_CORE.get()
    ) pos = pos.below();

    while(
      level.getBlockState(pos.west()).getBlock() == Lockbox.LOCKBOX_FRAME.get()
        || level.getBlockState(pos.west()).getBlock() == Lockbox.LOCKBOX_CORE.get()
    ) pos = pos.west();

    return pos;
  }

  @Nullable
  public static BlockPos explorePositive(Level level, BlockPos pos) {
    while(
      level.getBlockState(pos.above()).getBlock() == Lockbox.LOCKBOX_FRAME.get()
        || level.getBlockState(pos.above()).getBlock() == Lockbox.LOCKBOX_CORE.get()
    ) pos = pos.above();

    while(
      level.getBlockState(pos.east()).getBlock() == Lockbox.LOCKBOX_FRAME.get()
        || level.getBlockState(pos.east()).getBlock() == Lockbox.LOCKBOX_CORE.get()
    ) pos = pos.east();

    while(
      level.getBlockState(pos.south()).getBlock() == Lockbox.LOCKBOX_FRAME.get()
        || level.getBlockState(pos.south()).getBlock() == Lockbox.LOCKBOX_CORE.get()
    ) pos = pos.south();

    return pos;
  }
}
