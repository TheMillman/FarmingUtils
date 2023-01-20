# FarmingUtils
A mod for Minecraft that adds new blocks to easily farm all types of crops.

# Blocks
## Farmers

**The quantity of energy that Farmers can contain, their consumption and the speed with which they work can be changed in the config, each Farmer has its own config.**
**Crop Farmer does not currently work with Immersive Engineering's Hemp Seeds, but I am working on it.**

### Crop Farmer: 
The Crop Farmer destroys and plants all sorts of crops. It has a container with 18 slots, in which it inserts the drops, and another 3 slots for Upgrades.
To make the block work, you have to place the block in the center of the area in which to make it work, give it energy, and insert the seeds of the crop to be planted in the slots of the container.
The crop farmer works in an area of 3x3, but it can be increased with an Area Upgrade.

### Nether Wart Farmer:
This block works similar to the Crop Farmer, it has a container of 9 slots and 3 slots for upgrades, works in a 3x3 area around it and the only block
that plants / destroys is the Nether Wart. Obviously he only plants them on Soul Sand.

### Cocoa Beans Farmer:
Destroys and plants up to 1x5 Cocoa Beans in front of him, it can be incrased. It has 9 slots for Cocoa Beans and 3 for upgrades. Plants Cocoa Beans only on the Jungle Log and must be a block away from Cocoa Beans.

### Melon and Pumpkin Farmer:
This block only destroys Melons and Pumpkins blocks, does not plant anything and has an area of 5x5 blocks, the area cannot be enlarged. The container
has 9 slots for drops and 2 for upgrades, Area Upgrades cannot be inserted.

### Cactus Farmer and Sugar Cane Farmer:
These two blocks do not plant anything, they destroy the blocks above them, they both have an Area of 3x3 and it is possible to enlarge it
with Area Upgrades. Both have a container of 9 slots plus 3 for upgrades and need power to function.

# Items
## Area Upgrades:
Enlarge the Farmers' work area, the area is written in the tooltips.

## Redstone Upgrade:
Require a Redstone signal for Farmers to work, it works for all Farmers.

## Drop Upgrade
The farmer stops collecting the drop and drops it into the world.

# Tags
## Block Tags:
The mod adds tags to make the mod more compatible with other mods.
The tag is called age_3_crops, just enter the block name in the Json file, as you do with normal vanilla minecraft blocks, and the Crop Farmer will recognize the block.
