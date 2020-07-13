package com.jackiecrazi.taoism.common.item.weapon.melee.desword;

import com.jackiecrazi.taoism.api.PartDefinition;
import com.jackiecrazi.taoism.api.StaticRefs;
import com.jackiecrazi.taoism.capability.TaoCasterData;
import com.jackiecrazi.taoism.common.item.weapon.melee.TaoWeapon;
import com.jackiecrazi.taoism.config.CombatConfig;
import com.jackiecrazi.taoism.utils.TaoCombatUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nullable;
import java.util.List;

public class Rapier extends TaoWeapon {
    /*
     * An agile and protective stabbing sword. High speed and combo, medium range and defense, low power
     * Rapiers are not actually any lighter than an arming sword, but they can get up to 120cm!
     * The smallsword is what you're looking at if you want a quick dueling blade.
     * 3-hit combo as per sword standards
     * Leaping does not crit. Instead, if the enemy is in cooldown or you parried/dodged in the last second, crit for 1.5x damage
     * execution: attacks taunt the enemy, increasing its damage dealt and taken and fatiguing it more and more.
     * On the 9th hit the enemy is dealt great posture damage (with cap), some wearing down beforehand is necessary if you want a down
     * an attack will now pierce their heart and end the state (with cap)
     */
    public Rapier() {
        super(2, 1.6, 5, 0.5f);
    }

    @Override
    protected void perkDesc(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("rapier.combo"));
        tooltip.add(I18n.format("rapier.riposte"));
    }

    @Override
    public void onParry(EntityLivingBase attacker, EntityLivingBase defender, ItemStack item) {
        gettagfast(item).setInteger("lastParryTime", defender.ticksExisted);
    }

    @Override
    public void onOtherHandParry(EntityLivingBase attacker, EntityLivingBase defender, ItemStack item) {
        gettagfast(item).setInteger("lastParryTime", defender.ticksExisted);
    }

    @Override
    public Event.Result critCheck(EntityLivingBase attacker, EntityLivingBase target, ItemStack item, float crit, boolean vanCrit) {
        if(gettagfast(item).getInteger("lastParryTime")>attacker.ticksExisted){
            gettagfast(item).setInteger("lastParryTime", 0);
        }
        //just dodged or parried
        if (TaoCasterData.getTaoCap(attacker).getRollCounter() < CombatConfig.rollThreshold * 2 || attacker.ticksExisted - gettagfast(item).getInteger("lastParryTime") < 20)
            return Event.Result.ALLOW;
        //enemy isn't ready
        if (TaoCombatUtils.getHandCoolDown(target, EnumHand.MAIN_HAND) < 1 || TaoCombatUtils.getHandCoolDown(target, EnumHand.OFF_HAND) < 1)
            return Event.Result.ALLOW;
        //mob just attacked
        if (!(target instanceof EntityPlayer) && TaoCasterData.getTaoCap(target).getSwing() < CombatConfig.mobForcedCooldown)
            return Event.Result.ALLOW;
        return Event.Result.DENY;
    }

    @Override
    public PartDefinition[] getPartNames(ItemStack is) {
        return StaticRefs.SWORD;
    }

    @Override
    public int getMaxChargeTime() {
        return 10;
    }

    @Override
    public int getComboLength(EntityLivingBase wielder, ItemStack is) {
        return 3;
    }

    @Override
    public float getReach(EntityLivingBase p, ItemStack is) {
        return 3.5f;
    }

    @Override
    public float postureMultiplierDefend(Entity attacker, EntityLivingBase defender, ItemStack item, float amount) {
        return 1.1f;
    }
}
