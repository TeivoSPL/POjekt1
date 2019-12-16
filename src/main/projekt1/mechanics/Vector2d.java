package main.projekt1.mechanics;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean precedes(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }

    //klasa math
    public Vector2d upperRight(Vector2d other){
        int x, y;
        if( this.x >= other.x){
            x = this.x;
        }
        else{
            x = other.x;
        }
        if( this.y >= other.y){
            y = this.y;
        }
        else{
            y = other.y;
        }
        return new Vector2d(x,y);
    }

    public Vector2d lowerLeft(Vector2d other){
        int x, y;
        if( this.x <= other.y){
            x = this.x;
        }
        else{
            x = other.x;
        }
        if( this.y <= other.y){
            y = this.y;
        }
        else{
            y = other.y;
        }
        return new Vector2d(x,y);
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x,this.y+other.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x,this.y - other.y);
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return that.x == this.x && that.y == this.y;
    }

    public Vector2d opposite(){
        return new Vector2d(0-this.x,0-this.y);
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash += this.x*31;
        hash += this.y*17;
        return hash;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
