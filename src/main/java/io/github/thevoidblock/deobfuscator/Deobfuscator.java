package io.github.thevoidblock.deobfuscator;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

import static java.lang.String.format;

public class Deobfuscator implements ClientModInitializer {

    private static final String MOD_ID = "deobfuscator";
    public static boolean ENABLED = false;
    private static final Minecraft CLIENT = Minecraft.getInstance();

    @Override
    public void onInitializeClient() {
        KeyMapping toggleBind = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                format("key.%s.toggle", MOD_ID),
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_SEMICOLON,
                KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "main"))
        ));

        ClientTickEvents.END_CLIENT_TICK.register(_ -> {
            if(toggleBind.consumeClick()) {
                ENABLED = !ENABLED;
                if (CLIENT.player != null) {
                    CLIENT.player.sendOverlayMessage(Component.translatable(format("text.%s.toggle", MOD_ID), styleBoolean(ENABLED)));
                }
            }
        });
    }

    private static MutableComponent styleBoolean(boolean value) {
        MutableComponent text = Component.literal(value ? "ON" : "OFF");
        return text.withStyle(value ? ChatFormatting.GREEN : ChatFormatting.RED);
    }
}
