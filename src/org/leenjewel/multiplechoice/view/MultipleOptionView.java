/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.multiplechoice.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import org.leenjewel.multiplechoice.model.imodel.IOption;
import org.leenjewel.multiplechoice.view.iview.IOptionView;
import org.leenjewel.multiplechoice.view.iview.IQuestionView;
import org.leenjewel.multiplechoice.view.iview.ITopicView;

/**
 *
 * @author leenjewel
 */
public class MultipleOptionView extends JCheckBox implements IOptionView{

    private IQuestionView questionView = null;

    private ITopicView topicView = null;

    private IOption optionModel = null;

    public MultipleOptionView(IQuestionView questionView, ITopicView topicView, IOption optionModel) {
        super();
        this.questionView = questionView;
        this.topicView = topicView;
        this.optionModel = optionModel;
    }

    private void initComponents() {
        setText(optionModel.getOption() + "." + optionModel.getContent());

        this.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                questionView.startDoQuestion();
            }
        });
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
