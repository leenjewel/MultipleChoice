/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.multiplechoice.view;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JCheckBox;
import org.leenjewel.multiplechoice.model.imodel.IOption;
import org.leenjewel.multiplechoice.view.iview.IOptionView;
import org.leenjewel.multiplechoice.view.iview.ITopicView;

/**
 *
 * @author leenjewel
 */
public class MultipleOptionView extends JCheckBox implements IOptionView{

    private ITopicView topicView = null;

    private IOption optionModel = null;

    public MultipleOptionView(ITopicView topicView, IOption optionModel) {
        super();
        this.topicView = topicView;
        this.optionModel = optionModel;
    }

    private void initComponents() {
        setText(optionModel.getOption() + "." + optionModel.getContent());
    }

    @Override
    public void unSelected() {
        this.setSelected(false);
    }

    @Override
    public void Selected() {
        this.setSelected(true);
    }


    @Override
    public IOption getOptionModel() {
        return optionModel;
    }

    @Override
    public Component getComponent() {
        return this;
    }

}
