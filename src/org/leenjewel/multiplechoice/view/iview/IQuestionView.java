/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.multiplechoice.view.iview;

import java.awt.Frame;
import java.util.ArrayList;
import org.leenjewel.multiplechoice.model.imodel.IQuestion;

/**
 *
 * @author leenjewel
 */
public interface IQuestionView extends IView {

    public IQuestion getQuestionModel();

    public ArrayList<ITopicView> getTopicViews();

    public Frame getAppFrame();

}
