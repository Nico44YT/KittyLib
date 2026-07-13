package nico.kittylib.mixin.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nico.kittylib.api.item.TickingItemStackEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemFrameEntity.class)
public abstract class ItemFrameEntityMixin extends AbstractDecorationEntity {

    @Shadow
    public abstract ItemStack getHeldItemStack();

    private ItemFrameEntityMixin(EntityType<? extends AbstractDecorationEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();

        if(getHeldItemStack() != null && getHeldItemStack().getItem() instanceof TickingItemStackEntity tickingItemStack) {
            var entity = ((ItemFrameEntity) (Object) this);
            var pos = entity.getPos();
            tickingItemStack.itemFrameTick(getHeldItemStack(), entity.getWorld(), entity, pos.x, pos.y, pos.z);
        }
    }
}
