package byow.Core;


import java.util.Random;

public class Room {

    private int width;
    private int height;
    private Position position;

    public boolean[][] getXy() {
        return xy;
    }

    public void setXy(int a, int b) {
        this.xy[a][b] = true;
    }

    private static final int MAX = 8;
    private static final int MIN = 1;
    private static final int MAXWIDTH = 49;
    private static final int MAXHEIGHT = 49;
    private boolean[][] xy = new boolean[MAXWIDTH][MAXHEIGHT];


    public Room(Position position) {
        this.position = position;
    }

    public Room(Position position, int width, int height) {
        // including walls
        this.width = width;
        this.height = height;
        this.position = position;
        for (int i = 0; i < width && i + position.getX() < MAXWIDTH; i++) {
            for (int j = 0; j < height && j + position.getY() < MAXHEIGHT; j++) {
                xy[position.getX() + i][position.getY() + j] = true;
            }
        }

    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    public static Room generateRoomWithRandomSize(Random random) {
        // width and height refer to the size of the world
        int x = random.nextInt(2, MAXWIDTH-10);
        int y = random.nextInt(2, MAXHEIGHT-10);
        Position position = new Position(x, y);
//        System.out.println(position);
        Room room = new Room(position, random.nextInt(2, 5), random.nextInt(4, 6));
        return room;
    }


    public boolean isConnect(Room room) {
        for (int i = 0; i < xy.length; i++) {
            for (int j = 0; j < xy[0].length; j++) {
                if (xy[i][j] && room.xy[i][j]) {
                    this.combineTwoRoom(room);
                    return true;
                }
            }
        }
        return false;
    }

    public void combineTwoRoom(Room room) {
        for (int i = 0; i < room.xy.length; i++) {
            for (int j = 0; j < room.xy[0].length; j++) {
                if (room.xy[i][j]) {
                    this.xy[i][j] = true;
                }
            }
        }
    }
}
