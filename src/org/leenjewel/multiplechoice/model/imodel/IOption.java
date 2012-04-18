/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.multiplechoice.model.imodel;

import java.util.Comparator;

/**
 *
 * @author leenjewel
 */
public interface IOption extends Comparator {

    public String getContent();

    public void setContent(String content);

    public String getOption();

    public void setOption(String option);

    public boolean isSeleted();

    public void setSelected(boolean selected);

}
