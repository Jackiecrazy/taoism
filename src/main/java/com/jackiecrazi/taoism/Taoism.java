package com.jackiecrazi.taoism;

import com.jackiecrazi.taoism.common.CommonProxy;
import com.jackiecrazi.taoism.common.block.TaoBlocks;
import com.jackiecrazi.taoism.common.entity.TaoEntities;
import com.jackiecrazi.taoism.common.item.TaoItems;
import com.jackiecrazi.taoism.config.CombatConfig;
import com.jackiecrazi.taoism.config.TaoConfigs;
import com.jackiecrazi.taoism.crafting.TaoCrafting;
import com.jackiecrazi.taoism.handler.TaoCapabilityHandler;
import com.jackiecrazi.taoism.handler.TaoisticEventHandler;
import com.jackiecrazi.taoism.potions.TaoPotion;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Random;

@Mod(modid = Taoism.MODID, version = Taoism.VERSION)
public class Taoism {
    private static final Field atk = ObfuscationReflectionHelper.findField(EntityLivingBase.class, "field_184617_aD");
    private static final Field bypassArmor = ObfuscationReflectionHelper.findField(DamageSource.class, "field_76374_o");
    public static final String MODID = "taoism";
    public static final String VERSION = "2.0";
    public static final Random unirand = new Random();
    public static final CreativeTabs tabWea = new CreativeTabs("taoWea") {

        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(TaoItems.geom);
        }

    };
    @Mod.Instance(Taoism.MODID)
    public static Taoism INST = new Taoism();
    @SidedProxy(serverSide = "com.jackiecrazi.taoism.common.CommonProxy", clientSide = "com.jackiecrazi.taoism.client.ClientProxy")
    public static CommonProxy proxy;
    public static SimpleNetworkWrapper net;
    public static Logger logger = LogManager.getLogger(MODID);

    public static void setBypassArmor(DamageSource ds, boolean value) {
        try {
            bypassArmor.setBoolean(ds, value);
        } catch (Exception ignored) {

        }
    }

    public static void setAtk(Entity e, int cooldown) {
        try {
            atk.setInt(e, cooldown);
        } catch (Exception ignored) {

        }
    }

    public static int getAtk(Entity e) {
        try {
            return atk.getInt(e);
        } catch (Exception ignored) {
            return 0;
        }
    }
	/*
	public static final CreativeTabs tabBow = new CreativeTabs("taoBow") {

		@Override
		public ItemStack getTabIconItem() {
			return TaoBow.createRandomBow(null, unirand);
		}

	};
	
	public static final CreativeTabs tabArr = new CreativeTabs("taoArr") {

		@Override
		public ItemStack getTabIconItem() {
			return TaoBow.createRandomBow(null, unirand);
		}

	};

	 */

    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(TaoItems.class);
        MinecraftForge.EVENT_BUS.register(TaoBlocks.class);
        MinecraftForge.EVENT_BUS.register(TaoCrafting.class);
        MinecraftForge.EVENT_BUS.register(TaoPotion.class);
        MinecraftForge.EVENT_BUS.register(TaoisticEventHandler.class);
        MinecraftForge.EVENT_BUS.register(TaoCapabilityHandler.class);
        MinecraftForge.EVENT_BUS.register(TaoEntities.class);
        atk.setAccessible(true);
        bypassArmor.setAccessible(true);

        //MinecraftForge.EVENT_BUS.register(ClientProxy.class);
        TaoConfigs.init(event.getModConfigurationDirectory());
        net = NetworkRegistry.INSTANCE.newSimpleChannel("TaoistChannel");
        proxy.preinit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        if(CombatConfig.printParryList){
            CombatConfig.printParryList();
        }
        proxy.postinit(event);
    }

}