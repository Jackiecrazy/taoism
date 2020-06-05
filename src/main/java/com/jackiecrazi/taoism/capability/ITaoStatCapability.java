package com.jackiecrazi.taoism.capability;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.util.INBTSerializable;

public interface ITaoStatCapability extends INBTSerializable<NBTTagCompound> {
    default int getQiFloored() {
        return (int) Math.floor(getQi());
    }

    float getQi();

    void setQi(float amount);

    float addQi(float amount);

    boolean consumeQi(float amount);

    float getLing();

    void setLing(float amount);

    float addLing(float amount);

    boolean consumeLing(float amount);

    JUMPSTATE getJumpState();

    void setJumpState(JUMPSTATE js);

    ClingData getClingDirections();

    void setClingDirections(ClingData dirs);

    /**
     * this will return the cached cooldown for players, and will track mob swing instead. Because Mojang didn't implement attack timers and nobody else bothered.
     * <p>
     * not cool, Mojang, not cool. If you want a combat update, start with attack windups and recoveries.
     *
     * @return
     */
    float getSwing();

    void setSwing(float amount);

    float getPosture();

    void setPosture(float amount);

    float addPosture(float amount);

    float consumePosture(float amount, boolean canStagger);

    float consumePosture(float amount, boolean canStagger, EntityLivingBase assailant, DamageSource ds);

    int getParryCounter();

    void setParryCounter(int amount);

    void addParryCounter(int amount);

    int getRollCounter();

    void setRollCounter(int amount);

    void addRollCounter(int amount);

    Tuple<Float, Float> getPrevSizes();

    void setPrevSizes(float width, float height);

    int getComboSequence();

    void setComboSequence(int amount);

    void incrementComboSequence(int amount);

    void copy(ITaoStatCapability from);

    float getMaxLing();

    void setMaxLing(float amount);

    float getMaxPosture();

    void setMaxPosture(float amount);

    int getLingRechargeCD();

    void setLingRechargeCD(int amount);

    int getPostureRechargeCD();

    void setPostureRechargeCD(int amount);

    int getStaminaRechargeCD();

    void setStaminaRechargeCD(int amount);

    int getQiGracePeriod();

    void setQiGracePeriod(int amount);

    int getPosInvulTime();

    void setPosInvulTime(int time);

    long getLastUpdatedTime();

    void setLastUpdatedTime(long time);

    boolean isSwitchIn();

    void setSwitchIn(boolean suichi);

    int getOffhandCool();

    void setOffhandCool(int oc);

    boolean isOffhandAttack();

    void setOffhandAttack(boolean off);

    ItemStack getOffHand();

    void setOffHand(ItemStack is);

    boolean isProtected();

    void setProtected(boolean protect);

    int getDownTimer();

    void setDownTimer(int time);

    enum JUMPSTATE {
        GROUNDED,
        EXHAUSTED,
        JUMPING,
        DODGING,
        CLINGING
    }

    class ClingData {
        boolean n, s, e, w;

        public ClingData(boolean[] dirs) {
            this(dirs[5], dirs[4], dirs[0], dirs[1]);
        }

        public ClingData(boolean north, boolean south, boolean east, boolean west) {
            n = north;
            s = south;
            e = east;
            w = west;
        }

        public ClingData(NBTTagCompound nbt) {
            n = nbt.getBoolean("north");
            s = nbt.getBoolean("south");
            e = nbt.getBoolean("east");
            w = nbt.getBoolean("west");
        }

        public boolean north() {
            return n;
        }

        public boolean south() {
            return s;
        }

        public boolean east() {
            return e;
        }

        public boolean west() {
            return w;
        }

        public NBTTagCompound toNBT(NBTTagCompound nbt) {
            nbt.setBoolean("north", n);
            nbt.setBoolean("south", s);
            nbt.setBoolean("east", e);
            nbt.setBoolean("west", w);
            return nbt;
        }

        public boolean equals(Object o) {
            if (!(o instanceof ClingData)) return false;
            ClingData cd = (ClingData) o;
            return cd.e == e && cd.n == n && cd.s == s && cd.w == w;
        }
    }
}
