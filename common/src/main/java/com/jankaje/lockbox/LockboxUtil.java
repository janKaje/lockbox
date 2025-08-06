package com.jankaje.lockbox;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class LockboxUtil {
  static boolean isFrameBlock(Level level, BlockPos pos) {
    return level.getBlockState(pos).getBlock() == Lockbox.LOCKBOX_FRAME.get()
            || level.getBlockState(pos).getBlock() == Lockbox.LOCKBOX_CORE.get()
            || level.getBlockState(pos).getBlock() == Lockbox.HARDENED_LOCKBOX_FRAME.get()
            || level.getBlockState(pos).getBlock() == Lockbox.HARDENED_LOCKBOX_CORE.get();
  }

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

  public static BlockPos exploreNegative(Level level, BlockPos pos) {
    while(isFrameBlock(level, pos.below())) pos = pos.below();
    while(isFrameBlock(level, pos.west())) pos = pos.west();
    while(isFrameBlock(level, pos.north())) pos = pos.north();

    // Repeat this to account for non-corner edges
    while(isFrameBlock(level, pos.below())) pos = pos.below();
    while(isFrameBlock(level, pos.west())) pos = pos.west();

    return pos;
  }

  public static BlockPos explorePositive(Level level, BlockPos pos) {
    while(isFrameBlock(level, pos.above())) pos = pos.above();
    while(isFrameBlock(level, pos.east())) pos = pos.east();
    while(isFrameBlock(level, pos.south())) pos = pos.south();

    return pos;
  }

  static int[] getLockboxSize(BlockPos start, BlockPos end) {
    int len_x = end.getX() - start.getX() + 1;
    int len_y = end.getY() - start.getY() + 1;
    int len_z = end.getZ() - start.getZ() + 1;
    return new int[]{len_x, len_y, len_z};
  }

  public static int getMinLockboxSize(BlockPos start, BlockPos end) {
    int[] sizes = getLockboxSize(start, end);
    int min = sizes[0];
    for (int i = 1; i < sizes.length; i++) {
      if (sizes[i] < min) min = sizes[i];
    }
    return min;
  }

  public static int getMaxLockboxSize(BlockPos start, BlockPos end) {
    int[] sizes = getLockboxSize(start, end);
    int max = sizes[0];
    for (int i = 1; i < sizes.length; i++) {
      if (sizes[i] > max) max = sizes[i];
    }
    return max;
  }

  public static boolean ensureCompleteFrame(Level level, BlockPos start, BlockPos end) {
    for (int i = start.getX(); i <= end.getX(); i++) {
      if (!isFrameBlock(level, new BlockPos(i, start.getY(), start.getZ()))
              || !isFrameBlock(level, new BlockPos(i, end.getY(), end.getZ()))
              || !isFrameBlock(level, new BlockPos(i, start.getY(), end.getZ()))
              || !isFrameBlock(level, new BlockPos(i, end.getY(), start.getZ()))) {
        return false;
      }
    }
    for (int i = start.getY(); i <= end.getY(); i++) {
      if (!isFrameBlock(level, new BlockPos(start.getX(), i, start.getZ()))
              || !isFrameBlock(level, new BlockPos(end.getX(), i, end.getZ()))
              || !isFrameBlock(level, new BlockPos(start.getX(), i, end.getZ()))
              || !isFrameBlock(level, new BlockPos(end.getX(), i, start.getZ()))) {
        return false;
      }
    }
    for (int i = start.getZ(); i <= end.getZ(); i++) {
      if (!isFrameBlock(level, new BlockPos(start.getX(), start.getY(), i))
              || !isFrameBlock(level, new BlockPos(end.getX(), end.getY(), i))
              || !isFrameBlock(level, new BlockPos(start.getX(), end.getY(), i))
              || !isFrameBlock(level, new BlockPos(end.getX(), start.getY(), i))) {
        return false;
      }
    }
    return true;
  }
}
