package com.jackiecrazi.taoism.common.item;

import com.jackiecrazi.taoism.common.item.arrows.TaoArrow;
import com.jackiecrazi.taoism.common.item.prop.ItemProjectileProp;
import com.jackiecrazi.taoism.common.item.weapon.melee.ItemBlueprint;
import com.jackiecrazi.taoism.common.item.weapon.melee.axe.BanFu;
import com.jackiecrazi.taoism.common.item.weapon.melee.club.Bian;
import com.jackiecrazi.taoism.common.item.weapon.melee.club.Chui;
import com.jackiecrazi.taoism.common.item.weapon.melee.club.Tonfa;
import com.jackiecrazi.taoism.common.item.weapon.melee.dagger.Balisong;
import com.jackiecrazi.taoism.common.item.weapon.melee.dagger.Karambit;
import com.jackiecrazi.taoism.common.item.weapon.melee.desword.Ken;
import com.jackiecrazi.taoism.common.item.weapon.melee.desword.Rapier;
import com.jackiecrazi.taoism.common.item.weapon.melee.hand.Cestus;
import com.jackiecrazi.taoism.common.item.weapon.melee.pick.ChickenSickle;
import com.jackiecrazi.taoism.common.item.weapon.melee.polearm.pollaxe.Halberd;
import com.jackiecrazi.taoism.common.item.weapon.melee.polearm.pollaxe.Pollaxe;
import com.jackiecrazi.taoism.common.item.weapon.melee.polearm.pollaxe.QingLongJi;
import com.jackiecrazi.taoism.common.item.weapon.melee.polearm.spear.BohemianEarspoon;
import com.jackiecrazi.taoism.common.item.weapon.melee.polearm.spear.GouLianQiang;
import com.jackiecrazi.taoism.common.item.weapon.melee.polearm.spear.Qiang;
import com.jackiecrazi.taoism.common.item.weapon.melee.polearm.staff.Staff;
import com.jackiecrazi.taoism.common.item.weapon.melee.polearm.svardstav.GuanDao;
import com.jackiecrazi.taoism.common.item.weapon.melee.polearm.warhammer.ChangChui;
import com.jackiecrazi.taoism.common.item.weapon.melee.rope.Kusarigama;
import com.jackiecrazi.taoism.common.item.weapon.melee.rope.RopeDart;
import com.jackiecrazi.taoism.common.item.weapon.melee.sectional.Nunchaku;
import com.jackiecrazi.taoism.common.item.weapon.melee.sesword.Kampilan;
import com.jackiecrazi.taoism.common.item.weapon.melee.whip.CatNineTails;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TaoItems {
    public static Item prop;
    public static Item
            kampilan,
            cestus,
            banfu,
            balisong,
            karambit,
            chui,
            geom,
            qiang,
            tonfa,
            chickensickle,
            ninetail,
            pollaxe,
            halberd,
            changchui,
            goulianqiang,
            qinglongji,
            gun,
            yyd,
            bohear,
            nunchaku,
            ropedart,
            bian,
            rapier,
    kusarigama;


    //public static ItemDummy part = new ItemDummy();
    //public static TaoWeapon weap = new TaoWeapon();
    //public static TaoBow bow = new TaoBow();
    public static ItemBlueprint blueprint = new ItemBlueprint();
    public static TaoArrow arrow = new TaoArrow();

    //public static TaoArmor helm = new TaoArmor(EntityEquipmentSlot.HEAD), chest = new TaoArmor(EntityEquipmentSlot.CHEST), leg = new TaoArmor(EntityEquipmentSlot.LEGS), boot = new TaoArmor(EntityEquipmentSlot.FEET);
    //public static TaoArmor[] armor = {boot, leg, chest, helm};

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Item> e) {
        //base
        //e.getRegistry().register(part);
        //e.getRegistry().register(blueprint);
        kampilan = new Kampilan();
        cestus = new Cestus();
        banfu = new BanFu();
        balisong = new Balisong();
        karambit = new Karambit();
        chui = new Chui();
        geom = new Ken();
        qiang = new Qiang();
        tonfa = new Tonfa();
        chickensickle = new ChickenSickle();
        ninetail = new CatNineTails();
        pollaxe = new Pollaxe();
        halberd = new Halberd();
        changchui = new ChangChui();
        goulianqiang = new GouLianQiang();
        qinglongji = new QingLongJi();
        gun = new Staff();
        yyd = new GuanDao();
        bohear = new BohemianEarspoon();
        nunchaku = new Nunchaku();
        ropedart = new RopeDart();
        bian = new Bian();
        rapier=new Rapier();
        kusarigama=new Kusarigama();
        prop = new ItemProjectileProp();
        e.getRegistry().register(kampilan);
        e.getRegistry().register(cestus);
        e.getRegistry().register(geom);
        e.getRegistry().register(banfu);
        e.getRegistry().register(balisong);
        e.getRegistry().register(karambit);
        e.getRegistry().register(chui);
        e.getRegistry().register(qiang);
        e.getRegistry().register(tonfa);
        e.getRegistry().register(ninetail);
        e.getRegistry().register(chickensickle);
        e.getRegistry().register(pollaxe);
        e.getRegistry().register(halberd);
        e.getRegistry().register(changchui);
        e.getRegistry().register(goulianqiang);
        e.getRegistry().register(qinglongji);
        e.getRegistry().register(gun);
        e.getRegistry().register(yyd);
        e.getRegistry().register(bohear);
        e.getRegistry().register(nunchaku);
        e.getRegistry().register(ropedart);
        e.getRegistry().register(bian);
        e.getRegistry().register(rapier);
        e.getRegistry().register(kusarigama);
        e.getRegistry().register(prop);
        //weapon
        //e.getRegistry().register(weap);
        //armor
//        e.getRegistry().register(helm);
//        e.getRegistry().register(chest);
//        e.getRegistry().register(leg);
//        e.getRegistry().register(boot);
        //archery
        //e.getRegistry().register(bow);
        //e.getRegistry().register(arrow);
    }

}
