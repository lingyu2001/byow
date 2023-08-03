package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

public class WorldGenerator {

    // the weight and height of the world
    private static final int WIDTH = 80;
    private static final int HEIGHT = 80;

    public static void createNewWorld(TETile[][] tiles, Random random) {
        fillWorldWithNothing(tiles);
        Queue<Room> rooms = addRoom(tiles, random);
        connectRooms(tiles, rooms, random);
//        System.out.println(rooms.size());
        Room r = rooms.poll();
        drawRoom(tiles, r);
        addWalls(tiles);
        addAvatar(tiles);
    }

    public static void connectRooms(TETile[][] tiles, Queue<Room> rooms, Random random) {
        while (rooms.size() > 1) {
            Room r1 = rooms.poll();
            Room r2 = rooms.poll();
            boolean connected = r1.isConnect(r2);
            if (!connected) {
                Room room = addHallways(tiles, r1, r2, random);
                System.out.println("r1:" + r1.getPosition() + " r2:" + r2.getPosition() + " is connected");
                rooms.add(room);
            } else {
                r1.combineTwoRoom(r2);
                rooms.add(r1);
            }
        }
    }

    // add hallways to separate rooms
    public static Room addHallways(TETile[][] tiles, Room r1, Room r2, Random random) {
        Position p1 = r1.getPosition();
        Position p2 = r2.getPosition();
//        // p1 .... p2
//        if (p1.getX() < p2.getX()) {
//            // add horizontalHallway
//            for (int i = p1.getX(); i < p2.getX(); i++) {
//                r1.setXy(i, p1.getY());
//            }
//        } else {
//            for (int i = p2.getX(); i < p1.getX(); i++) {
//                r1.setXy(i, p2.getY());
//            }
//        }
//        if (p1.getY() < p2.getY()) {
//            for (int i = p1.getY(); i < p2.getY(); i++) {
//                r1.setXy(p1.getX(), i);
//            }
//        } else {
//            for (int i = p2.getY(); i < p1.getY(); i++) {
//                r1.setXy(p2.getX(), i);
//            }
//        }
        if (p1.getX() < p2.getX()) {
            if (p1.getY() < p2.getY()) {
                //      p2
                //p1
                for (int i = p1.getX(); i < p2.getX(); i++) {
                    r1.setXy(p1.getX(), i);
                }
            } else {
                //     p2
                //p1
                //or
                //p1   p2
            }

        } else {
            //     p1
            //p2
            if (p1.getY() > p2.getY()) {

            } else {
                // p2
                //      p1
                //or
                // p2   p1
            }
        }
        r1.combineTwoRoom(r2);
        return r1;
    }

    public static void addAvatar(TETile[][] tiles) {
    }

    //add all rooms to the world and return a queue including all rooms created in the world.
    public static Queue<Room> addRoom(TETile[][] world, Random random) {
        Queue<Room> rooms = new ArrayDeque<>();
        // the number of rooms added to in the word
        int num = random.nextInt(2, 4);
        for (int i = 0; i < num; i++) {
            // generate a random-sized room and draw the room
            Room room = Room.generateRoomWithRandomSize(random);
//            drawRoom(world, room);
            rooms.add(room);
        }
        return rooms;
    }

    private static void drawRoom(TETile[][] world, Room room) {
        boolean[][] xy = room.getXy();
        for (int i = 0; i < xy.length; i++) {
            for (int j = 0; j < xy[0].length; j++) {
                if (xy[i][j]) {
                    world[i][j] = Tileset.FLOOR;
                }
            }
        }
    }


    public static void fillWorldWithNothing(TETile[][] world) {
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    public static void addWalls(TETile[][] world) {
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                if (world[i][j] == Tileset.FLOOR) {
                    // down
                    if (j - 1 > 0 && world[i][j - 1] == Tileset.NOTHING) {
                        world[i][j - 1] = Tileset.WALL;
                    }
                    // up
                    if (j + 1 < world[0].length && world[i][j + 1] == Tileset.NOTHING) {
                        world[i][j + 1] = Tileset.WALL;
                    }
                    // left
                    if (i - 1 > 0 && world[i - 1][j] == Tileset.NOTHING) {
                        world[i - 1][j] = Tileset.WALL;
                    }
                    // right
                    if (i + 1 < world.length && world[i + 1][j] == Tileset.NOTHING) {
                        world[i + 1][j] = Tileset.WALL;
                    }
                }
            }
        }
    }

    // a demo for generating a world
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        int WIDTH = 50;
        int HEIGHT = 50;
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] tiles = new TETile[WIDTH - 1][HEIGHT - 1];
        Random random = new Random(-1);
        createNewWorld(tiles, random);
        ter.renderFrame(tiles);
    }
}
