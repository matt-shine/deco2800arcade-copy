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

    /**
     * Basic Constructor
     */
    public LibraryStyle() {
        layout = LIST_VIEW; // Default view
        colour = LIGHT_PURPLE; // Default color
    }

    /**
     * Set the layout
     * @param style New Layout Style
     */
    public void setLayout(int style) {
        if (style == LIST_VIEW || style == GRID_VIEW) {
            layout = style;
        }
    }

    /**
     * Get the current layout style
     * @return this.layout
     */
    public int getLayout() {
        return layout;
    }

    /**
     * Set a new colour Scheme
     * @param newColour Color Scheme
     */
    public void setColourScheme(int newColour) {
        if (newColour == LIGHT_PURPLE) {
            colour = newColour;
        }
    }

    /**
     * Get the current colour scheme
     * @return this.colour
     */
    public int getColourScheme() {
        return colour;
    }
}
