package deco2800.arcade.model;

/**
 * Class for customisable Library UI
 * @author Aaron Hayes
 */
public class LibraryStyle {


    public static final int LIST_VIEW = 0;
    public static final int GRID_VIEW = 1;

    public static final int LIGHT_PURPLE = 0;

    private int layout;
    private int colour;

    public LibraryStyle() {
        layout = LIST_VIEW; // Default view
        colour = LIGHT_PURPLE; // Default color
    }

    public void setLayout(int style) {
        if (style == LIST_VIEW || style == GRID_VIEW) {
            layout = style;
        }
    }

    public int getLayout() {
        return layout;
    }

    public void setColourScheme(int newColour) {
        if (newColour == LIGHT_PURPLE) {
            colour = newColour;
        }
    }
    public int getColourScheme() {
        return colour;
    }
}
