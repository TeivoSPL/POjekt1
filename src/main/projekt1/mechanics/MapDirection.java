package main.projekt1.mechanics;

public enum MapDirection {
    NORTH,SOUTH,EAST,WEST,NORTHEAST,SOUTHEAST,NORTHWEST,SOUTHWEST;

    public String toString(){
        switch (this) {
            case NORTH:
                return "N";
            case SOUTH:
                return "S";
            case EAST:
                return "E";
            case WEST:
                return "W";
            case NORTHEAST:
                return "NE";
            case SOUTHEAST:
                return "SE";
            case NORTHWEST:
                return "NW";
            case SOUTHWEST:
                return "SW";
        }
        return null;
    }

    public MapDirection next(){
        switch (this) {
            case NORTH:
                return NORTHEAST;
            case SOUTH:
                return SOUTHWEST;
            case EAST:
                return SOUTHEAST;
            case WEST:
                return NORTHWEST;
            case NORTHEAST:
                return EAST;
            case SOUTHEAST:
                return SOUTH;
            case NORTHWEST:
                return NORTH;
            case SOUTHWEST:
                return WEST;
        }
        return null;
    }

    public MapDirection previous(){
        switch (this) {
            case NORTH:
                return NORTHWEST;
            case SOUTH:
                return SOUTHEAST;
            case EAST:
                return SOUTHWEST;
            case WEST:
                return NORTHEAST;
            case NORTHEAST:
                return NORTH;
            case SOUTHEAST:
                return EAST;
            case NORTHWEST:
                return WEST;
            case SOUTHWEST:
                return SOUTH;
        }
        return null;
    }

    public Vector2d toUnitVector(){
        switch (this){
            case NORTH:
                return new Vector2d(0,1);
            case SOUTH:
                return new Vector2d(0,-1);
            case EAST:
                return new Vector2d(1,0);
            case WEST:
                return new Vector2d(-1,0);
            case NORTHEAST:
                return new Vector2d(1,1);
            case SOUTHEAST:
                return new Vector2d(1,-1);
            case NORTHWEST:
                return new Vector2d(-1,1);
            case SOUTHWEST:
                return new Vector2d(-1,-1);
        }
        return null;
    }
}
