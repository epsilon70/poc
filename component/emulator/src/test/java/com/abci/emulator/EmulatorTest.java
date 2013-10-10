package com.abci.emulator;

import junit.framework.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: mengwang
 * Date: 09/10/2013
 * Time: 21:36
 * To change this template use File | Settings | File Templates.
 */
public class EmulatorTest {
    private static final byte[] validInstruction = {(byte) 0x7E, (byte) 0x01, (byte) 0xFF, (byte) 0x01, (byte) 0x02, (byte) 0xFD, (byte) 0x0D};
    private static final byte[] invalidInstruction = {(byte) 0x7E, (byte) 0x01, (byte) 0xFF, (byte) 0x01, (byte) 0x02, (byte) 0xFE, (byte) 0x0D};

    @Test
    public void validChecksumShouldReturnTrue() {
        Assert.assertTrue("It is an valid checksum.", Emulator.validateInstruction(validInstruction));
    }

    @Test
    public void invalidChecksumShouldReturnFalse() {
        Assert.assertFalse("It is an invalid checksum.", Emulator.validateInstruction(invalidInstruction));
    }

    @Test
    public void validInstructionShouldHaveTheSameChecksum() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method calculateChecksum = Emulator.class.getDeclaredMethod("calculateChecksum", byte[].class);
        calculateChecksum.setAccessible(true);
        byte actualChecksum = (Byte)calculateChecksum.invoke(null, validInstruction);
        Assert.assertEquals(validInstruction[5], actualChecksum);
    }

    @Test
    public void invalidInstructionShouldHaveTheDifferenceChecksum() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method calculateChecksum = Emulator.class.getDeclaredMethod("calculateChecksum", byte[].class);
        calculateChecksum.setAccessible(true);
        byte actualChecksum = (Byte)calculateChecksum.invoke(null, invalidInstruction);
        Assert.assertFalse("The checksum should be wrong.", invalidInstruction[5] == actualChecksum);
    }

    @Test
    public void checkBOARDEnum(){
        Assert.assertEquals(4, Emulator.BOARD.values().length);
        Assert.assertEquals((byte) 0x01, Emulator.BOARD.BOARD_ONE.getIndex());
        Assert.assertEquals((byte) 0x02, Emulator.BOARD.BOARD_TWO.getIndex());
        Assert.assertEquals((byte) 0x03, Emulator.BOARD.BOARD_THREE.getIndex());
        Assert.assertEquals((byte) 0x04, Emulator.BOARD.BOARD_FOUR.getIndex());
    }

    @Test
    public void checkCOMMANDEnum(){
        Assert.assertEquals(15, Emulator.COMMAND.values().length);
        Assert.assertEquals((byte) 0xFF, Emulator.COMMAND.START.getCommand());
        Assert.assertEquals((byte) 0x0F, Emulator.COMMAND.STOP.getCommand());
        Assert.assertEquals((byte) 0xF0, Emulator.COMMAND.READING_DATA.getCommand());
        Assert.assertEquals((byte) 0x00, Emulator.COMMAND.RECEIVED_DATA.getCommand());
        Assert.assertEquals((byte) 0xAA, Emulator.COMMAND.RECEIVED_DATA_AND_READING_NEXT_ONE.getCommand());
        Assert.assertEquals((byte) 0xC0, Emulator.COMMAND.HEATING.getCommand());
        Assert.assertEquals((byte) 0xC2, Emulator.COMMAND.COOLING.getCommand());
        Assert.assertEquals((byte) 0xC5, Emulator.COMMAND.READING_DETECTOR_TEMPERATURE.getCommand());
        Assert.assertEquals((byte) 0xC6, Emulator.COMMAND.HEADING_END.getCommand());
        Assert.assertEquals((byte) 0xC8, Emulator.COMMAND.READING_TEMPERATURE.getCommand());
        Assert.assertEquals((byte) 0xD0, Emulator.COMMAND.SET_VOLTAGE.getCommand());
        Assert.assertEquals((byte) 0xD8, Emulator.COMMAND.READING_VOLTAGE.getCommand());
        Assert.assertEquals((byte) 0xDA, Emulator.COMMAND.UP_VOLTAGE.getCommand());
        Assert.assertEquals((byte) 0xDB, Emulator.COMMAND.DOWN_VOLTAGE.getCommand());
        Assert.assertEquals((byte) 0xDC, Emulator.COMMAND.RESET_VOLTAGE.getCommand());
    }
}
