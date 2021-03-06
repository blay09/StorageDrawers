package com.jaquadro.minecraft.storagedrawers.client.renderer;

import com.jaquadro.minecraft.storagedrawers.block.BlockDrawers;
import com.jaquadro.minecraft.storagedrawers.util.RenderHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class DrawersItemRenderer implements IItemRenderer
{
    private RenderHelper renderHelper = new RenderHelper();
    private ModularBoxRenderer boxRenderer = new ModularBoxRenderer();
    private float[] colorScratch = new float[3];

    @Override
    public boolean handleRenderType (ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper (ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem (ItemRenderType type, ItemStack item, Object... data) {
        RenderBlocks renderer = getRenderer(data);
        if (renderer == null)
            return;

        Block block = Block.getBlockFromItem(item.getItem());
        if (!(block instanceof BlockDrawers))
            return;

        renderDrawer((BlockDrawers) block, item, renderer, type);
    }

    private void renderDrawer (BlockDrawers block, ItemStack item, RenderBlocks renderer, ItemRenderType renderType) {
        int side = 4;
        boxRenderer.setUnit(.0625);
        boxRenderer.setColor(ModularBoxRenderer.COLOR_WHITE);
        for (int i = 0; i < 6; i++)
            boxRenderer.setIcon(block.getIcon(i, item.getItemDamage()), i);

        if (renderType == ItemRenderType.INVENTORY) {
            GL11.glRotatef(90, 0, 1, 0);
            GL11.glTranslatef(-.5f, -.5f, -.5f);
        }

        switch (side - 2) {
            case 0:
                renderer.uvRotateTop = 3;
                break;
            case 1:
                renderer.uvRotateTop = 0;
                break;
            case 2:
                renderer.uvRotateTop = 1;
                break;
            case 3:
                renderer.uvRotateTop = 2;
                break;
        }

        renderExterior(block, 0, 0, 0,side, renderer);

        renderer.uvRotateTop = 0;

        boxRenderer.setUnit(0);
        boxRenderer.setInteriorIcon(block.getIcon(side, item.getItemDamage()), ForgeDirection.OPPOSITES[side]);

        renderInterior(block, 0, 0, 0, side, renderer);

        boxRenderer.setExteriorIcon(block.getTapeIcon(), side);

        if (item.hasTagCompound() && item.getTagCompound().hasKey("tile")) {
            double depth = block.halfDepth ? .5 : 1;
            RenderHelper.instance.setRenderBounds(1 - depth - .005, 0, 0, 1, 1, 1);
            RenderHelper.instance.renderFace(side, null, block, block.getTapeIcon(), 1, 1, 1);
        }

        if (renderType == ItemRenderType.INVENTORY) {
            GL11.glTranslatef(.5f, .5f, .5f);
        }
    }

    private void renderExterior (BlockDrawers block, int x, int y, int z, int side, RenderBlocks renderer) {
        double depth = block.halfDepth ? .5 : 1;
        double xMin = 0, xMax = 0, zMin = 0, zMax = 0;

        switch (side) {
            case 2:
                xMin = 0; xMax = 1;
                zMin = 1 - depth; zMax = 1;
                break;
            case 3:
                xMin = 0; xMax = 1;
                zMin = 0; zMax = depth;
                break;
            case 4:
                xMin = 1 - depth; xMax = 1;
                zMin = 0; zMax = 1;
                break;
            case 5:
                xMin = 0; xMax = depth;
                zMin = 0; zMax = 1;
                break;
        }

        boxRenderer.renderExterior(renderer, block, x, y, z, xMin, 0, zMin, xMax, 1, zMax, 0, ModularBoxRenderer.sideCut[side]);
    }

    private void renderInterior (BlockDrawers block, int x, int y, int z, int side, RenderBlocks renderer) {
        double unit = block.getTrimDepth();
        double depth = block.halfDepth ? .5 : 1;
        double xMin = 0, xMax = 0, zMin = 0, zMax = 0;

        switch (side) {
            case 2:
                xMin = unit; xMax = 1 - unit;
                zMin = 1 - depth; zMax = 1 - depth + unit;
                break;
            case 3:
                xMin = unit; xMax = 1 - unit;
                zMin = depth - unit; zMax = depth;
                break;
            case 4:
                xMin = 1 - depth; xMax = 1 - depth + unit;
                zMin = unit; zMax = 1 - unit;
                break;
            case 5:
                xMin = depth - unit; xMax = depth;
                zMin = unit; zMax = 1 - unit;
                break;
        }

        boxRenderer.renderInterior(renderer, block, x, y, z, xMin, unit, zMin, xMax, 1 - unit, zMax, 0, ModularBoxRenderer.sideCut[side]);
    }

    private RenderBlocks getRenderer (Object[] data) {
        for (Object obj : data) {
            if (obj instanceof RenderBlocks)
                return (RenderBlocks)obj;
        }

        return null;
    }
}
