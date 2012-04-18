/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.multiplechoice.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author leenjewel
 */
public class MultipleChoiceUtil {

    static public void centerComponent(Component c) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension scmSize = toolkit.getScreenSize();
        Dimension size = c.getSize();
        int width = size.width;
        int height = size.height;
        c.setLocation(scmSize.width / 2 - (width / 2), scmSize.height / 2 - (height / 2));
    }
}
