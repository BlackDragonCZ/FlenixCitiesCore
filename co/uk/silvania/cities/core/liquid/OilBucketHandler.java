package co.uk.silvania.cities.core.liquid;

import co.uk.silvania.cities.core.CoreBlocks;
import co.uk.silvania.cities.core.CoreItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class OilBucketHandler {
	
	@ForgeSubscribe
	public void onBucketFill(FillBucketEvent event) {
		ItemStack result = fillCustomBucket(event.world, event.target);
		if (result == null)
			return;
		event.result = result;
		event.setResult(Result.ALLOW);
	}

	public ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
		int blockID = world.getBlockId(pos.blockX, pos.blockY, pos.blockZ);
		if ((blockID == CoreBlocks.blockOil.blockID) && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
			world.setBlock(pos.blockX, pos.blockY, pos.blockZ, 0);
			return new ItemStack(CoreItems.oilBucket);
		} else
			return null;
	}
}