package com.abci.emulator;

/**
 * Created with IntelliJ IDEA.
 * User: mengwang
 * Date: 09/10/2013
 * Time: 20:40
 * To change this template use File | Settings | File Templates.
 */
public class Emulator {
    final private static byte HEAD = 0x7E;
    final private static byte TAIL = 0x0D;

    public static boolean validateInstruction(byte[] instruction) {
        if (instruction.length != 7
                || instruction[0] != HEAD
                || instruction[6] != TAIL
                || (instruction[1] != BOARD.BOARD_ONE.index
                && instruction[1] != BOARD.BOARD_TWO.index
                && instruction[1] != BOARD.BOARD_THREE.index
                && instruction[1] != BOARD.BOARD_FOUR.index)){
            return false;
        }else if(calculateChecksum(instruction) != instruction[5]){
            return false;
        }
        return true;
    }

    private static byte calculateChecksum(byte[] instruction){
        byte checksum = instruction[1];
        for(int i = 2; i<instruction.length-2; i ++){
            checksum = (byte) (checksum ^ instruction[i]);
        }
        return checksum;
    }

    public static enum BOARD {
        BOARD_ONE((byte) 0x01),
        BOARD_TWO((byte) 0x02),
        BOARD_THREE((byte) 0x03),
        BOARD_FOUR((byte) 0x04);

        private final byte index;

        BOARD(byte index) {
            this.index = index;
        }

        public byte getIndex(){
            return index;
        }
    }

    public static enum COMMAND {
        START((byte) 0xFF),
        STOP((byte) 0x0F),
        READING_DATA((byte) 0xF0),
        RECEIVED_DATA((byte) 0x00),
        RECEIVED_DATA_AND_READING_NEXT_ONE((byte) 0xAA),
        HEATING((byte) 0xC0),
        COOLING((byte) 0xC2),
        READING_DETECTOR_TEMPERATURE((byte) 0xC5),
        HEADING_END((byte) 0xC6),
        READING_TEMPERATURE((byte) 0xC8),
        SET_VOLTAGE((byte) 0xD0),
        READING_VOLTAGE((byte) 0xD8),
        UP_VOLTAGE((byte) 0xDA),
        DOWN_VOLTAGE((byte) 0xDB),
        RESET_VOLTAGE((byte) 0xDC);

        private final byte command;

        COMMAND(byte value) {
            this.command = value;
        }

        public byte getCommand(){
            return this.command;
        }
    }
}
