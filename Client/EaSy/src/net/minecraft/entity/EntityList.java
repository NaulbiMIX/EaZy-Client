/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Constructor
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.Map
 *  java.util.Set
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityList {
    private static final Logger logger = LogManager.getLogger();
    private static final Map<String, Class<? extends Entity>> stringToClassMapping = Maps.newHashMap();
    private static final Map<Class<? extends Entity>, String> classToStringMapping = Maps.newHashMap();
    private static final Map<Integer, Class<? extends Entity>> idToClassMapping = Maps.newHashMap();
    private static final Map<Class<? extends Entity>, Integer> classToIDMapping = Maps.newHashMap();
    private static final Map<String, Integer> stringToIDMapping = Maps.newHashMap();
    public static final Map<Integer, EntityEggInfo> entityEggs = Maps.newLinkedHashMap();

    private static void addMapping(Class<? extends Entity> entityClass, String entityName, int id) {
        if (stringToClassMapping.containsKey((Object)entityName)) {
            throw new IllegalArgumentException("ID is already registered: " + entityName);
        }
        if (idToClassMapping.containsKey((Object)id)) {
            throw new IllegalArgumentException("ID is already registered: " + id);
        }
        if (id == 0) {
            throw new IllegalArgumentException("Cannot register to reserved id: " + id);
        }
        if (entityClass == null) {
            throw new IllegalArgumentException("Cannot register null clazz for id: " + id);
        }
        stringToClassMapping.put((Object)entityName, entityClass);
        classToStringMapping.put(entityClass, (Object)entityName);
        idToClassMapping.put((Object)id, entityClass);
        classToIDMapping.put(entityClass, (Object)id);
        stringToIDMapping.put((Object)entityName, (Object)id);
    }

    private static void addMapping(Class<? extends Entity> entityClass, String entityName, int entityID, int baseColor, int spotColor) {
        EntityList.addMapping(entityClass, entityName, entityID);
        entityEggs.put((Object)entityID, (Object)new EntityEggInfo(entityID, baseColor, spotColor));
    }

    public static Entity createEntityByName(String entityName, World worldIn) {
        Entity entity = null;
        try {
            Class oclass = (Class)stringToClassMapping.get((Object)entityName);
            if (oclass != null) {
                entity = (Entity)oclass.getConstructor(new Class[]{World.class}).newInstance(new Object[]{worldIn});
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return entity;
    }

    public static Entity createEntityFromNBT(NBTTagCompound nbt, World worldIn) {
        Entity entity = null;
        if ("Minecart".equals((Object)nbt.getString("id"))) {
            nbt.setString("id", EntityMinecart.EnumMinecartType.byNetworkID(nbt.getInteger("Type")).getName());
            nbt.removeTag("Type");
        }
        try {
            Class oclass = (Class)stringToClassMapping.get((Object)nbt.getString("id"));
            if (oclass != null) {
                entity = (Entity)oclass.getConstructor(new Class[]{World.class}).newInstance(new Object[]{worldIn});
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (entity != null) {
            entity.readFromNBT(nbt);
        } else {
            logger.warn("Skipping Entity with id " + nbt.getString("id"));
        }
        return entity;
    }

    public static Entity createEntityByID(int entityID, World worldIn) {
        Entity entity = null;
        try {
            Class<? extends Entity> oclass = EntityList.getClassFromID(entityID);
            if (oclass != null) {
                entity = (Entity)oclass.getConstructor(new Class[]{World.class}).newInstance(new Object[]{worldIn});
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (entity == null) {
            logger.warn("Skipping Entity with id " + entityID);
        }
        return entity;
    }

    public static int getEntityID(Entity entityIn) {
        Integer integer = (Integer)classToIDMapping.get((Object)entityIn.getClass());
        return integer == null ? 0 : integer;
    }

    public static Class<? extends Entity> getClassFromID(int entityID) {
        return (Class)idToClassMapping.get((Object)entityID);
    }

    public static String getEntityString(Entity entityIn) {
        return (String)classToStringMapping.get((Object)entityIn.getClass());
    }

    public static int getIDFromString(String entityName) {
        Integer integer = (Integer)stringToIDMapping.get((Object)entityName);
        return integer == null ? 90 : integer;
    }

    public static String getStringFromID(int entityID) {
        return (String)classToStringMapping.get(EntityList.getClassFromID(entityID));
    }

    public static void func_151514_a() {
    }

    public static List<String> getEntityNameList() {
        Set set = stringToClassMapping.keySet();
        ArrayList list = Lists.newArrayList();
        for (String s : set) {
            Class oclass = (Class)stringToClassMapping.get((Object)s);
            if ((oclass.getModifiers() & 1024) == 1024) continue;
            list.add((Object)s);
        }
        list.add((Object)"LightningBolt");
        return list;
    }

    public static boolean isStringEntityName(Entity entityIn, String entityName) {
        String s = EntityList.getEntityString(entityIn);
        if (s == null && entityIn instanceof EntityPlayer) {
            s = "Player";
        } else if (s == null && entityIn instanceof EntityLightningBolt) {
            s = "LightningBolt";
        }
        return entityName.equals((Object)s);
    }

    public static boolean isStringValidEntityName(String entityName) {
        return "Player".equals((Object)entityName) || EntityList.getEntityNameList().contains((Object)entityName);
    }

    static {
        EntityList.addMapping(EntityItem.class, "Item", 1);
        EntityList.addMapping(EntityXPOrb.class, "XPOrb", 2);
        EntityList.addMapping(EntityEgg.class, "ThrownEgg", 7);
        EntityList.addMapping(EntityLeashKnot.class, "LeashKnot", 8);
        EntityList.addMapping(EntityPainting.class, "Painting", 9);
        EntityList.addMapping(EntityArrow.class, "Arrow", 10);
        EntityList.addMapping(EntitySnowball.class, "Snowball", 11);
        EntityList.addMapping(EntityLargeFireball.class, "Fireball", 12);
        EntityList.addMapping(EntitySmallFireball.class, "SmallFireball", 13);
        EntityList.addMapping(EntityEnderPearl.class, "ThrownEnderpearl", 14);
        EntityList.addMapping(EntityEnderEye.class, "EyeOfEnderSignal", 15);
        EntityList.addMapping(EntityPotion.class, "ThrownPotion", 16);
        EntityList.addMapping(EntityExpBottle.class, "ThrownExpBottle", 17);
        EntityList.addMapping(EntityItemFrame.class, "ItemFrame", 18);
        EntityList.addMapping(EntityWitherSkull.class, "WitherSkull", 19);
        EntityList.addMapping(EntityTNTPrimed.class, "PrimedTnt", 20);
        EntityList.addMapping(EntityFallingBlock.class, "FallingSand", 21);
        EntityList.addMapping(EntityFireworkRocket.class, "FireworksRocketEntity", 22);
        EntityList.addMapping(EntityArmorStand.class, "ArmorStand", 30);
        EntityList.addMapping(EntityBoat.class, "Boat", 41);
        EntityList.addMapping(EntityMinecartEmpty.class, EntityMinecart.EnumMinecartType.RIDEABLE.getName(), 42);
        EntityList.addMapping(EntityMinecartChest.class, EntityMinecart.EnumMinecartType.CHEST.getName(), 43);
        EntityList.addMapping(EntityMinecartFurnace.class, EntityMinecart.EnumMinecartType.FURNACE.getName(), 44);
        EntityList.addMapping(EntityMinecartTNT.class, EntityMinecart.EnumMinecartType.TNT.getName(), 45);
        EntityList.addMapping(EntityMinecartHopper.class, EntityMinecart.EnumMinecartType.HOPPER.getName(), 46);
        EntityList.addMapping(EntityMinecartMobSpawner.class, EntityMinecart.EnumMinecartType.SPAWNER.getName(), 47);
        EntityList.addMapping(EntityMinecartCommandBlock.class, EntityMinecart.EnumMinecartType.COMMAND_BLOCK.getName(), 40);
        EntityList.addMapping(EntityLiving.class, "Mob", 48);
        EntityList.addMapping(EntityMob.class, "Monster", 49);
        EntityList.addMapping(EntityCreeper.class, "Creeper", 50, 894731, 0);
        EntityList.addMapping(EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
        EntityList.addMapping(EntitySpider.class, "Spider", 52, 3419431, 11013646);
        EntityList.addMapping(EntityGiantZombie.class, "Giant", 53);
        EntityList.addMapping(EntityZombie.class, "Zombie", 54, 44975, 7969893);
        EntityList.addMapping(EntitySlime.class, "Slime", 55, 5349438, 8306542);
        EntityList.addMapping(EntityGhast.class, "Ghast", 56, 16382457, 12369084);
        EntityList.addMapping(EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
        EntityList.addMapping(EntityEnderman.class, "Enderman", 58, 1447446, 0);
        EntityList.addMapping(EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
        EntityList.addMapping(EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
        EntityList.addMapping(EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
        EntityList.addMapping(EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
        EntityList.addMapping(EntityDragon.class, "EnderDragon", 63);
        EntityList.addMapping(EntityWither.class, "WitherBoss", 64);
        EntityList.addMapping(EntityBat.class, "Bat", 65, 4996656, 986895);
        EntityList.addMapping(EntityWitch.class, "Witch", 66, 3407872, 5349438);
        EntityList.addMapping(EntityEndermite.class, "Endermite", 67, 1447446, 7237230);
        EntityList.addMapping(EntityGuardian.class, "Guardian", 68, 5931634, 15826224);
        EntityList.addMapping(EntityPig.class, "Pig", 90, 15771042, 14377823);
        EntityList.addMapping(EntitySheep.class, "Sheep", 91, 15198183, 16758197);
        EntityList.addMapping(EntityCow.class, "Cow", 92, 4470310, 10592673);
        EntityList.addMapping(EntityChicken.class, "Chicken", 93, 10592673, 16711680);
        EntityList.addMapping(EntitySquid.class, "Squid", 94, 2243405, 7375001);
        EntityList.addMapping(EntityWolf.class, "Wolf", 95, 14144467, 13545366);
        EntityList.addMapping(EntityMooshroom.class, "MushroomCow", 96, 10489616, 12040119);
        EntityList.addMapping(EntitySnowman.class, "SnowMan", 97);
        EntityList.addMapping(EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
        EntityList.addMapping(EntityIronGolem.class, "VillagerGolem", 99);
        EntityList.addMapping(EntityHorse.class, "EntityHorse", 100, 12623485, 15656192);
        EntityList.addMapping(EntityRabbit.class, "Rabbit", 101, 10051392, 7555121);
        EntityList.addMapping(EntityVillager.class, "Villager", 120, 5651507, 12422002);
        EntityList.addMapping(EntityEnderCrystal.class, "EnderCrystal", 200);
    }

    public static class EntityEggInfo {
        public final int spawnedID;
        public final int primaryColor;
        public final int secondaryColor;
        public final StatBase field_151512_d;
        public final StatBase field_151513_e;

        public EntityEggInfo(int id, int baseColor, int spotColor) {
            this.spawnedID = id;
            this.primaryColor = baseColor;
            this.secondaryColor = spotColor;
            this.field_151512_d = StatList.getStatKillEntity(this);
            this.field_151513_e = StatList.getStatEntityKilledBy(this);
        }
    }

}

