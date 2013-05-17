/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.multiplechoice.model.imodel;

import java.awt.Component;
import java.util.ArrayList;

/**
 *
 * @author leenjewel
 */
public interface ITopic {

    public String getContent();

    public void setContent(String content);

    public String getId();

    public void setId(String index);

    public ArrayList<IOption> getOptions();

    public void setOptions(ArrayList<IOption> options);

    public void setOptions(IOption[] options);

    public void addOption(IOption option);

    public boolean isMultipleTopic();

    public void setMultipleTopic(boolean isMultiple);
    
    public void setAnswer(String answer);
    
    public String getAnswer();

}
