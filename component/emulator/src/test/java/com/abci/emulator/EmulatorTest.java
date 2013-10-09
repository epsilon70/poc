package com.abci.emulator;

import junit.framework.Assert;
import org.junit.Test;

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
    public void validInstructionShouldHaveTheSameChecksum() {
        byte actualChecksum = Emulator.calculateChecksum(validInstruction);
        Assert.assertEquals(validInstruction[5], actualChecksum);
    }

    @Test
    public void invalidInstructionShouldHaveTheDifferenceChecksum() {
        byte actualChecksum = Emulator.calculateChecksum(invalidInstruction);
        Assert.assertFalse("The checksum should be wrong.", invalidInstruction[5] == actualChecksum);
    }
}
