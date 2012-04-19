/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.multiplechoice.view;

import java.awt.Component;
import java.util.ArrayList;
import org.jdesktop.layout.GroupLayout;
import org.leenjewel.multiplechoice.model.imodel.IOption;
import org.leenjewel.multiplechoice.model.imodel.ITopic;
import org.leenjewel.multiplechoice.view.iview.IOptionView;
import org.leenjewel.multiplechoice.view.iview.ITopicView;

/**
 *
 * @author leenjewel
 */
public class TopicView extends javax.swing.JPanel implements ITopicView {

    private ITopic topicModel;

    private ArrayList<IOptionView> optionViews = null;

    public TopicView(ITopic topicModel) {
        super();
        this.topicModel = topicModel;
        initComponents();
    }

    private void initComponents() {
        javax.swing.JLabel jLabelTopicId = new javax.swing.JLabel();
        jLabelTopicId.setText("第" + String.valueOf(topicModel.getId()) + "题");

        javax.swing.JLabel jLabelTopicContent = new javax.swing.JLabel();
        jLabelTopicContent.setText(topicModel.getContent());

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);

        GroupLayout.ParallelGroup pg = layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jLabelTopicId, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jLabelTopicContent, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);

        GroupLayout.SequentialGroup sg = layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabelTopicId)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabelTopicContent)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED);

        for (IOption option : topicModel.getOptions()) {
            if (topicModel.isMultipleTopic()) {
                MultipleOptionView optionView = (new MultipleOptionView(this, option));
                pg = pg.add(optionView, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
                sg.add(optionView)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED);
                getOptionViews().add(optionView);
            } else {
                OptionView optionView = (new OptionView(this, option));
                pg = pg.add(optionView, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
                sg.add(optionView)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED);
                getOptionViews().add(optionView);
            }
        }

        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(pg)
                .addContainerGap())
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sg.addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    @Override
    public ArrayList<IOptionView> getOptionViews() {
        if (optionViews == null) {
            optionViews = new ArrayList<IOptionView>();
        }
        return optionViews;
    }

    @Override
    public void unSelectedAll() {
        for (IOptionView optionView : getOptionViews()) {
            optionView.unSelected();
        }
    }

    @Override
    public ITopic getTopicModel() {
        return topicModel;
    }

    @Override
    public Component getComponent() {
        return this;
    }

}
