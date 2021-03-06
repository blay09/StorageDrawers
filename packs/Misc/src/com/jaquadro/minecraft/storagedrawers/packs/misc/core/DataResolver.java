package com.jaquadro.minecraft.storagedrawers.packs.misc.core;

import com.jaquadro.minecraft.storagedrawers.api.pack.StandardDataResolver;
import net.minecraft.creativetab.CreativeTabs;

public class DataResolver extends StandardDataResolver
{
    public static String[][] textureBank = new String[][] {
        new String[] { "ebxl_redwood", "ebxl_fir", "ebxl_acacia", "ebxl_cypress", "ebxl_japanesemaple", "ebxl_rainboweucalyptus", "ebxl_autumn", "ebxl_baldcypress", "ebxl_sakura" },
        new String[] { "hl_yellow", "hl_white", "hl_red", "hl_grey", "tc_greatwood", "tc_silverwood", "witchery_rowan", "witchery_alder", "witchery_hawthorn", }
    };

    public static int[][] modBankMap = new int[][] {
        new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        new int[] { 1, 1, 1, 1, 2, 2, 3, 3, 3 }
    };

    public static int[][] modMetaMap = new int[][] {
        new int [] { 0, 1, 2, 3, 4, 5, 6, 7, 8 },
        new int [] { 0, 1, 2, 3, 6, 7, 0, 1, 2 }
    };

    public static int[][] modSlabMetaMap = new int[][] {
        new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 },
        new int[] { 0, 1, 2, 3, 0, 1, 0, 1, 2 }
    };

    public DataResolver (String modID, CreativeTabs creativeTab, int bankNumber) {
        super(modID, textureBank[bankNumber], creativeTab);
    }
}
