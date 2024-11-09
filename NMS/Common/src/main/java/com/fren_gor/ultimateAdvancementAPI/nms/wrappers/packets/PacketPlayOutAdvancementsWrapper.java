package com.fren_gor.ultimateAdvancementAPI.nms.wrappers.packets;

import com.fren_gor.ultimateAdvancementAPI.nms.util.ReflectionUtil;
import com.fren_gor.ultimateAdvancementAPI.nms.wrappers.MinecraftKeyWrapper;
import com.fren_gor.ultimateAdvancementAPI.nms.wrappers.advancement.AdvancementWrapper;
import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Set;

/**
 * Wrapper class for NMS {@code PacketPlayOutAdvancements}.
 */
public abstract class PacketPlayOutAdvancementsWrapper implements ISendable {

    private static final Constructor<? extends PacketPlayOutAdvancementsWrapper> resetConstructor, sendConstructor, removeConstructor;

    static {
        var clazz = ReflectionUtil.getWrapperClass(PacketPlayOutAdvancementsWrapper.class);
        Preconditions.checkNotNull(clazz, "PacketPlayOutAdvancementsWrapper implementation not found.");
        try {
            resetConstructor = clazz.getDeclaredConstructor();
            sendConstructor = clazz.getDeclaredConstructor(Map.class);
            removeConstructor = clazz.getDeclaredConstructor(Set.class);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Couldn't initialize PacketPlayOutAdvancementsWrapper.", e);
        }
    }

    /**
     * Creates a new {@code PacketPlayOutAdvancementsWrapper} which removes every previously sent advancement from the advancement GUI.
     *
     * @return A new {@code PacketPlayOutAdvancementsWrapper} which removes every previously sent advancement from the advancement GUI.
     * @throws ReflectiveOperationException If reflections goes wrong.
     */
    @NotNull
    public static PacketPlayOutAdvancementsWrapper craftResetPacket() throws ReflectiveOperationException {
        return resetConstructor.newInstance();
    }

    /**
     * Creates a new {@code PacketPlayOutAdvancementsWrapper} which adds some advancements to the advancement GUI.
     *
     * @param toSend The {@link Map} of the advancement to send paired with their respective progressions to display.
     * @return A new {@code PacketPlayOutAdvancementsWrapper} which adds some advancements to the advancement GUI.
     * @throws ReflectiveOperationException If reflections goes wrong.
     */
    @NotNull
    public static PacketPlayOutAdvancementsWrapper craftSendPacket(@NotNull Map<AdvancementWrapper, Integer> toSend) throws ReflectiveOperationException {
        Preconditions.checkNotNull(toSend, "Map<AdvancementWrapper, Integer> is null.");
        return sendConstructor.newInstance(toSend);
    }

    /**
     * Creates a new {@code PacketPlayOutAdvancementsWrapper} which removes some advancements from the advancement GUI.
     *
     * @param toRemove The {@link Set} containing the namespaced keys of the advancements to remove.
     * @return A new {@code PacketPlayOutAdvancementsWrapper} which removes some advancements from the advancement GUI.
     * @throws ReflectiveOperationException If reflections goes wrong.
     */
    @NotNull
    public static PacketPlayOutAdvancementsWrapper craftRemovePacket(@NotNull Set<MinecraftKeyWrapper> toRemove) throws ReflectiveOperationException {
        Preconditions.checkNotNull(toRemove, "Set<MinecraftKeyWrapper> is null.");
        return removeConstructor.newInstance(toRemove);
    }
}
