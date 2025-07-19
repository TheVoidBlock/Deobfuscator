package io.github.thevoidblock.deobfuscator.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.thevoidblock.deobfuscator.Deobfuscator;
import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Style.class)
public class StyleMixin {
    @ModifyReturnValue(method = "isObfuscated", at = @At(value = "RETURN"))
    boolean diableObfuscation(boolean original) {
        if(Deobfuscator.ENABLED) return false;
        return original;
    }
}
