package deco2800.arcade.wl6;

/**
 * describes a block in a wolfenstein map
 * @author Simon
 *
 */
public class BlockInfo {


    public BlockInfo() {
    }


    public BlockInfo(boolean s, String texture) {
        this.solid = s;
        this.texture = texture;
    }


    @Override
    public BlockInfo clone() {
        BlockInfo b = new BlockInfo();
        b.solid = this.solid;
        b.texture = this.texture;
        return b;
    }

    public boolean solid;
    public String texture;
}

