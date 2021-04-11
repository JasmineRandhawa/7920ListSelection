package com.example.newdesign;

import java.util.Arrays;

public class OuterList {
    public static String[] GetData()
    {
        String[] alphabets = new String[26];
        String[] cities = Cities.GetListData();
        int count = 0;
        for (int i = 0; i<cities.length;i++)
        {
            if(count==25)
            {
                break;
            }
            String firstChar = cities[i].charAt(0)+"";
            if (alphabets.length > 0) {
                if (!Arrays.asList(alphabets).contains(firstChar)) {
                    alphabets[count] = firstChar;
                    count++;
                }
            }
            else
            {
                alphabets[count] = firstChar;
                count++;
            }
        }
        return alphabets;
   }

    public static Integer[] GetItemColors() {


        Integer[] itemColors = new Integer[]{
                R.color.MediumAquamarine,
                R.color.FireBrick,
                R.color.Gold,
                R.color.DarkBlue,
                R.color.CadetBlue,
                R.color.DimGray,
                R.color.Chocolate,
                R.color.BurlyWood,
                R.color.Violet,
                R.color.Green,
                R.color.Crimson,
                R.color.CadetBlue,
                R.color.SeaGreen,
                R.color.RosyBrown,
                R.color.DarkGreen,
                R.color.DarkSalmon,
                R.color.DarkKhaki,
                R.color.DarkTurquoise,
                R.color.DarkViolet,
                R.color.OrangeRed,
                R.color.DarkCyan,
                R.color.DarkRed,
                R.color.DeepPink,
                R.color.DarkOliveGreen,
                R.color.PaleVioletRed
        };
        return itemColors;
    }



}
