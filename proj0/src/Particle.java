import edu.princeton.cs.algs4.StdRandom;

import java.awt.*;
import java.util.Map;

public class Particle {
    public ParticleFlavor flavor;
    public int lifespan;

    public static final int PLANT_LIFESPAN = 150;
    public static final int FLOWER_LIFESPAN = 75;
    public static final int FIRE_LIFESPAN = 10;
    public static final Map<ParticleFlavor, Integer> LIFESPANS =
            Map.of(ParticleFlavor.FLOWER, FLOWER_LIFESPAN,
                   ParticleFlavor.PLANT, PLANT_LIFESPAN,
                   ParticleFlavor.FIRE, FIRE_LIFESPAN);

    public Particle(ParticleFlavor flavor) {
        this.flavor = flavor;
        if (flavor == ParticleFlavor.FLOWER || flavor == ParticleFlavor.PLANT || flavor == ParticleFlavor.FIRE) {
            lifespan = LIFESPANS.get(flavor);
        } else {
            lifespan = -1;
        }
    }

    public Color color() {
        if (flavor == ParticleFlavor.EMPTY) {
            return Color.BLACK;
        }
        if (flavor == ParticleFlavor.SAND) {
            return Color.YELLOW;
        }
        if (flavor == ParticleFlavor.BARRIER) {
            return Color.GRAY;
        }
        if (flavor == ParticleFlavor.WATER) {
            return Color.BLUE;
        }
        if (flavor == ParticleFlavor.FOUNTAIN) {
            return Color.CYAN;
        }
        if (flavor == ParticleFlavor.FLOWER) {
            double ratio = (double) Math.max(0, Math.min(lifespan, FLOWER_LIFESPAN)) / FLOWER_LIFESPAN;
            int r = 120 + (int) Math.round((255 - 120) * ratio);
            int g = 70 + (int) Math.round((141 - 70) * ratio);
            int b = 80 + (int) Math.round((161 - 80) * ratio);
            return new Color(r, g, b);
        }
        if (flavor == ParticleFlavor.PLANT) {
            double ratio = (double) Math.max(0, Math.min(lifespan, PLANT_LIFESPAN)) / PLANT_LIFESPAN;
            int g = 120 + (int) Math.round((255 - 120) * ratio);
            return new Color(0, g, 0);
        }
        if (flavor == ParticleFlavor.FIRE) {
            double ratio = (double) Math.max(0, Math.min(lifespan, FIRE_LIFESPAN)) / FIRE_LIFESPAN;
            int r = (int) Math.round(255 * ratio);
            return new Color(r, 0, 0);
        } else {
            return Color.BLACK;
        }
    }

    public void moveInto(Particle other) {
        other.lifespan = this.lifespan;
        other.flavor = this.flavor;
        this.flavor = ParticleFlavor.EMPTY;
        this.lifespan = -1;
    }

    public void fall(Map<Direction, Particle> neighbors) {
        Particle Below = neighbors.get(Direction.DOWN);
        if (Below.flavor == ParticleFlavor.EMPTY) {
            this.moveInto(Below);
        }
    }

    public void flow(Map<Direction, Particle> neighbors) {
        int behavior = StdRandom.uniformInt(3);
        if (behavior == 0) {
            Particle Left = neighbors.get(Direction.LEFT);
            if (Left.flavor == ParticleFlavor.EMPTY) {
                this.moveInto(Left);
            }
        } else if (behavior == 1) {
            Particle Right = neighbors.get(Direction.RIGHT);
            if (Right.flavor == ParticleFlavor.EMPTY) {
                this.moveInto(Right);
            }
        } else {
            return;
        }
    }

    public void grow(Map<Direction, Particle> neighbors) {
        int behavior = StdRandom.uniformInt(10);

        if (behavior == 0) {
            Particle Left = neighbors.get(Direction.LEFT);
            if (Left.flavor == ParticleFlavor.EMPTY) {
                Left.lifespan = LIFESPANS.get(this.flavor);
                Left.flavor = this.flavor;
            }
        } else if (behavior == 1) {
            Particle Right = neighbors.get(Direction.RIGHT);
            if (Right.flavor == ParticleFlavor.EMPTY) {
                Right.lifespan = LIFESPANS.get(this.flavor);
                Right.flavor = this.flavor;
            }
        } else if (behavior == 2) {
            Particle Up = neighbors.get(Direction.UP);
            if (Up.flavor == ParticleFlavor.EMPTY) {
                Up.lifespan = LIFESPANS.get(this.flavor);
                Up.flavor = this.flavor;
            }
        } else {
            return;
        }
    }

    public void burn(Map<Direction, Particle> neighbors) {
        for (Particle particle : neighbors.values()) {
            int behavior = StdRandom.uniformInt(5);
            if (behavior < 2) {
                if (particle.flavor == ParticleFlavor.FLOWER || particle.flavor == ParticleFlavor.PLANT) {
                    particle.flavor = ParticleFlavor.FIRE;
                    particle.lifespan = FIRE_LIFESPAN;
                }
            }
        }
    }

    public void action(Map<Direction, Particle> neighbors) {
        if (this.flavor == ParticleFlavor.EMPTY) {
            return;
        }
        if (this.flavor != ParticleFlavor.BARRIER) {
            this.fall(neighbors);
        }
        if (this.flavor == ParticleFlavor.WATER) {
            this.flow(neighbors);
        }
        if (this.flavor == ParticleFlavor.PLANT ||  this.flavor == ParticleFlavor.FLOWER) {
            this.grow(neighbors);
        }
        if (this.flavor == ParticleFlavor.FIRE) {
            this.burn(neighbors);
        }
    }

    public void decrementLifespan(){
        if (this.lifespan > 0) {
            this.lifespan--;
        }
        if (this.lifespan == 0) {
            this.flavor = ParticleFlavor.EMPTY;
            this.lifespan = -1;
        }
    }
}