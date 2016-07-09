package org.rdiazs.android.myvegs.entities;

import java.io.Serializable;
import java.util.HashMap;

/**
 * POJO que representa al cultivo.
 */
public class Veg implements Serializable {
    private String name;
    private int germination;
    private HashMap<String, Float> necessities;
    private String image;

    public static final String LIGHT_KEY = "light";
    public static final String WATER_KEY = "water";
    public static final String GROUND_KEY = "ground";

    public Veg() {
    }

    public Veg(String name,
               int germination,
               float light,
               float water,
               float ground) {
        this.name = name;
        this.germination = germination;
        this.necessities = new HashMap<>();
        this.necessities.put(LIGHT_KEY, light);
        this.necessities.put(WATER_KEY, water);
        this.necessities.put(GROUND_KEY, ground);
        this.image=null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGermination() {
        return germination;
    }

    public HashMap<String, Float> getNecessities() {
        return this.necessities;
    }

    private void setNecessities(float light, float water, float ground) {
        this.necessities = new HashMap<>();

        this.necessities.put(LIGHT_KEY, light);
        this.necessities.put(WATER_KEY, water);
        this.necessities.put(GROUND_KEY, ground);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        boolean equal = false;

        if (o instanceof Veg) {
            Veg veg = (Veg) o;
            equal = this.name.equals(veg.getName()) && this.germination == veg.getGermination();
        }

        return equal;
    }
}
