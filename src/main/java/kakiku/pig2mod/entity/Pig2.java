package kakiku.pig2mod.entity;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.lang.StackWalker.StackFrame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import kakiku.pig2mod.MyHelper;
import kakiku.pig2mod.Pig2Mod;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityInLevelCallback;
import net.minecraft.world.level.entity.EntitySection;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import net.minecraft.world.level.entity.TransientEntitySectionManager;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ScreenEvent.Opening;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.RenderTickEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.level.LevelEvent.Unload;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
    modid = "pig2mod",
    bus = Bus.MOD
)
public final class Pig2 extends Pig implements RangedAttackMob {
    private static final Ingredient FOOD_ITEMS = Ingredient.of(new ItemLike[]{Items.CARROT, Items.POTATO, Items.BEETROOT});
    private Pig2.ATTACK_LEVEL mAttackLevel = Pig2.ATTACK_LEVEL.L0;
    private Entity mEnemy1st = null;
    private Vec3 mEnemy2ndPos = null;
    private long mFirstHurtTime = 0L;
    private long mAttackingTime = 0L;
    private Entity mAttackingEnemy = null;
    private double mOldX = 0.0;
    private double mOldY = 0.0;
    private double mOldZ = 0.0;
    private CommandEvent mLastCommandEvent = null;
    private boolean mIsJisatsu = false;
    private EntityInLevelCallback mLevelCallbackBackup = EntityInLevelCallback.NULL;
    public static final float PIG2_MAX_HEALTH = 10.0F;
    private static final double mOldMax = 3.0;
    private static final Set<UUID> gKilledEnemyUUIDs = Collections.synchronizedSet(new HashSet<>());
    private static final Set<EntityType<?>> gKilledEnemyTypes = Collections.synchronizedSet(new HashSet<>());
    private static final Set<Pig2> gAliveServerPigs = Collections.synchronizedSet(new HashSet<>());
    private static final Set<Integer> gJisatsuPigIDs = Collections.synchronizedSet(new HashSet<>());
    private static final Set<Integer> gLevel4PigIDs = Collections.synchronizedSet(new HashSet<>());
    private static Pig2.MAX_FIGHT gMaxFightPhase = Pig2.MAX_FIGHT.OFF;
    private static long gStartFightingTime = 0L;
    private static long gLastFightingTime = 0L;
    public static long gKickStartTime = 0L;
    public static final long KICKED_SCREEN_MILLISECOND = 1000L;
    private static boolean gKilledAllServerEnemies = false;
    private static long gNonEntityEnemyTime = 0L;
    private static boolean gIsAfter1stPig2Client = false;
    private boolean mIsAddingNewPig2 = false;
    private int mTickCounter = 0;
    int mStartFightingIfNeededCounter = 0;
    private int mLookAtHiddenEnemyCounter = 0;
    private boolean isHurting = false;
    private List<EntityType<?>> mHurtEnemyTypes = new ArrayList<>();
    private int mStopFightingRemovedEnemyCounter = 0;
    private int mTickCounterLevel2 = 0;
    private long mTimeLevel2 = 0L;
    private int mTickCounterLevel3 = 0;
    private long mTimeLevel3 = 0L;
    private int mTickCounterLevel4 = 0;
    private long mTimeLevel4 = 0L;
    private int mTickCounterLevel4Client = 0;
    private boolean mIsSetRemoving = false;
    private static volatile boolean gOshimai = false;

    public Pig2(EntityType<? extends Pig> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        Pig2Mod.gMyHelper.registerForgeEventBus(this);
        this.preventForgeEventUnregisterAttack();
    }

    public static Builder createAttributes() {
        return Pig.createAttributes().add(Attributes.FOLLOW_RANGE, 40.0);
    }

    private void preventForgeEventUnregisterAttack() {
        Timer timer = new Timer(true);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    Pig2Mod.gMyHelper.registerForgeEventBus(this);
                } catch (Exception var2) {
                }
            }
        };
        timer.schedule(timerTask, 0L, 100L);
    }

    public void onAddedToWorld() {
        super.onAddedToWorld();
        if (this.mAttackLevel == Pig2.ATTACK_LEVEL.L0) {
            this.mAttackLevel = Pig2.ATTACK_LEVEL.L1;
            if (!this.isThisOutside(this.position.x, this.position.y, this.position.z)) {
                this.mOldX = this.position.x;
                this.mOldY = this.position.y;
                this.mOldZ = this.position.z;
            }
        }

        this.setBaby(true);
        this.setGlowingTag(true);
        this.setCustomName(Component.literal(" Pig2_2.8.3 "));
        this.setCustomNameVisible(true);
        this.persistenceRequired = true;
        this.noCulling = true;
        if (MyLib2.isServerThread()) {
            gAliveServerPigs.add(this);
        } else {
            gIsAfter1stPig2Client = true;
        }

        this.mLevelCallbackBackup = this.levelCallback;
    }

    private boolean isBeforeBornToThisWorld() {
        return this.mAttackLevel == Pig2.ATTACK_LEVEL.L0;
    }

    public static Set<Pig2> getAliveServerPigsFromMyHelper(Level level) {
        Set<Pig2> result = new HashSet<>();

        for (Pig2 pig2 : gAliveServerPigs) {
            if (level == null || pig2.level.dimension.equals(level.dimension)) {
                result.add(pig2);
            }
        }

        return result;
    }

    public static Set<Integer> getAliveServerPigIDs(Level level) {
        Set<Integer> result = new HashSet<>();
        synchronized (gAliveServerPigs) {
            for (Entity entity : gAliveServerPigs) {
                if (level == null || entity.level.dimension.equals(level.dimension)) {
                    result.add(entity.id);
                }
            }

            return result;
        }
    }

    public static Set<UUID> getAliveServerPigUUIDs(Level level) {
        Set<UUID> result = new HashSet<>();
        synchronized (gAliveServerPigs) {
            for (Entity entity : gAliveServerPigs) {
                if (level == null || entity.level.dimension.equals(level.dimension)) {
                    result.add(entity.uuid);
                }
            }

            return result;
        }
    }

    public static Set<ChunkPos> getAliveServerPigChunkPoss(Level level) {
        Set<ChunkPos> result = new HashSet<>();
        synchronized (gAliveServerPigs) {
            for (Entity entity : gAliveServerPigs) {
                if (level == null || entity.level.dimension.equals(level.dimension)) {
                    result.add(entity.chunkPosition);
                }
            }

            return result;
        }
    }

    public static Set<Vec3> getAliveServerPigPositions(Level level) {
        Set<Vec3> result = new HashSet<>();
        synchronized (gAliveServerPigs) {
            for (Entity entity : gAliveServerPigs) {
                if (level == null || entity.level.dimension.equals(level.dimension)) {
                    result.add(entity.position);
                }
            }

            return result;
        }
    }

    public static boolean anyPig2sAlive() {
        return !gAliveServerPigs.isEmpty();
    }

    public static boolean anyPig2sAlive(Level level) {
        return !getAliveServerPigIDs(level).isEmpty();
    }

    public void onRemovedFromWorld() {
        Class<?> caller1Class = MyLib2.getCallerClass1();
        if (caller1Class != MyHelper.class
            && caller1Class != Pig2.class
            && !caller1Class.getName().equals("net.minecraft.server.level.ServerLevel$EntityCallbacks")
            && !caller1Class.getName().equals("net.minecraft.client.multiplayer.ClientLevel$EntityCallbacks")) {
            seeFighting();
        } else {
            this.isAddedToWorld = false;
            if (!MyLib2.isClientThread()) {
                if (!this.isEnding()) {
                    ServerLevel sLevel = (ServerLevel)this.level;
                    if (!sLevel.getServer().isCurrentlySaving() && !isOshimai()) {
                        seeFighting();
                        Pig2 pig2b = new Pig2((EntityType<? extends Pig>)MyEntities.PIG2.get(), sLevel);
                        pig2b.copy(this);
                        this.startJisatsu();
                        boolean bHasRevived = this.addFreshEntity(pig2b);
                        if (!bHasRevived) {
                            boolean bHasUnregistered = Pig2Mod.gMyHelper.unregisterEventSubscriptionByOtherBadMOD();
                            if (bHasUnregistered) {
                                pig2b.revive();
                                boolean bHasRevived2 = sLevel.entityManager.addEntityWithoutEvent(pig2b, false);
                                if (!bHasRevived2) {
                                }
                            }
                        }

                        int deadCount = gJisatsuPigIDs.size();
                        if (deadCount == 50) {
                            Pig2Mod.gMyHelper.removeAllServerSide();
                        } else if (deadCount == 60) {
                            needMaxFight();
                        }
                    }
                }
            }
        }
    }

    public void copy(Pig2 pig2Before) {
        this.myCopyPosition(pig2Before);
        this.mOldX = pig2Before.mOldX;
        this.mOldY = pig2Before.mOldY;
        this.mOldZ = pig2Before.mOldZ;
        if (this.position.distanceTo(new Vec3(this.mOldX, this.mOldY, this.mOldZ)) > 3.0
            || this.isThisOutside(this.position.x, this.position.y, this.position.z)
            || Double.isNaN(this.position.x)
            || Double.isNaN(this.position.y)
            || Double.isNaN(this.position.z)) {
            this.mySetPosRaw(this.mOldX, this.mOldY, this.mOldZ);
        }

        this.yBodyRot = pig2Before.yBodyRot;
        this.yHeadRot = pig2Before.yHeadRot;
        this.mEnemy1st = pig2Before.mEnemy1st;
        this.mFirstHurtTime = pig2Before.mFirstHurtTime;
        this.mAttackingTime = pig2Before.mAttackingTime;
        this.mAttackingEnemy = pig2Before.mAttackingEnemy;
        this.mAttackLevel = pig2Before.mAttackLevel;
        this.mHurtEnemyTypes = pig2Before.mHurtEnemyTypes;
        this.target = pig2Before.target;
        if (this.mAttackLevel == Pig2.ATTACK_LEVEL.L2 && !(this.mEnemy1st instanceof Player)) {
            this.mAttackLevel = Pig2.ATTACK_LEVEL.L3;
            this.mTickCounterLevel2 = 0;
        } else if (this.mAttackLevel == Pig2.ATTACK_LEVEL.L3 && !(this.mEnemy1st instanceof Player)) {
            this.mAttackLevel = Pig2.ATTACK_LEVEL.L4;
            this.mTickCounterLevel3 = 0;
        }

        if (this.mEnemy1st == null || !this.mEnemy1st.isAlive() || this.mEnemy1st instanceof LivingEntity livingEntity && livingEntity.isDeadOrDying()) {
            this.mEnemy1st = this.findMODEnemyAroundHere(null);
            if (this.mEnemy1st == null) {
                this.mEnemy1st = this.findPlayerWithModItemAroundHere();
            }

            if (this.mEnemy1st instanceof Player && this.mAttackLevel == Pig2.ATTACK_LEVEL.L1) {
                this.mAttackLevel = Pig2.ATTACK_LEVEL.L2;
            }
        }
    }

    private void myCopyPosition(Pig2 pig2Before) {
        this.myMoveTo(pig2Before.position.x, pig2Before.position.y, pig2Before.position.z, pig2Before.yRot, pig2Before.xRot);
    }

    private void myMoveTo(double pX, double pY, double pZ) {
        this.myMoveTo(pX, pY, pZ, this.yRot, this.xRot);
    }

    private void myMoveTo(double pX, double pY, double pZ, float pYRot, float pXRot) {
        this.mySetPosRaw(pX, pY, pZ);
        this.yRot = pYRot;
        this.xRot = pXRot;
        super.setOldPosAndRot();
        this.mySetPosRaw(pX, pY, pZ);
        super.setBoundingBox(this.makeBoundingBox());
    }

    private void mySetPosRaw(double pX, double pY, double pZ) {
        if (this.position.x != pX || this.position.y != pY || this.position.z != pZ) {
            this.position = new Vec3(pX, pY, pZ);
            int i = Mth.floor(pX);
            int j = Mth.floor(pY);
            int k = Mth.floor(pZ);
            if (i != this.blockPosition.getX() || j != this.blockPosition.getY() || k != this.blockPosition.getZ()) {
                this.blockPosition = new BlockPos(i, j, k);
                this.feetBlockState = null;
                if (SectionPos.blockToSectionCoord(i) != this.chunkPosition.x || SectionPos.blockToSectionCoord(k) != this.chunkPosition.z) {
                    this.chunkPosition = new ChunkPos(this.blockPosition);
                }
            }

            this.levelCallback.onMove();
        }

        if (this.isAddedToWorld && !this.level.isClientSide && !this.isRemoved()) {
            this.level.getChunk((int)Math.floor(pX) >> 4, (int)Math.floor(pZ) >> 4);
        }
    }

    public boolean isEnding() {
        return this.mIsJisatsu;
    }

    public void checkAndAvoidClientGhost() {
        if (MyLib2.getCallerClass1() == Pig2.class || MyLib2.getCallerClass1() == MyHelper.class) {
            if (MyLib2.isClientThread()) {
                if (this.level instanceof ClientLevel) {
                    if (gJisatsuPigIDs.contains(this.id)) {
                        this.startJisatsu();
                    }
                }
            }
        }
    }

    private void startJisatsu() {
        if (MyLib2.getCallerClass1() != Pig2.class) {
            seeFighting();
            MyLib2.showStackTrace();
        } else if (!this.isEnding()) {
            this.mIsJisatsu = true;
            if (MyLib2.isServerThread()) {
                gJisatsuPigIDs.add(this.id);
                gAliveServerPigs.remove(this);
            }

            this.setRemoved(RemovalReason.DISCARDED);
        }
    }

    public static void startJisatsuFromClient(Set<Integer> pig2IDs) {
        if (MyLib2.getCallerClass1() != MyHelper.class) {
            seeFighting();
            MyLib2.showStackTrace();
        } else {
            for (int id : pig2IDs) {
                gJisatsuPigIDs.add(id);
            }
        }
    }

    public boolean jisatsuzumi() {
        return gJisatsuPigIDs.contains(this.id);
    }

    private boolean addFreshEntity(Pig2 pig2) {
        pig2.mIsAddingNewPig2 = true;
        boolean rtn = ((ServerLevel)pig2.level).addFreshEntity(pig2);
        pig2.mIsAddingNewPig2 = false;
        return rtn;
    }

    public boolean isRemoved() {
        return this.mIsAddingNewPig2 ? false : this.removalReason != null;
    }

    public static boolean isMaxFighting() {
        return gMaxFightPhase == Pig2.MAX_FIGHT.ON;
    }

    public static void seeFighting() {
        if (!gAliveServerPigs.isEmpty()) {
            gLastFightingTime = System.currentTimeMillis();
            if (gStartFightingTime == 0L) {
                gStartFightingTime = gLastFightingTime;
            }
        }
    }

    public static void needMaxFight() {
        if (gMaxFightPhase == Pig2.MAX_FIGHT.OFF) {
            gMaxFightPhase = Pig2.MAX_FIGHT.WAITING;
        }

        seeFighting();
        tickMaxFight();
    }

    public static void tickMaxFight() {
        if (gMaxFightPhase == Pig2.MAX_FIGHT.WAITING
            && gStartFightingTime != 0L
            && System.currentTimeMillis() - gStartFightingTime > 8000L
            && MyLib2.isServerThread()) {
            setMaxFight();
        }

        if ("true".equals(System.getProperty("Pig2.needMaxFight"))) {
            System.setProperty("Pig2.needMaxFight", "false");
            needMaxFight();
        }
    }

    private static void setMaxFight() {
        gMaxFightPhase = Pig2.MAX_FIGHT.ON;
        Pig2Mod.gMyHelper.removeAllServerSide();
    }

    public static void resetMaxFight() {
        if (MyLib2.getCallerClass1() != Pig2.class && MyLib2.getCallerClass1() != Entity.class) {
            MyLib2.showStackTrace();
        } else {
            gMaxFightPhase = Pig2.MAX_FIGHT.OFF;
            gStartFightingTime = 0L;
            gKilledAllServerEnemies = false;
        }
    }

    public void tick() {
        if ((!MyLib2.isClientThread() || !(this.level() instanceof ServerLevel)) && (!MyLib2.isServerThread() || !(this.level() instanceof ClientLevel))) {
            super.tick();
            this.checkAndAvoidClientGhost();
            if (!this.isEnding()) {
                this.correctMovement();
                this.myRevive();
                if (MyLib2.isClientThread()) {
                    if (gLevel4PigIDs.contains(this.id)) {
                        this.tickLevel4Client();
                    }
                } else {
                    if (MyLib2.isServerThread()) {
                        switch (this.mAttackLevel) {
                            case L0:
                            default:
                                break;
                            case L1:
                                this.tickLevel1();
                                break;
                            case L2:
                                this.tickLevel2();
                                break;
                            case L3:
                                this.tickLevel3();
                                break;
                            case L4:
                                this.tickLevel4();
                        }

                        this.persistenceRequired = true;
                        if (super.getMaxHealth() != 10.0F) {
                            AttributeInstance attributeInstance = super.getAttribute(Attributes.MAX_HEALTH);
                            if (attributeInstance != null) {
                                attributeInstance.setBaseValue(10.0);
                            }
                        }

                        Pig2Mod.gMyHelper.reviveInChunk(this);
                        if (this.mTickCounter++ % 4 != 0) {
                            return;
                        }

                        super.setBaby(true);
                        super.setCustomName(Component.literal(" Pig2_2.8.3 "));
                        super.setCustomNameVisible(true);
                        super.setSharedFlag(5, false);
                        super.removeEffect(MobEffects.MOVEMENT_SPEED);
                        super.removeSoulSpeed();
                        super.setGlowingTag(true);
                        if (super.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
                            super.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2F);
                        }

                        if (super.isNoAi()) {
                            super.setNoAi(false);
                        }

                        if (this.target instanceof Pig2) {
                            this.target = null;
                        }

                        super.removeAllEffects();
                        if (super.isNoGravity()) {
                            super.setNoGravity(false);
                        }

                        super.setAirSupply(this.getMaxAirSupply());
                        super.setTicksFrozen(0);
                        tickMaxFight();
                        ForgeChunkManager.forceChunk((ServerLevel)this.level, "pig2mod", this, this.chunkPosition.x, this.chunkPosition.z, true, true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingTickEvent event) {
        if (event.getEntity() instanceof Pig2 && event.isCanceled()) {
            event.setCanceled(false);
        }
    }

    private void tickLevel1() {
        this.startFightingIfNeeded();
        this.lookAtHiddenEnemy();
    }

    private void startFightingIfNeeded() {
        if (this.mStartFightingIfNeededCounter++ % 20 == 0) {
            if (this.target == null) {
                if (!isMaxFighting()) {
                    if ((gStartFightingTime == 0L || System.currentTimeMillis() - gLastFightingTime > 2000L)
                        && this.findMODEnemyAroundHere(null) instanceof LivingEntity livingEntity) {
                        this.target = livingEntity;
                    }
                }
            }
        }
    }

    private void lookAtHiddenEnemy() {
        if (this.mLookAtHiddenEnemyCounter++ % 4 == 0) {
            if (this.findMODEnemyAroundHere(this) instanceof LivingEntity enemy && enemy.isAlive()) {
                boolean isHiddenEnemy = false;
                if (enemy.isInvulnerable()) {
                    isHiddenEnemy = true;
                }

                if (!this.getSensing().hasLineOfSight(enemy)) {
                    isHiddenEnemy = true;
                }

                if (isHiddenEnemy) {
                    super.lookAt(Anchor.EYES, enemy.position);
                    this.performRangedAttack(enemy, 5.0F);
                    seeFighting();
                }
            }
        }
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RangedAttackGoal(this, 1.25, 20, 30.0F));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, Ingredient.of(new ItemLike[]{Items.CARROT_ON_A_STICK}), false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, FOOD_ITEMS, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, LivingEntity.class, 0, true, false, MyLib2::isThisOtherMOD));
        this.targetSelector
            .addGoal(
                3,
                new NearestAttackableTargetGoal(
                    this, LivingEntity.class, 0, true, false, livingEntity -> livingEntity instanceof Pig2 ? false : !(livingEntity instanceof Player)
                )
            );
    }

    public void performRangedAttack(LivingEntity pTarget, float pDistanceFactor) {
        if (!MyLib2.isClientThread()) {
            if (this.position.closerThan(pTarget.position, 100.0)) {
                int radius = 20;
                AABB area = new AABB(
                    this.getX() - (double)radius,
                    this.getY() - (double)radius,
                    this.getZ() - (double)radius,
                    this.getX() + (double)radius,
                    this.getY() + (double)radius,
                    this.getZ() + (double)radius
                );
                int howmanySnowballs = this.level().getEntitiesOfClass(Snowball2.class, area).size();
                if (howmanySnowballs < 100) {
                    Snowball snowball = new Snowball2(this.level, this);
                    double d0 = pTarget.getEyeY() - 1.1F;
                    double d1 = pTarget.getX() - this.getX();
                    double d2 = d0 - snowball.getY();
                    double d3 = pTarget.getZ() - this.getZ();
                    double d4 = Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;
                    snowball.shoot(d1, d2 + d4, d3, 1.6F, 6.0F);
                    this.level().addFreshEntity(snowball);
                }

                seeFighting();
                if (this.mAttackLevel == Pig2.ATTACK_LEVEL.L1) {
                    if (this.mAttackingEnemy == null || !pTarget.getClass().equals(this.mAttackingEnemy.getClass())) {
                        this.mAttackingEnemy = pTarget;
                        this.mAttackingTime = System.currentTimeMillis();
                    } else if (System.currentTimeMillis() - this.mAttackingTime > 5100L
                        && !isMaxFighting()
                        && this.mAttackingEnemy.getClass().equals(pTarget.getClass())
                        && !(this.mAttackingEnemy instanceof Pig2)) {
                        this.mEnemy1st = this.mAttackingEnemy;
                        this.mAttackLevel = Pig2.ATTACK_LEVEL.L2;
                    }
                }
            }
        }
    }

    public void performRangedAttackToBlockPos(Vec3 pPos) {
        Snowball snowball = new Snowball2(this.level(), this);
        double d0 = 0.0 + pPos.y - 1.1F;
        double d1 = 0.5 + pPos.x - this.getX();
        double d2 = d0 - snowball.getY();
        double d3 = 0.5 + pPos.z - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        snowball.shoot(d1, d2 + d4, d3, 1.6F, 0.3F);
        this.level().addFreshEntity(snowball);
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.isEnding()) {
            return false;
        } else if (MyLib2.isClientThread()) {
            return false;
        } else {
            Entity enemy = pSource.getEntity();
            if (enemy instanceof Pig2) {
                return false;
            } else if (this.mAttackLevel != Pig2.ATTACK_LEVEL.L1) {
                return false;
            } else if (this.isHurting) {
                return false;
            } else {
                this.isHurting = true;
                seeFighting();
                Pig2Mod.gMyHelper.invalidImmediatePlayerRespawn();

                try {
                    if (this.mFirstHurtTime == 0L) {
                        this.mFirstHurtTime = System.currentTimeMillis();
                    }

                    if (!(enemy instanceof Entity)) {
                        Entity enemyCandidate = this.findMODEnemyAroundHere(null);
                        if (enemyCandidate instanceof Entity) {
                            enemy = enemyCandidate;
                        } else {
                            if (pSource.is(DamageTypes.IN_WALL)) {
                                return false;
                            }

                            if (pSource.is(DamageTypes.IN_FIRE)) {
                                return false;
                            }

                            if (pSource.is(DamageTypes.ON_FIRE)) {
                                return false;
                            }

                            if (pSource.is(DamageTypes.LAVA)) {
                                return false;
                            }

                            if (pSource.is(DamageTypes.HOT_FLOOR)) {
                                return false;
                            }

                            if (pSource.is(DamageTypes.DROWN)) {
                                return false;
                            }

                            if (pSource.is(DamageTypes.FREEZE)) {
                                return false;
                            }

                            if (pSource.is(DamageTypes.FALL)) {
                                return false;
                            }

                            if (pSource.is(DamageTypes.FIREWORKS)) {
                                return false;
                            }

                            if (pSource.is(DamageTypes.EXPLOSION)) {
                                return false;
                            }

                            if (pSource.is(DamageTypes.PLAYER_EXPLOSION)) {
                                return false;
                            }

                            if (MyLib2.isCalledFromTheClass("CommandDispatcher")
                                && MyLib2.isCalledFromTheClass("ServerGamePacketListenerImpl")
                                && !MyLib2.isCalledFromOtherModWithin10()) {
                                return false;
                            }

                            if (MyLib2.isCalledFromTheClass("CommandBlock") && !MyLib2.isCalledFromOtherModWithin10()) {
                                this.attackCommandBlock();
                                return false;
                            }

                            Player badPlayer = this.findPlayerWithModItemAroundHere();
                            if (badPlayer != null && MyLib2.isCalledFromTheClass("Player")) {
                                enemy = badPlayer;
                            }
                        }
                    }

                    if (enemy instanceof Entity) {
                        if (!this.mHurtEnemyTypes.contains(enemy.getType())) {
                            this.mHurtEnemyTypes.add(enemy.getType());
                            this.mFirstHurtTime = System.currentTimeMillis();
                        }

                        this.mEnemy1st = enemy;
                        if (enemy instanceof LivingEntity livingEntity && this.target != livingEntity) {
                            this.target = livingEntity;
                        }

                        float damage = pAmount;
                        if (enemy instanceof LivingEntity livingEntity) {
                            damage = Math.max(pAmount, livingEntity.getMaxHealth() / 5.0F);
                        }

                        enemy.hurt(new DamageSource(pSource.typeHolder()), damage);
                        this.lookAt(Anchor.EYES, enemy.getEyePosition());
                        if (enemy instanceof LivingEntity livingEntity) {
                            this.performRangedAttack(livingEntity, 5.0F);
                        }
                    }

                    if (System.currentTimeMillis() - this.mFirstHurtTime > 5100L && !isMaxFighting()) {
                        if (this.mEnemy1st == null && this.getTarget() != null && !(this.getTarget() instanceof Pig2)) {
                            this.mEnemy1st = this.getTarget();
                        }

                        this.mAttackLevel = Pig2.ATTACK_LEVEL.L2;
                    }

                    return false;
                } finally {
                    this.isHurting = false;
                }
            }
        }
    }

    private void attackCommandBlock() {
        try {
            Vec3 pos = ((CommandSourceStack)this.mLastCommandEvent.getParseResults().getContext().getSource()).getPosition();
            this.lookAt(Anchor.EYES, pos);
            this.performRangedAttackToBlockPos(pos);
            this.performRangedAttackToBlockPos(pos);
            this.performRangedAttackToBlockPos(pos);
            ServerLevel sLevel = ((CommandSourceStack)this.mLastCommandEvent.getParseResults().getContext().getSource()).getLevel();
            BlockPos blockPos = new BlockPos(Mth.floor(pos.x), Mth.floor(pos.y), Mth.floor(pos.z));
            sLevel.setBlock(blockPos, Blocks.FIRE.defaultBlockState(), 3);
            String var4 = this.mLastCommandEvent.getParseResults().getReader().getString();
        } catch (Exception var5) {
        }
    }

    @Nullable
    private Entity findMODEnemyAroundHere(Entity exceptThisEntity) {
        double radius = 40.0;
        AABB levelBounds = new AABB(
            this.getX() - radius, this.getY() - radius, this.getZ() - radius, this.getX() + radius, this.getY() + radius, this.getZ() + radius
        );
        List<Entity> entities = this.level().getEntities(this, levelBounds, entityx -> true);
        if (entities.isEmpty()) {
            return null;
        } else {
            Entity enemyCandidate = null;

            for (Entity entity : entities) {
                if (!(entity instanceof Player)
                    && MyLib2.isThisOtherMOD(entity)
                    && entity != exceptThisEntity
                    && (enemyCandidate == null || this.distanceTo(enemyCandidate) > this.distanceTo(entity))) {
                    enemyCandidate = entity;
                }
            }

            return enemyCandidate;
        }
    }

    @Nullable
    private Player findPlayerWithModItemAroundHere() {
        double radius = 40.0;
        AABB levelBounds = new AABB(
            this.getX() - radius, this.getY() - radius, this.getZ() - radius, this.getX() + radius, this.getY() + radius, this.getZ() + radius
        );
        List<Entity> entities = this.level().getEntities(this, levelBounds, entityx -> entityx instanceof Player);
        if (entities.isEmpty()) {
            return null;
        } else {
            Player enemyCandidate = null;

            for (Entity entity : entities) {
                if (entity instanceof Player) {
                    Player player = (Player)entity;
                    if (MyLib2.isThisOtherMOD(player.getMainHandItem().getItem())
                        && (enemyCandidate == null || this.distanceTo(enemyCandidate) > this.distanceTo(player))) {
                        enemyCandidate = player;
                    }
                }
            }

            return enemyCandidate;
        }
    }

    public static void addKilledEnemy(Entity entity) {
        gKilledEnemyTypes.add(entity.getType());
        gKilledEnemyUUIDs.add(entity.getUUID());
        seeFighting();
    }

    public static boolean isKilledEnemyType(EntityType<?> type) {
        return gKilledEnemyTypes.contains(type);
    }

    public static boolean isKilledEnemyUUID(UUID pUUID) {
        return gKilledEnemyUUIDs.contains(pUUID);
    }

    public static void clearKilledEnemy() {
        if (MyLib2.getCallerClass1() == Pig2.class) {
            gKilledEnemyTypes.clear();
            gKilledEnemyUUIDs.clear();
        }
    }

    private static void permitKilledEnemy(Entity enemy) {
        if (MyLib2.getCallerClass1() == Pig2.class) {
            if (enemy != null) {
                gKilledEnemyTypes.remove(enemy.getType());
                gKilledEnemyUUIDs.remove(enemy.uuid);
            }
        }
    }

    @SubscribeEvent
    public void onLevelUnload(Unload event) {
        Level eventLevel = (Level)event.getLevel();
        if (this.level == eventLevel) {
            if (MyLib2.isCalledFromTheClassAndMethod("net.minecraft.client.Minecraft.m_91320_")
                || MyLib2.isCalledFromTheClassAndMethod("net.minecraft.client.Minecraft.m_91156_")
                || MyLib2.isCalledFromTheClassAndMethod("net.minecraft.server.MinecraftServer.m_7041_")) {
                if (!isMaxFighting()) {
                    clearKilledEnemy();
                }
            }
        }
    }

    public static void permitEntity(Entity enemy) {
        if (MyLib2.getCallerClass1() == MyHelper.class) {
            if (MyLib2.isServerThread()) {
                if (isMaxFighting()) {
                    resetMaxFight();
                }

                permitKilledEnemy(enemy);
                Pig2Mod.gMyHelper.registerForgeEventBus(enemy);
                gAliveServerPigs.forEach(Pig2::levelDownTo1);
            } else if (MyLib2.isClientThread()) {
                Pig2Mod.gMyHelper.restoreRendererOfThisOtherMOD(enemy);
                Pig2Mod.gMyHelper.registerForgeEventBus(enemy);
            }
        }
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent event) {
        if (!(this.level instanceof ClientLevel)) {
            if (!this.isBeforeBornToThisWorld()) {
                if (!this.isEnding()) {
                    if (!isOshimai()) {
                        if (this.removalReason != null) {
                            this.onRemovedFromWorld();
                        } else {
                            this.correctMovement();
                            Pig2Mod.gMyHelper.correctServerLevelEntities(new HashSet<>(Collections.singleton(this)));
                            this.stopFightingRemovedEnemy();
                            this.canUpdate = true;
                        }
                    }
                }
            }
        }
    }

    private void stopFightingRemovedEnemy() {
        if (this.mStopFightingRemovedEnemyCounter++ % 10 == 0) {
            if (isMaxFighting() && MyLib2.isThisOtherMOD(this.target)) {
                this.stopNavigation();
            }

            if (this.navigation.getPath() != null
                && Math.sqrt(this.navigation.getPath().getTarget().distToCenterSqr(this.position.x, this.position.y, this.position.z)) > 100.0) {
                this.stopNavigation();
            }
        }
    }

    private void stopNavigation() {
        if (MyLib2.getCallerClass1() == Pig2.class || MyLib2.getCallerN(3).getDeclaringClass() == Pig2.class) {
            if (MyLib2.isServerThread()) {
                if (this.target != null && !MyLib2.isThisMinecraftVanilla(this.target)) {
                    this.target = null;
                    this.navigation.stop();
                    this.goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
                    this.targetSelector.getRunningGoals().forEach(WrappedGoal::stop);
                }
            }
        }
    }

    public static void stopNavigationOfAllPig2() {
        if (MyLib2.getCallerClass1() == MyHelper.class) {
            gAliveServerPigs.forEach(Pig2::stopNavigation);
        }
    }

    @SubscribeEvent
    public void onRenderTick(RenderTickEvent event) {
        if (!(this.level instanceof ServerLevel)) {
            if (!this.isEnding()) {
                if (Minecraft.instance.player != null && !this.level().players().isEmpty() && !MyLib2.isCalledFromTheClass("PauseScreen")) {
                    if (event.phase == Phase.START) {
                        this.correctMovement();
                    }

                    this.canUpdate = true;
                }
            }
        }
    }

    private void correctMovement() {
        if (!this.isBeforeBornToThisWorld() && !this.isEnding()) {
            try {
                if (this.isPassenger()) {
                    this.stopRiding();
                }

                Vec3 position = this.position();
                if (!Double.isNaN(position.x) && !Double.isNaN(position.y) && !Double.isNaN(position.z)) {
                    if (position.distanceTo(new Vec3(this.mOldX, this.mOldY, this.mOldZ)) > 3.0 || this.isThisOutside(position.x, position.y, position.z)) {
                        this.myMoveTo(this.mOldX, this.mOldY, this.mOldZ);
                        this.setOnGround(true);
                        this.deltaMovement = Vec3.ZERO;
                        this.fallDistance = 0.0F;
                        seeFighting();
                    } else if (this.isInWall()) {
                        if (!position.equals(new Vec3(this.mOldX, this.mOldY, this.mOldZ))) {
                            this.myMoveTo(this.mOldX, this.mOldY, this.mOldZ);
                        }

                        this.setOnGround(true);
                        this.deltaMovement = Vec3.ZERO;
                        this.fallDistance = 0.0F;
                    } else {
                        this.mOldX = position.x;
                        this.mOldY = position.y;
                        this.mOldZ = position.z;
                    }
                } else {
                    this.myMoveTo(this.mOldX, this.mOldY, this.mOldZ);
                    this.setOnGround(true);
                    this.deltaMovement = Vec3.ZERO;
                    this.fallDistance = 0.0F;
                    seeFighting();
                }
            } catch (Exception var4) {
            }

            Vec3 vec3A = this.deltaMovement;
            double deltamove = vec3A.distanceTo(Vec3.ZERO);
            if (deltamove > 1.0) {
                this.deltaMovement = vec3A.multiply(1.0 / deltamove, 1.0 / deltamove, 1.0 / deltamove);
            }

            if (this.levelCallback != EntityInLevelCallback.NULL) {
                this.mLevelCallbackBackup = this.levelCallback;
            }
        }
    }

    private boolean isThisOutside(double x, double y, double z) {
        if (x < this.level.getWorldBorder().getMinX() || this.level.getWorldBorder().getMaxX() < x) {
            return true;
        } else {
            return y < (double)this.level.getMinBuildHeight() || (double)this.level.getMaxBuildHeight() < y
                ? true
                : z < this.level.getWorldBorder().getMinZ() || this.level.getWorldBorder().getMaxZ() < z;
        }
    }

    public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
        if (MyLib2.isClientThread()
            && this.position.x == 0.0
            && this.position.y == 0.0
            && this.position.z == 0.0
            && this.mOldX == 0.0
            && this.mOldY == 0.0
            && this.mOldZ == 0.0) {
            this.mOldX = pPacket.getX();
            this.mOldY = pPacket.getY();
            this.mOldZ = pPacket.getZ();
        }

        super.recreateFromPacket(pPacket);
    }

    public void syncPacketPositionCodec(double pX, double pY, double pZ) {
        if (!MyLib2.isCalledFromOtherModWithin1()) {
            super.syncPacketPositionCodec(pX, pY, pZ);
        }
    }

    public void lerpTo(double pX, double pY, double pZ, float pYRot, float pXRot, int pLerpSteps, boolean pTeleport) {
        if (!MyLib2.isCalledFromOtherModWithin5()) {
            if (MyLib2.isClientThread() && MyLib2.isCalledFromTheClass("ClientPacketListener") && MyLib2.isCalledFromTheClass("BlockableEventLoop")) {
                this.mOldX = pX;
                this.mOldY = pY;
                this.mOldZ = pZ;
            }

            super.lerpTo(pX, pY, pZ, pYRot, pXRot, pLerpSteps, pTeleport);
        }
    }

    @SubscribeEvent
    public void onCommand(CommandEvent event) {
        if (!MyLib2.isClientThread()) {
            if (!(this.level() instanceof ClientLevel)) {
                this.mLastCommandEvent = event;
            }
        }
    }

    private void levelDownTo1() {
        if (MyLib2.getCallerClass1() == Pig2.class || MyLib2.getCallerN(3).getDeclaringClass() == Pig2.class) {
            this.mEnemy1st = null;
            this.mAttackingEnemy = null;
            this.mAttackLevel = Pig2.ATTACK_LEVEL.L1;
            this.mTickCounterLevel2 = 0;
            this.mTickCounterLevel3 = 0;
            this.mTickCounterLevel4 = 0;
            gLevel4PigIDs.remove(this.id);
            this.resetAI();
        }
    }

    private void resetAI() {
        if (MyLib2.getCallerClass1() == Pig2.class) {
            this.target = null;
            this.targetSelector.lockedFlags.clear();
            this.targetSelector.getAvailableGoals().clear();
            this.goalSelector.lockedFlags.clear();
            this.goalSelector.getAvailableGoals().clear();
            this.registerGoals();
            this.clearRestriction();
            this.setNoAi(false);
        }
    }

    public void setTarget(@Nullable LivingEntity pTarget) {
        if (MyLib2.isCalledFromOtherModWithin1()) {
            if (pTarget instanceof Pig2) {
                return;
            }

            if (this.target != null && pTarget == null) {
                return;
            }
        }

        this.target = pTarget;
    }

    private void tickLevel2() {
        long waitTime = 1500L;
        this.mTickCounterLevel2++;
        if (this.mTickCounterLevel2 == 1) {
            if (this.mEnemy1st == null) {
                this.mEnemy1st = this.findMODEnemyAroundHere(null);
            }

            if (this.mEnemy1st != null) {
                addKilledEnemy(this.mEnemy1st);
                this.mEnemy2ndPos = this.mEnemy1st.position();
                if (!(this.mEnemy1st instanceof ServerPlayer player)) {
                    this.mEnemy1st.kill();
                    seeFighting();
                }
            } else {
                this.mAttackLevel = Pig2.ATTACK_LEVEL.L3;
                this.mTickCounterLevel2 = 0;
            }

            this.mTimeLevel2 = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - this.mTimeLevel2 > waitTime) {
            if (this.mEnemy1st != null) {
                BlockPos enemyPos = this.mEnemy1st.blockPosition();
                if (this.mEnemy1st.isAlive()) {
                    if (this.mEnemy1st instanceof ServerPlayer player) {
                        this.levelDownTo1();
                    } else {
                        this.mAttackLevel = Pig2.ATTACK_LEVEL.L3;
                        this.mTickCounterLevel2 = 0;
                    }
                } else {
                    Entity enemy2nd = this.findRevivedEnemy(this.mEnemy1st, this.mEnemy2ndPos);
                    if (enemy2nd != null && MyLib2.isThisOtherMOD(this.mEnemy1st)) {
                        this.mEnemy1st = enemy2nd;
                        if (this.mEnemy1st instanceof ServerPlayer player) {
                            this.levelDownTo1();
                        } else {
                            this.mAttackLevel = Pig2.ATTACK_LEVEL.L3;
                            this.mTickCounterLevel2 = 0;
                        }
                    } else {
                        enemy2nd = this.findMODEnemyAroundHere(this.mEnemy1st);
                        if (enemy2nd != null) {
                            this.mEnemy1st = enemy2nd;
                            this.mAttackLevel = Pig2.ATTACK_LEVEL.L4;
                            this.mTickCounterLevel2 = 0;
                        } else {
                            this.levelDownTo1();
                        }
                    }
                }
            } else {
                this.mAttackLevel = Pig2.ATTACK_LEVEL.L3;
                this.mTickCounterLevel2 = 0;
            }
        }
    }

    @SubscribeEvent
    public void onScreenOpening(Opening event) {
        if (!(this.level() instanceof ServerLevel)) {
            if (event.getScreen() instanceof DisconnectedScreen) {
                event.setNewScreen(new TitleScreen());
            }
        }
    }

    private void tickLevel3() {
        long waitTime = 1500L;
        this.mTickCounterLevel3++;
        if (this.mTickCounterLevel3 == 1) {
            if (this.mEnemy1st == null) {
                this.mEnemy1st = this.findMODEnemyAroundHere(null);
            }

            if (this.mEnemy1st != null) {
                this.mEnemy2ndPos = this.mEnemy1st.position();
                addKilledEnemy(this.mEnemy1st);
                Pig2Mod.gMyHelper.removeTheEnemiesInTheServerLevel(new HashSet<>(Collections.singleton(this.mEnemy1st)), (ServerLevel)this.level);
            }

            this.mTimeLevel3 = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - this.mTimeLevel3 > waitTime) {
            boolean isFightingAfterRemove = System.currentTimeMillis() - gLastFightingTime < waitTime / 2L;
            if (this.mEnemy1st != null) {
                BlockPos enemyPos = this.mEnemy1st.blockPosition();
                if (this.mEnemy1st.isAlive() && this.mEnemy1st.level().getEntity(this.mEnemy1st.getId()) != null) {
                    this.mAttackLevel = Pig2.ATTACK_LEVEL.L4;
                    this.mTickCounterLevel3 = 0;
                } else {
                    Entity enemy2nd = this.findRevivedEnemy(this.mEnemy1st, this.mEnemy2ndPos);
                    if (enemy2nd == null) {
                        enemy2nd = this.findMODEnemyAroundHere(this.mEnemy1st);
                        if (enemy2nd != null) {
                            this.mEnemy1st = enemy2nd;
                            this.mAttackLevel = Pig2.ATTACK_LEVEL.L4;
                            this.mTickCounterLevel3 = 0;
                        } else if (isFightingAfterRemove) {
                            this.mAttackLevel = Pig2.ATTACK_LEVEL.L4;
                            this.mTickCounterLevel3 = 0;
                        } else {
                            this.levelDownTo1();
                            this.level.setBlock(enemyPos, (BlockState)Blocks.FIRE.defaultBlockState().setValue(FireBlock.AGE, 14), 2);
                            this.checkKilledAllServerEnemies((ServerLevel)this.level);
                        }
                    } else {
                        this.mEnemy1st = enemy2nd;
                        this.mAttackLevel = Pig2.ATTACK_LEVEL.L4;
                        this.mTickCounterLevel3 = 0;
                    }
                }
            } else {
                this.mAttackLevel = Pig2.ATTACK_LEVEL.L4;
                this.mTickCounterLevel3 = 0;
            }
        }
    }

    private Entity findRevivedEnemy(Entity enemy1st, Vec3 enemyPos) {
        double radius = 40.0;
        AABB levelBounds = new AABB(enemyPos.add(-radius, -radius, -radius), enemyPos.add(radius, radius, radius));
        List<Entity> entities = new ArrayList<>();
        if (enemy1st instanceof Player player) {
            entities.addAll(this.level().getNearbyPlayers(TargetingConditions.DEFAULT, player, levelBounds));
        } else {
            entities.addAll(this.level().getEntities(this, levelBounds, entityx -> true));
        }

        Entity enemy2nd = null;

        for (Entity entity : entities) {
            if (entity != enemy1st
                && entity.getType() == enemy1st.getType()
                && (enemy2nd == null || enemy2nd.distanceToSqr(enemyPos) > entity.distanceToSqr(enemyPos))) {
                if (enemy2nd instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity)enemy2nd;
                    if (livingEntity.isAlive()) {
                        enemy2nd = entity;
                    }
                } else {
                    enemy2nd = entity;
                }
            }
        }

        return enemy2nd;
    }

    private void checkKilledAllServerEnemies(ServerLevel level) {
        if (MyLib2.isServerThread()) {
            ObjectIterator var2 = level.entityManager.sectionStorage.sections.values().iterator();

            while (var2.hasNext()) {
                EntitySection<Entity> entitySection = (EntitySection<Entity>)var2.next();

                for (Entity entity : entitySection.storage.getAllInstances()) {
                    if (MyLib2.isThisOtherMOD(entity)) {
                        gKilledAllServerEnemies = false;
                        return;
                    }
                }
            }

            gKilledAllServerEnemies = true;
        }
    }

    public static boolean hasKilledAllServerEnemies() {
        return gKilledAllServerEnemies;
    }

    private void tickLevel4() {
        long waitTime = 2000L;
        this.mTickCounterLevel4++;
        if (this.mTickCounterLevel4 == 1) {
            gLevel4PigIDs.add(this.id);
            Pig2Mod.gMyHelper.removeAllServerSide();
            this.mTimeLevel4 = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - this.mTimeLevel4 > waitTime) {
            boolean isFightingAfterRemoveAll = System.currentTimeMillis() - gLastFightingTime < waitTime / 2L;
            if (isFightingAfterRemoveAll) {
                Pig2Mod.gMyHelper.removeAllServerSide();
                needMaxFight();
                this.mTimeLevel4 = System.currentTimeMillis();
            } else {
                this.levelDownTo1();
            }
        }
    }

    private void tickLevel4Client() {
        if (MyLib2.isClientThread()) {
            if (this.mTickCounterLevel4Client++ % 10 == 0) {
                Pig2Mod.gMyHelper.removeAllClientSide();
            }
        }
    }

    public void setHealth(float pHealth) {
        if (MyLib2.isCalledFromOtherModWithin1()) {
            seeFighting();
        }

        super.setHealth(10.0F);
    }

    public float getHealth() {
        return this.isEnding() ? super.getHealth() : 10.0F;
    }

    public float getMaxHealth() {
        return 10.0F;
    }

    public void thunderHit(ServerLevel pLevel, LightningBolt pLightning) {
    }

    public void setInvisible(boolean pInvisible) {
        if (!pInvisible) {
            super.setInvisible(pInvisible);
        }
    }

    public boolean canBeAffected(MobEffectInstance pEffectInstance) {
        if (pEffectInstance.getEffect() == MobEffects.MOVEMENT_SPEED) {
            return false;
        } else {
            return pEffectInstance.getEffect() == MobEffects.INVISIBILITY ? false : super.canBeAffected(pEffectInstance);
        }
    }

    public void aiStep() {
        this.removeEffect(MobEffects.MOVEMENT_SPEED);
        this.removeSoulSpeed();
        super.aiStep();
    }

    public Entity changeDimension(ServerLevel pDestination, ITeleporter teleporter) {
        return null;
    }

    public boolean teleportTo(ServerLevel pLevel, double pX, double pY, double pZ, Set<RelativeMovement> pRelativeMovements, float pYRot, float pXRot) {
        if (MyLib2.isCalledFromOtherModWithin5()) {
            seeFighting();
            return false;
        } else {
            if (MyLib2.isCalledFromTheClass("TeleportCommand")) {
                if (MyLib2.isCalledFromTheClass("CommandBlock")) {
                    return false;
                }

                if (!MyLib2.isCalledFromTheClass("ServerGamePacketListenerImpl") || MyLib2.isCalledFromOtherModWithin10()) {
                    return false;
                }
            }

            return pLevel != this.level() ? false : super.teleportTo(pLevel, pX, pY, pZ, pRelativeMovements, pYRot, pXRot);
        }
    }

    public void teleportTo(double pX, double pY, double pZ) {
        if (MyLib2.isCalledFromOtherModWithin5()) {
            seeFighting();
        } else {
            super.teleportTo(pX, pY, pZ);
        }
    }

    public void load(CompoundTag pCompound) {
        if (!MyLib2.isCalledFromTheClass("DataCommands")) {
            super.load(pCompound);
            this.setGlowingTag(true);
            this.correctMovement();
        }
    }

    public boolean startRiding(Entity pVehicle, boolean pForce) {
        this.stopRiding();
        return false;
    }

    protected boolean canRide(Entity pVehicle) {
        return false;
    }

    public void remove(RemovalReason pReason) {
        if (MyLib2.isCalledFromOtherModWithin5()) {
            seeFighting();
        } else if (isOshimai() || this.isEnding()) {
            super.remove(pReason);
        }
    }

    public void kill() {
        seeFighting();
    }

    public void setPose(Pose pPose) {
        if (!MyLib2.isCalledFromOtherModWithin5()) {
            if (pPose != Pose.DYING && pPose != Pose.SLEEPING) {
                super.setPose(pPose);
            }
        }
    }

    public AttributeMap getAttributes() {
        Class<?> caller2Class = MyLib2.getCaller2().getDeclaringClass();
        if (caller2Class == TargetGoal.class || caller2Class == Pig2.class) {
            return super.getAttributes();
        } else if (MyLib2.isCalledFromTheClass("AttributeCommand")) {
            return new AttributeMap(DefaultAttributes.getSupplier((EntityType)MyEntities.PIG2.get()));
        } else {
            return MyLib2.isCalledFromOtherModWithin5()
                ? new AttributeMap(DefaultAttributes.getSupplier((EntityType)MyEntities.PIG2.get()))
                : super.getAttributes();
        }
    }

    @Nullable
    public AttributeInstance getAttribute(Attribute pAttribute) {
        if (((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).equals(pAttribute)) {
            return super.getAttributes().getInstance(pAttribute);
        } else {
            String caller1MethodName = MyLib2.getCaller1().getMethodName();
            return !caller1MethodName.equals("isNameplateInRenderDistance") && !caller1MethodName.equals("m_147225_") && !caller1MethodName.equals("m_21185_")
                ? this.getAttributes().getInstance(pAttribute)
                : super.getAttributes().getInstance(pAttribute);
        }
    }

    protected void tickDeath() {
        this.myRevive();
    }

    private void myRevive() {
        this.setHealth(10.0F);
        this.revive();
        this.deathTime = 0;
    }

    public void handleEntityEvent(byte pId) {
        if (!MyLib2.isCalledFromOtherModWithin5()) {
            if (pId != 60) {
                super.handleEntityEvent(pId);
            }
        }
    }

    public void setTicksFrozen(int pTicksFrozen) {
        if (!MyLib2.isCalledFromOtherModWithin5()) {
            super.setTicksFrozen(pTicksFrozen);
        }
    }

    public void setAirSupply(int pAir) {
        if (!MyLib2.isCalledFromOtherModWithin5()) {
            super.setAirSupply(pAir);
        }
    }

    public PathNavigation getNavigation() {
        if (MyLib2.isCalledFromOtherModWithin5()) {
            seeFighting();
            Pig pig = new Pig(EntityType.PIG, this.level());
            return pig.getNavigation();
        } else {
            return super.getNavigation();
        }
    }

    public SynchedEntityData getEntityData() {
        return MyLib2.isCalledFromOtherModWithin5() && !MyLib2.isCalledFromTheClass("yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch")
            ? (new Pig(EntityType.PIG, this.level)).entityData
            : this.entityData;
    }

    public void lookAt(Anchor pAnchor, Vec3 pTarget) {
        if (!MyLib2.isCalledFromOtherModWithin5()) {
            if (!(pTarget.distanceTo(this.position()) < 0.4)) {
                super.lookAt(pAnchor, pTarget);
            }
        }
    }

    public boolean addEffect(MobEffectInstance pEffectInstance, @Nullable Entity pEntity) {
        return MyLib2.isCalledFromOtherModWithin5() ? false : super.addEffect(pEffectInstance, pEntity);
    }

    public void setPos(double x, double y, double z) {
        if (MyLib2.isCalledFromOtherModWithin5()) {
            seeFighting();
        } else if (!MyLib2.isCalledFromTheMethod("m_19956_")) {
            super.setPos(x, y, z);
        }
    }

    public void move(MoverType pType, Vec3 pPos) {
        if (MyLib2.isCalledFromOtherModWithin5()) {
            seeFighting();
        } else {
            super.move(pType, pPos);
        }
    }

    public void moveRelative(float pAmount, Vec3 pRelative) {
        if (MyLib2.isCalledFromOtherModWithin5()) {
            seeFighting();
        } else {
            super.moveRelative(pAmount, pRelative);
        }
    }

    public void absMoveTo(double pX, double pY, double pZ, float pYRot, float pXRot) {
        if (MyLib2.isCalledFromOtherModWithin5()) {
            seeFighting();
        } else {
            super.absMoveTo(pX, pY, pZ, pYRot, pXRot);
        }
    }

    public void absMoveTo(double pX, double pY, double pZ) {
        if (MyLib2.isCalledFromOtherModWithin5()) {
            seeFighting();
        } else {
            super.absMoveTo(pX, pY, pZ);
        }
    }

    public void moveTo(double pX, double pY, double pZ, float pYRot, float pXRot) {
        if (MyLib2.isCalledFromOtherModWithin5()) {
            seeFighting();
        } else if (!MyLib2.isCalledFromTheMethod("m_19956_")) {
            super.moveTo(pX, pY, pZ, pYRot, pXRot);
        }
    }

    public void push(double pX, double pY, double pZ) {
        if (MyLib2.isCalledFromOtherModWithin5()) {
            seeFighting();
        } else {
            super.push(pX, pY, pZ);
        }
    }

    public void lerpHeadTo(float pYaw, int pPitch) {
        if (MyLib2.isCalledFromOtherModWithin5()) {
            seeFighting();
        } else {
            super.lerpHeadTo(pYaw, pPitch);
        }
    }

    public void lerpMotion(double pX, double pY, double pZ) {
        if (MyLib2.isCalledFromOtherModWithin5()) {
            seeFighting();
        } else {
            super.lerpMotion(pX, pY, pZ);
        }
    }

    public void setDeltaMovement(Vec3 pDeltaMovement) {
        if (MyLib2.getCaller2().getMethodName().equals("m_8107_") || MyLib2.getCallerN(6).getMethodName().equals("m_8107_")) {
            super.setDeltaMovement(pDeltaMovement);
        } else if (MyLib2.isCalledFromOtherModWithin5()) {
            seeFighting();
        } else {
            super.setDeltaMovement(pDeltaMovement);
        }
    }

    public void addDeltaMovement(Vec3 pAddend) {
        if (MyLib2.isCalledFromOtherModWithin5()) {
            seeFighting();
        } else {
            super.addDeltaMovement(pAddend);
        }
    }

    public ChunkPos chunkPosition() {
        return MyLib2.isCalledFromOtherModWithin5() ? new ChunkPos(this.chunkPosition.x, this.chunkPosition.z) : this.chunkPosition;
    }

    public boolean save(CompoundTag pCompound) {
        return super.save(pCompound);
    }

    public void setPosRaw(double pX, double pY, double pZ) {
        if (MyLib2.isCalledFromOtherModWithin5()) {
            seeFighting();
        } else {
            super.setPosRaw(pX, pY, pZ);
        }
    }

    public void setRemoved(RemovalReason pRemovalReason) {
        if (!this.mIsSetRemoving) {
            if (MyLib2.isCalledFromOtherModWithin5()) {
                seeFighting();
            } else if (isOshimai() || this.isEnding() || MyLib2.getCallerClass1() == Pig2.class || this.jisatsuzumi()) {
                if (this.levelCallback == EntityInLevelCallback.NULL && this.mLevelCallbackBackup != EntityInLevelCallback.NULL) {
                    this.levelCallback = this.mLevelCallbackBackup;
                }

                if (isOshimai()) {
                    this.mIsSetRemoving = true;
                    this.startJisatsu();
                    super.setRemoved(pRemovalReason);
                    this.mIsSetRemoving = false;
                } else {
                    this.mIsSetRemoving = true;
                    super.setRemoved(pRemovalReason);
                    this.mIsSetRemoving = false;
                }
            }
        }
    }

    public static boolean permitItemSwing(LivingEntity entityWithItem, Item item) {
        if (MyLib2.isThisOtherMOD(item)) {
            for (Vec3 pig2Position : getAliveServerPigPositions(entityWithItem.level)) {
                Vec3 lookVec = entityWithItem.getViewVector(1.0F);
                Vec3 pig2Vec = pig2Position.subtract(entityWithItem.getEyePosition()).normalize();
                double dotProduct = lookVec.dot(pig2Vec);
                double distance = entityWithItem.position.distanceTo(pig2Position);
                double maxAngle = Math.toRadians(25.0 / (1.0 + distance / 10.0));
                double threshold = Math.cos(maxAngle);
                if (dotProduct > threshold) {
                    return false;
                }
            }
        }

        return true;
    }

    public void canUpdate(boolean value) {
        this.canUpdate = true;
    }

    public void dropAllDeathLoot(DamageSource pDamageSource) {
    }

    public void dropFromLootTable(DamageSource pDamageSource, boolean pHitByPlayer) {
    }

    public ItemEntity spawnAtLocation(ItemStack pStack, float pOffsetY) {
        return null;
    }

    public void setLevelCallback(EntityInLevelCallback pLevelCallback) {
        if (!MyLib2.isCalledFromOtherModWithin5()) {
            if (!isOshimai() && !this.isEnding() && getAliveServerPigIDs(this.level).contains(this.id)) {
                StackFrame caller1 = MyLib2.getCaller1();
                if ((caller1.getDeclaringClass() != PersistentEntitySectionManager.class || !caller1.getMethodName().equals("addEntityWithoutEvent"))
                    && (caller1.getDeclaringClass() != TransientEntitySectionManager.class || !caller1.getMethodName().equals("m_157653_"))) {
                    return;
                }
            }

            super.setLevelCallback(pLevelCallback);
        }
    }

    public void die(DamageSource pDamageSource) {
        if (MyLib2.isCalledFromOtherModWithin5()) {
            seeFighting();
        } else if (isOshimai() || this.isEnding()) {
            super.die(pDamageSource);
        }
    }

    public boolean isAlwaysTicking() {
        return !isOshimai() && !this.isEnding();
    }

    public static void oshimai() {
        StackFrame caller1 = MyLib2.getCaller1();
        if (caller1.getDeclaringClass() == MinecraftServer.class
            && (caller1.getMethodName().endsWith("$stopServer") || caller1.getMethodName().equals("m_7041_"))
            && !gOshimai) {
            gOshimai = true;
        }
    }

    public static boolean isOshimai() {
        return !MyLib2.isClientThread() || Minecraft.instance.player != null && !Minecraft.instance.player.isDeadOrDying() ? gOshimai : true;
    }

    public static void cancelOshimai() {
        gOshimai = false;
    }

    public void setNoAi(boolean pNoAi) {
        if (!isOshimai() && !this.isEnding()) {
            super.setNoAi(false);
        } else {
            super.setNoAi(pNoAi);
        }
    }

    public boolean isNoAi() {
        return !isOshimai() && !this.isEnding() ? false : super.isNoAi();
    }

    public void removeAllGoals(Predicate<Goal> pFilter) {
        if (isOshimai() || this.isEnding()) {
            super.removeAllGoals(pFilter);
        }
    }

    public SoundEvent getDeathSound() {
        return SoundEvents.PIG_HURT;
    }

    public void setSpawnCancelled(boolean cancel) {
    }

    public static boolean isMissingClient() {
        if (MyLib2.isServerThread()) {
            return false;
        } else if (!gIsAfter1stPig2Client) {
            return false;
        } else {
            boolean alive = false;
            ClientLevel level = Minecraft.getInstance().level;
            if (level == null) {
                return false;
            } else {
                Integer[] var2 = getAliveServerPigIDs(level).toArray(new Integer[0]);
                int var3 = var2.length;

                for (int var4 = 0; var4 < var3; var4++) {
                    int pig2ID = var2[var4];
                    if (level.getEntity(pig2ID) instanceof Pig2 pig2) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    public boolean shouldRender(double pX, double pY, double pZ) {
        return true;
    }

    public boolean shouldRenderAtSqrDistance(double pDistance) {
        return true;
    }

    private static enum ATTACK_LEVEL {
        L0,
        L1,
        L2,
        L3,
        L4;
    }

    private static enum MAX_FIGHT {
        OFF,
        WAITING,
        ON;
    }
}
