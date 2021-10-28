/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Character
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;

public class RecipesWeapons {
    private String[][] recipePatterns = new String[][]{{"X", "X", "#"}};
    private Object[][] recipeItems = new Object[][]{{Blocks.planks, Blocks.cobblestone, Items.iron_ingot, Items.diamond, Items.gold_ingot}, {Items.wooden_sword, Items.stone_sword, Items.iron_sword, Items.diamond_sword, Items.golden_sword}};

    public void addRecipes(CraftingManager p_77583_1_) {
        for (int i = 0; i < this.recipeItems[0].length; ++i) {
            Object object = this.recipeItems[0][i];
            for (int j = 0; j < this.recipeItems.length - 1; ++j) {
                Item item = (Item)this.recipeItems[j + 1][i];
                p_77583_1_.addRecipe(new ItemStack(item), new Object[]{this.recipePatterns[j], Character.valueOf((char)'#'), Items.stick, Character.valueOf((char)'X'), object});
            }
        }
        p_77583_1_.addRecipe(new ItemStack(Items.bow, 1), new Object[]{" #X", "# X", " #X", Character.valueOf((char)'X'), Items.string, Character.valueOf((char)'#'), Items.stick});
        p_77583_1_.addRecipe(new ItemStack(Items.arrow, 4), new Object[]{"X", "#", "Y", Character.valueOf((char)'Y'), Items.feather, Character.valueOf((char)'X'), Items.flint, Character.valueOf((char)'#'), Items.stick});
    }
}

