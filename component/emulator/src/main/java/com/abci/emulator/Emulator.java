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

    public static byte calculateChecksum(byte[] instruction){
        byte checksum = instruction[1];
        for(int i = 2; i<instruction.length-2; i ++){
            checksum = (byte) (checksum ^ instruction[i]);
        }
        return checksum;
    }

    private static enum BOARD {
        BOARD_ONE((byte) 0x01),
        BOARD_TWO((byte) 0x02),
        BOARD_THREE((byte) 0x03),
        BOARD_FOUR((byte) 0x04);

        private final byte index;

        BOARD(byte index) {
            this.index = index;
        }
    }

    private static enum COMMAND {
        START((byte) 0xFF),
        stop((byte) 0x0F),
        reading_data((byte) 0xF0),
        received_data((byte) 0x00),
        received_data_and_reading_next_one((byte) 0xAA),
        heating((byte) 0xC0),
        cooling((byte) 0xC2),
        reading_detector_temperature((byte) 0xC5),
        heading_end((byte) 0xC6),
        reading_temperature((byte) 0xC8),
        set_voltage((byte) 0xD0),
        reading_voltage((byte) 0xD8),
        up_voltage((byte) 0xDA),
        down_voltage((byte) 0xDB),
        reset_voltage((byte) 0xDC);

        private final byte command;

        COMMAND(byte value) {
            this.command = value;
        }
    }
}
