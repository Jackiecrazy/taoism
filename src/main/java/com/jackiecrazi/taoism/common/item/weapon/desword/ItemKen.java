package com.jackiecrazi.taoism.common.item.weapon.desword;

import com.jackiecrazi.taoism.api.PartDefinition;
import com.jackiecrazi.taoism.api.StaticRefs;
import com.jackiecrazi.taoism.common.item.weapon.TaoWeapon;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemKen extends TaoWeapon {
    //relentless.
    //normal attack chains up to 3 times before requiring cooldown (sword flowers). Small AoE
    public ItemKen() {
        super(1, 1.6, 6.5, 1f);
    }

    @Override
    public PartDefinition[] getPartNames(ItemStack is) {
        return StaticRefs.SWORD;
    }

    @Override
    //default attack code to AoE
    protected void aoe(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker, int chi) {
        if (!attacker.isAirBorne) {
            splash(attacker, attacker.world.getEntitiesInAABBexcluding(target, target.getEntityBoundingBox().grow(3d, 1.5d, 3d), null));
        }
    }

    @Override
    public float newCooldown(EntityLivingBase elb, ItemStack is) {
        return getCombo(elb, is) != getComboLength(elb, is)-1 ? 0.8f : 0f;
    }

    private boolean isAoE(EntityLivingBase attacker, EntityLivingBase target) {
        if(attacker.isAirBorne)return true;
        List<Entity> list = attacker.world.getEntitiesInAABBexcluding(target, target.getEntityBoundingBox().grow(3d, 1.5d, 3d), null);
        list.remove(attacker);
        return !list.isEmpty();
    }

    @Override
    public float critDamage(EntityLivingBase attacker, EntityLivingBase target, ItemStack item) {
        float air = attacker.isAirBorne ? 1.5f : 1f;
        float aoe = isAoE(attacker, target) ? 1f : 1.2f;
        return air * aoe;
    }

    @Override
    public float getReach(EntityLivingBase p, ItemStack is) {
        return 4;
    }

    @Override
    public int getComboLength(EntityLivingBase wielder, ItemStack is) {
        if(isCharged(wielder,is))return 9;
        return 3;
    }

    @Override
    public void parrySkill(EntityLivingBase attacker, EntityLivingBase defender, ItemStack item) {
        //combo limit is raised to 9 for the next 9 seconds
        setCombo(defender, item, 0);
        chargeWeapon(attacker, defender, item, 180);
    }

    @Override
    public float postureMultiplierDefend(EntityLivingBase attacker, EntityLivingBase defender, ItemStack item, float amount) {
        return 1f;
    }

    @Override
    protected void perkDesc(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("ken.combo"));
        tooltip.add(I18n.format("ken.aoe"));
        tooltip.add(I18n.format("ken.stab"));
        tooltip.add(I18n.format("ken.leap"));
        tooltip.add(I18n.format("ken.riposte"));
    }
}
