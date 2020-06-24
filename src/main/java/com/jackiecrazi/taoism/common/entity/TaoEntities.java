package com.jackiecrazi.taoism.common.entity;

import com.jackiecrazi.taoism.common.entity.projectile.arrows.EntityTaoProjectile;
import com.jackiecrazi.taoism.common.entity.projectile.weapons.EntityRopeDart;
import com.jackiecrazi.taoism.common.entity.projectile.weapons.EntityBanfu;
import com.jackiecrazi.taoism.common.entity.projectile.weapons.EntitySwordBeam;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

public class TaoEntities {
    public static final IAttribute DEFLECT = (new RangedAttribute(null, "generic.armorDeflect", 0.0D, 0, 1.0D)).setDescription("Deflection").setShouldWatch(true);
    public static final IAttribute ABLATION = (new RangedAttribute(null, "generic.armorAbsorption", 0.0D, 0, 10D)).setDescription("Hard Absorption").setShouldWatch(true);
    public static final IAttribute POSREGEN = (new RangedAttribute(null, "generic.postureRegen", 1.0D, 0, 10D)).setDescription("Posture Regeneration").setShouldWatch(true);
    public static final IAttribute HEAL = (new RangedAttribute(null, "generic.healRate", 1.0D, 0, 10D)).setDescription("Health Regeneration Multiplier").setShouldWatch(true);
    public static final IAttribute LINGREGEN = (new RangedAttribute(null, "generic.lingRegen", 1.0D, 0, 10D)).setDescription("Ling Regeneration").setShouldWatch(true);
    public static final IAttribute QIRATE = (new RangedAttribute(null, "generic.qiGen", 0.0D, -10D, 10D)).setDescription("Qi Accumulation Rate").setShouldWatch(true);
    private static int id=0;
    @SubscribeEvent
    public static void init(RegistryEvent.Register<EntityEntry> e){
//        e.getRegistry().register(factoryArrow(EntityTaoArrowBlunt.class));
//        e.getRegistry().register(factoryArrow(EntityTaoArrowScream.class));
//        e.getRegistry().register(factoryArrow(EntityTaoArrowHarpoon.class));
//        e.getRegistry().register(factoryMove(MoveSanMiguel.class));
//        e.getRegistry().register(factoryMove(MoveCleave.class));
//        e.getRegistry().register(factoryMove(MoveMultiStrike.class));
        e.getRegistry().register(factoryArrow(EntityRopeDart.class));
        e.getRegistry().register(factoryArrow(EntityBanfu.class));
        e.getRegistry().register(factoryArrow(EntitySwordBeam.class));
    }
    private static EntityEntry factoryMove(Class<?extends EntityMove> move){
        return EntityEntryBuilder.create().entity(move).name(move.getSimpleName()).tracker(64,20,false).id(move.getName(),id++).build();
    }
    private static EntityEntry factoryArrow(Class<?extends EntityTaoProjectile> arr){
        return EntityEntryBuilder.create().entity(arr).name(arr.getSimpleName()).tracker(64,20,false).id(arr.getName(),id++).build();
    }
    private static EntityEntry factoryProjectile(Class<?extends EntityThrowable> projectile){
        return EntityEntryBuilder.create().entity(projectile).name(projectile.getSimpleName()).tracker(64,5,true).id(projectile.getName(),id++).build();
    }
}
