/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.multiplechoice.model;

import org.leenjewel.multiplechoice.model.imodel.IOption;

/**
 *
 * @author leenjewel
 */
public class Option implements IOption{

    private String content = null;

    private String option = null;

    private boolean selected = false;

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getOption() {
        return option;
    }

    @Override
    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public boolean isSeleted() {
        return selected;
    }

    @Override
    public int compare(Object t, Object t1) {
        String option = ((IOption)t).getOption();
        if (option.equals(t1)) {
            return 1;
        }
        return 0;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
