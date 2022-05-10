package com.hodor.dota2partner.util;

public class MedalUtil {

    public static String getRankIconFromRankTier(int rankTier) {

        int a = rankTier/10;

        String rankIcon = "rank_icon_"+a+".png";

        return rankIcon;
    }

    public static String getRankStarFromRankTier(int rankTier) {

        int a = rankTier%10;

        String rankStar = "rank_star_"+a+".png";

        return rankStar;
    }

}
