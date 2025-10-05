package io.github.thevoidblock.deobfuscator;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import static java.lang.String.format;

public class Deobfuscator implements ClientModInitializer {

    private static final String MOD_ID = "deobfuscator";
    public static boolean ENABLED = false;
    private static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        KeyBinding toggleBind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                format("key.%s.toggle", MOD_ID),
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_SEMICOLON,
                KeyBinding.Category.create(Identifier.of(MOD_ID, "main"))
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(toggleBind.wasPressed()) {
                ENABLED = !ENABLED;
                if (CLIENT.player != null) {
                    CLIENT.player.sendMessage(Text.translatable(format("text.%s.toggle", MOD_ID), styleBoolean(ENABLED)), true);
                }
            }
        });
    }

    private static Text styleBoolean(boolean value) {
        MutableText text = Text.literal(value ? "ON" : "OFF");
        return text.formatted(value ? Formatting.GREEN : Formatting.RED);
    }
}
