/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.multiplechoice.view.iview;

import org.leenjewel.multiplechoice.model.imodel.ITopic;

/**
 *
 * @author leenjewel
 */
public interface ITopicView extends IView {

    public ITopic getTopicModel();

    public void unSelectedAll();

}
