package com.jackiecrazi.taoism.utils;

import com.jackiecrazi.taoism.api.NeedyLittleThings;
import com.jackiecrazi.taoism.capability.ITaoStatCapability;
import com.jackiecrazi.taoism.capability.TaoCasterData;
import com.jackiecrazi.taoism.config.CombatConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Method;
import java.util.List;

public class TaoMovementUtils {
    private static Method jump = ObfuscationReflectionHelper.findMethod(EntityLivingBase.class, "func_70664_aZ", Void.TYPE);

    public static boolean shouldStick(EntityLivingBase elb) {
        return (TaoCasterData.getTaoCap(elb).getQi() > 3
                && willHitWall(elb))
                && elb.isSprinting();
    }

    public static boolean isTouchingWall(EntityLivingBase elb) {
        boolean[] b = collisionStatus(elb);
        return !elb.onGround && !b[2] && !b[3] && ((b[0] || b[1]) || (b[4] || b[5]));
    }

    public static boolean[] collisionStatus(EntityLivingBase elb) {
        double allowance = 0.1;
        boolean[] ret = {false, false, false, false, false, false};
        AxisAlignedBB aabb = elb.getEntityBoundingBox();
        List<AxisAlignedBB> boxes = elb.world.getCollisionBoxes(elb, aabb.grow(allowance / 2));
        for (AxisAlignedBB a : boxes) {
            if (aabb.calculateXOffset(a, allowance) != allowance) ret[0] = true;
            if (aabb.calculateXOffset(a, -allowance) != -allowance) ret[1] = true;
            if (aabb.calculateYOffset(a, allowance) != allowance) ret[2] = true;
            if (aabb.calculateYOffset(a, -allowance) != -allowance) ret[3] = true;
            if (aabb.calculateZOffset(a, allowance) != allowance) ret[4] = true;
            if (aabb.calculateZOffset(a, -allowance) != -allowance) ret[5] = true;
        }
        return ret;
    }

    public static boolean[] collisionStatusVelocitySensitive(EntityLivingBase elb) {
        double allowance = 0.1;
        boolean[] ret = {false, false, false, false, false, false};
        AxisAlignedBB aabb = elb.getEntityBoundingBox();
        List<AxisAlignedBB> boxes = elb.world.getCollisionBoxes(elb, aabb.expand(elb.motionX, elb.motionY, elb.motionZ));
        for (AxisAlignedBB a : boxes) {
            if (aabb.calculateXOffset(a, allowance) != allowance) ret[0] = true;
            if (aabb.calculateXOffset(a, -allowance) != -allowance) ret[1] = true;
            if (aabb.calculateYOffset(a, allowance) != allowance) ret[2] = true;
            if (aabb.calculateYOffset(a, -allowance) != -allowance) ret[3] = true;
            if (aabb.calculateZOffset(a, allowance) != allowance) ret[4] = true;
            if (aabb.calculateZOffset(a, -allowance) != -allowance) ret[5] = true;
        }
        return ret;
    }

    /**
     * Checks the +x, -x, +y, -y, +z, -z, in that order
     *
     * @param elb
     * @return
     */
    public static boolean willHitWall(EntityLivingBase elb) {
        double allowance = 1;
        AxisAlignedBB aabb = elb.getEntityBoundingBox();
        List<AxisAlignedBB> boxes = elb.world.getCollisionBoxes(elb, aabb.expand(elb.motionX, elb.motionY, elb.motionZ));
        for (AxisAlignedBB a : boxes) {
            if (aabb.calculateXOffset(a, allowance) != allowance) return true;
            if (aabb.calculateXOffset(a, -allowance) != -allowance) return true;
            if (aabb.calculateZOffset(a, allowance) != allowance) return true;
            if (aabb.calculateZOffset(a, -allowance) != -allowance) return true;
        }
        return false;
    }

    public static boolean attemptJump(EntityLivingBase elb) {
        //if you're on the ground, I'll let vanilla handle you
        if (elb.onGround) return false;
        ITaoStatCapability itsc = TaoCasterData.getTaoCap(elb);
        //qi has to be nonzero
        if (itsc.getQi() == 0) return false;
        //if you're exhausted or just jumped, you can't jump again
        if ((itsc.getJumpState() == ITaoStatCapability.JUMPSTATE.EXHAUSTED || itsc.getJumpState() == ITaoStatCapability.JUMPSTATE.JUMPING))
            return false;
        if (itsc.getQi() > 3)
            itsc.setJumpState(ITaoStatCapability.JUMPSTATE.JUMPING);
        else itsc.setJumpState(ITaoStatCapability.JUMPSTATE.EXHAUSTED);
        itsc.setClingDirections(new ITaoStatCapability.ClingData(false, false, false, false));
        if (elb instanceof EntityPlayer)
            ((EntityPlayer) elb).jump();
        else try {
            jump.invoke(elb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isTouchingWall(elb)) {
            boolean[] dir = collisionStatus(elb);
            double speed = Math.sqrt(elb.motionX * elb.motionX + elb.motionY + elb.motionY + elb.motionZ + elb.motionZ);
            //Vec3d look=elb.getLookVec();
            elb.motionY/=2;
            if (dir[0]) {
                elb.motionX += speed / 2;
            }
            if (dir[1]) {
                elb.motionX -= speed / 2;
            }
            if (dir[4]) {
                elb.motionZ += speed / 2;
            }
            if (dir[5]) {
                elb.motionZ -= speed / 2;
            }
            elb.setSprinting(true);
        }
        elb.velocityChanged = true;
        TaoCasterData.forceUpdateTrackingClients(elb);
        return true;
    }

    public static boolean attemptDodge(EntityLivingBase elb, int side) {
        ITaoStatCapability itsc = TaoCasterData.getTaoCap(elb);
        if (itsc.getRollCounter() > CombatConfig.rollCooldown && itsc.getJumpState() != ITaoStatCapability.JUMPSTATE.DODGING && (elb.onGround || itsc.getQi() > 2) && !elb.isSneaking() && (side != 3 || !elb.onGround) && (!(elb instanceof EntityPlayer) || !((EntityPlayer) elb).capabilities.isFlying)) {
            //System.out.println("execute roll to side " + side);
            itsc.setRollCounter(0);
            if (itsc.getQi() > 3)
                itsc.setJumpState(ITaoStatCapability.JUMPSTATE.DODGING);
            else itsc.setJumpState(ITaoStatCapability.JUMPSTATE.EXHAUSTED);
            itsc.setPrevSizes(elb.width, elb.height);
            float min = Math.min(elb.width, elb.height);
            double x = 0, y = 0.3, z = 0;
            switch (side) {
                case 0://left
                    x = Math.cos(NeedyLittleThings.rad(elb.rotationYaw));
                    z = Math.sin(NeedyLittleThings.rad(elb.rotationYaw));
                    break;
                case 1://back
                    x = Math.cos(NeedyLittleThings.rad(elb.rotationYaw - 90));
                    z = Math.sin(NeedyLittleThings.rad(elb.rotationYaw - 90));
                    break;
                case 2://right
                    x = Math.cos(NeedyLittleThings.rad(elb.rotationYaw - 180));
                    z = Math.sin(NeedyLittleThings.rad(elb.rotationYaw - 180));
                    break;
                case 3://forward
                    x = Math.cos(NeedyLittleThings.rad(elb.rotationYaw + 90));
                    z = Math.sin(NeedyLittleThings.rad(elb.rotationYaw + 90));
                    break;
            }
            float divisor = side == 3 ? 5f : 20f;
            float multiplier = (1 + (itsc.getQi() / divisor));
            x *= 0.6 * multiplier;
            z *= 0.6 * multiplier;

            //NeedyLittleThings.setSize(elb, min, min);

            elb.addVelocity(x, y, z);
            itsc.setJumpState(ITaoStatCapability.JUMPSTATE.DODGING);
            elb.setSprinting(true);
            elb.velocityChanged = true;
            TaoCasterData.forceUpdateTrackingClients(elb);
            return true;
        }
        return false;
    }
}
