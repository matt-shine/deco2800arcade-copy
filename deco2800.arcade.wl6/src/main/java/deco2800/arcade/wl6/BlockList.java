package deco2800.arcade.wl6;

public class BlockList {

    public BlockList() {
    }

    public BlockInfo[] getBlocks() {
        return blocks.clone();
    }

    private static BlockInfo[] blocks =
            new BlockInfo[] {
                    //0: nothing
                    new BlockInfo(),

                    //1: grey brick wall
                    new BlockInfo(true, "greybrick"),

                    //2: grey brick wall 2
                    new BlockInfo(true, "greybrick"),

                    //3: grey brick wall with nazi flag
                    new BlockInfo(true, "greybrick"),

                    //4: grey brick wall with hitler portrait
                    new BlockInfo(true, "greybrick"),

                    //5: prison cell (blue brick)
                    new BlockInfo(true, "cell"),

                    //6: grey brick with nazi eagle
                    new BlockInfo(true, "greybrick"),

                    //7: prison cell with skeleton (blue brick)
                    new BlockInfo(true, "cell"),

                    //8: blue brick
                    new BlockInfo(true, "icewall"),

                    //9: blue brick 2
                    new BlockInfo(true, "icewall"),

                    //10: wood wall with nazi eagle
                    new BlockInfo(true, "frame_pb"),

                    //11: wood wall with portrait of hitler
                    new BlockInfo(true, "frame_lsp"),

                    //12: wood wall
                    new BlockInfo(true, "woodwall"),

                    //13: entrance elevator
                    new BlockInfo(true, "entranceelevator"),

                    //14: steel wall with sign
                    new BlockInfo(true, "steelsign"),

                    //15: steel wall
                    new BlockInfo(true, "steel"),

                    //16: starysky
                    new BlockInfo(true, "starysky"),

                    //17: red brick
                    new BlockInfo(true, "candywall"),

                    //18: red brick with swastika
                    new BlockInfo(true, "frame_erp"),

                    //19: purple
                    new BlockInfo(true, "purple"),

                    //20: red brick with flag
                    new BlockInfo(true, "frame_bp"),

                    //21: elevator
                    new BlockInfo(true, "elevator"),

                    //22: fake elevator
                    new BlockInfo(true, "elevatorfake"),

                    //23: wood with iron cross
                    new BlockInfo(true, "frame_wp"),

                    //24: dirty brick 1
                    new BlockInfo(true, "greybrick"),

                    //25: purple with blood
                    new BlockInfo(true, "purpleblood"),

                    //26: dirty brick 2
                    new BlockInfo(true, "greybrick"),

                    //27: grey brick 3
                    new BlockInfo(true, "greybrick"),

                    //28: grey brick with sign
                    new BlockInfo(true, "greybricksign"),

                    //29: nothing
                    new BlockInfo(),

                    //30: nothing
                    new BlockInfo(),

                    //31: nothing
                    new BlockInfo(),

                    //32: nothing
                    new BlockInfo(),

                    //33: nothing
                    new BlockInfo(),

                    //34: nothing
                    new BlockInfo(),

                    //35: nothing
                    new BlockInfo(),

                    //36: nothing
                    new BlockInfo(),

                    //37: nothing
                    new BlockInfo(),

                    //38: nothing
                    new BlockInfo(),

                    //39: nothing
                    new BlockInfo(),

                    //40: nothing
                    new BlockInfo(),

                    //41: nothing
                    new BlockInfo(),

                    //42: nothing
                    new BlockInfo(),

                    //43: nothing
                    new BlockInfo(),

                    //44: nothing
                    new BlockInfo(),

                    //45: nothing
                    new BlockInfo(),

                    //46: nothing
                    new BlockInfo(),

                    //47: nothing
                    new BlockInfo(),

                    //48: nothing
                    new BlockInfo(),

                    //49: nothing
                    new BlockInfo(),

                    //50: fake door
                    new BlockInfo(true, "doorfake"),

                    //51: side of door
                    new BlockInfo(),

                    //53: fake locked door
                    new BlockInfo(true, "doorlockedfake"),

                    //54: nothing
                    new BlockInfo(),

                    //55: nothing
                    new BlockInfo(),

                    //56: nothing
                    new BlockInfo(),

                    //57: nothing
                    new BlockInfo(),

                    //58: nothing
                    new BlockInfo(),

                    //59: nothing
                    new BlockInfo(),

                    //60: nothing
                    new BlockInfo(),

                    //61: nothing
                    new BlockInfo(),

                    //62: nothing
                    new BlockInfo(),

                    //63: nothing
                    new BlockInfo(),

                    //64: nothing
                    new BlockInfo(),

                    //65: nothing
                    new BlockInfo(),

                    //66: nothing
                    new BlockInfo(),

                    //67: nothing
                    new BlockInfo(),

                    //68: nothing
                    new BlockInfo(),

                    //69: nothing
                    new BlockInfo(),

                    //70: nothing
                    new BlockInfo(),

                    //71: nothing
                    new BlockInfo(),

                    //72: nothing
                    new BlockInfo(),

                    //73: nothing
                    new BlockInfo(),

                    //74: nothing
                    new BlockInfo(),

                    //75: nothing
                    new BlockInfo(),

                    //76: nothing
                    new BlockInfo(),

                    //77: nothing
                    new BlockInfo(),

                    //78: nothing
                    new BlockInfo(),

                    //79: nothing
                    new BlockInfo(),

                    //80: nothing
                    new BlockInfo(),

                    //81: nothing
                    new BlockInfo(),

                    //82: nothing
                    new BlockInfo(),

                    //83: nothing
                    new BlockInfo(),

                    //84: nothing
                    new BlockInfo(),

                    //85: elevator wall
                    new BlockInfo(true, "elevatorwall"),

                    //86: nothing
                    new BlockInfo(),

                    //87: nothing
                    new BlockInfo(),

                    //88: nothing
                    new BlockInfo(),

                    //89: nothing
                    new BlockInfo(),

                    //90: door vertical
                    new BlockInfo(),

                    //91: door horizontal
                    new BlockInfo(),

                    //92: door vertical (gold key)
                    new BlockInfo(),

                    //93: door horizontal (gold key)
                    new BlockInfo(),

                    //94: door vertical (silver key)
                    new BlockInfo(),

                    //95: door horizontal (silver key)
                    new BlockInfo(),

                    //96: nothing
                    new BlockInfo(),

                    //97: nothing
                    new BlockInfo(),

                    //98: nothing
                    new BlockInfo(),

                    //99: nothing
                    new BlockInfo(),

                    //100: elevator door (normal)
                    new BlockInfo(),

                    //101: elevator door (horizontal)
                    new BlockInfo(),

                    //106: floor (deaf guard)
                    new BlockInfo(),

                    //107: elevator to secret floor
                    new BlockInfo(true, "secretelevator"),

                    //108 Floor 6C
                    new BlockInfo(),

                    //109 Floor 6D
                    new BlockInfo(),

                    //110 Floor 6E
                    new BlockInfo(),

                    //111 Floor 6F
                    new BlockInfo(),

                    //112 Floor 70
                    new BlockInfo(),

                    //113 Floor 71
                    new BlockInfo(),

                    //114 Floor 72
                    new BlockInfo(),

                    //115 Floor 73
                    new BlockInfo(),

                    //116 Floor 74
                    new BlockInfo(),

                    //117 Floor 75
                    new BlockInfo(),

                    //118 Floor 76
                    new BlockInfo(),

                    //119 Floor 77
                    new BlockInfo(),

                    //120 Floor 78
                    new BlockInfo(),

                    //121 Floor 79
                    new BlockInfo(),

                    //122 Floor 7A
                    new BlockInfo(),

                    //123 Floor 7B
                    new BlockInfo(),

                    //124 Floor 7C
                    new BlockInfo(),

                    //125 Floor 7D
                    new BlockInfo(),

                    //126 Floor 7E
                    new BlockInfo(),

                    //127 Floor 7F
                    new BlockInfo(),

                    //128 Floor 80
                    new BlockInfo(),

                    //129 Floor 81
                    new BlockInfo(),

                    //130 Floor 82
                    new BlockInfo(),

                    //131 Floor 83
                    new BlockInfo(),

                    //132 Floor 84
                    new BlockInfo(),

                    //133 Floor 85
                    new BlockInfo(),

                    //134 Floor 86
                    new BlockInfo(),

                    //135 Floor 87
                    new BlockInfo(),

                    //136 Floor 88
                    new BlockInfo(),

                    //137 Floor 89
                    new BlockInfo(),

                    //138 Floor 8A
                    new BlockInfo(),

                    //139 Floor 8B
                    new BlockInfo(),

                    //140 Floor 8C
                    new BlockInfo(),

                    //141 Floor 8D
                    new BlockInfo(),

                    //142 Floor 8E
                    new BlockInfo(),

                    //143 Floor 8F
                    new BlockInfo(),
            };
}
