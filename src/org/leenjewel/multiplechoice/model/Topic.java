/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.multiplechoice.model;

import java.util.ArrayList;
import java.util.Arrays;
import org.leenjewel.multiplechoice.model.imodel.IOption;
import org.leenjewel.multiplechoice.model.imodel.ITopic;

/**
 *
 * @author leenjewel
 */
public class Topic implements ITopic {

    private String id;

    private String content = null;

    private ArrayList<IOption> options = null;

    private boolean multiple = false;

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public ArrayList<IOption> getOptions() {
        if (options == null) {
            options = new ArrayList<IOption>();
        }
        return options;
    }

    @Override
    public void setOptions(ArrayList<IOption> options) {
        this.options = options;
    }

    @Override
    public void setOptions(IOption[] options) {
        getOptions().addAll(Arrays.asList(options));
    }

    @Override
    public void addOption(IOption option) {
        getOptions().add(option);
    }

    @Override
    public boolean isMultipleTopic() {
        return multiple;
    }

    @Override
    public void setMultipleTopic(boolean isMultiple) {
        this.multiple = isMultiple;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

}
