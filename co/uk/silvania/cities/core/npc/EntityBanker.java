package co.uk.silvania.cities.core.npc;

import co.uk.silvania.cities.core.CoreItems;
import co.uk.silvania.cities.core.FlenixCities_Core;
import co.uk.silvania.cities.core.NBTConfig;
import co.uk.silvania.cities.core.npc.ai.NPCAITempt;
import co.uk.silvania.cities.core.npc.spawner.NPCSpawnerEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;

public class EntityBanker extends EntityAnimal {
	
	boolean wander;
	
	public EntityBanker(World par1World) {
		super(par1World);
		this.getNavigator().setAvoidsWater(true);
		this.setSize(0.6F, 1.8F);
        this.isImmuneToFire = false;
		float var2 = 0.25F;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new NPCAITempt(this, 1.1D, CoreItems.note10000.itemID, false));
        this.tasks.addTask(2, new NPCAITempt(this, 1.0D, CoreItems.note5000.itemID, false));
        this.tasks.addTask(3, new NPCAITempt(this, 0.9D, CoreItems.note2000.itemID, false));
        this.tasks.addTask(4, new NPCAITempt(this, 0.8D, CoreItems.note1000.itemID, false));
        this.tasks.addTask(5, new EntityAIPanic(this, 0.38F));
        this.tasks.addTask(6, new EntityAIMate(this, var2));
        this.tasks.addTask(7, new EntityAIFollowParent(this, 0.28F));
        this.tasks.addTask(8, new EntityAIWander(this, var2));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(10, new EntityAILookIdle(this));
	}
	
	@Override
	public boolean interact(EntityPlayer player) {
		//Right now this is just here so I can right-click the mob and printline what his NBT is as an in-game check.
		if (!player.inventory.hasItem(CoreItems.debitCardNew.itemID)) {
			player.inventory.addItemStackToInventory(new ItemStack(CoreItems.debitCardNew));
		}
		NBTTagCompound nbt = new NBTTagCompound();
		readEntityFromNBT(nbt);
		return true;
	}

	public boolean onInteractFirst(EntityPlayer player) {
		System.out.println("Interacting First!");
		player.openGui(FlenixCities_Core.instance, 0, worldObj, 0, 0, 0);
		return true;
	}
	
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		boolean wander = nbt.getBoolean("wander");
		boolean locked = nbt.getBoolean("entityLocked");
		boolean invincible = nbt.getBoolean("entityInvincible");
		String playerName = nbt.getString("playerName");
		int despawnTime = nbt.getInteger("despawnTime");
		int heldID = nbt.getInteger("heldID");
		int helmetID = nbt.getInteger("helmetID");
		int chestID = nbt.getInteger("chestID");
		int legsID = nbt.getInteger("legsID");
		int bootsID = nbt.getInteger("bootsID");
		System.out.println("NBT values are set as follows:");
		System.out.println("Wander: " + wander);
		System.out.println("Locked: " + locked);
		System.out.println("Invincible: " + invincible);
		System.out.println("Player Name: " + playerName);
		System.out.println("Despawn Time: " + despawnTime);
		System.out.println("Held ID: " + heldID);
		System.out.println("Helmet ID: " + helmetID);
		System.out.println("Chest ID: " + chestID);
		System.out.println("Legs ID: " + legsID);
		System.out.println("Boots ID: " + bootsID);
	}
	
	public boolean isAIEnabled() {
		return true;
	}
	
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue(); 
	}
	
	protected String getLivingSound() {
		return "mob.glog.say";
	}
	
	protected String getHurtSound() {
		return "mob.glog.say";
	}
	
	protected String getDeathSound() {
		return "mob.glog.death";
	}
	
	protected void playStepSound(int par1, int par2, int par3, int par4) {
		this.worldObj.playSoundAtEntity(this, "mob.glog.step", 0.15F,  1.0F);
	}
	
	protected int getDropItemId() {
		return CoreItems.note1000.itemID;
	}
	
	public EntityAgeable createChild(EntityAgeable var1) {
		return null;
	}
}