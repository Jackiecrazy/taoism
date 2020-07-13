package com.jackiecrazi.taoism.common.item.weapon.melee.club;

import com.jackiecrazi.taoism.api.PartDefinition;
import com.jackiecrazi.taoism.api.StaticRefs;
import com.jackiecrazi.taoism.capability.TaoCasterData;
import com.jackiecrazi.taoism.common.entity.TaoEntities;
import com.jackiecrazi.taoism.common.item.weapon.melee.TaoWeapon;
import com.jackiecrazi.taoism.potions.TaoPotion;
import com.jackiecrazi.taoism.utils.TaoPotionUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nullable;
import java.util.List;

public class Bian extends TaoWeapon {
    /*
    high power, middling defense and combo, low range and speed
    handles a little like the chui, but is more fluid, leading to higher defense and attack speed
        at the cost of less posture damage
    essentially a blunt sword, yeah.
    ignores some armor
    attacking and being parried disables opponent weapon briefly (this might be finicky)
    low posture per damage, but inflicts exhaustion, to play into its "continuous" nature

    execution:
    If the opponent's posture regen can't go any lower, break their bones for max hp and posture damage.
    Upon attacking a downed enemy this state ends in a cloud of dust that blind and fatigue in an area,
    dealing remaining hp damage to the target
     */
    public Bian() {
        super(0, 1.4, 6, 0.7f);
    }

    @Override
    protected void perkDesc(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.DARK_GREEN + I18n.format("weapon.disshield") + TextFormatting.RESET);
        tooltip.add(I18n.format("bian.stagger"));
        tooltip.add(I18n.format("bian.armpen"));
        tooltip.add(I18n.format("bian.enfeeble"));
    }

    @Override
    public float postureDealtBase(EntityLivingBase attacker, EntityLivingBase defender, ItemStack item, float amount) {
        float base = super.postureDealtBase(attacker, defender, item, amount);
        if (isCharged(attacker, item) && defender.getEntityAttribute(TaoEntities.POSREGEN).getAttributeValue() < 0) {
            base += TaoCasterData.getTaoCap(defender).getMaxPosture() / 10;
        }
        return base;
    }

    @Override
    public Event.Result critCheck(EntityLivingBase attacker, EntityLivingBase target, ItemStack item, float crit, boolean vanCrit) {
        return TaoCasterData.getTaoCap(target).getDownTimer() > 0 ? Event.Result.ALLOW : super.critCheck(attacker, target, item, crit, vanCrit);
    }

    @Override
    public float critDamage(EntityLivingBase attacker, EntityLivingBase target, ItemStack item) {
        float ground = !attacker.onGround ? 1.5f : 1f;
        float breach = TaoCasterData.getTaoCap(target).getDownTimer() > 0 ? 1.5f : 1f;
        return ground * breach;
    }

    @Override
    public void attackStart(DamageSource ds, EntityLivingBase attacker, EntityLivingBase target, ItemStack stack, float orig) {
        super.attackStart(ds, attacker, target, stack, orig);
        if (TaoCasterData.getTaoCap(target).getDownTimer() > 0) {
            gettagfast(stack).setBoolean("ouches", true);
        }
    }

    @Override
    public float hurtStart(DamageSource ds, EntityLivingBase attacker, EntityLivingBase target, ItemStack item, float orig) {
        float base = super.hurtStart(ds, attacker, target, item, orig);
        if (isCharged(attacker, item)) {
            if (target.getEntityAttribute(TaoEntities.POSREGEN).getAttributeValue() < 0) {
                if (TaoCasterData.getTaoCap(attacker).consumeQi(0.5f, 5))
                    base += TaoCasterData.getTaoCap(target).getMaxPosture();
            }
            if (TaoCasterData.getTaoCap(target).getDownTimer() > 0 && gettagfast(item).getBoolean("ouches")) {

            }
        }
        return base;
    }

    @Override
    public int armorIgnoreAmount(DamageSource ds, EntityLivingBase attacker, EntityLivingBase target, ItemStack stack, float orig) {
        return TaoCasterData.getTaoCap(attacker).getQiFloored() / 2;
    }

    @Override
    protected void applyEffects(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker, int chi) {
        if (isCharged(attacker, stack))
            TaoPotionUtils.attemptAddPot(target, TaoPotionUtils.stackPot(target, new PotionEffect(TaoPotion.FATIGUE, 40, chi / 3), TaoPotionUtils.POTSTACKINGMETHOD.ADD), false);
        else TaoPotionUtils.attemptAddPot(target, new PotionEffect(TaoPotion.FATIGUE, 40, chi / 3), false);
    }

    @Override
    public PartDefinition[] getPartNames(ItemStack is) {
        return StaticRefs.SWORD;
    }

    @Override
    public int getMaxChargeTime() {
        return 0;
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity, EntityLivingBase attacker) {
        return attacker.isAirBorne;
    }

    @Override
    public int getComboLength(EntityLivingBase wielder, ItemStack is) {
        return 1;
    }

    @Override
    public float getReach(EntityLivingBase p, ItemStack is) {
        return 3;
    }

    @Override
    public float postureMultiplierDefend(Entity attacker, EntityLivingBase defender, ItemStack item, float amount) {
        return 0.7f;
    }
}
