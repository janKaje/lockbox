package com.jankaje.lockbox;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.*;

public class LockboxData extends SavedData {
  Map<BlockPos, BlockPos> data = new HashMap<>();
  Map<BlockPos, List<GameProfile>> players = new HashMap<>();
  public static final Codec<LockboxData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
    Codec.INT.listOf().listOf()
      .fieldOf("data")
      .forGetter(
        data -> {
          List<List<Integer>> list = new ArrayList<>();
          for(var key: data.data.keySet()) {
            var value = data.data.get(key);
            List<Integer> pos = new ArrayList<>();
            pos.add(key.getX());
            pos.add(key.getY());
            pos.add(key.getZ());
            pos.add(value.getX());
            pos.add(value.getY());
            pos.add(value.getZ());
            list.add(pos);
          }
          return list;
        }
      ),
    Codec.INT.listOf().listOf()
      .fieldOf("playerKeys")
      .forGetter(
        data -> {
          List<List<Integer>> list = new ArrayList<>();
          for(var key: data.data.keySet()) {
            if(data.players.containsKey(key))
              for(int i = 0; i < data.players.get(key).size(); i++) {
                var value = data.data.get(key);
                List<Integer> pos = new ArrayList<>();
                pos.add(key.getX());
                pos.add(key.getY());
                pos.add(key.getZ());
                pos.add(value.getX());
                pos.add(value.getY());
                pos.add(value.getZ());
                list.add(pos);
              }
          }
          return list;
        }
      ),
    Codec.STRING.listOf()
      .fieldOf("playerValues")
      .forGetter(
        data -> {
          List<String> list = new ArrayList<>();
          for(var key: data.data.keySet())
            if(data.players.containsKey(key))
              for(var profile: data.players.get(key)) {
                if(profile.getId() != null)
                  list.add(profile.getId().toString());
                else
                  list.add(profile.getName());
              }
          return list;
        }
      )
  ).apply(instance, LockboxData::new));

  LockboxData(List<List<Integer>> list, List<List<Integer>> playerKeys, List<String> playerValues) {
    for(var pos: list)
      add(pos.get(0), pos.get(1), pos.get(2), pos.get(3), pos.get(4), pos.get(5));

    for(int i = 0; i < playerKeys.size(); i++) {
      var key = new BlockPos(playerKeys.get(i).get(0), playerKeys.get(i).get(1), playerKeys.get(i).get(2));
      var value = playerValues.get(i);

      try {
        add(key, new GameProfile(UUID.fromString(value), null));
      } catch (IllegalArgumentException e) {
        add(key, new GameProfile(null, value));
      }
    }
  }

  public LockboxData() { setDirty(); }

  public void add(BlockPos start, BlockPos end) {
    var minPos = min(start, end);
    data.put(minPos, max(start, end));
    if(!players.containsKey(minPos))
      players.put(minPos, new ArrayList<>());
    this.setDirty();
  }

  public void add(BlockPos pos, Player player) {
    if(!players.containsKey(pos))
      players.put(pos, new ArrayList<>());
    players.get(pos).add(player.getGameProfile());
    this.setDirty();
  }

  public void add(BlockPos pos, GameProfile player) {
    if(!players.containsKey(pos))
      players.put(pos, new ArrayList<>());
    players.get(pos).add(player);
    this.setDirty();
  }

  void add(int xi, int yi, int zi, int xa, int ya, int za) {
    data.put(new BlockPos(xi, yi, zi), new BlockPos(xa, ya, za));
  }

  public void remove(BlockPos start) {
    data.remove(start);
    players.remove(start);
  }

  public boolean check(BlockPos pos, Player player) {
    var start = lookup(pos);
    if(start == null) return true;
    var list = players.get(start);
    if(list != null)
      return list.contains(player.getGameProfile());
    return true;
  }

  public BlockPos lookup(BlockPos pos) {
    for(var key: data.keySet())
      if (key.getX() <= pos.getX() && key.getY() <= pos.getY() && key.getZ() <= pos.getZ()
          && data.get(key).getX() >= pos.getX() && data.get(key).getY() >= pos.getY()
          && data.get(key).getZ() >= pos.getZ())
        return key;
    return null;
  }

  @Override
  public CompoundTag save(CompoundTag compoundTag) {
    var result = CODEC.encodeStart(NbtOps.INSTANCE, this).get().left();
    result.ifPresent(tag -> compoundTag.put("data", tag));
    return compoundTag;
  }

  public static LockboxData load(CompoundTag compoundTag) {
    var result = CODEC.decode(NbtOps.INSTANCE, compoundTag.get("data")).get().left();
    if(result.isPresent() && result.get().getFirst() != null)
      return result.get().getFirst();
    return null;
  }

  public static LockboxData computeIfAbsent(ServerLevel level) {
    return level.getDataStorage().computeIfAbsent(LockboxData::load, LockboxData::new, "lockbox_coords");
  }

  BlockPos min(BlockPos a, BlockPos b) {
    int x = Math.min(a.getX(), b.getX());
    int y = Math.min(a.getY(), b.getY());
    int z = Math.min(a.getZ(), b.getZ());
    return new BlockPos(x, y, z);
  }

  BlockPos max(BlockPos a, BlockPos b) {
    int x = Math.max(a.getX(), b.getX());
    int y = Math.max(a.getY(), b.getY());
    int z = Math.max(a.getZ(), b.getZ());
    return new BlockPos(x, y, z);
  }
}
